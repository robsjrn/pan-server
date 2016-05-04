/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.task;

import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.ClientspoductsJpaController;
import com.panafrica.umash.controllers.UssdtempJpaController;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.jms.Producer;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Clientspoducts;
import com.panafrica.umash.model.Ussdtemp;
import com.panafrica.umash.services.UssdServices;
import com.panafrica.umash.sms.SmsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 *
 * @author User
 */
public class ussdtasks {
    
    UssdtempJpaController ussdtempJpaController;
    ClientsJpaController  clientsJpaController ;
     SmsService smsService;
     static Producer iprs = new Producer() ;

     private static org.apache.logging.log4j.Logger log = LogManager.getLogger(ussdtasks.class);
    //deprecated for processUssd called on PostPersist(ussdtemp) Method callback
    private void CheckUnprocessedData(){
        ussdtempJpaController = new UssdtempJpaController();     
        Ussdtemp ussdtemp=ussdtempJpaController.getUnprocessedData();
          if (ussdtemp==null){
             System.out.println("No Data to Process waiting ...");
          }else {
              smsService = new SmsService();
              String num = ussdtemp.getContact().toString().substring(1);
              smsService.sendSms(num,"Thank You For Your Interest In PanAfrica "+ussdtemp.getProductname()+"Plan .Your Request is Being Processed "); 
              UpdateStatus(ussdtemp);
              
          }
    }
    public void processUssd(Ussdtemp ussdtemp){
              smsService = new SmsService();
              String num = ussdtemp.getContact().toString().substring(1);
              smsService.sendSms(num,"Thank You For Your Interest In PanAfrica "+ussdtemp.getProductname()+" Plan .Your Request is Being Processed "); 
              UpdateStatus(ussdtemp);
        
    }
    private boolean checkAge(Ussdtemp ussdtemp){
        boolean status=true;
        
                 try {
            String expectedPattern = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
            String userInput = ussdtemp.getDateofbirth();
            Date date = formatter.parse(userInput);
                     
             LocalDate birthdate = new LocalDate (date);          //Birth date
            LocalDate now = new LocalDate();                    //Today's date
            Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
            Integer years = period.getYears();
             
                System.out.println(period.getYears());
                
                           if (years < 18 ){
                              smsService = new SmsService();
                              String num = ussdtemp.getContact().toString().substring(1);
                              smsService.sendSms(num," Sorry You Are Below The Age Limit (18) Set for this Plan "); 
                            // System.out.println("Sorry You Below The Age Limit (18) Set for this Product");
                             status=false;
                           }else if (years > 65 ){
                               smsService = new SmsService();
                              String num = ussdtemp.getContact().toString().substring(1);
                              smsService.sendSms(num," Sorry You Are  Above  The Age Limit (65) Set for this Plan "); 
                                
                                status=false;
                           }else {
                                System.out.println("good to Go"); 
                           }

            
            
            
        } catch (ParseException ex) {
             System.out.println("Sorry .We got a Wrong Date Format for Your Birth Date .Kindly Retry with the Correct Format (dd/mm/yyyy e.g 01/01/1980)");
               smsService = new SmsService();
               String num = ussdtemp.getContact().toString().substring(1);
               smsService.sendSms(num," Sorry .We got a Wrong Date Format for Your Birth Date .Kindly Retry with the Correct Format (dd/mm/yyyy e.g 01/01/1980)"); 
             status=false;
             log.error(ex);
        }
    
        return status;
        
    };
    
    private void UpdateStatus( Ussdtemp ussdtemp){
        
        try {
            ussdtempJpaController = new UssdtempJpaController();
            ussdtemp.setStatusid(2);
            ussdtemp.setStatusname("processed");
            ussdtempJpaController.edit(ussdtemp);
            if (checkAge(ussdtemp))
            {
            updateClienttable(ussdtemp);
            }else {
                    System.out.println("Invalid Age Entry ......cannt Proceeed");
                    }
        } catch (RollbackFailureException ex) {
            log.error(ex);
        } catch (Exception ex) {
           log.error(ex);
        }
           
           
        
    }
    
    private void updateClienttable(Ussdtemp ussdtemp){ 
        clientsJpaController = new ClientsJpaController();
       
                   
           List<Clients> client=clientsJpaController.findClients(ussdtemp.getDocid());
            System.out.println("Client Size " + client.size() );
         
             if (client == null || client.isEmpty()){
             
                        processClient(ussdtemp);
                }else {
                        processOther(ussdtemp);

                }
        
    }
    private void processOther(Ussdtemp ussdtemp){
        
               smsService = new SmsService();
               String num = ussdtemp.getContact().toString().substring(1);
               String msg="";
               boolean umashexist=false;
               boolean cashplanhexist=false;
               Integer sa=0;
               ClientspoductsJpaController clientspoductsJpaController = new ClientspoductsJpaController();
                List<Clientspoducts> cps =clientspoductsJpaController.getClientProducts(ussdtemp.getDocid());
                Iterator it =  cps.iterator();
                System.out.println("Product  Size " + cps.size() );
                   
                
                 while (it.hasNext()) {
                    Clientspoducts cp =(Clientspoducts) it.next();
                      System.out.println("Client Product " + cp.getProductname());
                     if (cp.getProductname().equalsIgnoreCase("umash")){
                         sa=sa+cp.getSumassured();
                         umashexist=true;
                     }else {
                         sa=sa+cp.getSumassured();
                         cashplanhexist=true;
                     }
                     
                 }
                 System.out.println("Total Sum Assured " + sa);
                 if (sa >= 600000){
                         msg=" Sorry You Have Reached The Maximum Number of Plan (Umash and Cash Plan) That You Can Have  "; 
                     
                   }
                 else if (umashexist && ussdtemp.getProductname().equalsIgnoreCase("umash")){
                      msg=" Sorry You Already Have Umash Plan ( limited to One Per Client ). Can We Interest You with  Another of Our Product Called Cash Plan ?";
             
                  }
                  else if (umashexist && ussdtemp.getProductname().equalsIgnoreCase("cash-plan")){
                        if (sa <= 600000){
                            processClient(ussdtemp);
                           msg= " Thank You For Your Interest In "+ussdtemp.getProductname().toString()+" Plan .You Request is Being Processed ";
                        } else {
                            msg=" Sorry You Have Reached The Maximum Number of Plans (Umash and Cash Plan) That You Can Apply For  "; 
                     
                        }
                  }
                 else if (cashplanhexist && ussdtemp.getProductname().toString().equalsIgnoreCase("cash-plan")){
                       if (sa <= 400000){
                            processClient(ussdtemp);
                           msg= " Thank You For Your Interest In "+ussdtemp.getProductname().toString()+" Product .You Request is Being Processed ";
                       }else {
                            msg=" Sorry You Have Reached The Maximum Number of Plans (Umash and Cash Plan) That You Can Apply For  "; 
                     
                        }
                  }
                  
                 
                
               smsService.sendSms(num,msg);
    }
    
    private void processClient(Ussdtemp ussdtemp){
               try {
                        
                        
                        Clients clients = new Clients();
                        clients.setDocid(ussdtemp.getDocid());
                        clients.setDob(ussdtemp.getDateofbirth());
                        clients.setChannelname("ussd");
                        clients.setProductname(ussdtemp.getProductname());
                        clients.setChannelid(2);
                        clients.setChildrenAvailable(0);
                        clients.setParentAvailable(0);
                        clients.setSpouseAvailable(0);
                        clients.setPaymentstatus(1);
                         clients.setPaymentDescription("Unpaid");
                        clients.setStatusid(1);
                        clients.setStatusname("New Request (Pending Iprs Confirmation)");
                        clients.setRegistrationdate(new Date());
                        clients.setContacts(ussdtemp.getContact());
                        clients.setPremiumpayable(ussdtemp.getPremiumpayable());
                       Integer pid= clientsJpaController.create(clients);
                        saveClientProducts(ussdtemp.getDocid(),ussdtemp.getProductname(),ussdtemp.getPremiumpayable(),pid);
                        CallIprs(ussdtemp);
                    } catch (Exception ex) {
                        log.error(ex);
                        ex.printStackTrace();
                    }
        
    }
    private void CallIprs(Ussdtemp ussdtemp){
        
         iprs.sendText(ussdtemp.getDocid(), "1");
        
    }
    
    private void saveClientProducts(String clientid,String productname,Integer premiumpayable,Integer pid){
        try {
            Integer sumassured =200000;
              if (productname.equalsIgnoreCase("cash-plan")){
                   if (premiumpayable==1800){
                      sumassured=100000; 
                   }
              }
            ClientspoductsJpaController clientspoductsJpaController = new ClientspoductsJpaController();
            Clientspoducts clientspoducts = new Clientspoducts();
            clientspoducts.setClientid( clientid);
            clientspoducts.setPremiumpayable(premiumpayable);
            clientspoducts.setSumassured(sumassured);
            clientspoducts.setProductname(productname);
            clientspoducts.setProductid(sumassured);
            clientspoductsJpaController.create(clientspoducts);
        } catch (Exception ex) {
            Logger.getLogger(ussdtasks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
