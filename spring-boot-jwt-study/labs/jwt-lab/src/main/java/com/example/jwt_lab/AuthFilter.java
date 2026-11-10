package com.example.jwt_lab;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component 
public class AuthFilter implements Filter{

    @Override 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException{
            HttpServletRequest req = (HttpServletRequest) request;
            System.out.println("过滤器看到请求：" + req.getMethod() + " " + req.getRequestURI());
            chain.doFilter(req, response);
        }
    
}
