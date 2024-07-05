package com.example.work;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印a1b2c3d4...z26
 * @author 陈翰垒
 */
public class seventhQuestion {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private static AtomicInteger count = new AtomicInteger();

    public void printLowercase() {
        lock.lock();
        try {
            int letter = 65;
            while (letter<=(int)'Z') {
                //打印abcd
                System.out.print(Character.toLowerCase((char) letter));
                letter++;
                condition.signal();
                condition.await();
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printNumber() {
        count.set(1);
        lock.lock();
        try {
            while (count.get()<27) {
                //打印123
                System.out.print(count.get());
                count.incrementAndGet();
                condition.signal();
                condition.await();
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        seventhQuestion sq = new seventhQuestion();
        Thread threadA = new Thread(() -> {
            sq.printLowercase();
        });
        Thread threadB = new Thread(() -> {
            sq.printNumber();
        });
        threadA.start();
        threadB.start();
    }
}
