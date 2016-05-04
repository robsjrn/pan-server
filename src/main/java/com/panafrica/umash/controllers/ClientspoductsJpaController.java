/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panafrica.umash.controllers;

import com.panafrica.umash.configuration.UmashConfig;
import com.panafrica.umash.controllers.exceptions.NonexistentEntityException;
import com.panafrica.umash.controllers.exceptions.RollbackFailureException;
import com.panafrica.umash.model.Clientspoducts;
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
public class ClientspoductsJpaController implements Serializable {

    public EntityManager getEntityManager() {
        return UmashConfig.createEntityManager();
    }

    public void create(Clientspoducts clientspoducts) throws RollbackFailureException, Exception {
           EntityManager em = getEntityManager();
           EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            em.persist(clientspoducts);
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

    public void edit(Clientspoducts clientspoducts) throws NonexistentEntityException, RollbackFailureException, Exception {
            EntityManager em = getEntityManager();
           EntityTransaction utx = em.getTransaction();
        try {
            utx.begin();
            clientspoducts = em.merge(clientspoducts);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientspoducts.getTid();
                if (findClientspoducts(id) == null) {
                    throw new NonexistentEntityException("The clientspoducts with id " + id + " no longer exists.");
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
            Clientspoducts clientspoducts;
            try {
                clientspoducts = em.getReference(Clientspoducts.class, id);
                clientspoducts.getTid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientspoducts with id " + id + " no longer exists.", enfe);
            }
            em.remove(clientspoducts);
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

    public List<Clientspoducts> findClientspoductsEntities() {
        return findClientspoductsEntities(true, -1, -1);
    }

    public List<Clientspoducts> findClientspoductsEntities(int maxResults, int firstResult) {
        return findClientspoductsEntities(false, maxResults, firstResult);
    }

    private List<Clientspoducts> findClientspoductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientspoducts.class));
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

    public Clientspoducts findClientspoducts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientspoducts.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientspoductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientspoducts> rt = cq.from(Clientspoducts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Clientspoducts>  getClientProducts(String clientid){
        
          List<Clientspoducts>  cli=null;    
            EntityManager em = getEntityManager();
         try {
            
            cli= em.createNamedQuery("Clientspoducts.findByClientid", Clientspoducts.class)
                    .setParameter("clientid", clientid)
                    .getResultList();
         }catch(Exception ex){
             
          ex.printStackTrace();
        } finally {
            em.close();
        }
         return cli;
        
    }
    
}
