package controller;

import model.Validate.ValidateCode;
import model.dao.DailyLogService;
import model.dao.UserService;
import model.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-4-28
 * Time: 上午11:23
 */

@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
    private static final String PREFIX = "/";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String info(Model model) {
        return PREFIX + "login";
    }

    @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
    public String error(Model model) {
        Subject userSub = SecurityUtils.getSubject();
        User user=(User)userSub.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return PREFIX+"error";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
    public String loginIn(Model model,HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "mail", required = false) String mail,
                          @RequestParam(value = "password", required = false) String password) throws FileNotFoundException, UnsupportedEncodingException {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mail,password);
        token.setRememberMe(true);
        String code = (String) session.getAttribute("validateCode");
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(code.toLowerCase(),submitCode.toLowerCase())) {
            return "redirect:/";
        }
        try {
            user.login(token);
            return "redirect:/list";
        }catch (AuthenticationException e) {
            token.clear();
            return "redirect:/login";
        }

        //session
//        HttpSession session = request.getSession();
//        User user = userService.getUser(mail, password);
//        if (user != null) {
//            session.setAttribute("user", user);
//            session.setAttribute("sa", "no");
//            return redirect("list");
//        } else {
//            model.addAttribute("msg", "Wrong mail or password!");
//            return PREFIX + "login";
//        }

    }

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_ALL_MIXED, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }

}
