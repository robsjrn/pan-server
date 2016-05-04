/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Childrens;
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
public class ChildrensJpaController implements Serializable {

   @Resource 
    UserTransaction utx;
    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Childrens childrens) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(childrens);
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
    
    public void Createlist(List<Childrens> childrens) throws RollbackFailureException, Exception{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();  
            // iterarte here 
            Iterator i = childrens.iterator();
            while (i.hasNext()) {
                 Childrens child = (Childrens)i.next(); 
                 validator(child);
                 em.persist(child);
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

    public void edit(Childrens childrens) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            childrens = em.merge(childrens);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = childrens.getRid();
                if (findChildrens(id) == null) {
                    throw new NonexistentEntityException("The childrens with id " + id + " no longer exists.");
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
            Childrens childrens;
            try {
                childrens = em.getReference(Childrens.class, id);
                childrens.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The childrens with id " + id + " no longer exists.", enfe);
            }
            em.remove(childrens);
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

    public List<Childrens> findChildrensEntities() {
        return findChildrensEntities(true, -1, -1);
    }

    public List<Childrens> findChildrensEntities(int maxResults, int firstResult) {
        return findChildrensEntities(false, maxResults, firstResult);
    }

    private List<Childrens> findChildrensEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Childrens.class));
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

    public Childrens findChildrens(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Childrens.class, id);
        } finally {
            em.close();
        }
    }

    public int getChildrensCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Childrens> rt = cq.from(Childrens.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public List<Childrens>  getClientParents(String clientid){
        
          List<Childrens>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Childrens.findByClientid", Childrens.class)
                    .setParameter("clientid", clientid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
     
      public List<Childrens>  getChildrenbyproductid(Integer productid){
        
          List<Childrens>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Childrens.findByProductid", Childrens.class)
                    .setParameter("productid", productid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
     private void validator(Childrens clients){
                                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        Validator validator = factory.getValidator();

                        Set<ConstraintViolation<Childrens>> constraintViolations = validator.validate(clients);

                        if (constraintViolations.size() > 0 ) {
                        System.out.println("Constraint Violations occurred..");
                        for (ConstraintViolation<Childrens> contraints : constraintViolations) {
                        System.out.println(contraints.getRootBeanClass().getSimpleName()+
                        "." + contraints.getPropertyPath() + " " + contraints.getMessage());
                          }
                        }
        }
    
}
