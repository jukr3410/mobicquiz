/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpacontroller.QuestionsJpaController;
import jpacontroller.QuizsJpaController;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Questions;
import model.Quizs;
import model.Teachers;

/**
 *
 * @author ACER
 */
public class RemoveServlet extends HttpServlet {

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
        String removeQuiz = request.getParameter("removequiz").trim();
        Teachers teacher = (Teachers) session.getAttribute("user");

        if (teacher != null) {
            QuizsJpaController qjc = new QuizsJpaController(utx, emf);
            List<Quizs> quizs = qjc.findQuizsByTeacherNo(teacher.getTeacherno());
            request.setAttribute("quizs", quizs);

            if (removeQuiz != null) {
                QuizsJpaController quijc = new QuizsJpaController(utx, emf);
                Quizs quiz = quijc.findQuizs(removeQuiz);

                if (quiz != null && teacher.getTeacherno().equals(quiz.getTeacherno().getTeacherno())) {

                    QuestionsJpaController quejc = new QuestionsJpaController(utx, emf);
                    List<Questions> questionses = quejc.findQuestionsByQuizNo(removeQuiz);
                    if (questionses != null) {
                        for (Questions questionse : questionses) {
                            try {
                                quejc.destroy(questionse.getQuestionno());
                            } catch (RollbackFailureException ex) {
                                Logger.getLogger(ManageQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                Logger.getLogger(ManageQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        System.out.println("Passs Que !!!!!!!!!!!");
                    }

                    try {
                        quijc.destroy(removeQuiz);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(ManageQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(ManageQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(ManageQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    response.sendRedirect("/MobicQuiz/ManageQuiz");
                    return;
                }
                
            }
        }
        request.setAttribute("errorremove", "Can not remove");
        getServletContext().getRequestDispatcher("/ManageQuiz.jsp").forward(request, response);
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
