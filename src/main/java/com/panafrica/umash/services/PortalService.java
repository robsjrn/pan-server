/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.services;

import com.panafrica.umash.controllers.AppusersJpaController;
import com.panafrica.umash.controllers.BeneficiariesJpaController;
import com.panafrica.umash.controllers.BeneficiarychangerequestJpaController;
import com.panafrica.umash.controllers.ChildrensJpaController;
import com.panafrica.umash.controllers.ClaimsJpaController;
import com.panafrica.umash.controllers.ClientsJpaController;
import com.panafrica.umash.controllers.FeedbackJpaController;
import com.panafrica.umash.controllers.IprserrorsJpaController;
import com.panafrica.umash.controllers.MpesatransactionsJpaController;
import com.panafrica.umash.controllers.ParentsJpaController;
import com.panafrica.umash.controllers.PaymentsJpaController;
import com.panafrica.umash.controllers.SmsJpaController;
import com.panafrica.umash.controllers.SpouseJpaController;
import com.panafrica.umash.controllers.TeuploadsJpaController;
import com.panafrica.umash.controllers.UsersJpaController;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.helpers.Policynumber;
import com.panafrica.umash.model.Appusers;
import com.panafrica.umash.model.Beneficiaries;
import com.panafrica.umash.model.Beneficiarychangerequest;
import com.panafrica.umash.model.Childrens;
import com.panafrica.umash.model.Claims;
import com.panafrica.umash.model.Clients;
import com.panafrica.umash.model.Feedback;
import com.panafrica.umash.model.Iprserrors;
import com.panafrica.umash.model.Mpesatransactions;
import com.panafrica.umash.model.Parents;
import com.panafrica.umash.model.Payments;
import com.panafrica.umash.model.Sms;
import com.panafrica.umash.model.Spouse;
import com.panafrica.umash.model.Teuploads;
import com.panafrica.umash.model.Users;
import com.panafrica.umash.security.MD5Checksum;
import com.panafrica.umash.sms.SmsService;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
public class PortalService {
    ClientsJpaController clientsJpaController;
    SmsJpaController smsJpaController;
    MpesatransactionsJpaController mpesatransactionsJpaController;
    ChildrensJpaController childrensJpaController;
    Policynumber policynumber;
    AppusersJpaController appusersJpaController;
    IprserrorsJpaController iprserrorsJpaController;
    BeneficiarychangerequestJpaController bcrCtrl;
    ClaimsJpaController clms;
    PaymentsJpaController paymentsJpaController;
 
     private static org.apache.logging.log4j.Logger log = LogManager.getLogger(PortalService.class);
      
   public List<Iprserrors>  getiprsErros(){
       iprserrorsJpaController = new IprserrorsJpaController();
       
       return iprserrorsJpaController.findIprserrorsEntities();
   }
   public List<Feedback>  Feedback(){
       FeedbackJpaController fjc = new FeedbackJpaController();
       
       return fjc.findFeedbackEntities();
   }
   
       public String createuser(String user){
         UsersJpaController  ujc= new UsersJpaController ();
         JSONObject reqObj= new JSONObject() ;
            JSONObject resObj= new JSONObject() ;
            JSONParser parser = new JSONParser();
        try {
            reqObj= (JSONObject) parser.parse(user);
            
            MD5Checksum md5 = new MD5Checksum();
            
            Users ipu = new Users();
                   ipu.setDepartment(reqObj.get("department").toString());
                   ipu.setEmail(reqObj.get("email").toString());
                   ipu.setStatusdescription("User Enabled");
                   ipu.setCreatedby("system");
                   ipu.setCreateddate(new Date());
                   ipu.setStatusid(1);
                   ipu.setUsername(reqObj.get("username").toString());
                   ipu.setPasssword(md5.hashPassword(reqObj.get("password").toString()));
              
                   ujc.create(ipu);                  
                   resObj.put("status", 1);
                   resObj.put("Message", "User Created");
                  
                  
            
       }catch(Exception ex){
           resObj.put("status", 2);
           resObj.put("Message", "Error Creaing User");
           resObj.put("DeveloperMessage", ex);
           ex.printStackTrace();
       }
   return resObj.toJSONString();
   }
    public List<Clients> getNewRequests(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.getnewRequests();
    }
    
    
     public List<Clients> Overpayments(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.Overpayments();
    }
     
      public List<Clients> getUnderpayments(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.getUnderpayments();
    }
    
    public List<Payments> getPayments(){
        
        paymentsJpaController = new PaymentsJpaController();
        
       return paymentsJpaController.findPaymentsEntities();
        
    }
    
    public List<Mpesatransactions> getMpesatrxn(){
        mpesatransactionsJpaController  = new MpesatransactionsJpaController();
        return mpesatransactionsJpaController.findMpesatransactionsEntities();
    }
     public List<Claims> getClaimsRequest(){
        clms = new ClaimsJpaController();
        
        return clms.getUnprocessedClaims();
    }
    
    public List<Beneficiarychangerequest> getAddBeneficiaryRequest(){
        bcrCtrl = new BeneficiarychangerequestJpaController();
      return bcrCtrl.findBeneficiaryAddchangerequest();
    }
    
     public List<Beneficiarychangerequest> getEditBeneficiaryRequest(){
        bcrCtrl = new BeneficiarychangerequestJpaController();
      return bcrCtrl.findBeneficiaryEditchangerequest();
    }
    
    public List<Mpesatransactions> getMpesaTransactions(){
        mpesatransactionsJpaController = new MpesatransactionsJpaController();
        
        return mpesatransactionsJpaController.findMpesatransactionsEntities();
    }
    
    public List<Clients> getUnpaid(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.getUnpaidRequests();
    }
    
     public List<Clients> getSuccessfulRegistrations(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.getSuccessfulRegistration();
    }
     
      public List<Clients> getTEUploads(){
        clientsJpaController = new ClientsJpaController();
        
        return clientsJpaController.getTEUploads();
    }
     
     public String getSummary(){
          clientsJpaController = new ClientsJpaController();
            JSONObject resObj= new JSONObject() ;
              resObj.put("umash", clientsJpaController.umashCount());
              resObj.put("cashplan", clientsJpaController.cashplanCount());
            return resObj.toJSONString();
     }
     
     public List<Sms> getSms(){
          smsJpaController = new SmsJpaController();
           
            return smsJpaController.findSmsEntities();
     }
     public String ProcessClient(JSONObject Obj){
          clientsJpaController = new ClientsJpaController();
            JSONObject resObj= new JSONObject() ;
            Policynumber policynumber = new Policynumber();
              
              Clients cl = new Clients();
              
                   cl.setPolicyConfirmedBy("test");
                   cl.setPolicycomment(Obj.get("comments").toString());
                   cl.setPolicyconfirmedDate(new Date());
                   cl.setPolicystatus(1);
                   cl.setStatusid(4);
              try {
                Clients client2 = new Clients();
             client2= clientsJpaController.editTrxn(cl, Obj.get("docId").toString(),Obj.get("trxnid").toString());
              if (client2.getContacts() !=null){
                  
                 SmsService  smsService = new SmsService();
                    String num = client2.getContacts().toString().substring(1);
                    
                     if (client2.getChannelname().equalsIgnoreCase("app")){
                         String pwd =generateUnameAndPassword(client2.getDocid(),client2);
                         
                         smsService.sendSms(client2.getContacts()," Thank You Once Again . To Login To the App Use Your ID NUMBER ( "+client2.getDocid()+" ) as Username and Password");
                        String policynum= policynumber.generatepolicynumberandsend(client2);
                        smsService.sendSms(client2.getContacts()," Thank You ( "+client2.getClientnames()+" ) Your Policy Number is ("+policynum+")");
                     }else {
                         String pwd =generateUnameAndPassword(client2.getDocid(),client2);
                          smsService.sendSms(client2.getContacts()," Thank You Once Again .For Enquiries from the USSD Use Your ID NUMBER  ( "+client2.getDocid()+") As Your Password ");
                         String policynum= policynumber.generatepolicynumberandsend(client2);
                          smsService.sendSms(client2.getContacts()," Thank You ( "+client2.getClientnames()+" ) Your Policy Number is ("+policynum+")");
                     }
                         
                    
                    
                  
              }
             resObj.put("status", 1);
             resObj.put("statusMessage", "Client Successfuly Processed");
                
            }  catch (Exception ex) {
                resObj.put("status", 0);
                resObj.put("statusMessage", "Error Processing Your Request");
                resObj.put("Developer Message", ex.getMessage());
                log.error(ex);
               // ex.printStackTrace();
            }      
                   
                   
            return resObj.toJSONString();
     }
     
     public String  TEFile(){
         log.debug(" Generating TE File ");
           FileOutputStream fileOut = null;
           String fl=null;
         try {
          Workbook wb = new HSSFWorkbook();
             Sheet sheet = wb.createSheet("DIGITAL FUNERAL PRODUCTS");
                Row row = sheet.createRow((short)0);
                     setUpFile(row);
                     startProcessing(sheet);
                      Date date = new Date(); 
                      long mills = date.getTime();
                     
               fl= "C:\\TEuploads\\"+mills+".xls";     
             fileOut = new FileOutputStream(fl);
             Date d = new Date();
             log.debug("File " + fl +" Date "+ d);
             
             wb.write(fileOut);
             fileOut.close();
             SendMail sendMail = new SendMail();
            sendMail.SendTeuploadMail(fl);
            saveTeupload(fl,"System");
            
         } catch (FileNotFoundException ex) {
             log.error(ex);
         } catch (IOException ex) {
            log.error(ex);
        } finally {
             try {
          if (fileOut==null){                  
          }else {
                 fileOut.close();
          }
             } catch (IOException ex) {
                 log.error(ex);
             }
         }  
         return fl;
     }
     
     public String generateTEFile(String docid){
          clientsJpaController = new ClientsJpaController();
        Clients cl=  clientsJpaController.findClient(docid);
           FileOutputStream fileOut = null;
           String fl=null;
           
                 
            
           
         try {
             Workbook wb = new HSSFWorkbook();
             Sheet sheet = wb.createSheet("DIGITAL FUNERAL PRODUCTS");
                Row row = sheet.createRow((short)0);
                     setUpFile(row);
                      
                        
                   Row row1 = sheet.createRow((short)1);  
                   processClientDetails(row1,cl);
                      if (cl.getSpouseAvailable()==1){
                         processpouseDetails(row1,cl.getRid(),cl) ;
                      }if (cl.getChildrenAvailable()==1){
                          processChildDetails(row1,cl.getRid(),cl);
                      }if (cl.getParentAvailable()==1){
                          processparentDetails(row1,cl.getRid(),cl);
                      }
                       updateProcessed(cl.getRid(),cl); 
                       
                   fl= "C:\\TEuploads\\"+docid+".xls";     
                  fileOut = new FileOutputStream(fl);
                   Date d = new Date();
                   log.debug("File " + fl +" Date "+ d);
             
             
             wb.write(fileOut);
             fileOut.close();
             
            SendMail sendMail = new SendMail();
            sendMail.SendTeuploadMail(fl);
            saveTeupload(fl,"System");
            
         } catch (FileNotFoundException ex) {
             log.error(ex);
         } catch (IOException ex) {
            log.error(ex);
        } finally {
             try {
                 fileOut.close();
             } catch (IOException ex) {
                log.error(ex);
             }
         }
             
   
         return fl;
     }
     
     private void setUpFile(Row row){
                        row.createCell(0).setCellValue("FULL-NAME");
                        row.createCell(1).setCellValue("NAME");
                        row.createCell(2).setCellValue("SURNAME");
                        row.createCell(3).setCellValue("BIRTH-DATE");
                        row.createCell(4).setCellValue("ID-NUMBER");
                        row.createCell(5).setCellValue("EMPLOYEE-NUMBER");
                        row.createCell(6).setCellValue("GENDER");
                        row.createCell(7).setCellValue("SALARY");
                        row.createCell(8).setCellValue("COVER-LEVEL");
                        row.createCell(9).setCellValue("SPOUSE-FULL-NAME");
                        row.createCell(10).setCellValue("SPOUSE-NAME");
                        row.createCell(11).setCellValue("SPOUSE-SURNAME");
                        row.createCell(12).setCellValue("SPOUSE-BIRTH-DATE");
                        row.createCell(13).setCellValue("SPOUSE-GENDER");
                        row.createCell(14).setCellValue("SPOUSE-COVER-LEVEL");
                        row.createCell(15).setCellValue("WIDER-1-FULL-NAME");
                        row.createCell(16).setCellValue("WIDER-1-NAME");
                        row.createCell(17).setCellValue("WIDER-1-SURNAME");
                        row.createCell(18).setCellValue("WIDER-1-BIRTH-DATE");
                        row.createCell(19).setCellValue("WIDER-1-GENDER");
                        
                        
                        row.createCell(20).setCellValue("WIDER-1-COVER-LEVEL");
                        row.createCell(21).setCellValue("WIDER-2-FULL-NAME");
                        row.createCell(22).setCellValue("WIDER-2-NAME");
                        row.createCell(23).setCellValue("WIDER-2-SURNAME");
                        row.createCell(24).setCellValue("WIDER-2-BIRTH-DATE");
                        row.createCell(25).setCellValue("WIDER-2-GENDER");
                        row.createCell(26).setCellValue("WIDER-2-COVER-LEVEL");
                        row.createCell(27).setCellValue("WIDER-3-FULL-NAME");
                        row.createCell(28).setCellValue("WIDER-3-NAME");
                        row.createCell(29).setCellValue("WIDER-3-SURNAME");
                        
                        row.createCell(30).setCellValue("WIDER-3-BIRTH-DATE");
                        row.createCell(31).setCellValue("WIDER-3-GENDER");
                        row.createCell(32).setCellValue("WIDER-3-COVER-LEVEL");
                        row.createCell(33).setCellValue("WIDER-4-FULL-NAME");
                        row.createCell(34).setCellValue("WIDER-4-NAME");
                        row.createCell(35).setCellValue("WIDER-4-SURNAME");
                        row.createCell(36).setCellValue("WIDER-4-BIRTH-DATE");
                        row.createCell(37).setCellValue("WIDER-4-GENDER");
                        row.createCell(38).setCellValue("WIDER-4-COVER-LEVEL");
                        row.createCell(39).setCellValue("CHILD-1-FULL-NAME");
                        
                         row.createCell(40).setCellValue("CHILD-1-NAME");
                        row.createCell(41).setCellValue("CHILD-1-SURNAME");
                        row.createCell(42).setCellValue("CHILD-1-BIRTH-DATE");
                        row.createCell(43).setCellValue("CHILD-1-COVER-LEVEL");
                        row.createCell(44).setCellValue("CHILD-2-FULL-NAME");
                        row.createCell(45).setCellValue("CHILD-2-NAME");
                        row.createCell(46).setCellValue("CHILD-2-SURNAME");
                        row.createCell(47).setCellValue("CHILD-2-BIRTH-DATE");
                        row.createCell(48).setCellValue("CHILD-2-COVER-LEVEL");
                        row.createCell(49).setCellValue("CHILD-3-FULL-NAME");
                        
                         row.createCell(50).setCellValue("CHILD-3-NAME");
                        row.createCell(51).setCellValue("CHILD-3-SURNAME");
                        row.createCell(52).setCellValue("CHILD-3-BIRTH-DATE");
                        row.createCell(53).setCellValue("CHILD-3-COVER-LEVEL");
                        row.createCell(54).setCellValue("CHILD-4-FULL-NAME");
                        row.createCell(55).setCellValue("CHILD-4-NAME");
                        row.createCell(56).setCellValue("CHILD-4-SURNAME");
                        row.createCell(57).setCellValue("CHILD-4-BIRTH-DATE");
                        row.createCell(58).setCellValue("CHILD-4-COVER-LEVEL");
                        row.createCell(59).setCellValue("CHILD-5-FULL-NAME");
                        
                        row.createCell(60).setCellValue("CHILD-5-NAME");
                        row.createCell(61).setCellValue("CHILD-5-SURNAME");
                        row.createCell(62).setCellValue("CHILD-5-BIRTH-DATE");
                        row.createCell(63).setCellValue("CHILD-5-COVER-LEVEL");
                        row.createCell(64).setCellValue("CHILD-6-FULL-NAME");
                        row.createCell(65).setCellValue("CHILD-6-NAME");
                        row.createCell(66).setCellValue("CHILD-6-SURNAME");
                        row.createCell(67).setCellValue("CHILD-6-BIRTH-DATE");
                        row.createCell(68).setCellValue("CHILD-6-COVER-LEVEL");
         
     }
     
    private void startProcessing(Sheet sheet){
        clientsJpaController = new ClientsJpaController();
          
        List<Clients> cl = clientsJpaController.getTEUploads();
        
        if (cl ==null || cl.isEmpty()){
            System.out.println("No Data to Process");
        }else {
        
               int i = 0;
               int rw=1;
               while (i < cl.size()) {
               // System.out.println("Processing Data For " + cl.get(i).getDocid());
                  Row row1 = sheet.createRow((short)rw);                  
                  processClientDetails(row1,cl.get(i));
                      if (cl.get(i).getSpouseAvailable()==1){
                         processpouseDetails(row1,cl.get(i).getRid(),cl.get(i)) ;
                      }if (cl.get(i).getChildrenAvailable()==1){
                          processChildDetails(row1,cl.get(i).getRid(),cl.get(i));
                      }if (cl.get(i).getParentAvailable()==1){
                          processparentDetails(row1,cl.get(i).getRid(),cl.get(i));
                      }
                       updateProcessed(cl.get(i).getRid(),cl.get(i)); 
                  rw++;
                  i++;
                  
            }
        }
    } 
    private void processClientDetails(Row row1,Clients cl){
         
    
             if (cl !=null){
                  row1.createCell(0).setCellValue(cl.getClientnames());
                 // row1.createCell(1).setCellValue(cl.getClientnames());
                  row1.createCell(3).setCellValue(dateFormat(cl.getDob()));
                  row1.createCell(4).setCellValue(cl.getDocid());
                  row1.createCell(5).setCellValue(cl.getRid());
                if (cl.getProductname().equalsIgnoreCase("cash-plan")){
                      if (cl.getSumassured()==100000){
                          row1.createCell(8).setCellValue("COVER-1");
                      }else {
                          row1.createCell(8).setCellValue("COVER-2");
                      }
                    
                     }
                else {
                     row1.createCell(8).setCellValue("UMASH-COVER");
                }
                              
                }
         
     }
   
     
     private void processChildDetails(Row row1,Integer productid,Clients cl){
         childrensJpaController  = new ChildrensJpaController();
         List<Childrens> chl = childrensJpaController.getChildrenbyproductid(productid);
         Iterator i = chl.iterator();
         int k =40;
          int p =42;
          int cvl=43;
         while (i.hasNext()) {
             Childrens child = (Childrens)i.next(); 
              row1.createCell(k).setCellValue(child.getChildName().toUpperCase());
              row1.createCell(p).setCellValue(dateFormat(child.getChildDob()));
                   if (cl.getProductname().equalsIgnoreCase("cash-plan")){
                      if (cl.getSumassured()==100000){
                          row1.createCell(cvl).setCellValue("COVER-1");
                      }else {
                          row1.createCell(cvl).setCellValue("COVER-2");
                      }
                    
                     }
                   else {
                     row1.createCell(cvl).setCellValue("UMASH-COVER");
                }
              k=k+5;
              p=p+5;
              cvl=cvl+5;
         }
         
     }
     private void processBeneficiaryDetails(Row row1,Integer productid){
         BeneficiariesJpaController beneficiariesJpaController = new BeneficiariesJpaController();
        List<Beneficiaries> ben = beneficiariesJpaController.getClientBeneficiariesUsingproductid(productid);
         
     }
     private void processparentDetails(Row row1,Integer productid,Clients cl){
       
         ParentsJpaController parentsJpaController = new ParentsJpaController();
         
         List<Parents> pp =parentsJpaController.getClientParentsbyproductid(productid);
         
          Iterator i = pp.iterator();
         int k =15;
          int p =18;
          int cvl=20;
         while (i.hasNext()) {
             Parents par = (Parents)i.next(); 
              row1.createCell(k).setCellValue(par.getParentNames().toUpperCase());
              row1.createCell(p).setCellValue(dateFormat(par.getParentDob()));
               if (cl.getProductname().equalsIgnoreCase("cash-plan")){
                      if (cl.getSumassured()==100000){
                          row1.createCell(cvl).setCellValue("COVER-1");
                      }else {
                          row1.createCell(cvl).setCellValue("COVER-2");
                      }
                    
                     }
               else {
                     row1.createCell(cvl).setCellValue("UMASH-COVER");
                }  
              k=k+6;
              p=p+6;
              cvl =cvl+6;
         }
         
     }
     
     private void updateProcessed(Integer userid,Clients clp){
   policynumber = new Policynumber();
       String ppn = policynumber.generatepolicynumberandsend(clp);
        try {
            //status id 5
            // file uploaded to TE
            
            Clients cl = new Clients();
                     cl.setStatusid(5);
                     cl.setStatusname(" Uploaded to TE ");
                     cl.setTEUploadDate(new Date());
                     cl.setPolicyid(ppn);
            clientsJpaController = new ClientsJpaController();
            clientsJpaController.statusedit(cl, userid);
        } catch (RollbackFailureException ex) {
            log.error(ex);
        } catch (Exception ex) {
             log.error(ex);
        }
     }
     private void processpouseDetails(Row row1,Integer productid,Clients cl){
          DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
         SpouseJpaController sp = new SpouseJpaController();
        Spouse spp= sp.getSpouseByProductid(productid);
        
         row1.createCell(9).setCellValue(spp.getSpousename().toUpperCase());
         row1.createCell(12).setCellValue(dateFormat(spp.getSpousedob()));
          if (cl.getProductname().equalsIgnoreCase("cash-plan")){
                      if (cl.getSumassured()==100000){
                          row1.createCell(14).setCellValue("COVER-1");
                      }else {
                          row1.createCell(14).setCellValue("COVER-2");
                      }
                    
                     }
           else {
                     row1.createCell(14).setCellValue("UMASH-COVER");
                }  
         
     }
     
      private String checkString (String str){
        String re="";
        if(str != null && !str.isEmpty()) {
            re=str;
        }
        
        return re;
        
    }
      private String dateFormat(String d){
           try {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate = df.parse(d);      
            Format formatter = new SimpleDateFormat("yyyyMMdd");
            d = formatter.format(startDate);
          
        } catch (ParseException ex) {
            d="";
        }
         return d; 
      }
      
      private String generateUnameAndPassword(String docid,Clients client2){
          
          String pwd=docid;
          
          createuser(client2);
          return pwd;
      };
      
      private void createuser(Clients client2){
          System.out.println(" Creating User ...");
        try {
              appusersJpaController   = new AppusersJpaController();
         Appusers user=  appusersJpaController.findUserByClientis(client2.getDocid());
         if (user==null){
            Appusers appu = new Appusers();
            appu.setClientid(client2.getDocid());
            appu.setUsername(client2.getDocid());
            appu.setPassword(client2.getDocid());
            appu.setPhonenumber(client2.getContacts());
            appu.setProgressstatusid(1);
            appu.setProgressstatusname("New Registration");
            appu.setCreatedby("System");
            appu.setDateCreated(new Date());
            appu.setStatusid(1);
            appu.setStatusname("Enabled");
            appu.setUserrole("client");
            appusersJpaController.create(appu);
         }
         else {
             System.out.println("User Already Exists");
             System.out.println("No need to Create One");
         }
        } catch (Exception ex) {
            
            System.out.println("Error Creating User");
            ex.printStackTrace();
             log.error(ex);
        }
                                 
      }
   private void saveTeupload(String filename,String uploadedby){
        try {
            TeuploadsJpaController tec = new TeuploadsJpaController();
            Teuploads te = new Teuploads();
            te.setFilename(filename);
            te.setProcessedby(uploadedby);
            te.setUploaddate(new Date());
            
            tec.create(te);
        } catch (Exception ex) {
            Logger.getLogger(PortalService.class.getName()).log(Level.SEVERE, null, ex);
        }
   }   
     
}
