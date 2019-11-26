/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ctrl.HistorysJpaController;
import ctrl.QuestionsJpaController;
import ctrl.QuizsJpaController;
import ctrl.exceptions.NonexistentEntityException;
import ctrl.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Historys;
import model.Questions;
import model.Quizs;

/**
 *
 * @author Jn
 */
public class RemoveServlet extends HttpServlet {

    @PersistenceUnit(unitName = "WebApplication1PU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String removeQuiz = "1";

        if (true) {
            QuizsJpaController qjc = new QuizsJpaController(utx, emf);
            List<Quizs> quizs = qjc.findQuizsEntities();
            request.setAttribute("quizs", quizs);

            if (removeQuiz != null) {

                QuizsJpaController quijc = new QuizsJpaController(utx, emf);
                Quizs quiz = quijc.findQuizs(removeQuiz);

                QuestionsJpaController quejc = new QuestionsJpaController(utx, emf);
                HistorysJpaController hjc = new HistorysJpaController(utx, emf);
                List<Historys> historysByQuiz = quiz.getHistorysList();
                List<Questions> questionses = quiz.getQuestionsList();
                if (questionses != null) {
                    for (Questions question : questionses) {
                        try {
                            quejc.destroy(question.getQuestionno());
                        } catch (RollbackFailureException ex) {
                            Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (historysByQuiz != null) {
                    for (Historys history : historysByQuiz) {
                        try {
                            hjc.destroy(history.getHistoryno());
                        } catch (RollbackFailureException ex) {
                            Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                try {
                    quijc.destroy(removeQuiz);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RemoveServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("msg", "Sessces");
            }

        }
        getServletContext().getRequestDispatcher("/Remove.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
