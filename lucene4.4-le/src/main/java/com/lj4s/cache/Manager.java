package com.lj4s.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-4
 * Time: 下午1:33
 * .
 * <p/>
 * <p/>
 * <p/>
 * maxElementsInMemory="10000"
 * eternal="false"
 * timeToIdleSeconds="120"
 * timeToLiveSeconds="120"
 * overflowToDisk="false"
 * diskPersistent="false"
 * diskExpiryThreadIntervalSeconds="120"
 */
public class Manager {

    private static final Logger LOG = LoggerFactory.getLogger(Manager.class);

    public final static CacheManager manager = CacheManager.create();
    public final static String Default_Cache_Name = "queryCache";

    private final static int maxElementsInMemory = 10000;
    private final static Boolean eternal = false;
    private final static Boolean overflowToDisk = false;
    private final static Boolean diskPersistent = false;
    private final static int timeToIdleSeconds = 120;
    private final static int timeToLiveSeconds = 120;
    private final static int diskExpiryThreadIntervalSeconds = 120;

    public static void addCache(Cache cache) {
        manager.addCacheIfAbsent(cache);
    }

    public static Cache getCache(String cacheName) {
        if (!manager.cacheExists(cacheName)) {
            if (LOG.isDebugEnabled())
                LOG.debug("Cache:{} doesn't exist,create now", cacheName);
            Cache cache = new Cache(cacheName, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds,
                    timeToIdleSeconds, diskPersistent, diskExpiryThreadIntervalSeconds);
            manager.addCache(cache);
        }
        Cache cache = manager.getCache(cacheName);
        return cache;
    }

    public static Cache getCache() {
        if (!manager.cacheExists(Default_Cache_Name)) {
            if (LOG.isDebugEnabled())
                LOG.debug("Cache:{} doesn't exist,create now", Default_Cache_Name);
            Cache cache = new Cache(Default_Cache_Name, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds,
                    timeToIdleSeconds, diskPersistent, diskExpiryThreadIntervalSeconds);
            manager.addCache(cache);
        }
        Cache cache = manager.getCache(Default_Cache_Name);
        return cache;
    }

    public static void main(String[] args) {
        CacheManager manager = CacheManager.create();
        Cache myCache = manager.getCache("queryCache");
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
//        println(elementsInMemoryS);

    }

    public static void println(Object obj) {
        System.out.println(obj);
    }


}
