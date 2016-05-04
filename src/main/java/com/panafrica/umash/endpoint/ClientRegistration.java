/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.services.Registration;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author User
 */
@Path("/Registration")
public class ClientRegistration {
    Registration  registration ;
    
@POST
@Consumes(MediaType.APPLICATION_JSON)
     public String umashRegistration(String details) throws Exception{
      
         registration = new Registration();
           System.out.println(details);   
         
     return registration.SaveDetails(details);
         
    
      }
}
