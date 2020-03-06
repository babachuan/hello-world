<nav>
<a href="#"></a><br/>
<a href="#1）Servlet是什么">1）Servlet是什么</a><br/>
<a href="#2）Servlet的运行过程及生命周期">2）Servlet的运行过程及生命周期</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2-1）Servlet运行过程">21）Servlet运行过程</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2-2）Servlet的生命周期">22）Servlet的生命周期</a><br/>
<a href="#3）实例验证">3）实例验证</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1）初始化阶段">1）初始化阶段</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#2）运行阶段">2）运行阶段</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#3）销毁阶段">3）销毁阶段</a><br/>
</nav>


# 1）Servlet是什么

一些流程的说法会说，Servlet是`Servlet 接口` `GenericServlet` 或者说是`HttpServlet` ,这种说法是错误的。我们写的任何Servlet，都不是独立能够运行的。为什么每次写的Servlet都需要放到Tomcat中运行，是不是觉得这并没有那么巧合？

​    我们写的Servlet也没有监控端口，更没有处理请求，更不会直接和客户端浏览器打交道，所以我们自己的写Servlet并不像我们自己想象的那么智能。那么问题来了，那我们写Servlet还有什么用呢？当然有用，如果Tomcat什么都能搞定，要程序员做什么，存在即合理。

​    `Servlet`只是一套规范，上面说的 `GenericServlet`  `HttpServlet`  都是实现了规范的实现类而已。下面看下继承关系。

`HttpServlet`继承自`GenericServlet`,而`GenericServlet`又实现了`Servlet`接口。

**HttpServlet源码**

```
package javax.servlet.http;

public abstract class HttpServlet extends GenericServlet {}
```

可以看到`HttpServlet`继承自`GenericServlet`抽象类。

---

**GenericServlet源码**

```
package javax.servlet;

public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {}
```

而`GenericServlet`这个抽象类又实现了`Servlet`接口

---

**Servlet接口源码**

```
package javax.servlet;

import java.io.IOException;

public interface Servlet {
    void init(ServletConfig var1) throws ServletException;

    ServletConfig getServletConfig();

    void service(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

    String getServletInfo();

    void destroy();
}
```

这个源码中什么都没有，只空荡荡的定义了几个方法，重点来了，这个就是遵循规范而制定的，是不是觉得规范也很接地气。Servlet规范是什么呢，感兴趣的小伙伴可以参考[Servle oracle官网](https://www.oracle.com/java/technologies/java-servlet-tec.html) [servletJSR规范](https://jcp.org/aboutJava/communityprocess/final/jsr340/index.html)。这里就针对性的对Servlet接口中的部分方法进行说明。

# 2）Servlet的运行过程及生命周期

自己编写的servlet是要放到servlet容器中运行的，我们可以把Tomcat看做一个Servlet容器。下面这段话是Tomcat官方给出的定义

```
This is the top-level entry point of the documentation bundle for the Apache Tomcat Servlet/JSP container. Apache Tomcat version 8.5 implements the Servlet 3.1 and JavaServer Pages 2.3 specifications from the Java Community Process, and includes many additional features that make it a useful platform for developing and deploying web applications and web services.
```

妥妥的就是玩它了。

## 2-1）Servlet运行过程

那么Tomcat是如何与Servlet协同工作的呢，分为如下几步

- 第一步：浏览器发送请求，访问服务器（也就是访问Tomcat）
- 第二步：Tomcat根据访问的URL实例化一个servlet对象（也就是上面方法的init()）
- 第三步：servlet方法的service()服务，这个时候会传递一个request对象和response对象给service;
- 第四步：请求的结果通过response返回给服务器，然后再返回给客户端
- 第五步：销毁实例

## 2-2）Servlet的生命周期

Servlet的生命周期是在服务器中进行的，可以理解为在Tomcat中，下面就Oracle对Servlet接口提供的方法进行说明。

| 方法声明                                                     | 功能描述                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| void init(ServletConfig var1)                                | 容器在创建好 Servlet 对象后，就会调用此方法。<br>该方法接收一个 ServletConfig 类型的参数，<br/>Servlet 容器通过该参数向 Servlet 传递初始化配置信息 |
| ServletConfig getServletConfig()                             | 用于获取 Servlet 对象的配置信息，返回 Servlet<br/> 的 ServletConfig 对象 |
| void service(ServletRequest var1,<br/> ServletResponse var2) | 负责响应用户的请求，当容器接收到客户端访问 <br/>Servlet 对象的请求时，就会调用此方法。<br/>容器会构造一个表示客户端请求信息的 <br/>ServletRequest 对象和一个用于响应客户端的<br/> ServletResponse 对象作为参数传递给 service() 方法。<br/>在 service() 方法中，可以通过 ServletRequest 对象<br/>得到客户端的相关信息和请求信息，在对请求进行处<br/>理后，调用 ServletResponse 对象的方法设置响应信息 |
| String getServletInfo()                                      | 返回一个字符串，其中包含关于 Servlet 的信息，<br/>如作者、版本和版权等信息 |
| void destroy()                                               | 负责释放 Servlet 对象占用的资源。当服务器关闭<br/>或者 Servlet 对象被移除时，Servlet 对象会被销毁，<br/>容器会调用此方法 |

列举了 Servlet 接口中的五个方法，其中 **init**()、**service**() 和 **destroy**() 方法可以表现 Servlet 的生命周期，它们会在某个特定的时刻被调用。

# 3）实例验证

为了验证上面的servlet运行周期，我们在继承`HttpServlet`后分别重写了`init()` `destroy()`方法

```
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyServlet extends HttpServlet {
    @Override
    public void destroy() {
        System.out.println("====调用destroy方法");
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("====调用init方法");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("====调用了doGet方法");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Hello Servlet");
    }
}
```

验证步骤：

- 第一步：启动Tomca
- 第二步：调用Servlet,t,调用servlet之前会打印`====调用init方法`，调用过程中会打印`====调用了doGet方法`
- 第三步：关闭Tomcat，关闭的过程中打印`====调用destroy方法`

**servlet对象是用户第一次访问时创建，对象创建之后就驻留在内存里面了，响应后续的请求**。servlet对象一旦被创建，init()就会被执行，客户端的每次请求导致service()方法被执行，servlet对象被摧毁时(web服务器停止后或者web应用从服务器里删除时)，destory()方法就会被执行。

在Servlet的整个生命周期内，Servlet的init方法只被调用一次。而对一个Servlet的每次访问请求都导致Servlet引擎调用一次servlet的service方法。对于每次访问请求，Servlet引擎都会创建一个新的HttpServletRequest请求对象和一个新的HttpServletResponse响应对象，然后将这两个对象作为参数传递给它调用的Servlet的service()方法，service方法再根据请求方式分别调用doXXX方法。

过程可以参见下图：![过程图](http://c.biancheng.net/uploads/allimg/190610/5-1Z610101ZS51.png)

#### 1）初始化阶段

当客户端向 Servlet 容器发出 HTTP 请求要求访问 Servlet 时，Servlet 容器首先会解析请求，检查内存中是否已经有了该 Servlet 对象，如果有，则直接使用该 Servlet 对象，如果没有，则创建 Servlet 实例对象，然后通过调用 init() 方法实现 Servlet 的初始化工作。需要注意的是，在 Servlet 的整个生命周期内，它的 init() 方法只能被调用一次。

#### 2）运行阶段

这是 Servlet 生命周期中最重要的阶段，在这个阶段中，Servlet 容器会为这个请求创建代表 HTTP 请求的 ServletRequest 对象和代表 HTTP 响应的 ServletResponse 对象，然后将它们作为参数传递给 Servlet 的 service() 方法。

service() 方法从 ServletRequest 对象中获得客户请求信息并处理该请求，通过 ServletResponse 对象生成响应结果。

在 Servlet 的整个生命周期内，对于 Servlet 的每一次访问请求，Servlet 容器都会调用一次 Servlet 的 service() 方法，并且创建新的 ServletRequest 和 ServletResponse 对象，也就是说，service() 方法在 Servlet 的整个生命周期中会被调用多次。

#### 3）销毁阶段

当服务器关闭或 Web 应用被移除出容器时，Servlet 随着 Web 应用的关闭而销毁。在销毁 Servlet 之前，Servlet 容器会调用 Servlet 的 destroy() 方法，以便让 Servlet 对象释放它所占用的资源。在 Servlet 的整个生命周期中，destroy() 方法也只能被调用一次。