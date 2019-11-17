/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpacontroller.HistorysJpaController;
import jpacontroller.QuestionsJpaController;
import jpacontroller.exceptions.RollbackFailureException;
import model.Historys;
import model.Questions;
import model.Quizs;
import model.Students;

/**
 *
 * @author Jn
 */
public class FinishExamServlet extends HttpServlet {

    @PersistenceUnit(unitName = "MobicQuizPU")
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
        HttpSession session = request.getSession();
        Quizs quiz = (Quizs) session.getAttribute("quiz");
        Students student = (Students) session.getAttribute("student");
        QuestionsJpaController qjc = new QuestionsJpaController(utx, emf);
        List<Questions> questions = qjc.findQuestionsByQuizNo(quiz.getQuizno());
        int score = 0;
        double scorePerQ = quiz.getFullscore() / questions.size();
        int done = 0;
        for (Questions question : questions) {
            String myAns = request.getParameter(question.getQuestionno());
            if (myAns == null) {
                continue;
            } else {
                done++;
                if (question.isCorrect(myAns)) {
                    score += scorePerQ;
                }
            }
        }

        HistorysJpaController hjc = new HistorysJpaController(utx, emf);
        int hisNo = hjc.getHistorysCount() + 1;
        Historys history = new Historys(Integer.toString(hisNo), score, new Date(), quiz, student);
        try {
            hjc.create(history);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(ExamServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExamServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Passs!!");
        System.out.println(score);
        request.setAttribute("done", done);
        request.setAttribute("score", score);
        session.removeAttribute("quiz");

        getServletContext().getRequestDispatcher("/FinishExam.jsp").forward(request, response);
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
