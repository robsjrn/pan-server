/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.Appnotification.gcm.SendAppNotification;
import com.panafrica.umash.ExceptionHandling.exceptions.RollbackFailureException;
import com.panafrica.umash.controllers.ApptokensJpaController;
import com.panafrica.umash.controllers.BeneficiariesJpaController;
import com.panafrica.umash.controllers.ChildrensJpaController;
import com.panafrica.umash.controllers.ClaimsJpaController;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.OfficelocationJpaController;
import com.panafrica.umash.controllers.ParentsJpaController;
import com.panafrica.umash.controllers.SpouseJpaController;
import com.panafrica.umash.model.Apptokens;
import com.panafrica.umash.model.Beneficiaries;
import com.panafrica.umash.model.Childrens;
import com.panafrica.umash.model.Claims;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Officelocation;
import com.panafrica.umash.model.Parents;
import com.panafrica.umash.model.Spouse;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author User
 */
public class ClientService {
    ClientsJpaController  clientsJpaController ;
    BeneficiariesJpaController beneficiariesJpaController;
    ChildrensJpaController childrensJpaController;
    ParentsJpaController parentsJpaController;
    SpouseJpaController spouseJpaController;
    OfficelocationJpaController  officelocationJpaController ;
    ClaimsJpaController claimsJpaController;
    SendMail sendMail;
    ApptokensJpaController apptokensJpaController;
    
   private static org.apache.logging.log4j.Logger log = LogManager.getLogger(ClientService.class);   
    public List<Clients> getClientDta(String clientid){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.findClients(clientid);
        
    }
    public String savetoken(String details) {
          JSONObject resObj= new JSONObject() ;
          SendAppNotification sn = new SendAppNotification();
        try {
          
            JSONObject reqObj= new JSONObject() ;
            JSONParser parser = new JSONParser();
            apptokensJpaController = new ApptokensJpaController();
            Apptokens apptokens = new Apptokens();      
            reqObj= (JSONObject) parser.parse(details);
             apptokens.setToken(reqObj.get("token").toString());
             apptokens.setDevice(reqObj.get("type").toString());
             apptokens.setTokendate(new Date());
            apptokensJpaController.create(apptokens);
            
            resObj.put("Status", 1);
            resObj.put("StatusMessage", "Token Saved");
            sn.sendMessage("Welcome To Pan-Africa Group App", "Pan-Africa Group", reqObj.get("token").toString());
        } catch (ParseException ex) {
           resObj.put("Status", 2);
           resObj.put("StatusMessage", "Error");
           resObj.put("DeveloperMessage", ex);
           log.error(ex);
           
        } catch (RollbackFailureException ex) {
          resObj.put("Status", 2);
           resObj.put("StatusMessage", "Error");
           resObj.put("DeveloperMessage", ex);
             log.error(ex);
        } catch (Exception ex) {
           resObj.put("Status", 2);
           resObj.put("StatusMessage", "Error");
           resObj.put("DeveloperMessage", ex);
             log.error(ex);
        }
          return resObj.toString();
    }
    
    public String saveClaim(String details){
        
         JSONObject reqObj= new JSONObject() ;
            JSONObject resObj= new JSONObject() ;
            JSONParser parser = new JSONParser();
            sendMail = new SendMail();
        try {
            reqObj= (JSONObject) parser.parse(details);
            
            claimsJpaController = new ClaimsJpaController();
            Claims clms = new Claims();
                  clms.setClaimcontact(reqObj.get("contacts").toString());
                  clms.setClaimdate(new Date());
                  clms.setClaimid(reqObj.get("doc").toString());
                  clms.setStatus(1);
                  clms.setStatusname("New Claim Request");
           
            claimsJpaController.create(clms);
            
            resObj.put("status", 1);
            resObj.put("Mesage", "Record Saved");
            
            //send mail
            sendMail.SendClaimMail(clms);
        } catch (Exception ex) {
               resObj.put("status", 2);
               resObj.put("Mesage", "Error Occured");
               resObj.put("DeveloperMesage", ex.toString());
            log.error(ex);
        }
        return resObj.toJSONString();
                }
    public List<Officelocation> getofficelocations(){
            officelocationJpaController = new OfficelocationJpaController();
            
        return officelocationJpaController.findOfficelocationEntities();
    }
    public Beneficiaries listClientBeneficiaries(String productid){
        beneficiariesJpaController = new BeneficiariesJpaController();
        
        return  beneficiariesJpaController.getClientBeneficiariesUsingproductidS(Integer.parseInt(productid));
    }
    
    public Spouse listClientSpouse(String productid){
        spouseJpaController = new SpouseJpaController();
        return spouseJpaController.getSpouseByProductid(Integer.parseInt(productid));
    }
    
    public List<Parents> listClientParents(String productid){
        
        parentsJpaController = new ParentsJpaController();
        
        return parentsJpaController.getClientParentsbyproductid(Integer.parseInt(productid));
        
    }
    public List<Childrens> listClientChilrens(String productid){
        childrensJpaController = new ChildrensJpaController();
        return childrensJpaController.getChildrenbyproductid(Integer.parseInt(productid));
    }
        
}
