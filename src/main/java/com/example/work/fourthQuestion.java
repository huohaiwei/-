package com.example.work;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器累加，100个线程累加100次
 *
 * @author 陈翰垒
 */
public class fourthQuestion {

    //解决竞态状态
    public static AtomicInteger counter = new AtomicInteger(0);

    public void test() {
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(100, 100, 4, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        //开启100个任务
        for (int i = 0; i < 100; i++) {
            //启动线程池
            executor.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.incrementAndGet();
                }
            });
        }
        //关闭线程池
        executor.shutdown();
        try {
            //等待60s，超时则强行关闭
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        System.out.println(counter);
    }

    public static void main(String[] args) {
        fourthQuestion fq = new fourthQuestion();
        fq.test();
    }
}
