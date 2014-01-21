package first;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-7-11
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class deo {

    @Test
    public void testU(){
        CacheManager.create();
        String[] cacheNames = CacheManager.getInstance().getCacheNames();

//       CacheManager.newInstance();
//       String[] cacheNames = manager.getCacheNames();
         println(cacheNames.length);
        int i=1;
    }

    @Test
    public void tst2(){
        CacheManager singletonManager = CacheManager.create();
        singletonManager.addCache("testCache");
       Boolean b=  singletonManager.cacheExists("testCache");
        Cache test = singletonManager.getCache("testCache");
        Cache query=singletonManager.getCache("demoCache");
        Element element = new Element("key1", "value1");
        query.put(element);
        Element element2 = query.get("key1");
        Object value = element.getObjectValue();
        long elementsInMemory = query.getMemoryStoreSize();
        int elementsInMemoryS = query.getSize();
        long elementsInMemoryD = query.getDiskStoreSize();
//        int hits2 = query.getMemoryStoreHitCount();
        singletonManager.removeCache("testCache");
        String[] cacheNamesForManager1 = singletonManager.getCacheNames();
//        query.
        CacheManager manager = CacheManager.newInstance();

        CacheManager.getInstance().shutdown();
        println("==");
    }

    public void println(Object obj){
        System.out.println(obj);
    }
}
