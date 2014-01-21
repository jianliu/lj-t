package lj.lucene.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-8-1
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPoolUtil {


    private final static int corePoolSize = 30;
    private final static int maximumPoolSize = 30;
    private final static int queueSize = 40;
    private final static long keepAliveTime = 10;
    private final static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueSize);
    private final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            keepAliveTime, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static Boolean isShut = false;

    public ThreadPoolExecutor getDefaultPool() {
        return poolExecutor;
    }

    public static void shutdownPool(ThreadPoolExecutor pool, int await) {

        if (isShut || pool == null) return;
        pool.shutdown();
        isShut = true;
        while (!pool.isTerminated()) {
            try {
                System.out.println(String.format("定时监控活跃线程## Active :%d .%d ", pool.getActiveCount(), pool.getTaskCount()));
                pool.awaitTermination(await, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


