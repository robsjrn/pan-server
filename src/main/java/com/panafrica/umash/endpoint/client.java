/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.Appnotification.gcm.SendAppNotification;
import com.panafrica.umash.model.Beneficiaries;
import com.panafrica.umash.model.Childrens;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Officelocation;
import com.panafrica.umash.model.Parents;
import com.panafrica.umash.model.Spouse;
import com.panafrica.umash.services.ClientService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
@Path("/client")
public class client {
    ClientService clientService;
    
    @GET
    @Path("details")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Clients> getClientDet(@Context HttpHeaders headers,@QueryParam(value = "clientid") final String clientid) {
      clientService = new ClientService();
        
        return clientService.getClientDta(clientid);
      }
    
     @POST
     @Path("claim")
     @Consumes(MediaType.APPLICATION_JSON)
     public String claim(String details){
         clientService = new ClientService();
         System.out.println("***** Claim *****");
         System.out.println(details);
         System.out.println("***** Claim *****");
        return clientService.saveClaim(details); 
     }
     
     @POST
     @Path("editbeneficiary")
     @Consumes(MediaType.APPLICATION_JSON)
     public String editbeneficiary(String details){
        return "ok"; 
     }
     
     @POST
     @Path("subscribe")
     @Consumes(MediaType.APPLICATION_JSON)
     public String subscribe(String details){
         System.out.println("Subscription details ..:" +details);
          clientService = new ClientService();
          
        return clientService.savetoken(details); 
     }
     @POST
     @Path("unsubcribe")
     @Consumes(MediaType.APPLICATION_JSON)
     public String unsubcribe(String details){
         System.out.println("Subscription details ..:" +details);
        return "ok"; 
     }
     
      @POST
     @Path("notification")
     @Consumes(MediaType.APPLICATION_JSON)
     public String notificationtest(String details){
         System.out.println("Subscription details ..:" +details);
         SendAppNotification sn = new SendAppNotification();
         String resp="";
           JSONObject resObj= new JSONObject() ;
        try {
          
            JSONObject reqObj= new JSONObject() ;
            JSONParser parser = new JSONParser();
             reqObj= (JSONObject) parser.parse(details);
            resp=sn.sendMessage(reqObj.get("message").toString(), reqObj.get("title").toString(), reqObj.get("devicetoken").toString());
         
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
         
        return resp; 
     }
     
     
     
    
    @GET
    @Path("beneficiaries")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Beneficiaries getBeneficiaries(@Context HttpHeaders headers,@QueryParam(value = "productid") final String productid)  {
      clientService = new ClientService();
      
        return clientService.listClientBeneficiaries(productid);
      }
    
    @GET
    @Path("parents")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Parents> getParents(@Context HttpHeaders headers,@QueryParam(value = "productid") final String productid)  {
           clientService = new ClientService();
           
        return  clientService.listClientParents(productid);
      }
    
    
    
    @GET
    @Path("officelocation")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Officelocation> getOfficelocation(@Context HttpHeaders headers)  {
           clientService = new ClientService();
           
        return  clientService.getofficelocations();
      }
    
    @GET
    @Path("children")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Childrens> gethildrens(@Context HttpHeaders headers,@QueryParam(value = "productid") final String productid)  {
        clientService = new ClientService();
        return clientService.listClientChilrens(productid);
      }
    @GET
    @Path("spouse")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Spouse getspouse(@Context HttpHeaders headers,@QueryParam(value = "productid") final String productid)  {
        clientService = new ClientService();
        return clientService.listClientSpouse(productid);
      }
    
}