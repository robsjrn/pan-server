/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Ussdtemp;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * @author User
 */
public class UssdtempJpaController implements Serializable {

   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Ussdtemp ussdtemp) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(ussdtemp);
            utx.commit();
        } catch (Exception ex) {
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

    public void edit(Ussdtemp ussdtemp) throws NonexistentEntityException, RollbackFailureException, Exception {
         EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            ussdtemp = em.merge(ussdtemp);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ussdtemp.getTid();
                if (findUssdtemp(id) == null) {
                    throw new NonexistentEntityException("The ussdtemp with id " + id + " no longer exists.");
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
            Ussdtemp ussdtemp;
            try {
                ussdtemp = em.getReference(Ussdtemp.class, id);
                ussdtemp.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ussdtemp with id " + id + " no longer exists.", enfe);
            }
            em.remove(ussdtemp);
            utx.commit();
        } catch (Exception ex) {
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

    public List<Ussdtemp> findUssdtempEntities() {
        return findUssdtempEntities(true, -1, -1);
    }

    public List<Ussdtemp> findUssdtempEntities(int maxResults, int firstResult) {
        return findUssdtempEntities(false, maxResults, firstResult);
    }

    private List<Ussdtemp> findUssdtempEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ussdtemp.class));
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

    public Ussdtemp findUssdtemp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ussdtemp.class, id);
        } finally {
            em.close();
        }
    }

    public int getUssdtempCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ussdtemp> rt = cq.from(Ussdtemp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Ussdtemp  getUnprocessedData(){
         Ussdtemp ussdtemp=null;
          String querry = "SELECT * FROM umashdb.ussdtemp where statusid =1 LIMIT 1" ;
         EntityManager em = getEntityManager();
        try {           
              ussdtemp=   (Ussdtemp ) em.createNativeQuery(querry,Ussdtemp.class)
                        .getSingleResult();
                          
             
        }catch(Exception ex){
            
        } 
        
        finally {
            em.close();
        }
        
        return ussdtemp;
         
     }
     
     
    
    
}
