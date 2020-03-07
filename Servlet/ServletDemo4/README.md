<nav>
<a href="#"></a><br/>
<a href="#1）ServletConfig接口">1）ServletConfig接口</a><br/>
<a href="#2）ServletContext接口">2）ServletContext接口</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2-1）读取初始化参数">21）读取初始化参数</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2-2）读取Web应用下的资源文件">22）读取Web应用下的资源文件</a><br/>
</nav>

# 1）ServletConfig接口

在运行 Servlet 程序时，可能需要一些辅助信息，例如，文件使用的编码、使用 Servlet 程序的共享信息等，这些信息可以在 web.xml 文件中使用一个或多个 <init-param> 元素进行配置。当 Tomcat 初始化一个 Servlet 时，会将该 Servlet 的配置信息封装到 ServletConfig 对象中，此时可以通过调用 init（ServletConfig config）方法将 ServletConfig 对象传递给 Servlet。

ServletConfig 接口中定义了一系列获取配置信息的方法

| 方法说明                             | 功能描述                                                     |
| ------------------------------------ | ------------------------------------------------------------ |
| String getInitParameter(String name) | 根据初始化参数名返回对应的初始化参数值                       |
| Enumeration getInitParameterNames()  | 返回一个 Enumeration 对象，其中包含了所有的初始化参数名      |
| ServletContext getServletContext()   | 返回一个代表当前 Web 应用的 ServletContext 对象              |
| String getServletName()              | 返回 Servlet 的名字，即 web.xml <br>中\ <servlet-name\>元素的值 |
| ServletConfig getServletConfig()     | 返回一个代表当前 Web 应用的 ServletConfig 对象               |

创建一个名称为 MyServlet2的 Servlet 类，并在类中编写用于读取 web.xml 文件中参数信息的代码

```
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class MyServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletConfig config = this.getServletConfig();
        String param = config.getInitParameter("encoding");
        out.println("encoding="+param);

    }
}
```

通过`config.getInitParameter("encoding")`获取参数列表中的值。

---

对应的web.xml配置文件如下：

```
    <servlet>
        <servlet-name>Servlet002</servlet-name>
        <servlet-class>MyServlet2</servlet-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>Servlet002</servlet-name>
        <url-pattern>/myservlet2</url-pattern>
    </servlet-mapping>
```

上面在对应的servlet中配置了`init-param`项，并且使用了`/myservlet2`转到对应的servlet上。

启动Tomcat，用`http://localhost:8080/my/myservlet2`访问得到的结果是：

```
encoding=UTF-8
```

# 2）ServletContext接口

当 Tomcat 启动时，Tomcat 会为每个 Web 应用创建一个唯一的 ServletContext 对象代表当前的 Web 应用，该对象封装了当前 Web 应用的所有信息。可以利用该对象获取 Web 应用程序的初始化信息、读取资源文件等。下面对 ServletContext 接口的不同作用分别进行讲解。

| 方法说明                             | 功能描述                                                     |
| ------------------------------------ | ------------------------------------------------------------ |
| String getInitParameter(String name) | 根据初始化参数名返回对应的初始化参数值                       |
| Enumeration getInitParameterNames()  | 返回一个 Enumeration 对象，其中包含了所有的初始化参数名      |
| ServletContext getServletContext()   | 返回一个代表当前 Web 应用的 ServletContext 对象              |
| String getServletName()              | 返回 Servlet 的名字，即 web.xml <br>中\ <servlet-name\>元素的值 |

## 2-1）读取初始化参数

在 web.xml 文件中，不仅可以配置 Servlet 的映射信息，还可以配置整个 Web 应用的初始化信息。Web 应用初始化参数的配置方式具体如下所示：

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>HelloFirstServlet</display-name>

    <context-param>
        <param-name>myusername</param-name>
        <param-value>zhangsan</param-value>
    </context-param>
    <context-param>
        <param-name>mypassword</param-name>
        <param-value>123456</param-value>
    </context-param>
    <servlet>
        <servlet-name>Servlet001</servlet-name>
        <servlet-class>MyServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>Servlet001</servlet-name>
        <url-pattern>/myservlet</url-pattern>
    </servlet-mapping>

    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
```

\<context-param\> 元素位于根元素 \<web-app\> 中，它的子元素 \<param-name\> 和 \<param-value\> 分别用于指定参数的名字和参数值。要想获取这些参数名和参数值的信息，可以使用 ServletContext 接口中定义的 getInitParameterNames() 和 getInitParameter（String name）方法分别获取。

---

对应的Servlet内容如下：

```
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        //获取ServletContext对象
        ServletContext context = this.getServletContext();
        //得到web.xml中的初始化参数名的Enumeration对象
        Enumeration<String> paramNames = context.getInitParameterNames();
        //遍历所有的初始化参数名，得到相应的参数值并打印
        while(paramNames.hasMoreElements()){
            String name = paramNames.nextElement();
            String value = context.getInitParameter(name);
            out.println(name+":"+value);
            out.println("<br/>");
        }
    }
}
```

首先通过`this.getServletContext()`获取上下文对象，然后通过`context.getInitParameterNames()`获取上下文初始化参数列表。最后循环遍历，分别拿到`context.getInitParameter(name)`对应的参数值。

启动Tomcat，使用`http://localhost:8080/my/myservlet`访问得到的结果是：

```
mypassword:123456
myusername:zhangsan
```

web.xml 文件中配置的信息被读取了出来。由此可见，通过 ServletContext 对象可以获取 Web 应用的初始化参数。

## 2-2）读取Web应用下的资源文件

在实际开发中，有时会需要读取 Web 应用中的一些资源文件，如配置文件和日志文件等。为此，在 ServletContext 接口中定义了一些读取 Web 资源的方法，这些方法是依靠 Servlet 容器实现的。Servlet 容器根据资源文件相对于 Web 应用的路径，返回关联资源文件的 I/O 流或资源文件在系统的绝对路径等。

 ServletContext 接口中用于获取资源路径的相关方法

| 方法说明                                     | 功能描述                                                     |
| -------------------------------------------- | ------------------------------------------------------------ |
| Set getResourcePaths(String path)            | 返回一个 Set 集合，集合中包含资源目录<br/>中子目录和文件的路径名 称。参数 path 必<br/>须以正斜线（/）开始，指定匹配资源的部分路径 |
| String getRealPath(String path)              | 返回资源文件在服务器文件系统上的真实<br/>路径（文件的绝对路径）。参数 path 代表<br/>资源文件的虚拟路径，它应该以正斜线（/）开<br/>始，/ 表示当前 Web 应用的根目录，如果 <br/>Servlet 容器不能将虚拟路径转换为文 件系统<br/>的真实路径，则返回 null |
| URL getResource(String path)                 | 返回映射到某个资源文件的 URL 对象。参数<br/> path 必须以正斜线（/）开始，/ 表示当<br/>前 Web 应用的根目录 |
| InputStream getResourceAsStream(String path) | 返回映射到某个资源文件的 InputStream 输<br/>入流对象。参数 path 的传递规<br/>则和 getResource() 方法完全一致 |

使用 ServletContext 对象读取资源文件。在项目的 src 目录中创建一个名称为 application.properties的文件，在创建好的文件中输入如下所示的配置信息

```
applicationname=myservlet
applicationAuthor=quhaichuan
```

---

创建对应的`SourceServlet`

```
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class SourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext context = this.getServletContext();
        //获取相对路径中的输入流对象
        InputStream in = context.getResourceAsStream("/WEB-INF/classes/application.properties");
        Properties prop = new Properties();
        prop.load(in);
        out.println("applicationname= "+prop.getProperty("applicationname")+"<br/>");
        out.println("applicationAuthor= "+prop.getProperty("applicationAuthor")+"<br/>");
    }
}
```

使用 ServletContext 的 getResourceAsStream（String path）方法获得了关联 application.properties资源文件的输入流对象，其中的 path 参数必须以正斜线（/）开始，表示application.properties 文件相对于 Web 应用的相对路径。

---

对应的web.xml

```
   <servlet>
        <servlet-name>MySourceServlet</servlet-name>
        <servlet-class>SourceServlet</servlet-class>
    </servlet>
        <servlet-mapping>
        <servlet-name>MySourceServlet</servlet-name>
        <url-pattern>/mysourceservlet</url-pattern>
    </servlet-mapping>
```

---

启动 Tomcat 服务器,在浏览器的地址栏中输入地址`http://localhost:8080/my/mysourceservlet` 访问 MySourceServlet，得到如下结果：

```
applicationname= myservlet
applicationAuthor= quhaichuan
```