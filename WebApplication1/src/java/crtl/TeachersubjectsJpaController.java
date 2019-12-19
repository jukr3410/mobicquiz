/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crtl;

import crtl.exceptions.NonexistentEntityException;
import crtl.exceptions.PreexistingEntityException;
import crtl.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import servlet.Subjects;
import servlet.Teachers;
import servlet.Teachersubjects;

/**
 *
 * @author Jn
 */
public class TeachersubjectsJpaController implements Serializable {

    public TeachersubjectsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teachersubjects teachersubjects) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subjects subjectno = teachersubjects.getSubjectno();
            if (subjectno != null) {
                subjectno = em.getReference(subjectno.getClass(), subjectno.getSubjectno());
                teachersubjects.setSubjectno(subjectno);
            }
            Teachers teacherno = teachersubjects.getTeacherno();
            if (teacherno != null) {
                teacherno = em.getReference(teacherno.getClass(), teacherno.getTeacherno());
                teachersubjects.setTeacherno(teacherno);
            }
            em.persist(teachersubjects);
            if (subjectno != null) {
                subjectno.getTeachersubjectsList().add(teachersubjects);
                subjectno = em.merge(subjectno);
            }
            if (teacherno != null) {
                teacherno.getTeachersubjectsList().add(teachersubjects);
                teacherno = em.merge(teacherno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTeachersubjects(teachersubjects.getTeachersubjectno()) != null) {
                throw new PreexistingEntityException("Teachersubjects " + teachersubjects + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teachersubjects teachersubjects) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Teachersubjects persistentTeachersubjects = em.find(Teachersubjects.class, teachersubjects.getTeachersubjectno());
            Subjects subjectnoOld = persistentTeachersubjects.getSubjectno();
            Subjects subjectnoNew = teachersubjects.getSubjectno();
            Teachers teachernoOld = persistentTeachersubjects.getTeacherno();
            Teachers teachernoNew = teachersubjects.getTeacherno();
            if (subjectnoNew != null) {
                subjectnoNew = em.getReference(subjectnoNew.getClass(), subjectnoNew.getSubjectno());
                teachersubjects.setSubjectno(subjectnoNew);
            }
            if (teachernoNew != null) {
                teachernoNew = em.getReference(teachernoNew.getClass(), teachernoNew.getTeacherno());
                teachersubjects.setTeacherno(teachernoNew);
            }
            teachersubjects = em.merge(teachersubjects);
            if (subjectnoOld != null && !subjectnoOld.equals(subjectnoNew)) {
                subjectnoOld.getTeachersubjectsList().remove(teachersubjects);
                subjectnoOld = em.merge(subjectnoOld);
            }
            if (subjectnoNew != null && !subjectnoNew.equals(subjectnoOld)) {
                subjectnoNew.getTeachersubjectsList().add(teachersubjects);
                subjectnoNew = em.merge(subjectnoNew);
            }
            if (teachernoOld != null && !teachernoOld.equals(teachernoNew)) {
                teachernoOld.getTeachersubjectsList().remove(teachersubjects);
                teachernoOld = em.merge(teachernoOld);
            }
            if (teachernoNew != null && !teachernoNew.equals(teachernoOld)) {
                teachernoNew.getTeachersubjectsList().add(teachersubjects);
                teachernoNew = em.merge(teachernoNew);
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
                String id = teachersubjects.getTeachersubjectno();
                if (findTeachersubjects(id) == null) {
                    throw new NonexistentEntityException("The teachersubjects with id " + id + " no longer exists.");
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
            Teachersubjects teachersubjects;
            try {
                teachersubjects = em.getReference(Teachersubjects.class, id);
                teachersubjects.getTeachersubjectno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teachersubjects with id " + id + " no longer exists.", enfe);
            }
            Subjects subjectno = teachersubjects.getSubjectno();
            if (subjectno != null) {
                subjectno.getTeachersubjectsList().remove(teachersubjects);
                subjectno = em.merge(subjectno);
            }
            Teachers teacherno = teachersubjects.getTeacherno();
            if (teacherno != null) {
                teacherno.getTeachersubjectsList().remove(teachersubjects);
                teacherno = em.merge(teacherno);
            }
            em.remove(teachersubjects);
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

    public List<Teachersubjects> findTeachersubjectsEntities() {
        return findTeachersubjectsEntities(true, -1, -1);
    }

    public List<Teachersubjects> findTeachersubjectsEntities(int maxResults, int firstResult) {
        return findTeachersubjectsEntities(false, maxResults, firstResult);
    }

    private List<Teachersubjects> findTeachersubjectsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teachersubjects.class));
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

    public Teachersubjects findTeachersubjects(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teachersubjects.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeachersubjectsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teachersubjects> rt = cq.from(Teachersubjects.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
