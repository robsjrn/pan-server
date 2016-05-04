/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Spouse;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author User
 */
public class SpouseJpaController implements Serializable {

      public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }


    public void create(Spouse spouse) throws RollbackFailureException, Exception {
         EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(spouse);
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

    public void edit(Spouse spouse) throws NonexistentEntityException, RollbackFailureException, Exception {
            EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            spouse = em.merge(spouse);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spouse.getTid();
                if (findSpouse(id) == null) {
                    throw new NonexistentEntityException("The spouse with id " + id + " no longer exists.");
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
            Spouse spouse;
            try {
                spouse = em.getReference(Spouse.class, id);
                spouse.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spouse with id " + id + " no longer exists.", enfe);
            }
            em.remove(spouse);
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

    public List<Spouse> findSpouseEntities() {
        return findSpouseEntities(true, -1, -1);
    }

    public List<Spouse> findSpouseEntities(int maxResults, int firstResult) {
        return findSpouseEntities(false, maxResults, firstResult);
    }

    private List<Spouse> findSpouseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spouse.class));
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

    public Spouse findSpouse(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spouse.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpouseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spouse> rt = cq.from(Spouse.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Spouse  getSpouseByProductid(Integer productid){
        
          Spouse  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli=(Spouse) em.createNamedQuery("Spouse.findByProductid", Spouse.class)
                    .setParameter("productid", productid)
                    .getSingleResult();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    
}
