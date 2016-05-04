/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.dbListener;

import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Ussdmpesarequest;
import com.panafrica.umash.sms.SmsService;
import com.panafrica.umash.sms.UssdService;
import javax.persistence.PostPersist;

/**
 *
 * @author User
 */
public class NewMpesarequest {
    
     @PostPersist
    public void processInquiry(Ussdmpesarequest ussdmpesarequest ){
        System.out.println(" Processing Mpesa  ");     
       ClientsJpaController  jp = new ClientsJpaController();
     
          System.out.println(" Processing Payments for contact " + ussdmpesarequest.getContact()); 
          Integer rid = Integer.parseInt(ussdmpesarequest.getInvoicenumber().replaceAll("[^0-9.]", ""));
      Clients clt = jp.findClients(rid);
       if (clt!=null){
           
          
			 if ( clt.getPaymentstatus()==1){
                             
                              UssdService ussd= new UssdService();
                               ussd.sendMpesaRequest(ussdmpesarequest.getContact(), clt.getPremiumpayable()+"", ussdmpesarequest.getInvoicenumber());
                            
                          }else {
                               SmsService smsService = new SmsService();
                              smsService.sendSms(ussdmpesarequest.getContact()," Your Policy For "+clt.getProductname()+" Has Already Been paid   "); 
                         }
		}
               else {
  
          
          SmsService  smsService = new SmsService();
             smsService.sendSms(ussdmpesarequest.getContact(), " Sorry The Payment For Invoice  ("+ussdmpesarequest.getInvoicenumber()+") You Are Making Does Not Exist "); 
       
        }
    
      
       
    }
    
}
