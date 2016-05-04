/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Teuploads;
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
public class TeuploadsJpaController implements Serializable {

     public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Teuploads teuploads) throws RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(teuploads);
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

    public void edit(Teuploads teuploads) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            teuploads = em.merge(teuploads);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = teuploads.getTid();
                if (findTeuploads(id) == null) {
                    throw new NonexistentEntityException("The teuploads with id " + id + " no longer exists.");
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
            Teuploads teuploads;
            try {
                teuploads = em.getReference(Teuploads.class, id);
                teuploads.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teuploads with id " + id + " no longer exists.", enfe);
            }
            em.remove(teuploads);
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

    public List<Teuploads> findTeuploadsEntities() {
        return findTeuploadsEntities(true, -1, -1);
    }

    public List<Teuploads> findTeuploadsEntities(int maxResults, int firstResult) {
        return findTeuploadsEntities(false, maxResults, firstResult);
    }

    private List<Teuploads> findTeuploadsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teuploads.class));
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

    public Teuploads findTeuploads(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teuploads.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeuploadsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teuploads> rt = cq.from(Teuploads.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
