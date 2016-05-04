
package com.panafrica.umash.dbListener;

import com.panafrica.umash.controllers.AppusersJpaController;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.model.Appusers;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Ussdinquiries;
import com.panafrica.umash.sms.SmsService;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.PostPersist;


public class NewInquiry {
     @PostPersist
    public void processInquiry(Ussdinquiries inq ){
       String[] result = inq.getInquirydetails().split(Pattern.quote("*"));
       
       String inctype= result[1];
       String idnum =result[2];
       
       
         if (inctype.equalsIgnoreCase("4")){
       
             ClientsJpaController cp = new ClientsJpaController();
             List< Clients> clients = cp.findClients(idnum);
             if (clients !=null && !clients.isEmpty()){
             Iterator it =  clients.iterator();
              SmsService smsService = new SmsService();
              while (it.hasNext()) {
                Clients cl=  (Clients)it.next();
                smsService.sendSms(inq.getContact()," Your Application Status For  "+cl.getProductname().toUpperCase()+" is " + cl.getStatusname()); 
              }
              }
         }
         AppusersJpaController ap = new AppusersJpaController();
          Appusers appusers= ap.findUserByClientis(idnum);
           System.out.println(" App user name is  " + appusers.getUsername());
        if (appusers !=null && appusers.getStatusid()==1){
         if (inctype.equalsIgnoreCase("1")){
             ClientsJpaController cp = new ClientsJpaController();
             List< Clients> clients = cp.findClients(idnum);
             Iterator it =  clients.iterator();
              SmsService smsService = new SmsService();
              while (it.hasNext()) {
                Clients cl=  (Clients)it.next();
                smsService.sendSms(inq.getContact()," Your Policy Number For "+cl.getProductname().toUpperCase()+" is " + cl.getPolicyid()); 
              }
         }
         else if (inctype.equalsIgnoreCase("2")){
              ClientsJpaController cp = new ClientsJpaController();
             List< Clients> clients = cp.findClients(idnum);
             Iterator it =  clients.iterator();
              SmsService smsService = new SmsService();
              while (it.hasNext()) {
                Clients cl=  (Clients)it.next();
                smsService.sendSms(inq.getContact()," Your Sum Assured  For "+cl.getProductname().toUpperCase()+" is " + cl.getSumassured()); 
              }
         }
          else if (inctype.equalsIgnoreCase("3")){
       
             ClientsJpaController cp = new ClientsJpaController();
             List< Clients> clients = cp.findClients(idnum);
             Iterator it =  clients.iterator();
              SmsService smsService = new SmsService();
              while (it.hasNext()) {
                Clients cl=  (Clients)it.next();
                smsService.sendSms(inq.getContact()," Your Premium Payable For "+cl.getProductname().toUpperCase()+" is " + cl.getPremiumpayable()); 
              }
              }
           
          }else {
              SmsService smsService = new SmsService();
              smsService.sendSms(inq.getContact()," The Id ( "+idnum+" ) Has Not Been Registered "); 
          }
       
    }
    
    private void invalidpassword(Ussdinquiries inq,String contact){
         SmsService smsService = new SmsService();
         smsService.sendSms(inq.getContact()," Invalid ID Number   "); 
    }
}
