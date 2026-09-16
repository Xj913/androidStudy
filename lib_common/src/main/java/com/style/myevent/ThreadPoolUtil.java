package com.style.myevent;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtil {
    private static final String TAG = "ThreadPoolUtil";

    private ThreadPoolUtil() { }

    // 线程池核心线程数
    private static final int CORE_POOL_SIZE = 5;
    // 线程池最大线程数
    private static final int MAX_POOL_SIZE = 10;
    // 额外线程空状态生存时间
    private static final int KEEP_ALIVE_TIME = 2;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    // 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
    private static final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(7);

    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "currentThread : " + atomicInteger.getAndIncrement());
        }
    };

    private static final RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Log.e(TAG, "rejectedExecution");
        }
    };

    private static final ThreadPoolExecutor sExecutor;
    static {
        sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory, rejectedExecutionHandler);
    }

    public static void execute(Runnable r){
        sExecutor.execute(r);
    }

    public static <V>  Future<V> submit(Callable<V> callable){
        return sExecutor.submit(callable);
    }

    public static void shutDown(){
        sExecutor.shutdown();
    }

}
