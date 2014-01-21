package jetty.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-9
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ids = request.getParameter("ids");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("hello!");

    }

}
