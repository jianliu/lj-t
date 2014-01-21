package loader;

import any.*;
import beans.ApiDeal;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-10-12
 * Time: 下午12:40
 * To change this template use File | Settings | File Templates.
 */
public class ApiXmlLoader implements ILoader {


    private static Translate defaultTranslate;

    //不精选验证
    private Boolean validatorXsd = false;

    public Boolean getValidatorXsd() {
        return validatorXsd;
    }

    public void setValidatorXsd(Boolean validatorXsd) {
        this.validatorXsd = validatorXsd;
    }


    /**
     * use xpath to parse in default
     *
     * @return Translate
     */
    public Translate getDefaultTranslate() {
        if (defaultTranslate == null) {
            defaultTranslate = new SimpleTranslate() {
                public Object translate(Document document) {
                    List<ApiDeal> deals = new ArrayList<ApiDeal>(100);

                    /** 先用xpath查找对象 */
                    List list = document.selectNodes("/urlset/url");
                    System.out.println(document.getName() + "  url.size=" + list.size());
                    Iterator iter = list.iterator();
                    while (iter.hasNext()) {
                        Element ownerElement = (Element) iter.next();
                        Node loc = ownerElement.selectSingleNode("loc");
                        Node data = ownerElement.selectSingleNode("data");
                        String locStr = loc.getText();
                        Node display = data.selectSingleNode("display");
                        Node identifier = display.selectSingleNode("identifier");
                        String city = display.selectSingleNode("city").getText();
                        String startTime = display.selectSingleNode("startTime").getText();
                        String endTime = display.selectSingleNode("endTime").getText();
                        String identifierStr = identifier.getText();
                        Node shops = data.selectSingleNode("shops");
                        String shopContent = shops.asXML();
                        ApiDeal apiDeal = new ApiDeal();
                        apiDeal.setLoc(locStr);
                        apiDeal.setIdentifier(identifierStr);
                        apiDeal.setShopsContent(shopContent);
                        apiDeal.setCity(city);
                        apiDeal.setStartTime(startTime);
                        apiDeal.setEndTime(endTime);

                        deals.add(apiDeal);
                    }
                    return deals;
                }
            };
        }
        return defaultTranslate;
    }

    /**
     * 利用dom4j解析xml文件，使用resources路径而非绝对路径
     *
     * @param filename
     * @return
     */
    public Object loadXml(String filename) {
        XmlDocParser xPathParser = new XPathParser();
        if (filename == null || filename == "")
            throw new RuntimeException("xml filename to parse is Empty");
        try {
            return xPathParser.parserXml(filename, false, getDefaultTranslate(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object loadXml(String filename, Translate translate) {
        XmlDocParser xPathParser = new XPathParser();
        if (filename == null || filename == "")
            throw new RuntimeException("xml filename to parse is Empty");
        if (translate == null)
            translate = getDefaultTranslate();
        try {
            return xPathParser.parserXml(filename, false, translate, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        String filename = "C:\\Users\\liu\\Desktop\\2419.xml";
        ILoader loader = new ApiXmlLoader();
        Object obj = loader.loadXml(filename);
        int i = 1;
    }

}
