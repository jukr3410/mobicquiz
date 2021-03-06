/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import jpacontroller.QuizsJpaController;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Quizs;
import model.Teachers;

/**
 *
 * @author Jn
 */
public class StatusQuizServlet extends HttpServlet {

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
        String enableQuiz = request.getParameter("enablequiz");
        String disableQuiz = request.getParameter("disablequiz");
        Teachers teacher = (Teachers) session.getAttribute("user");
        QuizsJpaController qjc = new QuizsJpaController(utx, emf);
        if (enableQuiz!=null) {
            Quizs quiz = qjc.findQuizs(enableQuiz);
            if (quiz!=null && teacher.getTeacherno().equals(quiz.getTeacherno().getTeacherno())) {
                quiz.setStatus("on");
                try {
                    qjc.edit(quiz);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
            }
        } else if (disableQuiz!=null) {
            Quizs quiz = qjc.findQuizs(disableQuiz);
            if (quiz!=null && teacher.getTeacherno().equals(quiz.getTeacherno().getTeacherno())) {
                String status = null;
                quiz.setStatus(status);
                try {
                    qjc.edit(quiz);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(StatusQuizServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        response.sendRedirect("/MobicQuiz/ManageQuiz");
        
        
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
