/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jn
 */
public class AuthenticationFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false); //รับ Session มา, false หากไม่มีก็ไม่ต้องสร้าง 

        //เช็คว่ามี Session หรือไม่
        if (session == null || session.getAttribute("student") == null) {
            config.getServletContext().getRequestDispatcher("/Login").forward(request, response); //ให้มันส่งหน้าเว็บ Login ไป
        } else {
            chain.doFilter(request, response); //เปลี่ยน filter
        }
    }

    @Override
    public void destroy() {
    }

}
