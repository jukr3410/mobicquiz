/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.sun.org.apache.xpath.internal.axes.SubContextList;
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
import jpacontroller.QuestionsJpaController;
import jpacontroller.QuizsJpaController;
import jpacontroller.SubjectsJpaController;
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
        HttpSession session = request.getSession();
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
        String correctAns = request.getParameter("correctans");

        if (teacher != null) {

            Quizs newQuiz = (Quizs) session.getAttribute("newquiz");
            List<Questions> newQuestions = (List<Questions>) session.getAttribute("newquestions");
            if (newQuiz == null && newQuestions == null) {
                newQuiz = new Quizs();
                newQuestions = new ArrayList(100);
                session.setAttribute("newquiz", newQuiz);
                session.setAttribute("newquestions", newQuestions);
            }

            QuizsJpaController quijc = new QuizsJpaController(utx, emf);
            QuestionsJpaController quejc = new QuestionsJpaController(utx, emf);
            
            if (title!=null && time !=null && fullScore != null) {
                SubjectsJpaController sjc = new SubjectsJpaController(utx, emf);   
                Subjects s = sjc.findSubjects(subject);
                
                newQuiz.setQuizno(s.getSubjectno()+Integer.toString(quijc.getQuizsCount()+1));
                newQuiz.setTitle(title);
                            
                newQuiz.setSubjectno(s);
                newQuiz.setLevelno(s.getLevelno());
                newQuiz.setTime(Integer.valueOf(time));
                newQuiz.setFullscore(Integer.valueOf(fullScore));
                newQuiz.setTeacherno(teacher);
                //session.setAttribute("newquiz", newQuiz);
            }
            
            if (newQuestion != null && ans1 != null && ans2 != null && ans3 != null && ans4 != null && correctAns !=null) {            
                Questions q = new Questions(Integer.toString(quijc.getQuizsCount()+1)+Integer.toString(newQuestions.size()+1), newQuestion, ans1, ans2, ans3, ans4, correctAns, newQuiz);
                newQuestions.add(q);
               
            }

        }
        SubjectsJpaController sjc = new SubjectsJpaController(utx, emf);
        List<Subjects> subjectses = sjc.findSubjectsEntities();
        request.setAttribute("subjects", subjectses);

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
