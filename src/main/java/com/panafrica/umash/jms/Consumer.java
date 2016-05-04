/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.jms;

import com.panafrica.umash.configuration.Appconstants;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.IprserrorsJpaController;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Iprserrors;
import com.panafrica.umash.services.SendMail;
import com.panafrica.umash.sms.SmsService;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


 


public class Consumer implements MessageListener, ExceptionListener{
    
        ClientsJpaController clientsJpaController;
        IprserrorsJpaController iprserrorsJpaController;
       SmsService smsService ;
        SendMail sendMail; 
         private static Appconstants appconst;
    private static int ackMode;
   // private static String ClientQueue;
    //private static String messageBrokerUrl="tcp://192.168.22.16:61616";
   // private static String messageBrokerUrl="tcp://localhost:61616";
    
   
 
    private Session session;
    private boolean transacted = false;
     private Connection connection;
     private ActiveMQConnectionFactory connectionFactory;
     private TextMessage txtMessage;
    
    static {
      
        ackMode = Session.AUTO_ACKNOWLEDGE;
      //  ClientQueue="IPRS_UMASH_RESPONSE";
    }
    
    
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Consumer.class);

    @Override
    public void onMessage(Message message) {
          String messageText = null;
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageText = textMessage.getText();
                System.out.println("Iprs Responded ");
               // System.out.println("Message Text is .."+messageText);
                
                JSONParser parser = new JSONParser();
                JSONObject reqObj= (JSONObject) parser.parse(messageText);
                System.out.println("Iprs Status = " + reqObj.get("iprsStatus"));           
                        
                 if ((boolean)reqObj.get("iprsStatus")==true){
                 
                      ProcessRequest(reqObj);
                 }
                 else {
                   log.error("Error From Iprs " + reqObj);
                     //impliment this later 
                           iprserrorsJpaController = new IprserrorsJpaController();
                           Iprserrors ip = new Iprserrors();
                           ip.setDocid(reqObj.get("docId").toString());
                           ip.setMessage(reqObj.get("statusDetails").toString());
                           ip.setEntrydate(new Date());
                           ip.setStatusid(1);
                           ip.setStatusname("Connection Error : Retry ");
                                      
                      
                      iprserrorsJpaController.create(ip);
                  
                 }
            }
        } catch (Exception  e) {
            e.printStackTrace();
           // System.out.println("Exception Redelivering a Message ");
           log.error(e);
        }
    }
    
    
    
    private void  ProcessRequest(JSONObject reqObj){
        //update client details 
        clientsJpaController = new ClientsJpaController();
        Clients client = new Clients();
        
        
         System.out.println("Processing Iprs Response Message ");
         client.setClientnames(reqObj.get("firstName").toString() + " " + reqObj.get("surname").toString());
         client.setPhoto(ConverteFromString(reqObj.get("photo").toString()));
          client.setStatusid(2);
         client.setStatusname("Iprs Confirmed ");
            
            
          // System.out.println("Getting id For " + reqObj.get("docId").toString());
           
           List<Clients> cls = clientsJpaController.findClients(reqObj.get("docId").toString());
          try {  
           if (cls ==null || cls.size()==0){
                 System.out.println("Client does Not Exist");
           }else{
              Iterator it = cls.iterator();
              
               while (it.hasNext()) {
                   Clients cl = (Clients)it.next();
                    //check if payment is already done
                     if (cl.getStatusid()==3){
                          client.setStatusid(3);
                          client.setStatusname("Payment Received");
                     }
                   
                        Clients client2 = new Clients();
                         System.out.println("Client rid is "+cl.getRid()) ;
                       client2= clientsJpaController.iprsedit(client,cl.getRid() );
                        if (client2.getContacts() !=null){
                          //  System.out.println("Message Processed Sending To Fund Message ");
                           smsService = new SmsService();
                           String num = client2.getContacts().toString().substring(1);
                           String inv =getInvoiceNumber(client2)+client2.getRid();
                          
                            smsService.sendSms(client2.getContacts()," Thank You "+ client2.getClientnames()+ " For Your Interest in Umash " + " Kindly Send a Premium of ( "+ client2.getPremiumpayable()+ " ) To Complete Your Transaction to Mpesa Paybill 120121 Use "+inv+" As Your Account ");
                  
                      }
               }
           }
                
            } catch (RollbackFailureException ex) {
                log.error(ex);
            } catch (Exception ex) {
                log.error(ex);
            }
         
        
    }
    private String checkString (String str){
        String re="";
        if(str != null && !str.isEmpty()) {
            re=str;
        }
        
        return re;
        
    }
    
    public void setupMessageQueueConsumer() {
         this.connectionFactory = new ActiveMQConnectionFactory(appconst.getConfigdata().getMessageBrokerUrl());
     
            try {
                this.connection = connectionFactory.createConnection();
                   this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                   Queue queue = this.session.createQueue( appconst.getConfigdata().getReplyQueue());
                   MessageConsumer consumer = this.session.createConsumer(queue);
                   consumer.setMessageListener(this);
                   this.connection.start();
                   this.connection.setExceptionListener(this);

                   log.debug("Iprs Message Queue Setup Complete");
                   // System.out.println(" Iprs Message Queue Setup Complete ");


            } catch (JMSException ex) {
               log.error(ex);
               log.debug(" Error Setting Up IPRS Umash Message Queue ");
            sendMail = new SendMail();
            sendMail.SendSystemStatus(" Critical : Error Setting Up IPRS Umash Message Queue ", ex);
            }
         
         
    }
    
    public void CloseConnections(){
        System.out.println("Closing Consumer Connections ");
            try {
                if (this.connection != null) {
                this.connection.close();
                this.session.close();
               System.out.println("Connection Closed ");
                }
            } catch (JMSException ex) {
                log.error(ex);
                sendMail = new SendMail();
                sendMail.SendSystemStatus(" Critical : Application Shutdown ", ex);
            }
       
        
    }
    
    public synchronized void onException(JMSException ex) {
                log.error(ex);
                sendMail = new SendMail();
                sendMail.SendSystemStatus(" Critical : JMS Exception occured ( Developer Msg )", ex);
        }
    private String getInvoiceNumber(Clients cl ){
      String inv="";
            if (cl.getProductname().equalsIgnoreCase("umash"))
            { inv = "um"+inv;
            }
            else if ((cl.getProductname().equalsIgnoreCase("cash-plan"))){
                inv = "cp"+inv;
            }
            
            return inv;
    }
    private byte[] ConverteFromString(String photostring)
  {
    byte[] ck = null;
    try
    {
      if ((photostring != null) && (!photostring.equals(""))) {
        ck = Base64.decodeBase64(photostring);
      }
    }
    catch (Exception ex) {
      log.error(ex);
    }
    return ck;
  }
}
