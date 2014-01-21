package lj.ehcache.manage;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-7-11
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class Demo {

    public static void main(String[] args) {
        CacheManager manager = CacheManager.create();
        Cache myCache = manager.getCache("demoCache");
        for (int i = 1; i < 500; i++) {
            Element element = new Element(i, "value" + i);
            myCache.put(element);
        }
        long elementsInMemory = myCache.getMemoryStoreSize();
        int elementsInMemoryS = myCache.getSize();
        long elementsInMemoryD = myCache.getDiskStoreSize();
        println(elementsInMemory);
        println(elementsInMemoryS);
        Element element = myCache.get(300);
        Object value = element.getObjectValue();
//        manager.shutdown();
//        println(elementsInMemoryS);

    }
    public static void println(Object obj){
        System.out.println(obj);
    }

}
