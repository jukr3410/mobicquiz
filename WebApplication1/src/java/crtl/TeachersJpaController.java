/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crtl;

import crtl.exceptions.IllegalOrphanException;
import crtl.exceptions.NonexistentEntityException;
import crtl.exceptions.PreexistingEntityException;
import crtl.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import servlet.Teachersubjects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import servlet.Quizs;
import servlet.Teachers;

/**
 *
 * @author Jn
 */
public class TeachersJpaController implements Serializable {

    public TeachersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teachers teachers) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (teachers.getTeachersubjectsList() == null) {
            teachers.setTeachersubjectsList(new ArrayList<Teachersubjects>());
        }
        if (teachers.getQuizsList() == null) {
            teachers.setQuizsList(new ArrayList<Quizs>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Teachersubjects> attachedTeachersubjectsList = new ArrayList<Teachersubjects>();
            for (Teachersubjects teachersubjectsListTeachersubjectsToAttach : teachers.getTeachersubjectsList()) {
                teachersubjectsListTeachersubjectsToAttach = em.getReference(teachersubjectsListTeachersubjectsToAttach.getClass(), teachersubjectsListTeachersubjectsToAttach.getTeachersubjectno());
                attachedTeachersubjectsList.add(teachersubjectsListTeachersubjectsToAttach);
            }
            teachers.setTeachersubjectsList(attachedTeachersubjectsList);
            List<Quizs> attachedQuizsList = new ArrayList<Quizs>();
            for (Quizs quizsListQuizsToAttach : teachers.getQuizsList()) {
                quizsListQuizsToAttach = em.getReference(quizsListQuizsToAttach.getClass(), quizsListQuizsToAttach.getQuizno());
                attachedQuizsList.add(quizsListQuizsToAttach);
            }
            teachers.setQuizsList(attachedQuizsList);
            em.persist(teachers);
            for (Teachersubjects teachersubjectsListTeachersubjects : teachers.getTeachersubjectsList()) {
                Teachers oldTeachernoOfTeachersubjectsListTeachersubjects = teachersubjectsListTeachersubjects.getTeacherno();
                teachersubjectsListTeachersubjects.setTeacherno(teachers);
                teachersubjectsListTeachersubjects = em.merge(teachersubjectsListTeachersubjects);
                if (oldTeachernoOfTeachersubjectsListTeachersubjects != null) {
                    oldTeachernoOfTeachersubjectsListTeachersubjects.getTeachersubjectsList().remove(teachersubjectsListTeachersubjects);
                    oldTeachernoOfTeachersubjectsListTeachersubjects = em.merge(oldTeachernoOfTeachersubjectsListTeachersubjects);
                }
            }
            for (Quizs quizsListQuizs : teachers.getQuizsList()) {
                Teachers oldTeachernoOfQuizsListQuizs = quizsListQuizs.getTeacherno();
                quizsListQuizs.setTeacherno(teachers);
                quizsListQuizs = em.merge(quizsListQuizs);
                if (oldTeachernoOfQuizsListQuizs != null) {
                    oldTeachernoOfQuizsListQuizs.getQuizsList().remove(quizsListQuizs);
                    oldTeachernoOfQuizsListQuizs = em.merge(oldTeachernoOfQuizsListQuizs);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTeachers(teachers.getTeacherno()) != null) {
                throw new PreexistingEntityException("Teachers " + teachers + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teachers teachers) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Teachers persistentTeachers = em.find(Teachers.class, teachers.getTeacherno());
            List<Teachersubjects> teachersubjectsListOld = persistentTeachers.getTeachersubjectsList();
            List<Teachersubjects> teachersubjectsListNew = teachers.getTeachersubjectsList();
            List<Quizs> quizsListOld = persistentTeachers.getQuizsList();
            List<Quizs> quizsListNew = teachers.getQuizsList();
            List<String> illegalOrphanMessages = null;
            for (Teachersubjects teachersubjectsListOldTeachersubjects : teachersubjectsListOld) {
                if (!teachersubjectsListNew.contains(teachersubjectsListOldTeachersubjects)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Teachersubjects " + teachersubjectsListOldTeachersubjects + " since its teacherno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Teachersubjects> attachedTeachersubjectsListNew = new ArrayList<Teachersubjects>();
            for (Teachersubjects teachersubjectsListNewTeachersubjectsToAttach : teachersubjectsListNew) {
                teachersubjectsListNewTeachersubjectsToAttach = em.getReference(teachersubjectsListNewTeachersubjectsToAttach.getClass(), teachersubjectsListNewTeachersubjectsToAttach.getTeachersubjectno());
                attachedTeachersubjectsListNew.add(teachersubjectsListNewTeachersubjectsToAttach);
            }
            teachersubjectsListNew = attachedTeachersubjectsListNew;
            teachers.setTeachersubjectsList(teachersubjectsListNew);
            List<Quizs> attachedQuizsListNew = new ArrayList<Quizs>();
            for (Quizs quizsListNewQuizsToAttach : quizsListNew) {
                quizsListNewQuizsToAttach = em.getReference(quizsListNewQuizsToAttach.getClass(), quizsListNewQuizsToAttach.getQuizno());
                attachedQuizsListNew.add(quizsListNewQuizsToAttach);
            }
            quizsListNew = attachedQuizsListNew;
            teachers.setQuizsList(quizsListNew);
            teachers = em.merge(teachers);
            for (Teachersubjects teachersubjectsListNewTeachersubjects : teachersubjectsListNew) {
                if (!teachersubjectsListOld.contains(teachersubjectsListNewTeachersubjects)) {
                    Teachers oldTeachernoOfTeachersubjectsListNewTeachersubjects = teachersubjectsListNewTeachersubjects.getTeacherno();
                    teachersubjectsListNewTeachersubjects.setTeacherno(teachers);
                    teachersubjectsListNewTeachersubjects = em.merge(teachersubjectsListNewTeachersubjects);
                    if (oldTeachernoOfTeachersubjectsListNewTeachersubjects != null && !oldTeachernoOfTeachersubjectsListNewTeachersubjects.equals(teachers)) {
                        oldTeachernoOfTeachersubjectsListNewTeachersubjects.getTeachersubjectsList().remove(teachersubjectsListNewTeachersubjects);
                        oldTeachernoOfTeachersubjectsListNewTeachersubjects = em.merge(oldTeachernoOfTeachersubjectsListNewTeachersubjects);
                    }
                }
            }
            for (Quizs quizsListOldQuizs : quizsListOld) {
                if (!quizsListNew.contains(quizsListOldQuizs)) {
                    quizsListOldQuizs.setTeacherno(null);
                    quizsListOldQuizs = em.merge(quizsListOldQuizs);
                }
            }
            for (Quizs quizsListNewQuizs : quizsListNew) {
                if (!quizsListOld.contains(quizsListNewQuizs)) {
                    Teachers oldTeachernoOfQuizsListNewQuizs = quizsListNewQuizs.getTeacherno();
                    quizsListNewQuizs.setTeacherno(teachers);
                    quizsListNewQuizs = em.merge(quizsListNewQuizs);
                    if (oldTeachernoOfQuizsListNewQuizs != null && !oldTeachernoOfQuizsListNewQuizs.equals(teachers)) {
                        oldTeachernoOfQuizsListNewQuizs.getQuizsList().remove(quizsListNewQuizs);
                        oldTeachernoOfQuizsListNewQuizs = em.merge(oldTeachernoOfQuizsListNewQuizs);
                    }
                }
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
                String id = teachers.getTeacherno();
                if (findTeachers(id) == null) {
                    throw new NonexistentEntityException("The teachers with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Teachers teachers;
            try {
                teachers = em.getReference(Teachers.class, id);
                teachers.getTeacherno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teachers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Teachersubjects> teachersubjectsListOrphanCheck = teachers.getTeachersubjectsList();
            for (Teachersubjects teachersubjectsListOrphanCheckTeachersubjects : teachersubjectsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Teachers (" + teachers + ") cannot be destroyed since the Teachersubjects " + teachersubjectsListOrphanCheckTeachersubjects + " in its teachersubjectsList field has a non-nullable teacherno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Quizs> quizsList = teachers.getQuizsList();
            for (Quizs quizsListQuizs : quizsList) {
                quizsListQuizs.setTeacherno(null);
                quizsListQuizs = em.merge(quizsListQuizs);
            }
            em.remove(teachers);
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

    public List<Teachers> findTeachersEntities() {
        return findTeachersEntities(true, -1, -1);
    }

    public List<Teachers> findTeachersEntities(int maxResults, int firstResult) {
        return findTeachersEntities(false, maxResults, firstResult);
    }

    private List<Teachers> findTeachersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teachers.class));
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

    public Teachers findTeachers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teachers.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeachersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teachers> rt = cq.from(Teachers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
