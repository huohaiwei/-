package com.example.work;

import org.apache.logging.log4j.ThreadContext;

/**
 * 两个线程轮流打印数字，从1-100
 *
 * @author 陈翰垒
 */
public class firstQuestion {
    private int x;
    private final Object lock = new Object();
    private boolean flag;

    public void test() {
        firstQuestion fq = new firstQuestion();
        fq.x = 1;
        flag = true;
        //线程A
        Thread threadA = new Thread(() -> {
            for (; fq.x < 100; ) {
                synchronized (lock) {
                    //防止虚假唤醒
                    while (!flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程A----" + fq.x);
                    fq.x++;
                    flag = false;
                    lock.notify();      //唤醒使用lock的线程
                }
            }
            if (fq.x>100){
                System.out.println(Thread.currentThread().getName()+"最后输出的值是"+fq.x);
            }
        });

        //线程B
        Thread threadB = new Thread(() -> {
            //<99的时候进入for循环，A++后输出100，最后返回101
            for (; fq.x < 100; ) {
                synchronized (lock) {
                    //防止虚假唤醒
                    while (flag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程B----" + fq.x);
                    fq.x++;
                    flag = true;
                    lock.notify();   //唤醒使用lock的线程
                }
            }
            if (fq.x>100){
                System.out.println(Thread.currentThread().getName()+"最后输出的值是"+fq.x);
            }
        });
        threadA.start();
        threadB.start();
    }

    public static void main(String[] args) {
        firstQuestion firstQuestion = new firstQuestion();
        firstQuestion.test();
    }
}
