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
import model.Levels;
import model.Subjects;

/**
 *
 * @author Jn
 */
public class SubjectsJpaController implements Serializable {

    public SubjectsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subjects subjects) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Levels levelno = subjects.getLevelno();
            if (levelno != null) {
                levelno = em.getReference(levelno.getClass(), levelno.getLevelno());
                subjects.setLevelno(levelno);
            }
            em.persist(subjects);
            if (levelno != null) {
                levelno.getSubjectsList().add(subjects);
                levelno = em.merge(levelno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSubjects(subjects.getSubjectno()) != null) {
                throw new PreexistingEntityException("Subjects " + subjects + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subjects subjects) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subjects persistentSubjects = em.find(Subjects.class, subjects.getSubjectno());
            Levels levelnoOld = persistentSubjects.getLevelno();
            Levels levelnoNew = subjects.getLevelno();
            if (levelnoNew != null) {
                levelnoNew = em.getReference(levelnoNew.getClass(), levelnoNew.getLevelno());
                subjects.setLevelno(levelnoNew);
            }
            subjects = em.merge(subjects);
            if (levelnoOld != null && !levelnoOld.equals(levelnoNew)) {
                levelnoOld.getSubjectsList().remove(subjects);
                levelnoOld = em.merge(levelnoOld);
            }
            if (levelnoNew != null && !levelnoNew.equals(levelnoOld)) {
                levelnoNew.getSubjectsList().add(subjects);
                levelnoNew = em.merge(levelnoNew);
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
                String id = subjects.getSubjectno();
                if (findSubjects(id) == null) {
                    throw new NonexistentEntityException("The subjects with id " + id + " no longer exists.");
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
            Subjects subjects;
            try {
                subjects = em.getReference(Subjects.class, id);
                subjects.getSubjectno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subjects with id " + id + " no longer exists.", enfe);
            }
            Levels levelno = subjects.getLevelno();
            if (levelno != null) {
                levelno.getSubjectsList().remove(subjects);
                levelno = em.merge(levelno);
            }
            em.remove(subjects);
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

    public List<Subjects> findSubjectsEntities() {
        return findSubjectsEntities(true, -1, -1);
    }

    public List<Subjects> findSubjectsEntities(int maxResults, int firstResult) {
        return findSubjectsEntities(false, maxResults, firstResult);
    }

    private List<Subjects> findSubjectsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subjects.class));
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

    public Subjects findSubjects(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subjects.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubjectsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subjects> rt = cq.from(Subjects.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
