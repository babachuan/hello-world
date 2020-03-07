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
