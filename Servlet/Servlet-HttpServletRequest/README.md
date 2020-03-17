# 1.获取请求行信息

HttpServletRequest 接口继承自 ServletRequest 接口，其主要作用是封装 HTTP 请求消息。由于 HTTP 请求消息分为请求行、请求消息头和请求消息体三部分。因此，在 HttpServletRequest 接口中定义了获取请求行、请求头和请求消息体的相关方法。这里介绍下获取请求行的信息。

## 获取请求行信息的相关方法

当访问 Servlet 时，所有请求消息将被封装到 HttpServletRequest 对象中，请求消息的请求行中包含请求方法、请求资源名、请求路径等信息，为了获取这些信息，HttpServletRequest 接口定义了一系列方法

| 方法声明                     | 功能描述                                                     |
| ---------------------------- | ------------------------------------------------------------ |
| String getMethod()           | 该方法用于获取 HTTP 请求消息中的请求方式（如 GET、POST 等）  |
| String getRequestURI()       | 该方法用于获取请求行中的资源名称部分即位于 URL 的主机和端门之后、参数部分之前的部分 |
| String getQueryString()      | 该方法用于获取请求行中的参数部分，也就是资源路径后问号（？）以后的所有内容 |
| String getContextPath()      | 该方法用于获取请求 URL 中属于 Web 应用程序的路径，这个路径以 / 开头，表示相对于整个 Web 站点的根目录，路径结尾不含 /。如果请求 URL 属于 Web 站点的根目录，那么返回结果为空字符串（""） |
| String getServletPath()      | 该方法用于获取 Servlet 的名称或 Servlet 所映射的路径         |
| String getRemoteAddr()       | 该方法用于获取请求客户端的 IP 地址，其格式类似于 192.168.0.3 |
| String getRemoteHost()       | 该方法用于获取请求客户端的完整主机名，其格式类似于 pcl.mengma.com。需要注意的是，如果无法解析出客户机的完整主机名，那么该方法将会返回客户端的 IP 地址 |
| int getRemotePort()          | 该方法用于获取请求客户端网络连接的端口号                     |
| String getLocaIAddr()        | 该方法用于获取 Web 服务器上接收当前请求网络连接的 IP 地址    |
| String getLocalName()        | 该方法用于获取 Web 服务器上接收当前网络连接 IP 所对应的主机名 |
| int getLocalPort()           | 该方法用于获取 Web 服务器上接收当前网络连接的端口号          |
| String getServerName()       | 该方法用于获取当前请求所指向的主机名，即 HTTP 请求消息中 Host 头字段所对应的主机名部分 |
| int gctServcrPort()          | 该方法用于获取当前请求所连接的服务器端口号，即 HTTP 请求消息中 Host 头字段所对应的端口号部分 |
| StringBuffcr getRequestURL() | 该方法用于获取客户端发出请求时的完整 URL，包括协议、服务器名、端口号、 资源路径等信息，但不包括后面的査询参数部分。注意，getRequcstURL() 方法返冋的结果是 StringBuffer 类型，而不是 String 类型，这样更便于对结果进行修改 |

新建一个servlet类`HttpServletRequestDemo`

```
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
```

启动tomcat后访问：`http://localhost:8080/demo/servlet`得到如下结果

```
getMethod:GET
getRequestURI:/demo/servlet
getQueryString:null
getContextPath:/demo
getServletPath:/servlet
getRemoteAddr:0:0:0:0:0:0:0:1
getRemoteHost:0:0:0:0:0:0:0:1
getRemotePort:8406
getRemoteUser:null
getLocalAddr:0:0:0:0:0:0:0:1
getLocalName:0:0:0:0:0:0:0:1
getLocalPort:8080
getServerName:localhost
getServerPort:8080
getRequestURL:http://localhost:8080/demo/servlet
```

## 获取请求消息头的相关方法

当浏览器发送 Servlet 请求时，需要通过请求消息头向服务器传递附加信息，例如，客户端可以接收的数据类型、压缩方式、语言等。为此，在 HttpServletRequest 接口中定义了一系列用于获取 HTTP 请求头字段的方法

| 方法声明                            | 功能描述                                                     |
| ----------------------------------- | ------------------------------------------------------------ |
| String getHeader(String name)       | 该方法用于获取一个指定头字段的值，如果请求消息中没有包含指定的头字段，则 getHeader() 方法返回 null；如果请求消息中包含多个指定名称的头字段，则 getHeader() 方法返回其中第一个头字段的值 |
| Enumeration getHeaders(String name) | 该方法返回一个 Enumeration 集合对象，该集合对象由请求消息中出现的某个指定名称的所有头字段值组成。在多数情况下，一个头字段名在请求消息中只出现一次，但有时可能会出现多次 |
| Enumeration getHeaderNames()        | 该方法用于获取一个包含所有请求头字段的 Enumeration 对象      |
| int getIntHeader(String name)       | 该方法用于获取指定名称的头字段，并且将其值转为 int 类型。需要注意的是，如果指定名称的头字段不存在，则返回值为 -1；如果获取到的头字段的值不能转为 int 类型，则将发生 NumberFormatException 异常 |
| long getDateHeader(String name)     | 该方法用于获取指定头字段的值，并将其按 GMT 时间格式转换为一个代表日期/时间的长整数，该长整数是自 1970 年 1 月 1 日 0 时 0 分 0 秒算起的以毫秒为单位的时间值 |
| String getContentType()             | 该方法用于获取 Content-Type 头字段的值，结果为 String 类型   |
| int getContentLength()              | 该方法用于获取 Content-Length 头字段的值，结果为 int 类型    |
| String getCharacterEncoding()       | 该方法用于返回请求消息的实体部分的字符集编码，通常是从 Content-Type 头字段中进行提取，结果为 String 类型 |

新建一个获取请求头信息的测试servlet

```
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
```

使用url·`http://localhost:8080/demo/headers`访问后得到的结果：

```
host:localhost:8080
connection:keep-alive
upgrade-insecure-requests:1
user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.9 Safari/537.36
accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
sec-fetch-site:none
sec-fetch-mode:navigate
sec-fetch-user:?1
sec-fetch-dest:document
accept-encoding:gzip, deflate, br
accept-language:zh-CN,zh;q=0.9
```

