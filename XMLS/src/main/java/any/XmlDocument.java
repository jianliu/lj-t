package any;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public interface XmlDocument {
    /**
     * 建立XML文档
     * @param fileName 文件全路径名称
     */
    public void createXml(String fileName);
    /**
     * 解析XML文档
     * @param fileName 文件全路径名称
     */
    public void parserXml(String fileName);
}