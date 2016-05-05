/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.endpoint;

import com.panafrica.umash.Appnotification.gcm.SendAppNotification;
import com.panafrica.umash.controllers.ClaimsJpaController;
import com.panafrica.umash.model.Beneficiaries;
import com.panafrica.umash.model.Childrens;
import com.panafrica.umash.model.Claims;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Officelocation;
import com.panafrica.umash.model.Parents;
import com.panafrica.umash.model.Spouse;
import com.panafrica.umash.services.ClientService;
import com.panafrica.umash.services.SendMail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
        return clientService.saveClaim(details); 
     }
     

     
      @POST
      @Path("uploadclaimdoc")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        public Response uploadAsset(FormDataMultiPart multipart) {

            Map<String, List<FormDataBodyPart>> map = multipart.getFields();

            for (Map.Entry<String, List<FormDataBodyPart>> entry : map.entrySet()) {

                for (FormDataBodyPart part : entry.getValue()) {
                    InputStream in = part.getEntityAs(InputStream.class);
                    String name = part.getName();
                 
                    saveimages(name,name,filetobase64Array(in));
                }
            }
            return Response.ok("files Received").build();
        }
        
        private byte[] filetobase64Array(InputStream is){
           byte[] bytes=null;
            try {
            bytes = IOUtils.toByteArray(is);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
         }
        
        private void saveimages(String contact,String docid,byte[] photoimage){
            
        try {
            ClaimsJpaController  claimsJpaController = new ClaimsJpaController();
            Claims clms = new Claims();
            clms.setClaimcontact(contact);
            clms.setClaimdate(new Date());
            clms.setClaimid(docid);
            clms.setStatus(1);
            clms.setStatusname("New Claim Request");
            clms.setPhotoavailable(Boolean.TRUE);
            clms.setPhoto(photoimage);
            
            claimsJpaController.create(clms);
            SendMail sendMail = new SendMail();
            sendMail.SendClaimMail(clms);
        } catch (Exception ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
     
     @POST
     @Path("comments")
     @Consumes(MediaType.APPLICATION_JSON)
     public String comments(String details){
         clientService = new ClientService();
        return clientService.savefeedback(details); 
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