package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 * User: liu
 * Date: 13-4-28
 * Time: 上午11:24
 */

@Controller
@RequestMapping(value = "/manage")
public class ManageController extends BaseController{
    private static final String PREFIX = "/manage/";


    /**
     * 较早版本的页面，提供了部分信息的展示，当有页面请求时
     * 它对内存中的数据进行分析，并展示与前台
     *
     * @param model
     * @param seriesSize
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(Model model/*, @RequestParam(value = "seriesSize", required = false) int seriesSize*/) {
        model.addAttribute("content", "美好的记忆!");
        return PREFIX + "info";
    }
}
