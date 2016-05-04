/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.jms;

import com.panafrica.umash.configuration.Appconstants;
import com.panafrica.umash.controllers.IprserrorsJpaController;
import com.panafrica.umash.model.Iprserrors;
import com.panafrica.umash.services.SendMail;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;

/**
 *
 * @author User
 */
public class Producer {
    private Session session;
    private static Appconstants appconst;
    private boolean transacted = false;
     private static Connection connection;
     private static ActiveMQConnectionFactory connectionFactory;
     private TextMessage txtMessage;
     private Destination tempDest;
     private MessageProducer producer;
      SendMail sendMail;    
   private static org.apache.logging.log4j.Logger log = LogManager.getLogger(Producer.class);  
       public Producer(){
           System.out.println("Setting up Message Producer");
        try {
            connectionFactory   = new ActiveMQConnectionFactory(appconst.getConfigdata().getMessageBrokerUrl());
            connection = connectionFactory.createConnection();
        } catch (JMSException ex) {
            sendMail = new SendMail();
            log.error(ex);
            sendMail.SendSystemStatus(" Iprs Queue Setup ", ex);
        }
       }
    
      public  void sendText(String docid,String option){
     
        try {
            //Now create the actual message you want to send
            
            session = connection.createSession(this.transacted, Session.AUTO_ACKNOWLEDGE);
            txtMessage= this.session.createTextMessage();
            
            
            tempDest = session.createQueue(appconst.getConfigdata().getReplyQueue());
            
            
            JSONObject obj = new JSONObject();
            obj.put("appname", "Umash");
            obj.put("docid",docid);
            obj.put("option",option);
            txtMessage.setText(obj.toJSONString());
 
            //Set the reply to field to the temp queue you created above, this is the queue the server
            //will respond to
            txtMessage.setJMSReplyTo(tempDest);
 
            //Set a correlation ID so when you get a response you know which sent message the response is for
            //If there is never more than one outstanding message to the server then the
            //same correlation ID can be used for all the messages...if there is more than one outstanding
            //message to the server you would presumably want to associate the correlation ID with this
            //message somehow...a Map works good
            String correlationId = this.createRandomString();
            txtMessage.setJMSCorrelationID(correlationId);
            Destination adminQueue = session.createQueue(appconst.getConfigdata().getClientQueueName());
            producer = session.createProducer(adminQueue);
            producer.send(txtMessage);
          //  System.out.println("Message Sent to Iprs Queue");
              session.close();
            producer.close();
        } catch (JMSException ex) {
             log.error(ex);
            IprserrorsJpaController ip = new IprserrorsJpaController();
                        Iprserrors iprserrors = new Iprserrors();
                        iprserrors.setDocid(docid);
                        iprserrors.setDocoption(option);
                        iprserrors.setMessage(ex.getMessage());
                        iprserrors.setStatusid(1);
                        iprserrors.setEntrydate(new Date());
                        iprserrors.setStatusname("Iprs Connection Error");
                sendMail = new SendMail();
                sendMail.SendSystemStatus(" Iprs Unreachable ", ex);         
            try {
                ip.create(iprserrors);
            } catch (Exception ex1) {
                log.error(ex);
            }
        }
        catch (Exception ex) {
            log.error(ex);
            IprserrorsJpaController ip = new IprserrorsJpaController();
                        Iprserrors iprserrors = new Iprserrors();
                        iprserrors.setDocid(docid);
                        iprserrors.setDocoption(option);
                        iprserrors.setMessage(ex.getMessage());
                        iprserrors.setStatusid(1);
                        iprserrors.setEntrydate(new Date());
                        iprserrors.setStatusname("Iprs Connection Error");
                        
                         sendMail = new SendMail();
                         sendMail.SendSystemStatus(" Iprs Unreachable ", ex);   
                        
                         try {
                ip.create(iprserrors);
            } catch (Exception ex1) {
                log.error(ex1);
            }
        }
           
            
          
      
         }   
      
      
       private String createRandomString() {
                Random random = new Random(System.currentTimeMillis());
                long randomLong = random.nextLong();
                return Long.toHexString(randomLong);
            }
        public void closeConn(){
        try {
            connection.close();
          
        } catch (JMSException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
}
