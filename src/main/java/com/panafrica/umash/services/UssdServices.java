/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.controllers.UssdtempJpaController;
import com.panafrica.umash.model.Ussdtemp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author User
 */
public class UssdServices {
    UssdtempJpaController ussdtempJpaController;;
    Ussdtemp ussdtemp;
      private static org.apache.logging.log4j.Logger log = LogManager.getLogger(UssdServices.class);
    public void SaveussdData(String docid,String phone,String dob,String productname,Integer premium){
        try {
            ussdtempJpaController = new UssdtempJpaController();
            ussdtemp = new Ussdtemp();
            
            ussdtemp.setContact(phone);
            ussdtemp.setDateofbirth(dob);
            ussdtemp.setDocid(docid);
            ussdtemp.setEntrydate(new Date());
            ussdtemp.setProductname(productname);
            ussdtemp.setStatusid(1);
            ussdtemp.setStatusname("unprocessed");
            ussdtemp.setPremiumpayable(premium);
            
            
            ussdtempJpaController.create(ussdtemp);
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
}
