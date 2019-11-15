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
import jpacontroller.StudentsJpaController;
import jpacontroller.TeachersJpaController;
import jpacontroller.exceptions.RollbackFailureException;
import model.EmailUtility;
import model.Levels;
import model.Students;
import model.Teachers;

/**
 *
 * @author Jn
 */
public class RegisterServlet extends HttpServlet {

    @PersistenceUnit(unitName = "MobicQuizPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    private String host = "smtp.gmail.com";
    private String port = "587";
    private String user = "mobicquiz@gmail.com";
    private String pass = "mobic303";

    public String activateKey() {
        int ramdom = (int) (Math.random() * 100000);
        String activateKey = String.valueOf(ramdom);
        return activateKey;
    }

    public void sentKey(String email, String name, String activateKey) {
        String subject = "[MobicQuiz] Please activate your account";
        String content = "Hi " + name + "\nPlease use this code to Activate your account.\nActivate Key :"
                + activateKey + "\nThanks,\nThe Mobic Quiz";
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
        HttpSession session = request.getSession();
        String usertype = request.getParameter("usertype");
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String grade = request.getParameter("grade");
        if (usertype != null && name != null && id != null && password != null && email != null) {
            if (usertype.equals("student") && grade != null) {
                StudentsJpaController sjc = new StudentsJpaController(utx, emf);               
                Students student = sjc.findStudents(Integer.valueOf(id));                
                if (student == null) {
                    String activateKey = activateKey();
                    student = new Students(Integer.valueOf(id), name, email, password, activateKey, new Levels(Integer.valueOf(grade)));
                    try {
                        sjc.create(student);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sentKey(email, name, activateKey);
                    session.setAttribute("email", email); 
                    session.setAttribute("id", id);
                    response.sendRedirect("/MobicQuiz/Activate");
                    return;
                }else{
                    request.setAttribute("errorregister", "ID or Email already exists!");
                    getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
                    return;
                }
            } else if (usertype.equals("teacher")) {
                TeachersJpaController tjc = new TeachersJpaController(utx, emf);
                Teachers teacher = tjc.findTeachers(Integer.valueOf(id));
                if (teacher == null) {
                    String activateKey = activateKey();
                    teacher = new Teachers(Integer.valueOf(id), name, email, password, activateKey);
                    try {
                        tjc.create(teacher);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sentKey(email, name, activateKey);
                    session.setAttribute("email", email); 
                    session.setAttribute("id", id);
                    response.sendRedirect("/MobicQuiz/Activate");
                    return;
                }else{
                    request.setAttribute("errorregister", "ID or Email already exists!");
                    getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
                    return;
                }
            }
            
        }
        getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
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
