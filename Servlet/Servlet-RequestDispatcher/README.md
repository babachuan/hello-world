# RequestDispatcher对象

当一个 Web 资源收到客户端的请求后，如果希望服务器通知另外一个资源处理请求，那么这时可以通过 RequestDispatcher 接口的实例对象实现。[Servlet](http://c.biancheng.net/servlet/)Request 接口中定义了一个获取 RequestDispatcher 对象的方法

| 方法声明                                             | 功能描述                                                     |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| RequestDispatcher getRequestDispatcher (String path) | 返回封装了某条路径所指定资源的 RequestDispatcher 对象。其中，参数 path 必须以“/”开头，用于表示当前 Web 应用的根目录。需要注意的是，WEB-INF 目录中的内容对 RequestDispatcher 对象也是可见的。因此，传递给 getRequestDispatcher(String path) 方法的资源可以是 WEB-INF 目录中的文件 |

获取到 RequestDispatcher 对象后，最重要的工作就是通知其他 Web 资源处理当前的 Servlet 请求，为此，RequestDispatcher 接口定义了两个相关方法

| 方法声明                                                 | 功能描述                                                     |
| -------------------------------------------------------- | ------------------------------------------------------------ |
| forward(ServletRequest request,ServletResponse response) | 该方法用于将请求从一个 Servlet 传递给另一个 Web 资源。在 Servlet 中，可以对请求做一个初步处理，然后通过调用这个方法，将请求传递给其他资源进行响应。需要注意的是，该方法必须在响应提交给客户端之前被调用，否则将抛出 IllegalStateException 异常 |
| include(ServletRequest request,ServletResponse response) | 该方法用于将其他的资源作为当前响应内容包含进来               |

# 转发请求

在 RequestDispatcher 接口中，forward() 方法可以实现请求转发，include() 方法可以实现请求包含，本节将以请求转发为例，讲解 forward() 方法的使用。

在 Servlet 中，如果当前 Web 资源不想处理请求，则可以通过 forward() 方法将当前请求传递给其他的 Web 资源进行处理，这种方式称为请求转发。请求转发的工作原理如图 1 所示。

从图 1 中可以看出，当客户端访问 Servlet1 时，可以通过 forward() 方法将请求转发给其他 Web 资源，其他 Web 资源处理完请求后，直接将响应结果返回到客户端。

了解了 forward() 方法的工作原理后，下面通过案例演示 forward() 方法的使用。在 servletDemo02 项目的 com.mengma.request 包中创建一个名为 RequestForwardServlet 的 Servlet 类，该类使用 forword() 方法将请求转发到一个新的 Servlet 页面

![原理图](http://c.biancheng.net//uploads/allimg/190611/5-1Z611114149608.png)

编写`FromDispatcher`类

```
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
```

模拟在这里获得一个请求，属性用`req.setAttribute("username","孙悟空")`模拟，然后将请求转到`req.getRequestDispatcher("/todispather")`

**ToDispatcher类**

```
package com.qhc.dispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ToDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = resp.getWriter();
        String username = (String) req.getAttribute("username");
        printWriter.println("用户名字是："+username);
    }
}
```

在这个servlet里是真实处理数据的。

下面是web.xml的配置

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>dispatcher</display-name>
    <servlet>
        <servlet-name>FromDispatcher</servlet-name>
        <servlet-class>com.qhc.dispatcher.FromDispatcher</servlet-class>
    </servlet>

    <servlet>
        <servlet-name>ToDispatcher</servlet-name>
        <servlet-class>com.qhc.dispatcher.ToDispatcher</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>FromDispatcher</servlet-name>
        <url-pattern>/fromdispathcer</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>ToDispatcher</servlet-name>
        <url-pattern>/todispather</url-pattern>
    </servlet-mapping>
</web-app>
```

*注意*：上面转发请求用的是`<url-pattern>/todispather</url-pattern>`这里的配置，不是对应sevlet类的名字，开始以为对应的是`<servlet-name>ToDispatcher</servlet-name>`，测试后不是。

通过url:`http://localhost:8080/demo/fromdispathcer`访问到结果是：

```
用户名字是：孙悟空
```

访问成功后url没有改变，仍然是`http://localhost:8080/demo/fromdispathcer`