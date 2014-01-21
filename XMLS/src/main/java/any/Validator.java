package any;

import org.dom4j.Document;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-30
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public interface Validator {

    /**
     * 使用inputStream来验证格式
     * 由于inputStream不持久，因此设计的默认校验器不推荐是它
     * @param inputStream
     * @throws Exception
     */
    public void validateXml(InputStream inputStream) throws Exception;

    /**
     * 使用xml文件名来验证格式
     * @param xmlFileName  xml文件名
     * @throws Exception
     */
    public void validateXml(String xmlFileName) throws Exception;

    /**
     *  校验文档  推荐
     * @param xmlDocument 校验xml文档实例
     * @throws Exception
     */
    public void validateXml(Document xmlDocument) throws Exception;

}
