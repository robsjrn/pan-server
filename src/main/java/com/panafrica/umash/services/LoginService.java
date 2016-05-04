/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.controllers.AppusersJpaController;
import com.panafrica.umash.controllers.UsersJpaController;
import com.panafrica.umash.model.Appusers;
import com.panafrica.umash.model.Users;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author User
 */

public class LoginService {
   AppusersJpaController appusersJpaController; 
   private static org.apache.logging.log4j.Logger log = LogManager.getLogger(LoginService.class);  
    public boolean customerExist(String username,String password,String credentials){
        boolean status=false;
         Appusers appusers=null ;
        appusersJpaController = new AppusersJpaController();
        try {
         System.out.println("The Password is ......" + password);
          System.out.println("The credentials rd is ......" + credentials);
        if (credentials.equalsIgnoreCase("phone")){
              appusers =  appusersJpaController.findUserByContact(username);
        }else {
              appusers  =  appusersJpaController.findUser(username);
        }
     
         if (appusers != null){
              if ( appusers.getPassword().equalsIgnoreCase(password.trim()) && appusers.getUserrole().equalsIgnoreCase("client")){
                 System.out.println("Passed Login  first stage");
                  status=true; 
              }else if (appusers.getStatusid()!=1 ){
                  System.out.println("Passed Login second  stage");
                  status=false; 
              }
             
         }
        }catch(Exception e){
            log.error(e);
        }
        return status;
    }
    
      public boolean staffExist(String username,String password){
          boolean status=false;
          UsersJpaController appu = new UsersJpaController();
          Users appusers  =  appu.findUserByusername(username);
          
          if (appusers != null){
              if ( appusers.getPasssword().equalsIgnoreCase(password)){
                 status=true; 
              }
             
         }
          return status;
      }
}
