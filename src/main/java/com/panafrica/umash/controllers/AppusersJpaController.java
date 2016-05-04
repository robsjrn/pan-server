/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.jms.Consumer;
import com.panafrica.umash.model.Appusers;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author User
 */
public class AppusersJpaController implements Serializable {
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(AppusersJpaController.class);
   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }
   
    public void create(Appusers appusers) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
           // validator(appusers);
            em.persist(appusers);
            utx.commit();
        } catch (Exception ex) {
            log.error(ex);
            try {
                utx.rollback();
            } catch (Exception re) {
                log.error(re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Appusers appusers) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            appusers = em.merge(appusers);
            utx.commit();
        } catch (Exception ex) {
            log.error(ex);
            try {
                utx.rollback();
            } catch (Exception re) {
                log.error(re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = appusers.getRid();
                if (findAppusers(id) == null) {
                    throw new NonexistentEntityException("The appusers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            Appusers appusers;
            try {
                appusers = em.getReference(Appusers.class, id);
                appusers.getRid();
            } catch (EntityNotFoundException enfe) {
                log.error(enfe);
                throw new NonexistentEntityException("The appusers with id " + id + " no longer exists.", enfe);
            }
            em.remove(appusers);
            utx.commit();
        } catch (Exception ex) {
            log.error(ex);
            try {
                utx.rollback();
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

    public List<Appusers> findAppusersEntities() {
        return findAppusersEntities(true, -1, -1);
    }

    public List<Appusers> findAppusersEntities(int maxResults, int firstResult) {
        return findAppusersEntities(false, maxResults, firstResult);
    }

    private List<Appusers> findAppusersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appusers.class));
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

    public Appusers findAppusers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appusers.class, id);
        } finally {
            em.close();
        }
    }
    
     public Appusers findUser(String username){
            Appusers cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Appusers.findByUsername", Appusers.class)
                    .setParameter("username", username)
                    .getSingleResult();    
          
        } 
         catch(Exception ex){
         log.error(ex);   
        }finally {
            em.close();
        }
         return cli;
    }
     
      public Appusers findUserByClientis(String clientid){
            Appusers cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Appusers.findByClientid", Appusers.class)
                    .setParameter("clientid", clientid)
                    .getSingleResult();    
          
        }catch(Exception ex){
            System.out.println("Client " + clientid + " does not Exist in Appuser");
            log.error(ex);
        } 
         finally {
            em.close();
        }
         return cli;
    }
      
      
      public Appusers findUserByContact(String contact){
            Appusers cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Appusers.findByPhonenumber", Appusers.class)
                    .setParameter("phonenumber", contact)
                    .getSingleResult();    
          
        }catch(Exception ex){
            log.error(ex);
        } 
         finally {
            em.close();
        }
         return cli;
    }

    public int getAppusersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Appusers> rt = cq.from(Appusers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     private void validator(Appusers clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Appusers >> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Appusers > contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
    
}
