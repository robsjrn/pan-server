/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Iprserrors;
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
public class IprserrorsJpaController implements Serializable {

   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }
    public void create(Iprserrors iprserrors) throws RollbackFailureException, Exception {
        EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
   
            em.persist(iprserrors);
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

    public void edit(Iprserrors iprserrors) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            iprserrors = em.merge(iprserrors);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = iprserrors.getTid();
                if (findIprserrors(id) == null) {
                    throw new NonexistentEntityException("The iprserrors with id " + id + " no longer exists.");
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
            em = getEntityManager();
            Iprserrors iprserrors;
            try {
                iprserrors = em.getReference(Iprserrors.class, id);
                iprserrors.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The iprserrors with id " + id + " no longer exists.", enfe);
            }
            em.remove(iprserrors);
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

    public List<Iprserrors> findIprserrorsEntities() {
        return findIprserrorsEntities(true, -1, -1);
    }

    public List<Iprserrors> findIprserrorsEntities(int maxResults, int firstResult) {
        return findIprserrorsEntities(false, maxResults, firstResult);
    }

    private List<Iprserrors> findIprserrorsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Iprserrors.class));
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

    public Iprserrors findIprserrors(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Iprserrors.class, id);
        } finally {
            em.close();
        }
    }

    public int getIprserrorsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Iprserrors> rt = cq.from(Iprserrors.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
