/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Beneficiaries;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author User
 */
public class BeneficiariesJpaController implements Serializable {

   @Resource 
    UserTransaction utx;
    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Beneficiaries beneficiaries) throws RollbackFailureException, Exception {
     EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();      
            em.persist(beneficiaries);
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

    public void edit(Beneficiaries beneficiaries) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            beneficiaries = em.merge(beneficiaries);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = beneficiaries.getRid();
                if (findBeneficiaries(id) == null) {
                    throw new NonexistentEntityException("The beneficiaries with id " + id + " no longer exists.");
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
            Beneficiaries beneficiaries;
            try {
                beneficiaries = em.getReference(Beneficiaries.class, id);
                beneficiaries.getRid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The beneficiaries with id " + id + " no longer exists.", enfe);
            }
            em.remove(beneficiaries);
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

    public List<Beneficiaries> findBeneficiariesEntities() {
        return findBeneficiariesEntities(true, -1, -1);
    }

    public List<Beneficiaries> findBeneficiariesEntities(int maxResults, int firstResult) {
        return findBeneficiariesEntities(false, maxResults, firstResult);
    }

    private List<Beneficiaries> findBeneficiariesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Beneficiaries.class));
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

    public Beneficiaries findBeneficiaries(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Beneficiaries.class, id);
        } finally {
            em.close();
        }
    }

    public int getBeneficiariesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Beneficiaries> rt = cq.from(Beneficiaries.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Beneficiaries>  getClientBeneficiaries(String clientid){
        
          List<Beneficiaries>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Beneficiaries.findByClientid", Beneficiaries.class)
                    .setParameter("clientid", clientid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
       public Beneficiaries  getClientBeneficiariesUsingproductidS(Integer productid){
        
          Beneficiaries  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Beneficiaries.findByProductid", Beneficiaries.class)
                    .setParameter("productid", productid)
                    .getSingleResult();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    public List<Beneficiaries>  getClientBeneficiariesUsingproductid(Integer productid){
        
          List<Beneficiaries>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Beneficiaries.findByProductid", Beneficiaries.class)
                    .setParameter("productid", productid)
                    .getResultList();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    public Beneficiaries getClientBeneficiary(String clientid){
        
         Beneficiaries  cli=null;    
            EntityManager em = getEntityManager();
         try {
             cli= (Beneficiaries)em.createNativeQuery("Select * from clients where docid =? and paymentid = ? ", Beneficiaries.class)
                    .setParameter("clientid", clientid)
                    .getSingleResult();
          
        } finally {
            em.close();
        }
         return cli;
        
    }
    
}
