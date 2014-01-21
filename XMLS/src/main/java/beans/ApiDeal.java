package beans;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-10-12
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
public class ApiDeal {

    /**
     * 进行抓取的网站的url
     */
    private String loc;
    /**
     *网站的唯一标示符
     */
    private String identifier;
    /**
     *shops节点的xml string
     */
    private String shopsContent;
    /**
     *城市中文名
     */
    private String city;
    /**
     *deal的开始时间
     */
    private String startTime;
    /**
     *eal的结束时间
     */
    private String endTime;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getShopsContent() {
        return shopsContent;
    }

    public void setShopsContent(String shopsContent) {
        this.shopsContent = shopsContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
