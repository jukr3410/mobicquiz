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
import model.Levels;
import model.Studentsubjects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpacontroller.exceptions.IllegalOrphanException;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Historys;
import model.Students;

/**
 *
 * @author Jn
 */
public class StudentsJpaController implements Serializable {

    public StudentsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Students students) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (students.getStudentsubjectsList() == null) {
            students.setStudentsubjectsList(new ArrayList<Studentsubjects>());
        }
        if (students.getHistorysList() == null) {
            students.setHistorysList(new ArrayList<Historys>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Levels levelno = students.getLevelno();
            if (levelno != null) {
                levelno = em.getReference(levelno.getClass(), levelno.getLevelno());
                students.setLevelno(levelno);
            }
            List<Studentsubjects> attachedStudentsubjectsList = new ArrayList<Studentsubjects>();
            for (Studentsubjects studentsubjectsListStudentsubjectsToAttach : students.getStudentsubjectsList()) {
                studentsubjectsListStudentsubjectsToAttach = em.getReference(studentsubjectsListStudentsubjectsToAttach.getClass(), studentsubjectsListStudentsubjectsToAttach.getStudentsubjectno());
                attachedStudentsubjectsList.add(studentsubjectsListStudentsubjectsToAttach);
            }
            students.setStudentsubjectsList(attachedStudentsubjectsList);
            List<Historys> attachedHistorysList = new ArrayList<Historys>();
            for (Historys historysListHistorysToAttach : students.getHistorysList()) {
                historysListHistorysToAttach = em.getReference(historysListHistorysToAttach.getClass(), historysListHistorysToAttach.getHistoryno());
                attachedHistorysList.add(historysListHistorysToAttach);
            }
            students.setHistorysList(attachedHistorysList);
            em.persist(students);
            if (levelno != null) {
                levelno.getStudentsList().add(students);
                levelno = em.merge(levelno);
            }
            for (Studentsubjects studentsubjectsListStudentsubjects : students.getStudentsubjectsList()) {
                Students oldStudentnoOfStudentsubjectsListStudentsubjects = studentsubjectsListStudentsubjects.getStudentno();
                studentsubjectsListStudentsubjects.setStudentno(students);
                studentsubjectsListStudentsubjects = em.merge(studentsubjectsListStudentsubjects);
                if (oldStudentnoOfStudentsubjectsListStudentsubjects != null) {
                    oldStudentnoOfStudentsubjectsListStudentsubjects.getStudentsubjectsList().remove(studentsubjectsListStudentsubjects);
                    oldStudentnoOfStudentsubjectsListStudentsubjects = em.merge(oldStudentnoOfStudentsubjectsListStudentsubjects);
                }
            }
            for (Historys historysListHistorys : students.getHistorysList()) {
                Students oldStudentnoOfHistorysListHistorys = historysListHistorys.getStudentno();
                historysListHistorys.setStudentno(students);
                historysListHistorys = em.merge(historysListHistorys);
                if (oldStudentnoOfHistorysListHistorys != null) {
                    oldStudentnoOfHistorysListHistorys.getHistorysList().remove(historysListHistorys);
                    oldStudentnoOfHistorysListHistorys = em.merge(oldStudentnoOfHistorysListHistorys);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findStudents(students.getStudentno()) != null) {
                throw new PreexistingEntityException("Students " + students + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Students students) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Students persistentStudents = em.find(Students.class, students.getStudentno());
            Levels levelnoOld = persistentStudents.getLevelno();
            Levels levelnoNew = students.getLevelno();
            List<Studentsubjects> studentsubjectsListOld = persistentStudents.getStudentsubjectsList();
            List<Studentsubjects> studentsubjectsListNew = students.getStudentsubjectsList();
            List<Historys> historysListOld = persistentStudents.getHistorysList();
            List<Historys> historysListNew = students.getHistorysList();
            List<String> illegalOrphanMessages = null;
            for (Studentsubjects studentsubjectsListOldStudentsubjects : studentsubjectsListOld) {
                if (!studentsubjectsListNew.contains(studentsubjectsListOldStudentsubjects)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Studentsubjects " + studentsubjectsListOldStudentsubjects + " since its studentno field is not nullable.");
                }
            }
            for (Historys historysListOldHistorys : historysListOld) {
                if (!historysListNew.contains(historysListOldHistorys)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historys " + historysListOldHistorys + " since its studentno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (levelnoNew != null) {
                levelnoNew = em.getReference(levelnoNew.getClass(), levelnoNew.getLevelno());
                students.setLevelno(levelnoNew);
            }
            List<Studentsubjects> attachedStudentsubjectsListNew = new ArrayList<Studentsubjects>();
            for (Studentsubjects studentsubjectsListNewStudentsubjectsToAttach : studentsubjectsListNew) {
                studentsubjectsListNewStudentsubjectsToAttach = em.getReference(studentsubjectsListNewStudentsubjectsToAttach.getClass(), studentsubjectsListNewStudentsubjectsToAttach.getStudentsubjectno());
                attachedStudentsubjectsListNew.add(studentsubjectsListNewStudentsubjectsToAttach);
            }
            studentsubjectsListNew = attachedStudentsubjectsListNew;
            students.setStudentsubjectsList(studentsubjectsListNew);
            List<Historys> attachedHistorysListNew = new ArrayList<Historys>();
            for (Historys historysListNewHistorysToAttach : historysListNew) {
                historysListNewHistorysToAttach = em.getReference(historysListNewHistorysToAttach.getClass(), historysListNewHistorysToAttach.getHistoryno());
                attachedHistorysListNew.add(historysListNewHistorysToAttach);
            }
            historysListNew = attachedHistorysListNew;
            students.setHistorysList(historysListNew);
            students = em.merge(students);
            if (levelnoOld != null && !levelnoOld.equals(levelnoNew)) {
                levelnoOld.getStudentsList().remove(students);
                levelnoOld = em.merge(levelnoOld);
            }
            if (levelnoNew != null && !levelnoNew.equals(levelnoOld)) {
                levelnoNew.getStudentsList().add(students);
                levelnoNew = em.merge(levelnoNew);
            }
            for (Studentsubjects studentsubjectsListNewStudentsubjects : studentsubjectsListNew) {
                if (!studentsubjectsListOld.contains(studentsubjectsListNewStudentsubjects)) {
                    Students oldStudentnoOfStudentsubjectsListNewStudentsubjects = studentsubjectsListNewStudentsubjects.getStudentno();
                    studentsubjectsListNewStudentsubjects.setStudentno(students);
                    studentsubjectsListNewStudentsubjects = em.merge(studentsubjectsListNewStudentsubjects);
                    if (oldStudentnoOfStudentsubjectsListNewStudentsubjects != null && !oldStudentnoOfStudentsubjectsListNewStudentsubjects.equals(students)) {
                        oldStudentnoOfStudentsubjectsListNewStudentsubjects.getStudentsubjectsList().remove(studentsubjectsListNewStudentsubjects);
                        oldStudentnoOfStudentsubjectsListNewStudentsubjects = em.merge(oldStudentnoOfStudentsubjectsListNewStudentsubjects);
                    }
                }
            }
            for (Historys historysListNewHistorys : historysListNew) {
                if (!historysListOld.contains(historysListNewHistorys)) {
                    Students oldStudentnoOfHistorysListNewHistorys = historysListNewHistorys.getStudentno();
                    historysListNewHistorys.setStudentno(students);
                    historysListNewHistorys = em.merge(historysListNewHistorys);
                    if (oldStudentnoOfHistorysListNewHistorys != null && !oldStudentnoOfHistorysListNewHistorys.equals(students)) {
                        oldStudentnoOfHistorysListNewHistorys.getHistorysList().remove(historysListNewHistorys);
                        oldStudentnoOfHistorysListNewHistorys = em.merge(oldStudentnoOfHistorysListNewHistorys);
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
                Integer id = students.getStudentno();
                if (findStudents(id) == null) {
                    throw new NonexistentEntityException("The students with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Students students;
            try {
                students = em.getReference(Students.class, id);
                students.getStudentno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The students with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Studentsubjects> studentsubjectsListOrphanCheck = students.getStudentsubjectsList();
            for (Studentsubjects studentsubjectsListOrphanCheckStudentsubjects : studentsubjectsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Students (" + students + ") cannot be destroyed since the Studentsubjects " + studentsubjectsListOrphanCheckStudentsubjects + " in its studentsubjectsList field has a non-nullable studentno field.");
            }
            List<Historys> historysListOrphanCheck = students.getHistorysList();
            for (Historys historysListOrphanCheckHistorys : historysListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Students (" + students + ") cannot be destroyed since the Historys " + historysListOrphanCheckHistorys + " in its historysList field has a non-nullable studentno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Levels levelno = students.getLevelno();
            if (levelno != null) {
                levelno.getStudentsList().remove(students);
                levelno = em.merge(levelno);
            }
            em.remove(students);
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

    public List<Students> findStudentsEntities() {
        return findStudentsEntities(true, -1, -1);
    }

    public List<Students> findStudentsEntities(int maxResults, int firstResult) {
        return findStudentsEntities(false, maxResults, firstResult);
    }

    private List<Students> findStudentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Students.class));
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

    public Students findStudents(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Students.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Students> rt = cq.from(Students.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
