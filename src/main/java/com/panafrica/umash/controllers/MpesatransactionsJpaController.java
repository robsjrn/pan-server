/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Mpesatransactions;
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
public class MpesatransactionsJpaController implements Serializable {

   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Mpesatransactions mpesatransactions) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(mpesatransactions);
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

    public void edit(Mpesatransactions mpesatransactions) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
      
            mpesatransactions = em.merge(mpesatransactions);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mpesatransactions.getMpesarid();
                if (findMpesatransactions(id) == null) {
                    throw new NonexistentEntityException("The mpesatransactions with id " + id + " no longer exists.");
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
        
            Mpesatransactions mpesatransactions;
            try {
                mpesatransactions = em.getReference(Mpesatransactions.class, id);
                mpesatransactions.getMpesarid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mpesatransactions with id " + id + " no longer exists.", enfe);
            }
            em.remove(mpesatransactions);
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

    public List<Mpesatransactions> findMpesatransactionsEntities() {
        return findMpesatransactionsEntities(true, -1, -1);
    }

    public List<Mpesatransactions> findMpesatransactionsEntities(int maxResults, int firstResult) {
        return findMpesatransactionsEntities(false, maxResults, firstResult);
    }

    private List<Mpesatransactions> findMpesatransactionsEntities(boolean all, int maxResults, int firstResult) {
         EntityManager em = getEntityManager();
     
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mpesatransactions.class));
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

    public Mpesatransactions findMpesatransactions(Integer id) {
        EntityManager em = getEntityManager();
     
        try {
            return em.find(Mpesatransactions.class, id);
        } finally {
            em.close();
        }
    }

    public int getMpesatransactionsCount() {
       EntityManager em = getEntityManager();
       
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mpesatransactions> rt = cq.from(Mpesatransactions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
