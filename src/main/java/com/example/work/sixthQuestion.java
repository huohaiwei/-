package com.example.work;

import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印26个字母 Ab
 * @author 陈翰垒
 */
public class sixthQuestion {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private static AtomicInteger count = new AtomicInteger();

    public void printLowercase() {
        lock.lock();
        try {
            while (count.get() <= (int) 'Z') {
                //打印abcd
                System.out.print(Character.toLowerCase((char) count.get()));
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

    public void printCapital() {
        count.set(65);
        lock.lock();
        try {
            while (count.get() <= (int) 'Z') {
                //打印ABCDE
                System.out.print((char) count.get());
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
        sixthQuestion sq = new sixthQuestion();
        Thread threadA = new Thread(() -> {
            sq.printCapital();
        });
        Thread threadB = new Thread(() -> {
            sq.printLowercase();
        });
        threadA.start();
        threadB.start();
    }
}
