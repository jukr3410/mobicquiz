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
import model.Subjects;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.PreexistingEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Levels;

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
        if (levels.getSubjectsList() == null) {
            levels.setSubjectsList(new ArrayList<Subjects>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Subjects> attachedSubjectsList = new ArrayList<Subjects>();
            for (Subjects subjectsListSubjectsToAttach : levels.getSubjectsList()) {
                subjectsListSubjectsToAttach = em.getReference(subjectsListSubjectsToAttach.getClass(), subjectsListSubjectsToAttach.getSubjectno());
                attachedSubjectsList.add(subjectsListSubjectsToAttach);
            }
            levels.setSubjectsList(attachedSubjectsList);
            em.persist(levels);
            for (Subjects subjectsListSubjects : levels.getSubjectsList()) {
                Levels oldLevelnoOfSubjectsListSubjects = subjectsListSubjects.getLevelno();
                subjectsListSubjects.setLevelno(levels);
                subjectsListSubjects = em.merge(subjectsListSubjects);
                if (oldLevelnoOfSubjectsListSubjects != null) {
                    oldLevelnoOfSubjectsListSubjects.getSubjectsList().remove(subjectsListSubjects);
                    oldLevelnoOfSubjectsListSubjects = em.merge(oldLevelnoOfSubjectsListSubjects);
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

    public void edit(Levels levels) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Levels persistentLevels = em.find(Levels.class, levels.getLevelno());
            List<Subjects> subjectsListOld = persistentLevels.getSubjectsList();
            List<Subjects> subjectsListNew = levels.getSubjectsList();
            List<Subjects> attachedSubjectsListNew = new ArrayList<Subjects>();
            for (Subjects subjectsListNewSubjectsToAttach : subjectsListNew) {
                subjectsListNewSubjectsToAttach = em.getReference(subjectsListNewSubjectsToAttach.getClass(), subjectsListNewSubjectsToAttach.getSubjectno());
                attachedSubjectsListNew.add(subjectsListNewSubjectsToAttach);
            }
            subjectsListNew = attachedSubjectsListNew;
            levels.setSubjectsList(subjectsListNew);
            levels = em.merge(levels);
            for (Subjects subjectsListOldSubjects : subjectsListOld) {
                if (!subjectsListNew.contains(subjectsListOldSubjects)) {
                    subjectsListOldSubjects.setLevelno(null);
                    subjectsListOldSubjects = em.merge(subjectsListOldSubjects);
                }
            }
            for (Subjects subjectsListNewSubjects : subjectsListNew) {
                if (!subjectsListOld.contains(subjectsListNewSubjects)) {
                    Levels oldLevelnoOfSubjectsListNewSubjects = subjectsListNewSubjects.getLevelno();
                    subjectsListNewSubjects.setLevelno(levels);
                    subjectsListNewSubjects = em.merge(subjectsListNewSubjects);
                    if (oldLevelnoOfSubjectsListNewSubjects != null && !oldLevelnoOfSubjectsListNewSubjects.equals(levels)) {
                        oldLevelnoOfSubjectsListNewSubjects.getSubjectsList().remove(subjectsListNewSubjects);
                        oldLevelnoOfSubjectsListNewSubjects = em.merge(oldLevelnoOfSubjectsListNewSubjects);
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

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
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
            List<Subjects> subjectsList = levels.getSubjectsList();
            for (Subjects subjectsListSubjects : subjectsList) {
                subjectsListSubjects.setLevelno(null);
                subjectsListSubjects = em.merge(subjectsListSubjects);
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
