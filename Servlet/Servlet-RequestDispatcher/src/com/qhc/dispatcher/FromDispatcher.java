package com.qhc.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FromDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setAttribute("username","孙悟空");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/todispather");
        requestDispatcher.forward(req,resp);

        /**
         * http://localhost:8080/demo/fromdispathcer通过这个访问虽然拿到的结果是ToDispatcher处理的。
         */
    }
}
