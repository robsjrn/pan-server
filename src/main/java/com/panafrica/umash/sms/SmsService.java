/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.sms;

import com.panafrica.umash.controllers.SmsJpaController;
import com.panafrica.umash.controllers.SmserrosJpaController;
import com.panafrica.umash.model.Sms;
import com.panafrica.umash.model.Smserros;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

/**
 *
 * @author User
 */
public class SmsService {
    
    SmsJpaController smsJpaController;
    Sms sms;
    
      public void sendSms(String number,String msg)  {
          
     try {
         
         SSLContextBuilder builder = new SSLContextBuilder();
         builder.loadTrustMaterial(null, new TrustStrategy() {
             @Override
             public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                 return true;
             }

            
         });
         
         SSLConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(builder.build(),
                 SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
         
         CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslSF).build();
         CloseableHttpResponse response=null ;
         try {
             URI uri;
             try {
                 uri = new URIBuilder()
                         .setScheme("https")
                         .setHost("192.168.0.29")
                         .setPath("/websms.jsp")
                         .setParameter("uname", "umash")
                         .setParameter("passwd", "Pass098!")
                         .setParameter("dest", number)
                         .setParameter("source", "PanAfrica")
                         .setParameter("msg", msg)
                         
                         .build();
                 
                 
                 
                 HttpGet httpget = new HttpGet(uri);
                 
                 
                 System.out.println(httpget.getURI());
                 
                 response = httpclient.execute(httpget);
                 
                 smsJpaController = new SmsJpaController();
                 sms = new Sms();
                    sms.setMsgText(msg);
                    sms.setNumber(number);
                    sms.setStatusId(response.getStatusLine().getStatusCode());
                    sms.setStatusDesc(response.getStatusLine().getReasonPhrase());
                    sms.setSmsdate(new Date());
                    smsJpaController.create(sms);
                 
                 System.out.println("Sms Response is " + response);
             } catch (URISyntaxException ex) {
                 ex.printStackTrace();
                  smsError(number,msg,ex.getMessage());
             }
         } catch (IOException ex) {
             ex.printStackTrace();
              smsError(number,msg,ex.getMessage());
         } 
         catch (Exception ex) {
                 ex.printStackTrace();
                  smsError(number,msg,ex.getMessage());
             }
         finally {
             if (response != null) {
                 try {
                     response.close();
                 } catch (IOException ex) {
                     Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }
         
     } catch (NoSuchAlgorithmException ex) {
         smsError(number,msg,ex.getMessage());
              Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
          } catch (KeyStoreException ex) {
              Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
          } catch (KeyManagementException ex) {
              Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
          }
     
      }
      
      private void smsError(String contact,String msg,String em){
        try {
            SmserrosJpaController smc= new SmserrosJpaController();
            Smserros sme = new Smserros ();
            sme.setContact(contact);
            sme.setMessage(msg);
            sme.setSmsdate(new Date());
            sme.setStatusname(em);
            sme.setStatusid(1);
            
            smc.create(sme);
        } catch (Exception ex) {
            Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
}
    

