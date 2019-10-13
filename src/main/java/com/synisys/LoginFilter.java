package com.synisys;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest)servletRequest).getCookies();
        if(cookies != null){
            boolean exists = false;
            for(int i = 0; i < cookies.length; i++){
                if(cookies[i].getName().equals("token") && !cookies[i].getValue().equals("")){
                    exists = true;
                    ((HttpServletResponse)servletResponse).sendRedirect("/home");
                    break;
                }
            }
            if(!exists){
                ((HttpServletResponse)servletResponse).sendRedirect("/login");
            }
        } else {
            ((HttpServletResponse)servletResponse).sendRedirect("/login");
        }

    }
}
