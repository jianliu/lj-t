package any;

import org.dom4j.DocumentException;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
public interface XmlDocParser {


    /**
     * 解析XML文档
     *
     * @param fileName   文件名
     * @param getMessage 附带信息
     * @param translate  内容转换器
     * @return
     */
    public Object parserXml(String fileName, Translate translate, Object getMessage) throws FileNotFoundException, DocumentException;

    /**
     * 解析xml文档
     *
     * @param filename    文件名
     * @param validateXsd 是否校验文档
     * @param translate   内容转换器
     * @return
     */
    public Object parserXml(String filename, Boolean validateXsd, Translate translate, Object getMessage) throws FileNotFoundException, DocumentException;


}
