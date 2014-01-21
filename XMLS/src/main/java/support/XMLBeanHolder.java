package support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-30
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public class XMLBeanHolder {

    private String beanClass;
    private String beanName;
    private ConcurrentHashMap<String, Object> propertiesMap;
    private final Map<String, Object> beanDefinitionMap = new ConcurrentHashMap<String, Object>();

}
