package com.example.work;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印数字和英文，输出12A34B56C
 *
 * @author 陈翰垒
 */
public class fifthQuestion {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void printNumber() {
        int number = 1;
        lock.lock();
        try {
            while (number < 7) {
                //打印12 34 56 78
                System.out.print("" + (char) (number + '0') + (char) (++number + '0'));
                number++;
                condition.signal();
                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printLetter() {
        int letter = 65;
        lock.lock();
        try {
            while (letter < (int) 'D') {
                //打印ABCDE
                System.out.print((char) letter);
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

    public static void main(String[] args) {
        fifthQuestion fq = new fifthQuestion();
        Thread threadA = new Thread(() -> {
            fq.printNumber();
        });
        Thread threadB = new Thread(() -> {
            fq.printLetter();
        });
        threadA.start();
        threadB.start();
    }
}
