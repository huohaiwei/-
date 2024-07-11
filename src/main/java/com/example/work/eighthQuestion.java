package com.example.work;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程一个打印字母
 *
 * @author 陈翰垒
 */
public class eighthQuestion {
    /**
     * 控制数字和字母的交替打印
     */
    private boolean flag = false;
    /**
     * 控制轮次结束
     */
    private boolean roundEnd = false;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    /**
     * 新的条件变量用于控制轮次结束消息
     */
    private final Condition roundEndCondition = lock.newCondition();

    public void printNumber() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int number = 1;
            for (int j = 0; j < 4; j++) {
                lock.lock();
                try {
                    while (!flag) {
                        condition.await();
                    }
                    System.out.print(number);
                    number++;
                    flag = false;
                    condition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            lock.lock();
            try {
                System.out.println("   第 " + (i + 1) + " 轮结束----------");
                roundEnd = true;
                roundEndCondition.signal(); // 发送轮次结束信号
            } finally {
                lock.unlock();
            }
        }
    }

    public void printLetter() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int letter = 97;
            for (int j = 0; j < 4; j++) {
                lock.lock();
                try {
                    while (flag) {
                        condition.await();
                    }
                    System.out.print((char) letter);
                    letter++;
                    flag = true;
                    condition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
            lock.lock();
            try {
                while (!roundEnd) {
                    // 等待轮次结束信号
                    roundEndCondition.await();
                }
                // 重置轮次结束标志
                roundEnd = false;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        eighthQuestion eq = new eighthQuestion();
        Thread threadA = new Thread(() -> {
            try {
                eq.printLetter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                eq.printNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadA.start();
        threadB.start();
    }
}
