/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpacontroller.StudentsJpaController;
import jpacontroller.TeachersJpaController;
import model.EmailUtility;
import model.Students;
import model.Teachers;

/**
 *
 * @author Student
 */
public class ForgotPasswordServlet extends HttpServlet {

    @PersistenceUnit(unitName = "MobicQuizPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    private String host = "smtp.gmail.com";
    private String port = "587";
    private String user = "mobicquiz@gmail.com";
    private String pass = "mobic303";

    public void sentURL(String email, String name, String url) {
        String subject = "[MobicQuiz] Password Recovery";
        String content = "We have received a password change request for your MOBIC account "
                + name + "\nplease click the link below.\n"
                + url + "\nThanks,\nThe Mobic Quiz";
        if (email != null) {
            try {
                EmailUtility.sendEmail(host, port, user, pass, email, subject, content);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

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
        String idForReset = request.getParameter("idforreset");
        if (idForReset != null) {
            StudentsJpaController sjc = new StudentsJpaController(utx, emf);
            Students student = sjc.findStudents(idForReset);
            if (student == null) {
                student = sjc.findStudentsByEmail(idForReset);
            }
            if (student != null) {
                String url = "http://localhost:8080/MobicQuiz/ResetPassword.jsp?idforreset="+student.getStudentno();
                sentURL(student.getEmail(), student.getName(), url);
                request.setAttribute("statusforgot", "Check in you Email: " + student.getEmail());
            } else {
                TeachersJpaController tjc = new TeachersJpaController(utx, emf);
                Teachers teacher = tjc.findTeachers(idForReset);
                if (teacher == null) {
                    teacher = tjc.findTeachersByEmail(idForReset);
                }
                if (teacher != null) {
                    String url = "http://localhost:8080/MobicQuiz/ResetPassword.jsp?idforreset="+teacher.getTeacherno();
                    sentURL(teacher.getEmail(), teacher.getName(), url);
                    request.setAttribute("statusforgot", "Check in you Email: " + teacher.getEmail());
                } else {
                    request.setAttribute("errorforgot", "! This account does not exist.");
                }
            }

        }

        getServletContext().getRequestDispatcher("/ForgotPassword.jsp").forward(request, response);
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
