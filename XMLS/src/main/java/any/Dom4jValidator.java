package any;


import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;


/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-29
 * Time: 下午1:06
 * To change this template use File | Settings | File Templates.
 */
public class Dom4jValidator implements Validator {


    public static SAXParserFactory getSAXParserFactory() {
        //获取基于 SAX 的解析器的实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //解析器在解析时验证 XML 内容。
        factory.setValidating(true);
        //指定由此代码生成的解析器将提供对 XML 名称空间的支持。
        factory.setNamespaceAware(false);
        return factory;
    }


    public static SAXParser getDefaultParse() throws SAXNotRecognizedException, SAXNotSupportedException {
        SAXParser parser = null;
        try {
            parser = getSAXParserFactory().newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (parser != null) {
            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");

            parser.setProperty(
                    "http://java.sun.com/xml/jaxp/properties/schemaSource",
                    "file:/F:/minede/lj/XMLS/target/classes/config/st.xsd");

        }
        return parser;
    }


    public void validateXml(InputStream inputStream) throws Exception {
        if (inputStream == null) throw new RuntimeException("Null Pointor inputStream to validate");
        //创建默认的XML错误处理器
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        SAXParser parser = getDefaultParse();
        SAXReader xmlReader = new SAXReader();
//        xmlReader.setEncoding("utf-8");
        Document xmlDocument = xmlReader.read(inputStream);
        //创建一个SAXValidator校验工具，并设置校验工具的属性
        SAXValidator validator = new SAXValidator(parser.getXMLReader());
        //设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
        validator.setErrorHandler(errorHandler);
        validator.setXMLReader(parser.getXMLReader());
        //校验
        validator.validate(xmlDocument);
        //如果错误信息不为空，说明校验失败，打印错误信息
        if (errorHandler.getErrors().hasContent()) {

            System.out.println("XML文件通过XSD文件校验失败！");
            System.out.println(errorHandler.getErrors().getTextTrim());
            throw new RuntimeException("XML文件通过XSD文件校验失败");

        }

    }

    public void validateXml(Document xmlDocument) throws Exception {
        //创建默认的XML错误处理器
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        SAXParser parser = getDefaultParse();
//        xmlReader.setEncoding("utf-8");
        //创建一个SAXValidator校验工具，并设置校验工具的属性
        SAXValidator validator = new SAXValidator(parser.getXMLReader());
        //设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
        validator.setErrorHandler(errorHandler);
        validator.setXMLReader(parser.getXMLReader());
        //校验
        validator.validate(xmlDocument);
        //如果错误信息不为空，说明校验失败，打印错误信息
        if (errorHandler.getErrors().hasContent()) {

            System.out.println("XML文件通过XSD文件校验失败！");
            System.out.println(errorHandler.getErrors().getTextTrim());
            throw new RuntimeException("XML文件通过XSD文件校验失败 doc:"+xmlDocument.getName());

        }

    }

    public void validateXml(String xmlFileName) throws Exception {
        //创建默认的XML错误处理器
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        //获取基于 SAX 的解析器的实例
        SAXParserFactory factory = getSAXParserFactory();
        //使用当前配置的工厂参数创建 SAXParser 的一个新实例。
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //创建一个读取工具
        SAXReader xmlReader = new SAXReader();
        //获取要校验xml文档实例
        Document xmlDocument = xmlReader.read(new File(xmlFileName));
        //设置 XMLReader 的基础实现中的特定属性。核心功能和属性列表可以在 [url]http://sax.sourceforge.net/?selected=get-set[/url] 中找到。
        assert parser != null;
        parser.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        //创建一个SAXValidator校验工具，并设置校验工具的属性
        SAXValidator validator = new SAXValidator(parser.getXMLReader());
        //设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
        validator.setErrorHandler(errorHandler);
        //校验
        validator.validate(xmlDocument);
        //如果错误信息不为空，说明校验失败，打印错误信息
        if (errorHandler.getErrors().hasContent()) {

            System.out.println("XML文件通过XSD文件校验失败！");
            System.out.println(errorHandler.getErrors().getTextTrim());
            throw new RuntimeException("XML文件通过XSD文件校验失败,文档名:" + xmlFileName);
        }

    }

    public static void main(String[] args) throws Exception {
        String filename = "F:\\minede\\lj\\XMLS\\src\\main\\resources\\config/st2.xml";
        Validator validator = new Dom4jValidator();
        File file = new File(filename);

        InputStream inputStream = new FileInputStream(file);
        validator.validateXml(filename);
    }


}
