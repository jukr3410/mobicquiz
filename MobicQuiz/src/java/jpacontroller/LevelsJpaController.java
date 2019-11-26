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
import model.Quizs;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Levels;
import model.Students;

/**
 *
 * @author Jn
 */
public class LevelsJpaController implements Serializable {

    public LevelsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Levels levels) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (levels.getQuizsList() == null) {
            levels.setQuizsList(new ArrayList<Quizs>());
        }
        if (levels.getStudentsList() == null) {
            levels.setStudentsList(new ArrayList<Students>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Quizs> attachedQuizsList = new ArrayList<Quizs>();
            for (Quizs quizsListQuizsToAttach : levels.getQuizsList()) {
                quizsListQuizsToAttach = em.getReference(quizsListQuizsToAttach.getClass(), quizsListQuizsToAttach.getQuizno());
                attachedQuizsList.add(quizsListQuizsToAttach);
            }
            levels.setQuizsList(attachedQuizsList);
            List<Students> attachedStudentsList = new ArrayList<Students>();
            for (Students studentsListStudentsToAttach : levels.getStudentsList()) {
                studentsListStudentsToAttach = em.getReference(studentsListStudentsToAttach.getClass(), studentsListStudentsToAttach.getStudentno());
                attachedStudentsList.add(studentsListStudentsToAttach);
            }
            levels.setStudentsList(attachedStudentsList);
            em.persist(levels);
            for (Quizs quizsListQuizs : levels.getQuizsList()) {
                Levels oldLevelnoOfQuizsListQuizs = quizsListQuizs.getLevelno();
                quizsListQuizs.setLevelno(levels);
                quizsListQuizs = em.merge(quizsListQuizs);
                if (oldLevelnoOfQuizsListQuizs != null) {
                    oldLevelnoOfQuizsListQuizs.getQuizsList().remove(quizsListQuizs);
                    oldLevelnoOfQuizsListQuizs = em.merge(oldLevelnoOfQuizsListQuizs);
                }
            }
            for (Students studentsListStudents : levels.getStudentsList()) {
                Levels oldLevelnoOfStudentsListStudents = studentsListStudents.getLevelno();
                studentsListStudents.setLevelno(levels);
                studentsListStudents = em.merge(studentsListStudents);
                if (oldLevelnoOfStudentsListStudents != null) {
                    oldLevelnoOfStudentsListStudents.getStudentsList().remove(studentsListStudents);
                    oldLevelnoOfStudentsListStudents = em.merge(oldLevelnoOfStudentsListStudents);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findLevels(levels.getLevelno()) != null) {
                throw new PreexistingEntityException("Levels " + levels + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Levels levels) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Levels persistentLevels = em.find(Levels.class, levels.getLevelno());
            List<Quizs> quizsListOld = persistentLevels.getQuizsList();
            List<Quizs> quizsListNew = levels.getQuizsList();
            List<Students> studentsListOld = persistentLevels.getStudentsList();
            List<Students> studentsListNew = levels.getStudentsList();
            List<String> illegalOrphanMessages = null;
            for (Quizs quizsListOldQuizs : quizsListOld) {
                if (!quizsListNew.contains(quizsListOldQuizs)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quizs " + quizsListOldQuizs + " since its levelno field is not nullable.");
                }
            }
            for (Students studentsListOldStudents : studentsListOld) {
                if (!studentsListNew.contains(studentsListOldStudents)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Students " + studentsListOldStudents + " since its levelno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Quizs> attachedQuizsListNew = new ArrayList<Quizs>();
            for (Quizs quizsListNewQuizsToAttach : quizsListNew) {
                quizsListNewQuizsToAttach = em.getReference(quizsListNewQuizsToAttach.getClass(), quizsListNewQuizsToAttach.getQuizno());
                attachedQuizsListNew.add(quizsListNewQuizsToAttach);
            }
            quizsListNew = attachedQuizsListNew;
            levels.setQuizsList(quizsListNew);
            List<Students> attachedStudentsListNew = new ArrayList<Students>();
            for (Students studentsListNewStudentsToAttach : studentsListNew) {
                studentsListNewStudentsToAttach = em.getReference(studentsListNewStudentsToAttach.getClass(), studentsListNewStudentsToAttach.getStudentno());
                attachedStudentsListNew.add(studentsListNewStudentsToAttach);
            }
            studentsListNew = attachedStudentsListNew;
            levels.setStudentsList(studentsListNew);
            levels = em.merge(levels);
            for (Quizs quizsListNewQuizs : quizsListNew) {
                if (!quizsListOld.contains(quizsListNewQuizs)) {
                    Levels oldLevelnoOfQuizsListNewQuizs = quizsListNewQuizs.getLevelno();
                    quizsListNewQuizs.setLevelno(levels);
                    quizsListNewQuizs = em.merge(quizsListNewQuizs);
                    if (oldLevelnoOfQuizsListNewQuizs != null && !oldLevelnoOfQuizsListNewQuizs.equals(levels)) {
                        oldLevelnoOfQuizsListNewQuizs.getQuizsList().remove(quizsListNewQuizs);
                        oldLevelnoOfQuizsListNewQuizs = em.merge(oldLevelnoOfQuizsListNewQuizs);
                    }
                }
            }
            for (Students studentsListNewStudents : studentsListNew) {
                if (!studentsListOld.contains(studentsListNewStudents)) {
                    Levels oldLevelnoOfStudentsListNewStudents = studentsListNewStudents.getLevelno();
                    studentsListNewStudents.setLevelno(levels);
                    studentsListNewStudents = em.merge(studentsListNewStudents);
                    if (oldLevelnoOfStudentsListNewStudents != null && !oldLevelnoOfStudentsListNewStudents.equals(levels)) {
                        oldLevelnoOfStudentsListNewStudents.getStudentsList().remove(studentsListNewStudents);
                        oldLevelnoOfStudentsListNewStudents = em.merge(oldLevelnoOfStudentsListNewStudents);
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
                String id = levels.getLevelno();
                if (findLevels(id) == null) {
                    throw new NonexistentEntityException("The levels with id " + id + " no longer exists.");
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
            Levels levels;
            try {
                levels = em.getReference(Levels.class, id);
                levels.getLevelno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The levels with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Quizs> quizsListOrphanCheck = levels.getQuizsList();
            for (Quizs quizsListOrphanCheckQuizs : quizsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Quizs " + quizsListOrphanCheckQuizs + " in its quizsList field has a non-nullable levelno field.");
            }
            List<Students> studentsListOrphanCheck = levels.getStudentsList();
            for (Students studentsListOrphanCheckStudents : studentsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Levels (" + levels + ") cannot be destroyed since the Students " + studentsListOrphanCheckStudents + " in its studentsList field has a non-nullable levelno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(levels);
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

    public List<Levels> findLevelsEntities() {
        return findLevelsEntities(true, -1, -1);
    }

    public List<Levels> findLevelsEntities(int maxResults, int firstResult) {
        return findLevelsEntities(false, maxResults, firstResult);
    }

    private List<Levels> findLevelsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Levels.class));
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

    public Levels findLevels(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Levels.class, id);
        } finally {
            em.close();
        }
    }

    public int getLevelsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Levels> rt = cq.from(Levels.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
