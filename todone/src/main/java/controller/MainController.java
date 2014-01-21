package controller;

import model.DailyLog;
import model.dao.DailyLogService;
import model.dao.UserService;
import model.pojo.User;
import model.vo.FoodItem;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import utils.PageInfo;
import utils.files.FileUtils;
import utils.notice.mail.MailService;
import utils.notice.mail.MessageWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-4-28
 * Time: 下午2:08
 */

@Controller("mainController")
@RequestMapping(value = "/")
public class MainController extends BaseController {
    static String path = System.getProperty("user.dir");
    private static final String PREFIX = "/";

    @Autowired
    private DailyLogService dailyLogService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index() {
        return redirect("login");
    }

//    @RequestMapping("/content")
//    public String getContent(Model model) {
//        java.util.List<FoodItem> list = new ArrayList<FoodItem>(10);
//        for (int i = 0; i < 10; i++)
//            list.add(new FoodItem());
//        model.addAttribute("list", list);
//        return PREFIX + "content";
//    }

    @RequestMapping("/hehe")
    public String getHehe(Model model) {
        model.addAttribute("username", "liu");
        model.addAttribute("content", "美好的记忆!");
        model.addAttribute("time",tellMeTime());
        return PREFIX + "hehe";
    }

    public String tellMeTime(){
        String now=new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        logger.info("getHeTime:{}",now);
        return  now;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "start", defaultValue = "0") int start, HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        if(user==null) return redirect("login");
        java.util.List<DailyLog> dailyLogs = dailyLogService.getDailyLogs(user.getUsername(), start);
        PageInfo page = new PageInfo(start, dailyLogs.size(), PageInfo.DEFAULT_PAGESIZE, dailyLogs);
        model.addAttribute("rolePager", page);
        model.addAttribute("username", user.getUsername());
        return PREFIX + "list";
    }

    //    @RequiresRoles("user")
    @RequiresPermissions("list:add")
    @RequestMapping(value = "/add")
    public String addLog(Model model, DailyLog dailyLog, HttpServletRequest request) throws IOException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();

//        HttpSession session = request.getSession();
//        Object obj = session.getAttribute("user");
//        User user =userService.;
        if (user == null) return redirect("login");

        dailyLogService.saveDailyLog(dailyLog, user.getUsername());
        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setMailForm(user.getMail());
        messageWrapper.setMailSubject("日志");
        messageWrapper.setMailTo(user.getMailTo());
        messageWrapper.setPassword(user.getPassword());
        try {
            mailService.sendMail(dailyLog.toStringWithHtml(), messageWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirect("list");
    }


}
