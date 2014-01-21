package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-4-28
 * Time: 上午10:23
 */


public abstract class BaseController {

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    /**
     * @param to
     * @return
     */
    public static String redirect(String to) {
        return "redirect:/" + to;
    }

    /**
     * @param to
     * @return
     */
    public static String forward(String to) {
        return "forward:/" + to;
    }

    public static void write(ServletResponse response, String text) {
        try {
            response.getWriter().write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeJson(ServletResponse response, String text) {
        response.setContentType("application/json");
        write(response, text);
    }

    public static Boolean isSuperAdmin(HttpServletRequest request) {
        Object sa = request.getSession().getAttribute("sa");
        return null != sa && (Boolean) sa;
    }
}
