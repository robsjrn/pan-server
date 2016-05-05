/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Clients;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author User
 */
public class ClientsJpaController implements Serializable {


    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public Integer  create(Clients clients) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Integer cl=0;
        try {
            tx.begin(); 
           validator(clients);
            em.persist(clients);
             em.flush();
            tx.commit();
           
            cl=clients.getRid();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                tx.rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return cl;
    }
    public Clients iprsedit(Clients clients,Integer rid) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
         Clients client=null;
        try {
             client = findClients(rid);
           
             
                 
               
                if (client==null){
                  //member does not exist Create new Member
                  System.out.println("Client Does Not Exist ..Creating One");
                  //clients.setStatusname(docid);
                 // create(clients);
                  
              }else {
            
                  tx.begin();

                             client.setClientnames(clients.getClientnames());
                             client.setPhoto(clients.getPhoto());                   
                              client.setStatusid(clients.getStatusid());
                             client.setStatusname(clients.getStatusname());
                              client = em.merge(client);
                   em.flush();
                    tx.commit();
                                
                }
           
           
     
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getRid();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return client;
    }
    
        public Clients policydetails(String policyid,Integer rid) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
         Clients client=null;
        try {
             client = findClients(rid);
           
             
                 
               
                if (client!=null){
                          
            
                  tx.begin();

                            client.setPolicyid(policyid);
                              client = em.merge(client);
                   em.flush();
                    tx.commit();
                                
                }
           
           
     
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rid;
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return client;
    }
    
    
     public Clients statusedit(Clients clients,Integer userid) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
         Clients client;
        try {
                client= findClients(userid);
                if (client==null){
                 
                  
              }else {
            
            tx.begin();                           
             client.setStatusid(clients.getStatusid());
            client.setStatusname(clients.getStatusname()); 
            client.setTEUploadDate(clients.getTEUploadDate());
            client.setPolicyid(clients.getPolicyid());
             client = em.merge(client);
             em.flush();
            tx.commit();
          
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getRid();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return client;
    }
    

    public Clients edit(Clients clients,String docid) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
         Clients client;
        try {
                client= findClient(docid);
                if (client==null){
                  //member does not exist Create new Member
             
                  //clients.setStatusname(docid);
                 // create(clients);
                  
              }else {
            
            tx.begin();
          
                     
            
                    client.setPolicyConfirmedBy(clients.getPolicyConfirmedBy());
                   client.setPolicycomment(clients.getPolicyid());
                   client.setPolicyconfirmedDate(clients.getPolicyconfirmedDate());
                   client.setPolicyid(clients.getPolicyid());
                   client.setPolicystatus(clients.getPolicystatus());
                   client.setStatusid(clients.getStatusid());
            validator(client);
             client = em.merge(client);
             em.flush();
            tx.commit();
           
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getRid();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return client;
    }
    
     public Clients editTrxn(Clients clients,String docid,String trxnid) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
         Clients client;
        try {
                client= getClientUsingIdandTrxnid(docid,trxnid);
                if (client==null){
                  //member does not exist Create new Member
             
                  //clients.setStatusname(docid);
                 // create(clients);
                  
              }else {
            
            tx.begin();
          
                     
            
                    client.setPolicyConfirmedBy(clients.getPolicyConfirmedBy());
                   client.setPolicycomment(clients.getPolicyid());
                   client.setPolicyconfirmedDate(clients.getPolicyconfirmedDate());
                   client.setPolicyid(clients.getPolicyid());
                   client.setPolicystatus(clients.getPolicystatus());
                   client.setStatusid(clients.getStatusid());
            validator(client);
             client = em.merge(client);
             em.flush();
            tx.commit();
           
                }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clients.getRid();
                if (findClients(id) == null) {
                    throw new NonexistentEntityException("The clients with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return client;
    }


    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em.getTransaction().begin();
            em = getEntityManager();
            Clients clients;
            try {
                clients = em.getReference(Clients.class, id);
                clients.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clients with id " + id + " no longer exists.", enfe);
            }
            em.remove(clients);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clients> findClientsEntities() {
        return findClientsEntities(true, -1, -1);
    }

    public List<Clients> findClientsEntities(int maxResults, int firstResult) {
        return findClientsEntities(false, maxResults, firstResult);
    }

    private List<Clients> findClientsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clients.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Clients findClients(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clients.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clients> rt = cq.from(Clients.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public Clients findClient(String docid){
            Clients cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByDocid", Clients.class)
                    .setParameter("docid", docid)
                    .getSingleResult();      
        } 
         catch(Exception ex){
            ex.printStackTrace();
          System.out.println("Client Does Not Exist ..");
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
        
           public List<Clients>  findClients(String docid){
            List<Clients>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByDocid", Clients.class)
                    .setParameter("docid", docid)
                    .getResultList();
        } 
         catch(Exception ex){
          //   ex.printStackTrace();
          System.out.println("Client Does Not Exist ..");
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
          public Clients findClientByContact(String contact){
            Clients cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByContacts", Clients.class)
                    .setParameter("contacts", contact)
                    .getSingleResult();      
        } 
         catch(Exception ex){
            ex.printStackTrace();
          System.out.println("Client Does Not Exist ..");
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
          
           public List<Clients> findClientsByContact(String contact){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByContacts", Clients.class)
                    .setParameter("contacts", contact)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Client Does Not Exist ..");
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
           
    public List<Clients> getnewRequests(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByStatusid", Clients.class)
                    .setParameter("statusid", 1)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
    
    public List<Clients> getregisteredclients(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clients.findByStatusid", Clients.class)
                    .setParameter("statusid", 4)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
    
    public List<Clients> getUnpaidRequests(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNativeQuery("Select * from clients where paymentstatus ='1' and statusid = '2' ", Clients.class)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
    
     public List<Clients> Overpayments(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNativeQuery("Select * from clients where statusid = '10' ", Clients.class)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
     
      public List<Clients> getUnderpayments(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNativeQuery("Select * from clients where  statusid = '11' ", Clients.class)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
    
    
    
        public Clients getClientUsingIdandTrxnid(String docid,String trxnid){
           Clients cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= (Clients)em.createNativeQuery("Select * from clients where docid =? and paymentid = ? ", Clients.class)
                    .setParameter(1, docid)
                   .setParameter(2, trxnid)
                    .getSingleResult();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
    
    
    public int umashCount(){
          int no=0;
          EntityManager em = getEntityManager();
         try {
            
           no= em.createNativeQuery("Select * from clients where productname ='umash' and statusid='1' ", Clients.class)
                    .getResultList().size();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return no;
        
    }
    
     public int cashplanCount(){
          int no=0;
          EntityManager em = getEntityManager();
         try {
            
           no= em.createNativeQuery("Select * from clients where productname ='cash-plan' and statusid='1' ", Clients.class)
                    .getResultList().size();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return no;
        
    }
        public List<Clients> getSuccessfulRegistration(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNativeQuery("Select * from clients where paymentstatus ='2' and statusid = '3' ", Clients.class)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
        
         public List<Clients> getTEUploads(){
            List<Clients> cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNativeQuery("Select * from clients where paymentstatus ='2' and statusid = '4' and policystatus='1' ", Clients.class)
                    .getResultList();
        } 
         catch(Exception ex){
            ex.printStackTrace();
          
                 }
         finally {
          
            em.close();
             
         }
         return cli;
    }
         
         
        
        private void validator(Clients clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Clients>> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Clients> contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
    
}
