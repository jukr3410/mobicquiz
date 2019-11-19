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
import model.Subjects;
import model.Questions;
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
import model.Quizs;

/**
 *
 * @author Student
 */
public class QuizsJpaController implements Serializable {

    public QuizsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Quizs quizs) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (quizs.getQuestionsList() == null) {
            quizs.setQuestionsList(new ArrayList<Questions>());
        }
        if (quizs.getHistorysList() == null) {
            quizs.setHistorysList(new ArrayList<Historys>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Levels levelno = quizs.getLevelno();
            if (levelno != null) {
                levelno = em.getReference(levelno.getClass(), levelno.getLevelno());
                quizs.setLevelno(levelno);
            }
            Subjects subjectno = quizs.getSubjectno();
            if (subjectno != null) {
                subjectno = em.getReference(subjectno.getClass(), subjectno.getSubjectno());
                quizs.setSubjectno(subjectno);
            }
            List<Questions> attachedQuestionsList = new ArrayList<Questions>();
            for (Questions questionsListQuestionsToAttach : quizs.getQuestionsList()) {
                questionsListQuestionsToAttach = em.getReference(questionsListQuestionsToAttach.getClass(), questionsListQuestionsToAttach.getQuestionno());
                attachedQuestionsList.add(questionsListQuestionsToAttach);
            }
            quizs.setQuestionsList(attachedQuestionsList);
            List<Historys> attachedHistorysList = new ArrayList<Historys>();
            for (Historys historysListHistorysToAttach : quizs.getHistorysList()) {
                historysListHistorysToAttach = em.getReference(historysListHistorysToAttach.getClass(), historysListHistorysToAttach.getHistoryno());
                attachedHistorysList.add(historysListHistorysToAttach);
            }
            quizs.setHistorysList(attachedHistorysList);
            em.persist(quizs);
            if (levelno != null) {
                levelno.getQuizsList().add(quizs);
                levelno = em.merge(levelno);
            }
            if (subjectno != null) {
                subjectno.getQuizsList().add(quizs);
                subjectno = em.merge(subjectno);
            }
            for (Questions questionsListQuestions : quizs.getQuestionsList()) {
                Quizs oldQuiznoOfQuestionsListQuestions = questionsListQuestions.getQuizno();
                questionsListQuestions.setQuizno(quizs);
                questionsListQuestions = em.merge(questionsListQuestions);
                if (oldQuiznoOfQuestionsListQuestions != null) {
                    oldQuiznoOfQuestionsListQuestions.getQuestionsList().remove(questionsListQuestions);
                    oldQuiznoOfQuestionsListQuestions = em.merge(oldQuiznoOfQuestionsListQuestions);
                }
            }
            for (Historys historysListHistorys : quizs.getHistorysList()) {
                Quizs oldQuiznoOfHistorysListHistorys = historysListHistorys.getQuizno();
                historysListHistorys.setQuizno(quizs);
                historysListHistorys = em.merge(historysListHistorys);
                if (oldQuiznoOfHistorysListHistorys != null) {
                    oldQuiznoOfHistorysListHistorys.getHistorysList().remove(historysListHistorys);
                    oldQuiznoOfHistorysListHistorys = em.merge(oldQuiznoOfHistorysListHistorys);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findQuizs(quizs.getQuizno()) != null) {
                throw new PreexistingEntityException("Quizs " + quizs + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Quizs quizs) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Quizs persistentQuizs = em.find(Quizs.class, quizs.getQuizno());
            Levels levelnoOld = persistentQuizs.getLevelno();
            Levels levelnoNew = quizs.getLevelno();
            Subjects subjectnoOld = persistentQuizs.getSubjectno();
            Subjects subjectnoNew = quizs.getSubjectno();
            List<Questions> questionsListOld = persistentQuizs.getQuestionsList();
            List<Questions> questionsListNew = quizs.getQuestionsList();
            List<Historys> historysListOld = persistentQuizs.getHistorysList();
            List<Historys> historysListNew = quizs.getHistorysList();
            List<String> illegalOrphanMessages = null;
            for (Questions questionsListOldQuestions : questionsListOld) {
                if (!questionsListNew.contains(questionsListOldQuestions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Questions " + questionsListOldQuestions + " since its quizno field is not nullable.");
                }
            }
            for (Historys historysListOldHistorys : historysListOld) {
                if (!historysListNew.contains(historysListOldHistorys)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historys " + historysListOldHistorys + " since its quizno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (levelnoNew != null) {
                levelnoNew = em.getReference(levelnoNew.getClass(), levelnoNew.getLevelno());
                quizs.setLevelno(levelnoNew);
            }
            if (subjectnoNew != null) {
                subjectnoNew = em.getReference(subjectnoNew.getClass(), subjectnoNew.getSubjectno());
                quizs.setSubjectno(subjectnoNew);
            }
            List<Questions> attachedQuestionsListNew = new ArrayList<Questions>();
            for (Questions questionsListNewQuestionsToAttach : questionsListNew) {
                questionsListNewQuestionsToAttach = em.getReference(questionsListNewQuestionsToAttach.getClass(), questionsListNewQuestionsToAttach.getQuestionno());
                attachedQuestionsListNew.add(questionsListNewQuestionsToAttach);
            }
            questionsListNew = attachedQuestionsListNew;
            quizs.setQuestionsList(questionsListNew);
            List<Historys> attachedHistorysListNew = new ArrayList<Historys>();
            for (Historys historysListNewHistorysToAttach : historysListNew) {
                historysListNewHistorysToAttach = em.getReference(historysListNewHistorysToAttach.getClass(), historysListNewHistorysToAttach.getHistoryno());
                attachedHistorysListNew.add(historysListNewHistorysToAttach);
            }
            historysListNew = attachedHistorysListNew;
            quizs.setHistorysList(historysListNew);
            quizs = em.merge(quizs);
            if (levelnoOld != null && !levelnoOld.equals(levelnoNew)) {
                levelnoOld.getQuizsList().remove(quizs);
                levelnoOld = em.merge(levelnoOld);
            }
            if (levelnoNew != null && !levelnoNew.equals(levelnoOld)) {
                levelnoNew.getQuizsList().add(quizs);
                levelnoNew = em.merge(levelnoNew);
            }
            if (subjectnoOld != null && !subjectnoOld.equals(subjectnoNew)) {
                subjectnoOld.getQuizsList().remove(quizs);
                subjectnoOld = em.merge(subjectnoOld);
            }
            if (subjectnoNew != null && !subjectnoNew.equals(subjectnoOld)) {
                subjectnoNew.getQuizsList().add(quizs);
                subjectnoNew = em.merge(subjectnoNew);
            }
            for (Questions questionsListNewQuestions : questionsListNew) {
                if (!questionsListOld.contains(questionsListNewQuestions)) {
                    Quizs oldQuiznoOfQuestionsListNewQuestions = questionsListNewQuestions.getQuizno();
                    questionsListNewQuestions.setQuizno(quizs);
                    questionsListNewQuestions = em.merge(questionsListNewQuestions);
                    if (oldQuiznoOfQuestionsListNewQuestions != null && !oldQuiznoOfQuestionsListNewQuestions.equals(quizs)) {
                        oldQuiznoOfQuestionsListNewQuestions.getQuestionsList().remove(questionsListNewQuestions);
                        oldQuiznoOfQuestionsListNewQuestions = em.merge(oldQuiznoOfQuestionsListNewQuestions);
                    }
                }
            }
            for (Historys historysListNewHistorys : historysListNew) {
                if (!historysListOld.contains(historysListNewHistorys)) {
                    Quizs oldQuiznoOfHistorysListNewHistorys = historysListNewHistorys.getQuizno();
                    historysListNewHistorys.setQuizno(quizs);
                    historysListNewHistorys = em.merge(historysListNewHistorys);
                    if (oldQuiznoOfHistorysListNewHistorys != null && !oldQuiznoOfHistorysListNewHistorys.equals(quizs)) {
                        oldQuiznoOfHistorysListNewHistorys.getHistorysList().remove(historysListNewHistorys);
                        oldQuiznoOfHistorysListNewHistorys = em.merge(oldQuiznoOfHistorysListNewHistorys);
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
                String id = quizs.getQuizno();
                if (findQuizs(id) == null) {
                    throw new NonexistentEntityException("The quizs with id " + id + " no longer exists.");
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
            Quizs quizs;
            try {
                quizs = em.getReference(Quizs.class, id);
                quizs.getQuizno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The quizs with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Questions> questionsListOrphanCheck = quizs.getQuestionsList();
            for (Questions questionsListOrphanCheckQuestions : questionsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Quizs (" + quizs + ") cannot be destroyed since the Questions " + questionsListOrphanCheckQuestions + " in its questionsList field has a non-nullable quizno field.");
            }
            List<Historys> historysListOrphanCheck = quizs.getHistorysList();
            for (Historys historysListOrphanCheckHistorys : historysListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Quizs (" + quizs + ") cannot be destroyed since the Historys " + historysListOrphanCheckHistorys + " in its historysList field has a non-nullable quizno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Levels levelno = quizs.getLevelno();
            if (levelno != null) {
                levelno.getQuizsList().remove(quizs);
                levelno = em.merge(levelno);
            }
            Subjects subjectno = quizs.getSubjectno();
            if (subjectno != null) {
                subjectno.getQuizsList().remove(quizs);
                subjectno = em.merge(subjectno);
            }
            em.remove(quizs);
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

    public List<Quizs> findQuizsEntities() {
        return findQuizsEntities(true, -1, -1);
    }

    public List<Quizs> findQuizsEntities(int maxResults, int firstResult) {
        return findQuizsEntities(false, maxResults, firstResult);
    }

    private List<Quizs> findQuizsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Quizs.class));
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

    public Quizs findQuizs(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Quizs.class, id);
        } finally {
            em.close();
        }
    }
    
        public List<Quizs> findQuizsByLevelNo(String levelno) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Quizs.findByLevelno");
        query.setParameter("levelno", levelno);
        List<Quizs> resultList = query.getResultList();
        try {
            return resultList.isEmpty() ? null : resultList;
        } finally {
            em.close();
        }
    }
    
    public List<Quizs> findQuizsByTeacherNo(String teacherno) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Historys.findByTeacherno");
        query.setParameter("teacherno", teacherno);
        List<Quizs> resultList = query.getResultList();
        try {
            return resultList.isEmpty() ? null : resultList;
        } finally {
            em.close();
        }
    }
    

    public int getQuizsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Quizs> rt = cq.from(Quizs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
