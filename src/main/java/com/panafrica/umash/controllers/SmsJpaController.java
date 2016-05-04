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
import com.panafrica.umash.model.Sms;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
public class SmsJpaController implements Serializable {

     public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Sms sms) throws RollbackFailureException, Exception {
                EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
           
           // validator(sms);
            em.persist(sms);
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

    public void edit(Sms sms) throws NonexistentEntityException, RollbackFailureException, Exception {
             EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em = getEntityManager();
            sms = em.merge(sms);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sms.getTid();
                if (findSms(id) == null) {
                    throw new NonexistentEntityException("The sms with id " + id + " no longer exists.");
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
            Sms sms;
            try {
                sms = em.getReference(Sms.class, id);
                sms.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sms with id " + id + " no longer exists.", enfe);
            }
            em.remove(sms);
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

    public List<Sms> findSmsEntities() {
        return findSmsEntities(true, -1, -1);
    }

    public List<Sms> findSmsEntities(int maxResults, int firstResult) {
        return findSmsEntities(false, maxResults, firstResult);
    }

    private List<Sms> findSmsEntities(boolean all, int maxResults, int firstResult) {
            EntityManager em = getEntityManager();
      
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sms.class));
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

    public Sms findSms(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sms.class, id);
        } finally {
            em.close();
        }
    }

    public int getSmsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sms> rt = cq.from(Sms.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     private void validator(Sms sms){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Sms>> constraintViolations = validator.validate(sms);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Sms> contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
    
}
