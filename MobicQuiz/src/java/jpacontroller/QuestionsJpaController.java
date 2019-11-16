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
import model.Questions;
import model.Quizs;

/**
 *
 * @author Student
 */
public class QuestionsJpaController implements Serializable {

    public QuestionsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Questions questions) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quizs quizno = questions.getQuizno();
            if (quizno != null) {
                quizno = em.getReference(quizno.getClass(), quizno.getQuizno());
                questions.setQuizno(quizno);
            }
            em.persist(questions);
            if (quizno != null) {
                quizno.getQuestionsList().add(questions);
                quizno = em.merge(quizno);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findQuestions(questions.getQuestionno()) != null) {
                throw new PreexistingEntityException("Questions " + questions + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Questions questions) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Questions persistentQuestions = em.find(Questions.class, questions.getQuestionno());
            Quizs quiznoOld = persistentQuestions.getQuizno();
            Quizs quiznoNew = questions.getQuizno();
            if (quiznoNew != null) {
                quiznoNew = em.getReference(quiznoNew.getClass(), quiznoNew.getQuizno());
                questions.setQuizno(quiznoNew);
            }
            questions = em.merge(questions);
            if (quiznoOld != null && !quiznoOld.equals(quiznoNew)) {
                quiznoOld.getQuestionsList().remove(questions);
                quiznoOld = em.merge(quiznoOld);
            }
            if (quiznoNew != null && !quiznoNew.equals(quiznoOld)) {
                quiznoNew.getQuestionsList().add(questions);
                quiznoNew = em.merge(quiznoNew);
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
                String id = questions.getQuestionno();
                if (findQuestions(id) == null) {
                    throw new NonexistentEntityException("The questions with id " + id + " no longer exists.");
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
            Questions questions;
            try {
                questions = em.getReference(Questions.class, id);
                questions.getQuestionno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The questions with id " + id + " no longer exists.", enfe);
            }
            Quizs quizno = questions.getQuizno();
            if (quizno != null) {
                quizno.getQuestionsList().remove(questions);
                quizno = em.merge(quizno);
            }
            em.remove(questions);
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

    public List<Questions> findQuestionsEntities() {
        return findQuestionsEntities(true, -1, -1);
    }

    public List<Questions> findQuestionsEntities(int maxResults, int firstResult) {
        return findQuestionsEntities(false, maxResults, firstResult);
    }

    private List<Questions> findQuestionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Questions.class));
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

    public Questions findQuestions(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Questions.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuestionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Questions> rt = cq.from(Questions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
