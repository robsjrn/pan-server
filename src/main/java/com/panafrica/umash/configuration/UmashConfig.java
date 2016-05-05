/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.configuration;

import com.panafrica.umash.jms.Consumer;
import com.panafrica.umash.jms.Producer;
import com.panafrica.umash.services.SendMail;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;


public class UmashConfig implements ServletContextListener {
    
      private static org.apache.logging.log4j.Logger log = LogManager.getLogger(UmashConfig.class);  
   private static EntityManagerFactory emf; 
   private static Appconstants appc;
           Consumer iprs;
           Producer prod;
    SendMail sendMail; 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      System.out.println("Initializing com.panafrica_Umash_war_1PU");
        emf = Persistence.createEntityManagerFactory("com.panafrica_Umash_war_1PU"); 
        loadUmashconfiguration();
        try {
            iprs = new Consumer();
            iprs.setupMessageQueueConsumer();
           
        }catch (Exception ex){
           System.out.println("Error Initialising Iprs  Queue Connection Kindly Note you Will not ble to Process Iprs requests "); 
           sendMail = new SendMail();
            log.error(ex);
            sendMail.SendSystemStatus(" Error Initialising Iprs  Consumer Queue Connection ", ex);
        }
    
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       emf.close(); 
       log.debug("Closing Consumer Connection");
      try {
       iprs.CloseConnections();
      }catch(Exception ex){
           sendMail = new SendMail();
            log.error(ex);
            sendMail.SendSystemStatus(" Application Shutdown :Unable to Close Iprs Connection ", ex);
      }
      // prod.closeConn();
    }
     public static EntityManager createEntityManager() {  
       
       if (emf == null) {  
             emf = Persistence.createEntityManagerFactory("com.panafrica_Umash_war_1PU"); 
       }
        return emf.createEntityManager();  
    }  
     
   private void loadUmashconfiguration(){
           System.out.println(" Initializing Configurations... ");    
           String configpath ="C:\\umash\\config\\config.xml"; 
           Configdata conf = new Configdata();
           
              try {
              XMLConfiguration config = new XMLConfiguration(configpath);
                                 conf.setApikey(config.getString("portal.apikey"));
                                 conf.setTokenIssuer(config.getString("portal.tokenIssuer"));
                                 conf.setTokenTtl(config.getString("portal.tokenTtl"));
                                 conf.setLog4jconfigpath(config.getString("portal.logXmlPath"));
                                 conf.setDebugFile(config.getString("portal.DebugFile"));
                                 conf.setInfoLogFile(config.getString("portal.InfoLogFile"));
                                 conf.setErrorLogFile(config.getString("portal.ErrorLogFile"));                               
                                 conf.setDownloaddir(config.getString("portal.filedownloadPath"));
                                 conf.setUploaddir(config.getString("portal.fileUploadPath"));
                                 conf.setMessageBrokerUrl(config.getString("portal.messageBrokerUrl"));
                                 conf.setReplyQueue(config.getString("portal.ReplyQueue"));
                                 conf.setClientQueueName(config.getString("portal.clientQueueName"));                                
                                 conf.setBancagroupemail(config.getString("portal.Bancagroupemail"));
                                 conf.setITgroupemail(config.getString("portal.ITgroupemail"));
                                 conf.setAppEmailaddress(config.getString("portal.AppEmailaddress"));
                                 conf.setAppEmailhost(config.getString("portal.AppEmailhost"));
                                 conf.setAppEmailport(config.getString("portal.AppEmailport"));
                                 conf.setTtlMillis(config.getString("portal.TtlMillis"));
                                 
                                 
              
               } catch(Exception ex){
                   
               }
          appc.setConfigdata(conf);
   }
}
