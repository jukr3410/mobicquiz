/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import model.Levels;
import model.Questions;
import model.Quizs;
import model.Subjects;
import model.Teachers;

/**
 *
 * @author ACER
 */
public class CreateQuizServlet extends HttpServlet {
    
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
        HttpSession session =request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("user");
        String subject = request.getParameter("subject");
        String level = request.getParameter("level");
        String title = request.getParameter("title");
        String time = request.getParameter("time");
        String fullScore = request.getParameter("fullscore"); 
        String newQuestion = request.getParameter("question");
        String ans1 = request.getParameter("ans1");
        String ans2 = request.getParameter("ans2");
        String ans3 = request.getParameter("ans3");
        String ans4 = request.getParameter("ans4");
        
        QuizsJpaController qjc = new QuizsJpaController(utx, emf);

        Quizs quiz = new Quizs(Integer.toString(qjc.getQuizsCount()+1), title, Integer.valueOf(time), Integer.valueOf(fullScore), new Levels(level), new Subjects(subject), teacher);
        Questions question = new Questions(fullScore, time, ans1, ans2, ans3, ans4, subject, quiz);
        List<Questions> questions = new ArrayList(100);
        questions.add(question);
        
        
        getServletContext().getRequestDispatcher("/CreateQuiz.jsp").forward(request, response);
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
