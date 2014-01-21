package box;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public enum ExpressType {

    TString(1, "字符串匹配"),
    TJsoup(2, "Jsoup表达式"),
    TRegex(3, "正则表达式匹配"),
    TBox(4, "MonitorBox"),
    TNot(5, "非，它后面不再跟字符串,而是（MonitorBox）");
    private int code;
    private String desc;

    private ExpressType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
