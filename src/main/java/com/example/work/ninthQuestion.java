package com.example.work;

import java.util.concurrent.CountDownLatch;

/**
 * 保证T3在T2后执行，T2在T1后执行
 *
 * @author 陈翰垒
 */
public class ninthQuestion {
    /**
     * T2在T1后执行计数器
     */
    private static CountDownLatch c1 = new CountDownLatch(1);
    /**
     * T3在T2后执行计数器
     */
    private static CountDownLatch c2 = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " running。。。。。");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            System.out.println(Thread.currentThread().getName() + " 执行了 " + runTime + " 毫秒");
            //ThreadA运行，减少计数器
            c1.countDown();
        }, "T1");
        Thread threadB = new Thread(() -> {
            try {
                c1.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " running。。。。。");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            System.out.println(Thread.currentThread().getName() + " 执行了 " + runTime + " 毫秒");
            c2.countDown();
        }, "T2");
        Thread threadC = new Thread(() -> {
            try {
                c2.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " running。。。。。");
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;
            System.out.println(Thread.currentThread().getName() + " 执行了 " + runTime + " 毫秒");
        }, "T3");

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
