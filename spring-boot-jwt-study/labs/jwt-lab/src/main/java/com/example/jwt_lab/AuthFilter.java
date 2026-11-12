package com.example.jwt_lab;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component 
public class AuthFilter implements Filter{

    private final JwtService jwtService;

    public AuthFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException{

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            if(req.getRequestURI().equals("/login")){
                chain.doFilter(request, response);
                return;
            }
            
            String path = req.getRequestURI();
            if(path.equals("/login") || path.startsWith("/h2-console")){
                chain.doFilter(request, response);
                return;
            }
            
            String token = req.getHeader("Authorization");

            if(token == null || jwtService.verify(token) == null){
                resp.setStatus(403);
                resp.setContentType("text/plain;charset=UTF-8");
                resp.getWriter().write("没有通行证");
                return;
            }


            chain.doFilter(request, response);

        }
    
}
