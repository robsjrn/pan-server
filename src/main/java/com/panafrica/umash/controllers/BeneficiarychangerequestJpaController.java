/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Beneficiarychangerequest;
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
public class BeneficiarychangerequestJpaController implements Serializable {

      public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Beneficiarychangerequest beneficiarychangerequest) throws RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(beneficiarychangerequest);
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

    public void edit(Beneficiarychangerequest beneficiarychangerequest) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            beneficiarychangerequest = em.merge(beneficiarychangerequest);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = beneficiarychangerequest.getTid();
                if (findBeneficiarychangerequest(id) == null) {
                    throw new NonexistentEntityException("The beneficiarychangerequest with id " + id + " no longer exists.");
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
            Beneficiarychangerequest beneficiarychangerequest;
            try {
                beneficiarychangerequest = em.getReference(Beneficiarychangerequest.class, id);
                beneficiarychangerequest.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The beneficiarychangerequest with id " + id + " no longer exists.", enfe);
            }
            em.remove(beneficiarychangerequest);
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

    public List<Beneficiarychangerequest> findBeneficiarychangerequestEntities() {
        return findBeneficiarychangerequestEntities(true, -1, -1);
    }

    public List<Beneficiarychangerequest> findBeneficiarychangerequestEntities(int maxResults, int firstResult) {
        return findBeneficiarychangerequestEntities(false, maxResults, firstResult);
    }

    private List<Beneficiarychangerequest> findBeneficiarychangerequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Beneficiarychangerequest.class));
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
        public List<Beneficiarychangerequest> findBeneficiaryAddchangerequest(){
             EntityManager em = getEntityManager();
             List<Beneficiarychangerequest> cli=null;
        try {
                cli= em.createNativeQuery("select * from beneficiarychangerequest where stagename='Add' and stageid=1 ", Beneficiarychangerequest.class)
                    .getResultList();
        } finally {
            em.close();
        }
        return cli;
        }
        
        public List<Beneficiarychangerequest> findBeneficiaryEditchangerequest(){
             EntityManager em = getEntityManager();
             List<Beneficiarychangerequest> cli=null;
        try {
                cli= em.createNativeQuery("select * from beneficiarychangerequest where stagename='Edit' and stageid=1 ", Beneficiarychangerequest.class)
                    .getResultList();
        } finally {
            em.close();
        }
        return cli;
        }


    public Beneficiarychangerequest findBeneficiarychangerequest(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Beneficiarychangerequest.class, id);
        } finally {
            em.close();
        }
    }

    public int getBeneficiarychangerequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Beneficiarychangerequest> rt = cq.from(Beneficiarychangerequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
