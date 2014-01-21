package loader;

import any.Dom4jAdapter;
import any.Dom4jValidator;
import any.SimpleTranslate;
import any.Translate;
import box.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-30
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class BoxLoader implements ILoader {

    private static final String defaultFilename = "F:\\minede\\lj\\XMLS\\src\\main\\resources\\config/st2.xml";

    private static Translate defaultTranslate;

    private Boolean validatorXsd = true;

    public Boolean getValidatorXsd() {
        return validatorXsd;
    }

    public void setValidatorXsd(Boolean validatorXsd) {
        this.validatorXsd = validatorXsd;
    }

    protected enum InfoType {
        TipInfo, ShopInfo, ExpiredTime, Category
    }

    protected enum BoxType {
        and, or, not
    }


    public Translate getDefaultTranslate() {
        if (defaultTranslate == null) {
            defaultTranslate = getSimpleTranslate();
        }
        return defaultTranslate;
    }

    public Boolean checkType(String boxType) {
        return boxType != null && BoxType.valueOf(boxType) != null;
    }

    public Translate getSimpleTranslate() {
        return new SimpleTranslate() {

            @SuppressWarnings("ConstantConditions")
            private Object genContent(Element element) {
                List<ArrayBean> list = new ArrayList<ArrayBean>();
                Element box = element;
                String boxType = box.attributeValue("type");
                MBox<List<ArrayBean>> mBox;
                if (checkType(boxType) && BoxType.valueOf(boxType) == BoxType.and) {
                    mBox = new AndBox<List<ArrayBean>>(list);
                } else if (checkType(boxType) && BoxType.valueOf(boxType) == BoxType.or) {
                    mBox = new OrBox<List<ArrayBean>>(list);
                } else {
                    //无效的node，放弃
                    return null;
                }
                for (Iterator j = box.elementIterator(); j.hasNext(); ) {
                    ExpressType expressType = null;
                    Object content = null;
                    Element node = (Element) j.next();
                    if (node.getName() == "TString") {
                        expressType = ExpressType.TString;
                        content = node.getTextTrim();
                    } else if (node.getName() == "TJsoup") {
                        expressType = ExpressType.TJsoup;
                        content = node.getTextTrim();
//                        content=node.get
                    } else if (node.getName() == "TRegex") {
                        expressType = ExpressType.TRegex;
                        content = node.getTextTrim();
                    } else if (node.getName() == "TBox") {
                        expressType = ExpressType.TBox;
                        List<Object> listTmp = new ArrayList<Object>();
                        for (Iterator i = node.elementIterator(); i.hasNext(); ) {
                            Element box1 = (Element) i.next();
                            //每个 Iterator对应一个box，可能是and，也可能是or
                            listTmp.add(genContent(box1));
                        }
                        content = listTmp;
                    } else if (node.getName() == "TNot") {
                        expressType = ExpressType.TNot;
                        List<Object> listTmp = new ArrayList<Object>();
                        for (Iterator i = node.elementIterator(); i.hasNext(); ) {
                            Element box1 = (Element) i.next();
                            //每个 Iterator对应一个box，可能是and，也可能是or
                            listTmp.add(genContent(box1));
                        }
                        content = listTmp;
                    }
                    list.add(new ArrayBean(expressType, content));
//                    System.out.println(node.getName() + ":" + node.getText());
                }
                return mBox;
            }

            public Object translate(Document document) {
                Map<String, Object> map = new HashMap<String, Object>(4);

                Element boxs = document.getRootElement();
                for (Iterator i = boxs.elementIterator(); i.hasNext(); ) {
                    //每个 Iterator对应一个box，可能是and，也可能是or
                    Element box = (Element) i.next();
                    String type = box.attributeValue("info");
                    if (type != null && InfoType.valueOf(type) != null) {
                        Object ret = genContent(box);
                        map.put(type, ret);
                    }
                }

                return map;
            }

        };
    }

    /**
     * 利用dom4j解析xml文件，使用resources路径而非绝对路径
     *
     * @param filename
     * @return
     */
    public Object loadXml(String filename) {
        Dom4jAdapter dom4jAdapter = new Dom4jAdapter();
        if (filename == null || filename == "")
            filename = defaultFilename;
        Translate translate = getDefaultTranslate();

        try {
            return dom4jAdapter.parserXml(filename, validatorXsd, translate, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Object loadXml(String filename, Translate translate) {
        Dom4jAdapter dom4jAdapter = new Dom4jAdapter();
        if (filename == null || filename == "")
            filename = defaultFilename;
        if (translate == null)
            translate = getDefaultTranslate();

        try {
            return dom4jAdapter.parserXml(filename, false, translate, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DocumentException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
//        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        String filename = "/config/st2.xml";
        BoxLoader loader = new BoxLoader();
        loader.setValidatorXsd(true);
        Object map = loader.loadXml(filename);
        int i = 1;
    }

}
