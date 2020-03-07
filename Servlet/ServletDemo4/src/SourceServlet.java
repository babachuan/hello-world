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
