/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.dbListener;

import com.panafrica.umash.controllers.AppusersJpaController;
import com.panafrica.umash.model.Appusers;
import com.panafrica.umash.model.Ussdinquiries;
import com.panafrica.umash.model.Ussdpasswordreset;
import com.panafrica.umash.sms.SmsService;
import java.util.regex.Pattern;
import javax.persistence.PostPersist;

/**
 *
 * @author User
 */
public class NewPasswordReset {
        @PostPersist
    public void processpasswordreset(Ussdpasswordreset pr ){
        String[] result = pr.getRequestdetails().split(Pattern.quote("*"));
       String idnum =result[1];
         AppusersJpaController ap = new AppusersJpaController();
          Appusers appusers= ap.findUserByClientis(idnum);
          SmsService smsService = new SmsService();
         smsService.sendSms(pr.getContact()," Your New Password is "+appusers.getPassword()); 
    }
}
