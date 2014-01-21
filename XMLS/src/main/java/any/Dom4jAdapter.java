package any;

import demo.Dom4jDemo;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultText;
import po.ConfigBean;
import po.Employee;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class Dom4jAdapter extends Dom4jDemo implements XmlDocParser {


    private Validator validator = new Dom4jValidator();

    private ConfigBean configBean;

    public ConfigBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }


    public Translate getDefaultTranslate() {
        Translate translate = new Translate() {
            public Object translate(Document document) {
                ConfigBean configBean1 = new ConfigBean();
                Employee employeeMe = new Employee();
                //name.sex.age
                Element employees = document.getRootElement();
                for (Iterator i = employees.elementIterator(); i.hasNext(); ) {
                    Element employee = (Element) i.next();
                    for (Iterator j = employee.elementIterator(); j.hasNext(); ) {
                        Element node = (Element) j.next();
                        if (node.getName() == "name") {
                            employeeMe.setName(node.getText());
                        } else if (node.getName() == "age") {
                            employeeMe.setAge(node.getText());
                        } else if (node.getName() == "sex") {
                            employeeMe.setSex(node.getText());
                        }
                        System.out.println(node.getName() + ":" + node.getText());
                    }

                }
                configBean1.setEmployee(employeeMe);
                return configBean1;
            }

            public Object translateV(Object bean, Document document) {
                //no op
                return null;
            }
        };
        return translate;
    }

    public Object parserXml(String fileName,Translate translate, Object getMessage) throws FileNotFoundException, DocumentException {
//        File inputXml = new File(fileName);
        InputStream inputStream = this.getClass().getResourceAsStream(fileName);
        //无法在resources下面找到文件，则尝试用绝对路径方式打开文件
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(new File(fileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(inputStream);
            if (translate == null) {
                translate = getDefaultTranslate();
            }
            return translate.translate(document);
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("dom4j parserXml");
        return null;
    }

    public InputStream loadStream(String filename) {
        InputStream inputStream = this.getClass().getResourceAsStream(filename);
        //无法在resources下面找到文件，则尝试用绝对路径方式打开文件
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(new File(filename));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    public Object parserXml(String filename, Boolean validateXsd, Translate translate, Object getMessage) throws FileNotFoundException, DocumentException{

        InputStream inputStream = loadStream(filename);
        SAXReader saxReader = new SAXReader();

        Document document = null;
        try {
            document = saxReader.read(inputStream);
//            document.setName("doc for:" + fileName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        if (validateXsd) {
            try {
                validator.validateXml(document);
            } catch (Exception e) {
                RuntimeException re = new RuntimeException("验证xml文档:" + filename + "失败");
                re.addSuppressed(e);
                throw re;
            }
        }
        try {

            if (translate == null) {
                translate = getDefaultTranslate();
            }
            return translate.translate(document);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("dom4j parserXml");
        return null;
    }


    /**
     * 修改XML文件中内容,并另存为一个新文件
     * 重点掌握dom4j中如何添加节点,修改节点,删除节点
     *
     * @param filename    修改对象文件
     * @param newfilename 修改后另存为该文件
     * @return 返回操作结果, 0表失败, 1表成功
     */
    public int ModiXMLFile(String filename, String newfilename) {
        int returnValue = 0;
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(filename));
            /** 修改内容之一: 如果book节点中show属性的内容为yes,则修改成no */
            /** 先用xpath查找对象 */
            List list = document.selectNodes("/employees/employee/name/@show");
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                Attribute attribute = (Attribute) iter.next();
                if (attribute.getValue().equals("yes")) {
                    attribute.setValue("no");
                }
            }

            /**
             * 修改内容之二: 把owner项内容改为Tshinghua
             * 并在owner节点中加入date节点,date节点的内容为2004-09-11,还为date节点添加一个属性type
             */
            list = document.selectNodes("/employees/group");
            iter = list.iterator();
            if (iter.hasNext()) {
                Element ownerElement = (Element) iter.next();
                ownerElement.setText("cd");
                ownerElement.remove(new DefaultText("cd"));
                Element dateElement = ownerElement.addElement("date");
                dateElement.setText("2004-09-12");
                dateElement.addAttribute("type", "Gregorian calendar");
            }

            /** 修改内容之三: 若title内容为Dom4j Tutorials,则删除该节点 */
            list = document.selectNodes("/books/book");
            iter = list.iterator();
            while (iter.hasNext()) {
                Element bookElement = (Element) iter.next();
                Iterator iterator = bookElement.elementIterator("title");
                while (iterator.hasNext()) {
                    Element titleElement = (Element) iterator.next();
                    if (titleElement.getText().equals("Dom4j Tutorials")) {
                        bookElement.remove(titleElement);
                    }
                }
            }

            try {
                /** 将document中的内容写入文件中 */
                XMLWriter writer = new XMLWriter(new FileWriter(new File(newfilename)));
                writer.write(document);
                writer.close();
                /** 执行成功,需返回1 */
                returnValue = 1;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }


}
