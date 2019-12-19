/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacontroller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Historys;
import model.Quizs;
import model.Students;

/**
 *
 * @author Jn
 */
public class HistorysJpaController implements Serializable {

    public HistorysJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historys historys) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quizs quizno = historys.getQuizno();
            if (quizno != null) {
                quizno = em.getReference(quizno.getClass(), quizno.getQuizno());
                historys.setQuizno(quizno);
            }
            Students studentno = historys.getStudentno();
            if (studentno != null) {
                studentno = em.getReference(studentno.getClass(), studentno.getStudentno());
                historys.setStudentno(studentno);
            }
            em.persist(historys);
            if (quizno != null) {
                quizno.getHistorysList().add(historys);
                quizno = em.merge(quizno);
            }
            if (studentno != null) {
                studentno.getHistorysList().add(historys);
                studentno = em.merge(studentno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistorys(historys.getHistoryno()) != null) {
                throw new PreexistingEntityException("Historys " + historys + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historys historys) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historys persistentHistorys = em.find(Historys.class, historys.getHistoryno());
            Quizs quiznoOld = persistentHistorys.getQuizno();
            Quizs quiznoNew = historys.getQuizno();
            Students studentnoOld = persistentHistorys.getStudentno();
            Students studentnoNew = historys.getStudentno();
            if (quiznoNew != null) {
                quiznoNew = em.getReference(quiznoNew.getClass(), quiznoNew.getQuizno());
                historys.setQuizno(quiznoNew);
            }
            if (studentnoNew != null) {
                studentnoNew = em.getReference(studentnoNew.getClass(), studentnoNew.getStudentno());
                historys.setStudentno(studentnoNew);
            }
            historys = em.merge(historys);
            if (quiznoOld != null && !quiznoOld.equals(quiznoNew)) {
                quiznoOld.getHistorysList().remove(historys);
                quiznoOld = em.merge(quiznoOld);
            }
            if (quiznoNew != null && !quiznoNew.equals(quiznoOld)) {
                quiznoNew.getHistorysList().add(historys);
                quiznoNew = em.merge(quiznoNew);
            }
            if (studentnoOld != null && !studentnoOld.equals(studentnoNew)) {
                studentnoOld.getHistorysList().remove(historys);
                studentnoOld = em.merge(studentnoOld);
            }
            if (studentnoNew != null && !studentnoNew.equals(studentnoOld)) {
                studentnoNew.getHistorysList().add(historys);
                studentnoNew = em.merge(studentnoNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = historys.getHistoryno();
                if (findHistorys(id) == null) {
                    throw new NonexistentEntityException("The historys with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historys historys;
            try {
                historys = em.getReference(Historys.class, id);
                historys.getHistoryno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historys with id " + id + " no longer exists.", enfe);
            }
            Quizs quizno = historys.getQuizno();
            if (quizno != null) {
                quizno.getHistorysList().remove(historys);
                quizno = em.merge(quizno);
            }
            Students studentno = historys.getStudentno();
            if (studentno != null) {
                studentno.getHistorysList().remove(historys);
                studentno = em.merge(studentno);
            }
            em.remove(historys);
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

    public List<Historys> findHistorysEntities() {
        return findHistorysEntities(true, -1, -1);
    }

    public List<Historys> findHistorysEntities(int maxResults, int firstResult) {
        return findHistorysEntities(false, maxResults, firstResult);
    }

    private List<Historys> findHistorysEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historys.class));
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

    public Historys findHistorys(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historys.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorysCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historys> rt = cq.from(Historys.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Historys> findHistorysByStudentNo(String studentno) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Historys.findByStudentno");
        query.setParameter("studentno", studentno);
        List<Historys> resultList = query.getResultList();
        try {
            return resultList.isEmpty() ? null : resultList;
        } finally {
            em.close();
        }
    }

    public List<Historys> findHistorysByQuizNo(String quizno) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Historys.findByQuizno");
        query.setParameter("quizno", quizno);
        List<Historys> resultList = query.getResultList();
        try {
            return resultList.isEmpty() ? null : resultList;
        } finally {
            em.close();
        }
    }

}
