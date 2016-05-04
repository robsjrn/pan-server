/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Ussdinquiries;
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
public class UssdinquiriesJpaController implements Serializable {


      public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Ussdinquiries ussdinquiries) throws RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(ussdinquiries);
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

    public void edit(Ussdinquiries ussdinquiries) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            ussdinquiries = em.merge(ussdinquiries);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ussdinquiries.getTid();
                if (findUssdinquiries(id) == null) {
                    throw new NonexistentEntityException("The ussdinquiries with id " + id + " no longer exists.");
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
            Ussdinquiries ussdinquiries;
            try {
                ussdinquiries = em.getReference(Ussdinquiries.class, id);
                ussdinquiries.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ussdinquiries with id " + id + " no longer exists.", enfe);
            }
            em.remove(ussdinquiries);
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

    public List<Ussdinquiries> findUssdinquiriesEntities() {
        return findUssdinquiriesEntities(true, -1, -1);
    }

    public List<Ussdinquiries> findUssdinquiriesEntities(int maxResults, int firstResult) {
        return findUssdinquiriesEntities(false, maxResults, firstResult);
    }

    private List<Ussdinquiries> findUssdinquiriesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ussdinquiries.class));
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

    public Ussdinquiries findUssdinquiries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ussdinquiries.class, id);
        } finally {
            em.close();
        }
    }

    public int getUssdinquiriesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ussdinquiries> rt = cq.from(Ussdinquiries.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
