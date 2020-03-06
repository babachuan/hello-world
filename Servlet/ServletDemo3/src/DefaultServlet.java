import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultServlet extends HttpServlet {
    @Override
    public void destroy() {
        System.out.println("====调用default.destroy方法");
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("====调用default.init方法");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("====调用了default.doGet方法");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("This is default Servlet");
    }
}
