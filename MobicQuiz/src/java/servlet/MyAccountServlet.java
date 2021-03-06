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
import model.ChangeImage;
import model.Students;
import model.Teachers;

/**
 *
 * @author Student
 */
public class MyAccountServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String userType = (String) session.getAttribute("usertype");
        
        String target = (getServletContext().getRealPath("/images")).replace("build\\", "") + "\\";
        String name = null;

        PrintWriter out = response.getWriter();
        String password = request.getParameter("password");
        String newpassword = request.getParameter("newpassword");
        String confirmnewpassword = request.getParameter("confirmnewpassword");

        StudentsJpaController sjc = new StudentsJpaController(utx, emf);
        TeachersJpaController tjc = new TeachersJpaController(utx, emf);

        if (userType != null) {
            if (userType.equals("student")) {
                Students student = (Students) session.getAttribute("user");
                if (password != null) {
                    if (student.getPassword().equals(password) && newpassword.equals(confirmnewpassword)) {
                        student.setPassword(newpassword);
                        try {
                            sjc.edit(student);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        session.invalidate();
                        response.sendRedirect("/MobicQuiz/Login");
                        return;
                    } else {
                        response.sendRedirect("/MobicQuiz/Login");
                        return;
                    }
                }

            } else if (userType.equals("teacher")) {
                Teachers teacher = (Teachers) session.getAttribute("user");
                if (password != null) {
                    if (teacher.getPassword().equals(password) && newpassword.equals(confirmnewpassword)) {
                        teacher.setPassword(newpassword);
                        try {
                            tjc.edit(teacher);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        session.invalidate();
                        response.sendRedirect("/MobicQuiz/Login");
                        return;
                    } else {
                        response.sendRedirect("/MobicQuiz/Login");
                        return;
                    }

                }
            }
        }

        if (userType != null) {
            if (userType.equals("student")) {
                Students student = (Students) session.getAttribute("user");
                if (student != null) {
                    name = "S" + student.getStudentno();
                }
            } else if (userType.equals("teacher")) {
                Teachers teacher = (Teachers) session.getAttribute("user");
                if (teacher != null) {
                    name = "T" + teacher.getTeacherno();
                }
            }
            ChangeImage img = new ChangeImage();
            img.editImages(request, target, name);
            try {
                Thread.sleep(1700);
                response.sendRedirect("/MobicQuiz/MyAccount");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
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
        getServletContext().getRequestDispatcher("/MyAccount.jsp").forward(request, response);
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
