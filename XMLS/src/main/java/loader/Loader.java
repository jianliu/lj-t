//package loader;
//
//import any.Dom4jAdapter;
//import any.SimpleTranslate;
//import any.Translate;
//import box.*;
//import org.dom4j.Document;
//import org.dom4j.Element;
//
//import java.util.*;
//
///**
// * Created with IntelliJ IDEA.
// * User: liu
// * Date: 13-9-27
// * Time: 上午10:06
// * To change this template use File | Settings | File Templates.
// */
//public class Loader {
//
//    private static final String defaultFilename = "F:\\minede\\lj\\XMLS\\src\\main\\resources\\config/st2.xml";
//
//    private static Translate defaultTranslate;
//
//    public static Translate getDefaultTranslate() {
//        if (defaultTranslate == null) {
//            defaultTranslate = getSimpleTranslate();
//        }
//        return defaultTranslate;
//    }
//
//
//    public static Translate getSimpleTranslate() {
//        return new SimpleTranslate() {
//
//            @SuppressWarnings("ConstantConditions")
//            private Object genContent(Element element) {
//                List<ArrayBean> list = new ArrayList<ArrayBean>();
//                Element box = element;
//                MBox<List<ArrayBean>> mBox;
//                if (box.getName() == "and") {
//                    mBox = new AndBox<List<ArrayBean>>(list);
//                } else if (box.getName() == "or") {
//                    mBox = new OrBox<List<ArrayBean>>(list);
//                } else {
//                    //无效的node，放弃
//                    return null;
//                }
//                for (Iterator j = box.elementIterator(); j.hasNext(); ) {
//                    ExpressType expressType = null;
//                    Object content = null;
//                    Element node = (Element) j.next();
//                    if (node.getName() == "TString") {
//                        expressType = ExpressType.TString;
//                        content = node.getText();
//                    } else if (node.getName() == "TJsoup") {
//                        expressType = ExpressType.TJsoup;
//                        content = node.getText();
//                    } else if (node.getName() == "TRegex") {
//                        expressType = ExpressType.TRegex;
//                        content = node.getText();
//                    } else if (node.getName() == "TBox") {
//                        expressType = ExpressType.TBox;
//                        List<Object> listTmp = new ArrayList<Object>();
//                        for (Iterator i = node.elementIterator(); i.hasNext(); ) {
//                            Element box1 = (Element) i.next();
//                            //每个 Iterator对应一个box，可能是and，也可能是or
//                            listTmp.add(genContent(box1));
//                        }
//                        content = listTmp;
//                    } else if (node.getName() == "TNot") {
//                        expressType = ExpressType.TNot;
//                        List<Object> listTmp = new ArrayList<Object>();
//                        for (Iterator i = node.elementIterator(); i.hasNext(); ) {
//                            Element box1 = (Element) i.next();
//                            //每个 Iterator对应一个box，可能是and，也可能是or
//                            listTmp.add(genContent(box1));
//                        }
//                        content = listTmp;
//                    }
//                    list.add(new ArrayBean(expressType, content));
////                    System.out.println(node.getName() + ":" + node.getText());
//                }
//                return mBox;
//            }
//
//            public Object translate(Document document) {
//                Map<String, Object> map = new HashMap<String, Object>(4);
//
//                Element boxs = document.getRootElement();
//                for (Iterator i = boxs.elementIterator(); i.hasNext(); ) {
//                    //每个 Iterator对应一个box，可能是and，也可能是or
//                    Element box = (Element) i.next();
//                    String type = box.attributeValue("type");
//                    if (type != null) {
//                        Object ret = genContent(box);
//                        map.put(type, ret);
//                    }
//                }
//
//                return map;
//            }
//
//        };
//    }
//
//    /**
//     * 利用dom4j解析xml文件，使用resources路径而非绝对路径
//     *
//     * @param filename
//     * @return
//     */
//    public static Object loadXml(String filename) {
//        Dom4jAdapter dom4jAdapter = new Dom4jAdapter();
//        if (filename == null || filename == "")
//            filename = defaultFilename;
//        Translate translate = getDefaultTranslate();
//
//        return dom4jAdapter.parserXml(filename, null, translate);
//    }
//
//    public static Object loadXml(String filename, Translate translate) {
//        Dom4jAdapter dom4jAdapter = new Dom4jAdapter();
//        if (filename == null || filename == "")
//            filename = defaultFilename;
//        if (translate == null)
//            translate = getDefaultTranslate();
//
//        return dom4jAdapter.parserXml(filename, null, translate);
//    }
//
//    public static void main(String[] args) {
////        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
//        String filename = "/config/st2.xml";
//        Object map = Loader.loadXml("");
//        int i = 1;
//    }
//
//}
