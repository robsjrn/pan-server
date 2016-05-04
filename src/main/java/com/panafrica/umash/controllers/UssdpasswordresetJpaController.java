/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Appusers;
import com.panafrica.umash.model.Ussdpasswordreset;
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
public class UssdpasswordresetJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }
    public void create(Ussdpasswordreset ussdpasswordreset) throws RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
          //  validator(ussdpasswordreset);
            em.persist(ussdpasswordreset);
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

    public void edit(Ussdpasswordreset ussdpasswordreset) throws NonexistentEntityException, RollbackFailureException, Exception {
          EntityManager em = getEntityManager();
        EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            ussdpasswordreset = em.merge(ussdpasswordreset);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ussdpasswordreset.getTid();
                if (findUssdpasswordreset(id) == null) {
                    throw new NonexistentEntityException("The ussdpasswordreset with id " + id + " no longer exists.");
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
            Ussdpasswordreset ussdpasswordreset;
            try {
                ussdpasswordreset = em.getReference(Ussdpasswordreset.class, id);
                ussdpasswordreset.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ussdpasswordreset with id " + id + " no longer exists.", enfe);
            }
            em.remove(ussdpasswordreset);
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

    public List<Ussdpasswordreset> findUssdpasswordresetEntities() {
        return findUssdpasswordresetEntities(true, -1, -1);
    }

    public List<Ussdpasswordreset> findUssdpasswordresetEntities(int maxResults, int firstResult) {
        return findUssdpasswordresetEntities(false, maxResults, firstResult);
    }

    private List<Ussdpasswordreset> findUssdpasswordresetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ussdpasswordreset.class));
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

    public Ussdpasswordreset findUssdpasswordreset(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ussdpasswordreset.class, id);
        } finally {
            em.close();
        }
    }

    public int getUssdpasswordresetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ussdpasswordreset> rt = cq.from(Ussdpasswordreset.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
     private void validator(Ussdpasswordreset clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Ussdpasswordreset >> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Ussdpasswordreset > contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
}
