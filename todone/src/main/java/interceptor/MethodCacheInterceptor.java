package interceptor;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-7-12
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public class MethodCacheInterceptor implements MethodInterceptor,
        InitializingBean {

    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private Cache cache;


    public void setCache(Cache cache) {
        this.cache = cache;
    }


    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cache,
                "A cache is required. Use setCache(Cache) to provide one.");
    }


    public Object invoke(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        Object result;

        String cacheKey = getCacheKey(targetName, methodName, arguments);
        logger.info("getCacheKey");
        Element element = null;
        synchronized (this) {
            element = cache.get(cacheKey);

            if (element == null) {
//调用实际的方法
                result = invocation.proceed();
                element = new Element(cacheKey, (Serializable) result);
                cache.put(element);
            }
        }
        return element.getValue();
    }


    private String getCacheKey(String targetName, String methodName,
                               Object[] arguments) {
        logger.info("getCacheKey---targetName:{},methodName:{}", targetName, methodName);
        StringBuffer sb = new StringBuffer();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        logger.info("getCacheKey---result:{}", sb.toString());

        return sb.toString();
    }
}