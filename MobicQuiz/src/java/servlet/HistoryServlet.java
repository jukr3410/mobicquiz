/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import jpacontroller.HistorysJpaController;
import jpacontroller.QuizsJpaController;
import model.Historys;
import model.Quizs;
import model.Students;
import model.Teachers;

/**
 *
 * @author Jn
 */
public class HistoryServlet extends HttpServlet {

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
        String userType = (String) session.getAttribute("usertype");
        HistorysJpaController hjc = new HistorysJpaController(utx, emf);
        if (userType.equals("student")) {
            Students student = (Students) session.getAttribute("user");           
            if (student != null) {               
                List<Historys> historys = hjc.findHistorysByStudentNo(student.getStudentno());
                if (historys != null) {
                    request.setAttribute("historys", historys);
                }
            }
        }else if(userType.equals("teacher")){
            Teachers teacher = (Teachers) session.getAttribute("user");
            if (teacher!=null) {
                QuizsJpaController qjc = new QuizsJpaController(utx, emf);
                List<Quizs> quizs = qjc.findQuizsByTeacherNo(teacher.getTeacherno());
                //List<Quizs> quizs = qjc.findQuizsEntities();
                List<Historys> historys = hjc.findHistorysByTeacherNo(teacher.getTeacherno());
                if (historys != null) {
                    request.setAttribute("tquizs", quizs);
                    request.setAttribute("historys", historys);
                }
            }
        }

        getServletContext().getRequestDispatcher("/HistoryTeacher.jsp").forward(request, response);
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
