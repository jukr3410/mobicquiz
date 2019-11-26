/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Teachersubjects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Quizs;
import model.Studentsubjects;
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
        if (subjects.getTeachersubjectsList() == null) {
            subjects.setTeachersubjectsList(new ArrayList<Teachersubjects>());
        }
        if (subjects.getQuizsList() == null) {
            subjects.setQuizsList(new ArrayList<Quizs>());
        }
        if (subjects.getStudentsubjectsList() == null) {
            subjects.setStudentsubjectsList(new ArrayList<Studentsubjects>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Teachersubjects> attachedTeachersubjectsList = new ArrayList<Teachersubjects>();
            for (Teachersubjects teachersubjectsListTeachersubjectsToAttach : subjects.getTeachersubjectsList()) {
                teachersubjectsListTeachersubjectsToAttach = em.getReference(teachersubjectsListTeachersubjectsToAttach.getClass(), teachersubjectsListTeachersubjectsToAttach.getTeachersubjectno());
                attachedTeachersubjectsList.add(teachersubjectsListTeachersubjectsToAttach);
            }
            subjects.setTeachersubjectsList(attachedTeachersubjectsList);
            List<Quizs> attachedQuizsList = new ArrayList<Quizs>();
            for (Quizs quizsListQuizsToAttach : subjects.getQuizsList()) {
                quizsListQuizsToAttach = em.getReference(quizsListQuizsToAttach.getClass(), quizsListQuizsToAttach.getQuizno());
                attachedQuizsList.add(quizsListQuizsToAttach);
            }
            subjects.setQuizsList(attachedQuizsList);
            List<Studentsubjects> attachedStudentsubjectsList = new ArrayList<Studentsubjects>();
            for (Studentsubjects studentsubjectsListStudentsubjectsToAttach : subjects.getStudentsubjectsList()) {
                studentsubjectsListStudentsubjectsToAttach = em.getReference(studentsubjectsListStudentsubjectsToAttach.getClass(), studentsubjectsListStudentsubjectsToAttach.getStudentsubjectno());
                attachedStudentsubjectsList.add(studentsubjectsListStudentsubjectsToAttach);
            }
            subjects.setStudentsubjectsList(attachedStudentsubjectsList);
            em.persist(subjects);
            for (Teachersubjects teachersubjectsListTeachersubjects : subjects.getTeachersubjectsList()) {
                Subjects oldSubjectnoOfTeachersubjectsListTeachersubjects = teachersubjectsListTeachersubjects.getSubjectno();
                teachersubjectsListTeachersubjects.setSubjectno(subjects);
                teachersubjectsListTeachersubjects = em.merge(teachersubjectsListTeachersubjects);
                if (oldSubjectnoOfTeachersubjectsListTeachersubjects != null) {
                    oldSubjectnoOfTeachersubjectsListTeachersubjects.getTeachersubjectsList().remove(teachersubjectsListTeachersubjects);
                    oldSubjectnoOfTeachersubjectsListTeachersubjects = em.merge(oldSubjectnoOfTeachersubjectsListTeachersubjects);
                }
            }
            for (Quizs quizsListQuizs : subjects.getQuizsList()) {
                Subjects oldSubjectnoOfQuizsListQuizs = quizsListQuizs.getSubjectno();
                quizsListQuizs.setSubjectno(subjects);
                quizsListQuizs = em.merge(quizsListQuizs);
                if (oldSubjectnoOfQuizsListQuizs != null) {
                    oldSubjectnoOfQuizsListQuizs.getQuizsList().remove(quizsListQuizs);
                    oldSubjectnoOfQuizsListQuizs = em.merge(oldSubjectnoOfQuizsListQuizs);
                }
            }
            for (Studentsubjects studentsubjectsListStudentsubjects : subjects.getStudentsubjectsList()) {
                Subjects oldSubjectnoOfStudentsubjectsListStudentsubjects = studentsubjectsListStudentsubjects.getSubjectno();
                studentsubjectsListStudentsubjects.setSubjectno(subjects);
                studentsubjectsListStudentsubjects = em.merge(studentsubjectsListStudentsubjects);
                if (oldSubjectnoOfStudentsubjectsListStudentsubjects != null) {
                    oldSubjectnoOfStudentsubjectsListStudentsubjects.getStudentsubjectsList().remove(studentsubjectsListStudentsubjects);
                    oldSubjectnoOfStudentsubjectsListStudentsubjects = em.merge(oldSubjectnoOfStudentsubjectsListStudentsubjects);
                }
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

    public void edit(Subjects subjects) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Subjects persistentSubjects = em.find(Subjects.class, subjects.getSubjectno());
            List<Teachersubjects> teachersubjectsListOld = persistentSubjects.getTeachersubjectsList();
            List<Teachersubjects> teachersubjectsListNew = subjects.getTeachersubjectsList();
            List<Quizs> quizsListOld = persistentSubjects.getQuizsList();
            List<Quizs> quizsListNew = subjects.getQuizsList();
            List<Studentsubjects> studentsubjectsListOld = persistentSubjects.getStudentsubjectsList();
            List<Studentsubjects> studentsubjectsListNew = subjects.getStudentsubjectsList();
            List<String> illegalOrphanMessages = null;
            for (Teachersubjects teachersubjectsListOldTeachersubjects : teachersubjectsListOld) {
                if (!teachersubjectsListNew.contains(teachersubjectsListOldTeachersubjects)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Teachersubjects " + teachersubjectsListOldTeachersubjects + " since its subjectno field is not nullable.");
                }
            }
            for (Quizs quizsListOldQuizs : quizsListOld) {
                if (!quizsListNew.contains(quizsListOldQuizs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quizs " + quizsListOldQuizs + " since its subjectno field is not nullable.");
                }
            }
            for (Studentsubjects studentsubjectsListOldStudentsubjects : studentsubjectsListOld) {
                if (!studentsubjectsListNew.contains(studentsubjectsListOldStudentsubjects)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Studentsubjects " + studentsubjectsListOldStudentsubjects + " since its subjectno field is not nullable.");
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
            subjects.setTeachersubjectsList(teachersubjectsListNew);
            List<Quizs> attachedQuizsListNew = new ArrayList<Quizs>();
            for (Quizs quizsListNewQuizsToAttach : quizsListNew) {
                quizsListNewQuizsToAttach = em.getReference(quizsListNewQuizsToAttach.getClass(), quizsListNewQuizsToAttach.getQuizno());
                attachedQuizsListNew.add(quizsListNewQuizsToAttach);
            }
            quizsListNew = attachedQuizsListNew;
            subjects.setQuizsList(quizsListNew);
            List<Studentsubjects> attachedStudentsubjectsListNew = new ArrayList<Studentsubjects>();
            for (Studentsubjects studentsubjectsListNewStudentsubjectsToAttach : studentsubjectsListNew) {
                studentsubjectsListNewStudentsubjectsToAttach = em.getReference(studentsubjectsListNewStudentsubjectsToAttach.getClass(), studentsubjectsListNewStudentsubjectsToAttach.getStudentsubjectno());
                attachedStudentsubjectsListNew.add(studentsubjectsListNewStudentsubjectsToAttach);
            }
            studentsubjectsListNew = attachedStudentsubjectsListNew;
            subjects.setStudentsubjectsList(studentsubjectsListNew);
            subjects = em.merge(subjects);
            for (Teachersubjects teachersubjectsListNewTeachersubjects : teachersubjectsListNew) {
                if (!teachersubjectsListOld.contains(teachersubjectsListNewTeachersubjects)) {
                    Subjects oldSubjectnoOfTeachersubjectsListNewTeachersubjects = teachersubjectsListNewTeachersubjects.getSubjectno();
                    teachersubjectsListNewTeachersubjects.setSubjectno(subjects);
                    teachersubjectsListNewTeachersubjects = em.merge(teachersubjectsListNewTeachersubjects);
                    if (oldSubjectnoOfTeachersubjectsListNewTeachersubjects != null && !oldSubjectnoOfTeachersubjectsListNewTeachersubjects.equals(subjects)) {
                        oldSubjectnoOfTeachersubjectsListNewTeachersubjects.getTeachersubjectsList().remove(teachersubjectsListNewTeachersubjects);
                        oldSubjectnoOfTeachersubjectsListNewTeachersubjects = em.merge(oldSubjectnoOfTeachersubjectsListNewTeachersubjects);
                    }
                }
            }
            for (Quizs quizsListNewQuizs : quizsListNew) {
                if (!quizsListOld.contains(quizsListNewQuizs)) {
                    Subjects oldSubjectnoOfQuizsListNewQuizs = quizsListNewQuizs.getSubjectno();
                    quizsListNewQuizs.setSubjectno(subjects);
                    quizsListNewQuizs = em.merge(quizsListNewQuizs);
                    if (oldSubjectnoOfQuizsListNewQuizs != null && !oldSubjectnoOfQuizsListNewQuizs.equals(subjects)) {
                        oldSubjectnoOfQuizsListNewQuizs.getQuizsList().remove(quizsListNewQuizs);
                        oldSubjectnoOfQuizsListNewQuizs = em.merge(oldSubjectnoOfQuizsListNewQuizs);
                    }
                }
            }
            for (Studentsubjects studentsubjectsListNewStudentsubjects : studentsubjectsListNew) {
                if (!studentsubjectsListOld.contains(studentsubjectsListNewStudentsubjects)) {
                    Subjects oldSubjectnoOfStudentsubjectsListNewStudentsubjects = studentsubjectsListNewStudentsubjects.getSubjectno();
                    studentsubjectsListNewStudentsubjects.setSubjectno(subjects);
                    studentsubjectsListNewStudentsubjects = em.merge(studentsubjectsListNewStudentsubjects);
                    if (oldSubjectnoOfStudentsubjectsListNewStudentsubjects != null && !oldSubjectnoOfStudentsubjectsListNewStudentsubjects.equals(subjects)) {
                        oldSubjectnoOfStudentsubjectsListNewStudentsubjects.getStudentsubjectsList().remove(studentsubjectsListNewStudentsubjects);
                        oldSubjectnoOfStudentsubjectsListNewStudentsubjects = em.merge(oldSubjectnoOfStudentsubjectsListNewStudentsubjects);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            List<Teachersubjects> teachersubjectsListOrphanCheck = subjects.getTeachersubjectsList();
            for (Teachersubjects teachersubjectsListOrphanCheckTeachersubjects : teachersubjectsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subjects (" + subjects + ") cannot be destroyed since the Teachersubjects " + teachersubjectsListOrphanCheckTeachersubjects + " in its teachersubjectsList field has a non-nullable subjectno field.");
            }
            List<Quizs> quizsListOrphanCheck = subjects.getQuizsList();
            for (Quizs quizsListOrphanCheckQuizs : quizsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subjects (" + subjects + ") cannot be destroyed since the Quizs " + quizsListOrphanCheckQuizs + " in its quizsList field has a non-nullable subjectno field.");
            }
            List<Studentsubjects> studentsubjectsListOrphanCheck = subjects.getStudentsubjectsList();
            for (Studentsubjects studentsubjectsListOrphanCheckStudentsubjects : studentsubjectsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Subjects (" + subjects + ") cannot be destroyed since the Studentsubjects " + studentsubjectsListOrphanCheckStudentsubjects + " in its studentsubjectsList field has a non-nullable subjectno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
