package demo;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午10:43
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.util.Iterator;

import any.XmlDocument;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import po.ConfigBean;

/**
 * @author hongliang.dinghl
 *         Dom4j 生成XML文档与解析XML文档
 */
public class Dom4jDemo implements XmlDocument {

    public void createXml(String fileName) {
        Document document = DocumentHelper.createDocument();
        Element employees = document.addElement("employees");
        Element employee = employees.addElement("employee");
        Element name = employee.addElement("name");
        name.setText("ddvip");
        Element sex = employee.addElement("sex");
        sex.setText("m");
        Element age = employee.addElement("age");
        age.setText("29");
        try {
            Writer fileWriter = new FileWriter(fileName);
            XMLWriter xmlWriter =null;
//            = new XMLWriter(fileWriter)
            xmlWriter = prettyPrint(fileWriter);
//            OutputFormat format = OutputFormat.createPrettyPrint();
//            xmlWriter = new XMLWriter(fileWriter, format );
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {

            System.out.println(e.getMessage());
        }


    }

    public XMLWriter prettyPrint( Writer writer) {

        OutputFormat format = OutputFormat.createPrettyPrint();
       return new XMLWriter(writer, format);
    }

    public void parserXml(String fileName) {
        File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputXml);
            Element employees = document.getRootElement();
            for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                Element employee = (Element) i.next();
                for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }

            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("dom4j parserXml");
    }
}