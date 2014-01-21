package any;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-10-12
 * Time: 上午11:48
 * To change this template use File | Settings | File Templates.
 */
public class XPathParser extends XmlParser implements XmlDocParser {

    public void parserXml(String fileName) {
        //Do nothing
    }

    public Object parserXml(String filename, Translate translate, Object getMessage) throws FileNotFoundException, DocumentException {

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new FileInputStream(filename));
        /** 修改内容之一: 如果book节点中show属性的内容为yes,则修改成no */
        /** 先用xpath查找对象 */
        List list = document.selectNodes("/urlset/url");
        Iterator iter = list.iterator();
        if (iter.hasNext()) {
            Element ownerElement = (Element) iter.next();
            Node loc = ownerElement.selectSingleNode("/loc");
            Node data = ownerElement.selectSingleNode("/data");
            System.out.println("----------------");
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object parserXml(String filename, Boolean validateXsd, Translate translate, Object message) throws FileNotFoundException, DocumentException {
        SAXReader saxReader = new SAXReader();
        System.out.println("----------------start");
        long startTime = System.currentTimeMillis();
        Document document = saxReader.read(new FileInputStream(filename));
        document.setName("file:" + filename);
        Object result = null;
        if (validateXsd) {
            //Empty
        }
        if (message == null) {
            result = translate.translate(document);
        } else {
            result = translate.translateV(message, document);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----------------endTime:" + (endTime - startTime) + "ms");
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
