<nav>
<a href="#"></a><br/>
<a href="#1.用IDEA创建第一个Servlet应用">1用IDEA创建第一个Servlet应用</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.1创建Web项目">11创建Web项目</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.2创建Servlet和配置web.xml">12创建Servlet和配置webxml</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.3配置项目">13配置项目</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="#1.3测试">13测试</a><br/>
</nav>


# 1.用IDEA创建第一个Servlet应用

工欲善其事必先利其器，作为Servlet学习的第一步，有一个框架性的应用对细节知识的学习相当有用，下面就介绍下如何用最近很流行的IDEA创建第一个servlet应用。

## 1.1创建Web项目

在File->New->Project..->Java Enterprise->Web Application创建一个Web Application项目，参见下图：

![创建Web Application](https://note.youdao.com/yws/public/resource/cf4b2a4757bd3195ea253c4aba444f2b/xmlnote/B8669A0A229A4CF9B5A48478F8F77A86/14302)

然后在web->WEB-INF下创建classes和lib目录(这两个目录是标准web项目的配置)

## 1.2创建Servlet和配置web.xml

在src目录下新建一个`MyServlet.java`类

```
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Hello Servlet");
    }
}
```

这里通过继承`HttpServlet`来实现自己的Servlet。

*注意*：这里在继承这个类的时候需要提前把Tomcat配置好，不然可能提示找不到这个类。

---

**web.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <display-name>HelloFirstServlet</display-name>
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



## 1.3配置项目

- 在File->Project Structure...->Project Settings ->Modules-> 选中自己的Web项目->Paths->选中`Use module compile output path`

  将`Output path`和`Test output path`设置成之前创建的classes目录。

- 在File->Project Structure...->Project Settings ->Modules-> 选中自己的Web项目->Dependencies->添加->JARs or directories... ->选中前面创建的lib目录->Jar Directory->保存即可

  *注意*：如果上面的HttpServlet不能找到Tomcat，可以在这里的`添加->Library->Tomcat`进行添加。

- 在File->Project Structure...->Project Settings ->Artifacts ->添加->ServletDemo1:war exploded

  这里的`war exploded`标识打成war包，这里同时选定`include in project build`和`Show content of element`

- 在Run->Edit Configurations->添加->Tomcat Server->Local（如果已经添加可不用再执行）

  在`Server`页签配置Tomcat,JRE,端口信息，这里URL是启动后使用浏览器默认访问的url，可以自行设置。

  在`Deployment`页签添加项目，Artifact->选中自己的项目，同时可以在这里设置项目名（注意是在用浏览器访问时需要，可以自定义,我再本地设置的/my），然后点击保存即可。添加完后IDEA工具左下角会出现`service`项目，可以在这里启动Tomcat

![配置项目](https://note.youdao.com/yws/public/resource/cf4b2a4757bd3195ea253c4aba444f2b/xmlnote/BBCD99ED82EC414A93BF398E940F35FC/14314)

![配置Tomcat](https://note.youdao.com/yws/public/resource/cf4b2a4757bd3195ea253c4aba444f2b/xmlnote/79BB520AE8864A5D8070EC1F9187E35F/14318)

## 1.3测试

启动Tomcat，使用`http://localhost:8080/my/myservlet`进行访问

结果如下：

```
浏览器得到：Hello Servlet
```

**注意**：下载本项目代码后需要重新在IDEA工具中创建classes和lib目录。比较重要的一点就是下载完代码后要把`src`目录标记成`Source root`，把`web`目录转换成web模块。之后再按照上面的配置过程进行配置即可。