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
import model.Students;
import model.Studentsubjects;
import model.Subjects;

/**
 *
 * @author Student
 */
public class StudentsubjectsJpaController implements Serializable {

    public StudentsubjectsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Studentsubjects studentsubjects) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Students studentno = studentsubjects.getStudentno();
            if (studentno != null) {
                studentno = em.getReference(studentno.getClass(), studentno.getStudentno());
                studentsubjects.setStudentno(studentno);
            }
            Subjects subjectno = studentsubjects.getSubjectno();
            if (subjectno != null) {
                subjectno = em.getReference(subjectno.getClass(), subjectno.getSubjectno());
                studentsubjects.setSubjectno(subjectno);
            }
            em.persist(studentsubjects);
            if (studentno != null) {
                studentno.getStudentsubjectsList().add(studentsubjects);
                studentno = em.merge(studentno);
            }
            if (subjectno != null) {
                subjectno.getStudentsubjectsList().add(studentsubjects);
                subjectno = em.merge(subjectno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findStudentsubjects(studentsubjects.getStudentsubjectno()) != null) {
                throw new PreexistingEntityException("Studentsubjects " + studentsubjects + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Studentsubjects studentsubjects) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Studentsubjects persistentStudentsubjects = em.find(Studentsubjects.class, studentsubjects.getStudentsubjectno());
            Students studentnoOld = persistentStudentsubjects.getStudentno();
            Students studentnoNew = studentsubjects.getStudentno();
            Subjects subjectnoOld = persistentStudentsubjects.getSubjectno();
            Subjects subjectnoNew = studentsubjects.getSubjectno();
            if (studentnoNew != null) {
                studentnoNew = em.getReference(studentnoNew.getClass(), studentnoNew.getStudentno());
                studentsubjects.setStudentno(studentnoNew);
            }
            if (subjectnoNew != null) {
                subjectnoNew = em.getReference(subjectnoNew.getClass(), subjectnoNew.getSubjectno());
                studentsubjects.setSubjectno(subjectnoNew);
            }
            studentsubjects = em.merge(studentsubjects);
            if (studentnoOld != null && !studentnoOld.equals(studentnoNew)) {
                studentnoOld.getStudentsubjectsList().remove(studentsubjects);
                studentnoOld = em.merge(studentnoOld);
            }
            if (studentnoNew != null && !studentnoNew.equals(studentnoOld)) {
                studentnoNew.getStudentsubjectsList().add(studentsubjects);
                studentnoNew = em.merge(studentnoNew);
            }
            if (subjectnoOld != null && !subjectnoOld.equals(subjectnoNew)) {
                subjectnoOld.getStudentsubjectsList().remove(studentsubjects);
                subjectnoOld = em.merge(subjectnoOld);
            }
            if (subjectnoNew != null && !subjectnoNew.equals(subjectnoOld)) {
                subjectnoNew.getStudentsubjectsList().add(studentsubjects);
                subjectnoNew = em.merge(subjectnoNew);
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
                String id = studentsubjects.getStudentsubjectno();
                if (findStudentsubjects(id) == null) {
                    throw new NonexistentEntityException("The studentsubjects with id " + id + " no longer exists.");
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
            Studentsubjects studentsubjects;
            try {
                studentsubjects = em.getReference(Studentsubjects.class, id);
                studentsubjects.getStudentsubjectno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The studentsubjects with id " + id + " no longer exists.", enfe);
            }
            Students studentno = studentsubjects.getStudentno();
            if (studentno != null) {
                studentno.getStudentsubjectsList().remove(studentsubjects);
                studentno = em.merge(studentno);
            }
            Subjects subjectno = studentsubjects.getSubjectno();
            if (subjectno != null) {
                subjectno.getStudentsubjectsList().remove(studentsubjects);
                subjectno = em.merge(subjectno);
            }
            em.remove(studentsubjects);
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

    public List<Studentsubjects> findStudentsubjectsEntities() {
        return findStudentsubjectsEntities(true, -1, -1);
    }

    public List<Studentsubjects> findStudentsubjectsEntities(int maxResults, int firstResult) {
        return findStudentsubjectsEntities(false, maxResults, firstResult);
    }

    private List<Studentsubjects> findStudentsubjectsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Studentsubjects.class));
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

    public Studentsubjects findStudentsubjects(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Studentsubjects.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentsubjectsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Studentsubjects> rt = cq.from(Studentsubjects.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
