package demo;

import any.Dom4jAdapter;
import demo.Dom4jDemo;
import demo.DomDemo;
import demo.SaxDemo;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
public class TestXml {

    public static void main(String[] args) {
        String filename = "F:\\minede\\lj\\XMLS\\src\\main\\resources\\config/st.xml";
        //OrBox(Array((TJsoup, "div.detail-map"),
       // (TString, "分店信息"),(TJsoup,"dl.datext")))
        testDom4jAdapter(filename);

    }

    public static void testDom(String filename) {
        DomDemo domDemo = new DomDemo();
        String path = domDemo.getClass().getResource("/").getPath();
        domDemo.parserXml(filename);
    }

    public static void testSax(String filename) {
        SaxDemo saxDemo = new SaxDemo();
        saxDemo.parserXml(filename);
    }

    public static void testDom4j(String filename) {
        Dom4jDemo dom4jDemo = new Dom4jDemo();
        dom4jDemo.createXml(filename);
    }

    public static void testDom4jAdapter(String filename) {
        Dom4jAdapter dom4jAdapter = new Dom4jAdapter();
//        dom4jAdapter.parserXml(filename,null,null);
        dom4jAdapter.ModiXMLFile(filename,"F:\\minede\\lj\\XMLS\\src\\main\\resources\\config/st3.xml");
    }
}
