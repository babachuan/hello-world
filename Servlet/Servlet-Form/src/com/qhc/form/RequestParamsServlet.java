package com.qhc.form;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestParamsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        printWriter.println("用户名："+name);
        printWriter.println("密码："+password);
        //获取参数为hobby的值
        String[] hobbys = req.getParameterValues("hobby");
        System.out.println("爱好是：");
        for(int i=0;i< hobbys.length;i++){
            printWriter.println(hobbys[i]+",");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
