package com.lj4s.pool;

import lj.lucene.utils.ThreadPoolUtil;

import java.sql.SQLException;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-11
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPool {

    final static String charset = "GBK";
    static int timeoutLimit = 10;
    static int corePoolSize = 4;
    static int maximumPoolSize = 4;
    static int queueSize = 10;
    static long keepAliveTime = 0;
    static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(3);
    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
            keepAliveTime, TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static Byte[] lock = new Byte[]{};

    public static void main(String[] args) throws SQLException, InterruptedException {


        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    println(String.format("定时监控活跃线程## Active :%d .coreSize: %d .maxSize: %d .taskCount: %d,queueSize:%d",
                            poolExecutor.getActiveCount(),
                            poolExecutor.getCompletedTaskCount(), poolExecutor.getMaximumPoolSize(), poolExecutor.getTaskCount(), poolExecutor.getQueue().size()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        });
        thread.setDaemon(true);
//        thread.start();

        for (int i = 1; i <= 20; i++) {

            final int finalI = i;
            while (poolExecutor.getQueue().size() >= 3) {
                println("=============500ms sleeping");
                Thread.sleep(500);
            }

            poolExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    System.out.println("===========" + finalI);
                    try {
                        Thread.sleep(3000);
                        if(finalI==18){
                            Thread.sleep(6000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
        }
//        poolExecutor.shutdown();
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        ThreadPoolUtil.shutdownPool(poolExecutor, 5);
        poolExecutor.shutdown();
        poolExecutor.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println("===========" + 22);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
        System.out.println(11);
//
    }

    public static void addWord(final int finalI) {

        poolExecutor.execute(new Runnable() {

            @Override
            public void run() {

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        long accessTime = System.currentTimeMillis();
                        while (true) {
                            long now = System.currentTimeMillis();
                            println("monitor 任务" + finalI);
                            if (now - accessTime > timeoutLimit * 1000) {
                                println("should over 任务" + finalI);
                                return;
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }
                    }
                });
                thread.setDaemon(true);
                if (finalI == 1) {
                    thread.start();
                  /*  try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }*/

                }
                println("第 " + finalI + " 个任务执行  " + Thread.currentThread().getId());
//                try {
//                    if(finalI==18)
//                        Thread.sleep(10000);
////                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//                throw new RuntimeException("test  " + finalI);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

}
