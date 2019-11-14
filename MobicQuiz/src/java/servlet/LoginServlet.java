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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpacontroller.StudentsJpaController;
import jpacontroller.TeachersJpaController;
import model.Students;
import model.Teachers;

/**
 *
 * @author Jn
 */
public class LoginServlet extends HttpServlet {

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
        String id = request.getParameter("id");      
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");
        
        if (id != null && password != null && usertype != null) {
            if (usertype.equals("student")) {
                StudentsJpaController sjc = new StudentsJpaController(utx, emf);
                Students student = sjc.findStudents(Integer.valueOf(id));
                if (student != null && student.getPassword().equals(password)) {
                    session.setAttribute("student", student);
                    response.sendRedirect("/MobicQuiz/MobicQuiz.jsp");
                    return;
                }else{
                    request.setAttribute("errorlogin", "Wrong ID or password !!");
                }
            } else if (usertype.equals("teacher")) {
                TeachersJpaController tjc = new TeachersJpaController(utx, emf);
                Teachers teacher = tjc.findTeachers(Integer.valueOf(id));
                if (teacher != null && teacher.getPassword().equals(password)) {
                    session.setAttribute("teacher", teacher);
                    response.sendRedirect("/MobicQuiz/MobicQuiz.jsp");
                    return;
                }else{
                    request.setAttribute("errorlogin", "Wrong ID or password !!");
                }
            }
            
        }

        getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);

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
