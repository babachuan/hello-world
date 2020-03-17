package com.qhc.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpServletRequestDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        //获取请求行相关信息
        out.println("getMethod:"+req.getMethod());
        out.println("getRequestURI:"+req.getRequestURI());
        out.println("getQueryString:"+req.getQueryString());
        out.println("getContextPath:"+req.getContextPath());
        out.println("getServletPath:"+req.getServletPath());
        out.println("getRemoteAddr:"+req.getRemoteAddr());
        out.println("getRemoteHost:"+req.getRemoteHost());
        out.println("getRemotePort:"+req.getRemotePort());
        out.println("getRemoteUser:"+req.getRemoteUser());
        out.println("getLocalAddr:"+req.getLocalAddr());
        out.println("getLocalName:"+req.getLocalName());
        out.println("getLocalPort:"+req.getLocalPort());
        out.println("getServerName:"+req.getServerName());
        out.println("getServerPort:"+req.getServerPort());
        out.println("getRequestURL:"+req.getRequestURL());


    }
}
