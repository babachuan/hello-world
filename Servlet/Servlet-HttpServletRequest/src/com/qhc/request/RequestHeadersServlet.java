package com.qhc.request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class RequestHeadersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        //获取请求消息中的头字段
        Enumeration headerNames = req.getHeaderNames();
        //用循环遍历所有请求头，并通过 getHeader() 方法获取一个指定名称的头字段
        while(headerNames.hasMoreElements()){
            String headerName = (String) headerNames.nextElement();
            printWriter.println(headerName+":"+req.getHeader(headerName));
        }
    }
}
