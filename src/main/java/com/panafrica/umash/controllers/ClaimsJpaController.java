/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Claims;
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
public class ClaimsJpaController implements Serializable {

   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Claims claims) throws RollbackFailureException, Exception {
             EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(claims);
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

    public void edit(Claims claims) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            claims = em.merge(claims);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = claims.getTid();
                if (findClaims(id) == null) {
                    throw new NonexistentEntityException("The claims with id " + id + " no longer exists.");
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
            Claims claims;
            try {
                claims = em.getReference(Claims.class, id);
                claims.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claims with id " + id + " no longer exists.", enfe);
            }
            em.remove(claims);
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

    public List<Claims> findClaimsEntities() {
        return findClaimsEntities(true, -1, -1);
    }

    public List<Claims> findClaimsEntities(int maxResults, int firstResult) {
        return findClaimsEntities(false, maxResults, firstResult);
    }
    
    public List<Claims> getUnprocessedClaims() {
        EntityManager em = getEntityManager();
             List<Claims> cli=null;
        try {
                cli= em.createNativeQuery("select * from claims where status=1 ", Claims.class)
                    .getResultList();
        } finally {
            em.close();
        }
        return cli;
    }

    private List<Claims> findClaimsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Claims.class));
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

    public Claims findClaims(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Claims.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaimsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Claims> rt = cq.from(Claims.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
