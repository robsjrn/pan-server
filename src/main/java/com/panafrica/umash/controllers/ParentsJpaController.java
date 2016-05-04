/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Parents;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
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
public class ParentsJpaController implements Serializable {

  @Resource 
    UserTransaction utx;
    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Parents parents) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(parents);
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
    
    public void Createlist(List<Parents> parents) throws RollbackFailureException, Exception{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();  
            // iterarte here 
            Iterator i = parents.iterator();
            while (i.hasNext()) {
                 Parents parent = (Parents)i.next();
                 validator(parent);
                 em.persist(parent);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                em.getTransaction().rollback();
            } catch (Exception re) {
                re.printStackTrace();
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parents parents) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            parents = em.merge(parents);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parents.getRid();
                if (findParents(id) == null) {
                    throw new NonexistentEntityException("The parents with id " + id + " no longer exists.");
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
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Parents parents;
            try {
                parents = em.getReference(Parents.class, id);
                parents.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parents with id " + id + " no longer exists.", enfe);
            }
            em.remove(parents);
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

    public List<Parents> findParentsEntities() {
        return findParentsEntities(true, -1, -1);
    }

    public List<Parents> findParentsEntities(int maxResults, int firstResult) {
        return findParentsEntities(false, maxResults, firstResult);
    }

    private List<Parents> findParentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parents.class));
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

    public Parents findParents(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parents.class, id);
        } finally {
            em.close();
        }
    }

    public int getParentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parents> rt = cq.from(Parents.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Parents>  getClientParents(String clientid){
        
          List<Parents>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Parents.findByClientid", Parents.class)
                    .setParameter("clientid", clientid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    
    
     public List<Parents>  getClientParentsbyproductid(Integer productid){
        
          List<Parents>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Parents.findByProductid", Parents.class)
                    .setParameter("productid", productid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    private void validator(Parents clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Parents>> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Parents> contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        } 
    
}
