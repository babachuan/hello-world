# 获取请求参数

在实际开发中，经常需要获取用户提交的表单数据，例如用户名和密码等，为了方便获取表单中的请求参数，在 HttpServletRequest 接口的父类 ServletRequest 中定义了一系列获取请求参数的方法

| 方法声明                                   | 功能描述                                                     |
| ------------------------------------------ | ------------------------------------------------------------ |
| String getParameter(String name)           | 该方法用于获取某个指定名称的参数值。 如果请求消息中没有包含指定名称的参数，则 getParameter() 方法返回 null。 如果指定名称的参数存在但没有设置值，则返回一个空串。 如果请求消息中包含多个该指定名称的参数，则 getParameter() 方法返回第一个出现的参数值。 |
| String [] getParameterValues (String name) | HTTP 请求消息中可以有多个相同名称的参数（通常由一个包含多个同名的字段元素的 form 表单生成），如果要获得 HTTP 请求消息中的同一个参数名所对应的所有参数值，那么就应该使用 getParameterValues() 方法，该方法用于返回一个 String 类型的数组。 |
| Enumeration getParameterNames()            | 方法用于返回一个包含请求消息中所有参数名的 Enumeration 对象，在此基础上，可以对请求消息中的所有参数进行遍历处理。 |
| Map getParameterMap()                      | getParameterMap() 方法用于将请求消息中的所有参数名和值装入一个 Map 对象中返回。 |

编写一个index.html文件，作为欢迎页进行测试提交表单：

```
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<form action="/demo/ParamServlet" method="POST">
    用户名：<input type="text" name="username"><br/>
    密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password"/><br/>
    <br/>
    爱好：
    <input type="checkbox" name="hobby" value="sing"/>唱歌
    <input type="checkbox" name="hobby" value="dance"/>跳舞
    <input type="checkbox" name="hobby" value="game"/>玩游戏
    <input type="submit" value="提交"/>
</form>
</body>
</html>
```

用`POST`方式提交请求表单到`/demo/ParamServlet`

自己写的servlet类如下：

```
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

```

分别通过`String name = req.getParameter("username")`和` String[] hobbys = req.getParameterValues("hobby");`分别获取对应的值。注意这里使用post转get请求。将结果打印到页面上，结果如下：

![结果图片](http://c.biancheng.net//uploads/allimg/190611/5-1Z611104040W2.gif)

## 通过 Request 对象传递数据

Request 对象不仅可以获取一系列数据，还可以通过属性传递数据。ServletRequest 接口中定义了一系列操作属性的方法。

#### 1）setAttribute() 方法

该方法用于将一个对象与一个名称关联后存储到 ServletRequest 对象中，其完整语法定义如下：

public void setAttribute(java.lang.String name,java.lang.Object o);

需要注意的是，如果 ServletRequest 对象中已经存在指定名称的属性，则 setAttribute() 方法将会先删除原来的属性，然后再添加新的属性。如果传递给 setAttribute() 方法的属性值对象为 null，则删除指定名称的属性，这时的效果等同于 removeAttribute() 方法。

#### 2）getAttribute() 方法

该方法用于从 ServletRequest 对象中返回指定名称的属性对象，其完整的语法定义如下：

public java.lang.Object getAttribute(java.lang.String name);

#### 3）removeAttribute() 方法

该方法用于从 ServletRequest 对象中删除指定名称的属性，其完整的语法定义如下：

public void removeAttribute(java.lang.String name);

#### 4）getAttributeNames() 方法

该方法用于返回一个包含 ServletRequest 对象中的所有属性名的 Enumeration 对象，在此基础上，可以对 ServletRequest 对象中的所有属性进行遍历处理。getAttributeNames() 方法的完整语法定义如下：

public java.util.Enumeration getAttributeNames();

需要注意的是，只有属于同一个请求中的数据才可以通过 ServletRequest 对象传递数据。关于 ServletRequest 对象操作属性的具体用法，教程将在后续章节进行详细讲解，在此读者只需了解即可。