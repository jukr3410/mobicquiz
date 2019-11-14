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
import javax.transaction.UserTransaction;
import jpacontroller.StudentsJpaController;
import jpacontroller.TeachersJpaController;
import jpacontroller.exceptions.NonexistentEntityException;
import jpacontroller.exceptions.RollbackFailureException;
import model.Students;
import model.Teachers;
import org.jboss.weld.context.http.HttpRequestContext;

/**
 *
 * @author Jn
 */
public class ActivateServlet extends HttpServlet {

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
        String activateCode = request.getParameter("activatecode");
        String id = request.getParameter("id");
        String email = request.getParameter("email");
        if (activateCode != null && id != null) {
            StudentsJpaController sjc = new StudentsJpaController(utx, emf);
            Students student = sjc.findStudents(Integer.valueOf(id));
            
            TeachersJpaController tjc = new TeachersJpaController(utx, emf);
            Teachers teacher = tjc.findTeachers(Integer.valueOf(id));
            
            
            if (student!=null&&student.getActivated()==null) {
                student.setActivated("activated");
                try {
                    sjc.edit(student);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("/MobicQuiz/Login");
                return;
            }else if (teacher!=null&&teacher.getActivated()==null) {
                teacher.setActivated("activated");
                try {
                    tjc.edit(teacher);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect("/MobicQuiz/Login");
                return;
            }else{
                request.setAttribute("erroractivate", "! This account does not exist or has been Activated.");
            }
            
            

        }
        request.setAttribute("email", email);
        getServletContext().getRequestDispatcher("/Activate.jsp").forward(request, response);
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
