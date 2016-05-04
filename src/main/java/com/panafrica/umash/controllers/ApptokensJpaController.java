/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.ExceptionHandling.exceptions.NonexistentEntityException;
import com.panafrica.umash.ExceptionHandling.exceptions.PreexistingEntityException;
import com.panafrica.umash.ExceptionHandling.exceptions.RollbackFailureException;
import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.model.Apptokens;
import com.panafrica.umash.model.Appusers;
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
public class ApptokensJpaController implements Serializable {

   public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }
    public void create(Apptokens apptokens) throws PreexistingEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            validator(apptokens);
            em.persist(apptokens);
            utx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findApptokens(apptokens.getTid()) != null) {
                throw new PreexistingEntityException("Apptokens " + apptokens + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Apptokens apptokens) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();

            apptokens = em.merge(apptokens);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = apptokens.getTid();
                if (findApptokens(id) == null) {
                    throw new NonexistentEntityException("The apptokens with id " + id + " no longer exists.");
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
            Apptokens apptokens;
            try {
                apptokens = em.getReference(Apptokens.class, id);
                apptokens.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The apptokens with id " + id + " no longer exists.", enfe);
            }
            em.remove(apptokens);
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

    public List<Apptokens> findApptokensEntities() {
        return findApptokensEntities(true, -1, -1);
    }

    public List<Apptokens> findApptokensEntities(int maxResults, int firstResult) {
        return findApptokensEntities(false, maxResults, firstResult);
    }

    private List<Apptokens> findApptokensEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Apptokens.class));
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

    public Apptokens findApptokens(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Apptokens.class, id);
        } finally {
            em.close();
        }
    }

    public int getApptokensCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Apptokens> rt = cq.from(Apptokens.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
         private void validator(Apptokens clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Apptokens >> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Apptokens > contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
    
    
}
