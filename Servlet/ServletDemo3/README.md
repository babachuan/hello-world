<nav>
<a href="#"></a><br/>
<a href="#1）Servlet多重映射">1）Servlet多重映射</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.1）配置多个servlet-mapping元素">11）配置多个servletmapping元素</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.2）配置多个url-pattern元素">12）配置多个urlpattern元素</a><br/>
<a href="#2）Servlet映射路径中使用通配符">2）Servlet映射路径中使用通配符</a><br/>
<a href="#3）配置默认Servlet">3）配置默认Servlet</a><br/>
<a href="#4）为什么一定要实现HttpServlet的doGet方法解析">4）为什么一定要实现HttpServlet的doGet方法解析</a><br/>
</nav>

# 1）Servlet多重映射

创建好的 Servlet 只有映射成虚拟路径，客户端才能对其进行访问。但是在映射 Servlet 时，还有内容需要学习，如 Servlet 的多重映射、在映射路径中使用通配符、配置默认的 Servlet 等。本节将对这些内容进行讲解。

Servlet 的多重映射指同一个 Servlet 可以被映射成多条虚拟路径。也就是说，客户端可以通过多条路径实现对同一个 Servlet 的访问。Servlet 多重映射的实现方式有以下两种。

- 配置多个 \<servlet-mapping\> 元素
- 配置多个 \<url-pattern\> 子元素

## 1.1）配置多个servlet-mapping元素

配置的web.xml内容如下：

```
    <!--配置多个Servlet-mapping元素   -->
    <servlet-mapping>
        <servlet-name>Servlet001</servlet-name>
        <url-pattern>/myservlet2</url-pattern>
    </servlet-mapping>
    <servlet-mapping>
        <servlet-name>Servlet001</servlet-name>
        <url-pattern>/myservlet3</url-pattern>
    </servlet-mapping>
```

上面配置了2个servlet-mapping元素，在浏览器访问的时候通过以下2个链接访问得到的结果是一样的：

- `http://localhost:8080/my/myservlet2`
- `http://localhost:8080/my/myservlet3`

## 1.2）配置多个url-pattern元素

配置的web.xml内容如下：

```
    <servlet-mapping>
        <servlet-name>Servlet001</servlet-name>
        <url-pattern>/myservlet</url-pattern><!--配置多个url-pattern元素-->
        <url-pattern>/myservleta</url-pattern>
    </servlet-mapping>
```

上面配置了2个url-pattern元素，在浏览器访问的时候通过以下2个链接访问得到的结果是一样的：

- `http://localhost:8080/my/myservlet`
- `http://localhost:8080/my/myservleta`

# 2）Servlet映射路径中使用通配符

在实际开发过程中，开发者有时会希望某个目录下的所有路径都可以访问同一个 Servlet，这时，可以在 Servlet 映射的路径中使用通配符*。通配符的格式有两种，具体如下。

- 格式为“\*.扩展名”，例如 \*.do 匹配以 .do 结尾的所有 URL 地址。
- 格式为 /\*，例如 /abc/\* 匹配以 /abc 开始的所有 URL 地址。

*注意*：这两种通配符的格式不能混合使用，例如，/abc/\*.do 是不合法的映射路径。另外，当客户端访问一个 Servlet 时，如果请求的 URL 地址能够匹配多条虚拟路径，那么 Tomcat 将采取最具体匹配原则查找与请求 URL 最接近的虚拟映射路径。由于在平时开发中不会有这么变态的使用，这里不做过多介绍。

# 3）配置默认Servlet

Servlet 服务器在接收到访问请求时，如果在 web.xml 文件中找不到匹配的 \<servlet-mapping\> 元素的 URL，则会将访问请求交给**默认 Servlet** 处理，也就是说，**默认 Servlet 用于处理其他 Servlet 都不处理的访问请求**。默认 Servlet 的映射路径仅仅是一个正斜线（/），如：

```
    <servlet-mapping>
        <servlet-name>MyDefaultServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

当访问url中没有任何能够匹配web.xml中的虚拟路径时，都会在这个默认servlet上进行处理。

当用`http://localhost:8080/my/noservletdddd`进行访问时会得到

```
浏览器的返回结果：This is default Servlet
```

需要注意的是，在 Tomcat 安装目录下的 web.xml 文件中也配置了一个默认的 Servlet，配置信息如下所示：

```
<servlet>
    <servlet-name>default</servlet-name>
    <serlet-class>org.apache.catalina.servlets.DefaultServlet</serlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

在上面的配置信息中，org.apache.catalina.servlets.DefaultServlet 被设置为默认的 Servlet，它对 Tomcat 服务器上所有的 Web 应用都起作用。

当 Tomcat 服务器中的某个 Web 应用没有默认 Servlet 时，都会将 DefaultServlet 作为默认的 Servlet。当客户端访问 Tomcat 服务器中的某个静态 HTML 文件时，DefaultServlet 会判断 HTML 是否存在，如果存在，则会将数据以流的形式回送给客户端，否则会报告 404 错误。

# 4）为什么一定要实现HttpServlet的doGet方法解析

首先查看HttpServlet的部分源码

```
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        } catch (ClassCastException var6) {
            throw new ServletException("non-HTTP request or response");
        }

        this.service(request, response);
    }
```

上面的源码又调用了一个`this.service(request, response)`方法，再继续看`this.service(request, response)`的源码：

```
protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
        if (method.equals("GET")) {
            lastModified = this.getLastModified(req);
            if (lastModified == -1L) {
                this.doGet(req, resp);//这里调用了doGet方法
            } else {
                long ifModifiedSince;
                try {
                    ifModifiedSince = req.getDateHeader("If-Modified-Since");
                } catch (IllegalArgumentException var9) {
                    ifModifiedSince = -1L;
                }

                if (ifModifiedSince < lastModified / 1000L * 1000L) {
                    this.maybeSetLastModified(resp, lastModified);
                    this.doGet(req, resp);
                } else {
                    resp.setStatus(304);
                }
            }
        } ...
     }
```

上面的源码中又调用了`doGet()`方法，那么重点来了，查看下`doGet()`方法的源码吧

```
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }

    }
```

恍然大悟，如果子类中不对doGet方法进行实现（当只使用GET方法时），那么浏览器会报错误，错误信息如下：

```
HTTP Status 405 – Method Not Allowed
Type Status Report

Message HTTP method GET is not supported by this URL

Description The method received in the request-line is known by the origin server but not supported by the target resource.

Apache Tomcat/8.5.34
```

看来看东西还是要深入源码找原因，世界上有那么多为什么，其实答案往往就在身边。