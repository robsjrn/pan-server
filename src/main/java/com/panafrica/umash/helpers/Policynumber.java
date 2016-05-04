/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.helpers;

import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.model.Clients;

/**
 *
 * @author User
 */
public class Policynumber {
    ClientsJpaController clientsJpaController;
    
     public String  generatepolicynumberandsend(Clients client2){
           String  inv="";
           clientsJpaController = new ClientsJpaController();
           try {
          if (client2.getProductname().equalsIgnoreCase("umash"))
            { 
                 inv = "200/1/50201/UM"+client2.getRid();
            }
          else if ((client2.getProductname().toString().equalsIgnoreCase("cash-plan"))){
                 inv = "200/1/50200/CP"+client2.getRid();
            }
          clientsJpaController.policydetails(inv, client2.getRid());
           }catch(Exception ex){
               
           }
          return inv;
      }
    
}
