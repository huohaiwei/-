package com.example.work;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仿购票系统，目前有1000张票，同时有10个购票窗口，模拟购票流程，打印购票结果
 *
 * @author 陈翰垒
 */
public class tenthQuestion {
    private static final int TICKETS = 1000;

    /**
     * 剩余票数
     */
    private static int remainingTickets = TICKETS;

    /**
     * 锁
     */
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 标志位，指示是否票已卖光
     */
    private static volatile boolean soldOut = false;

    private void buyTicket() {
        int book = new Random().nextInt(10) + 1;
        if (remainingTickets >= book) {
            remainingTickets -= book;
            System.out.println(Thread.currentThread().getName() + " 购买了 " + book + " 张票, 还剩下" + remainingTickets + "票。");
            if (remainingTickets == 0) {
                soldOut = true;
                System.out.println("票已卖光");
            }
        }
    }

    public static void main(String[] args) {
        tenthQuestion tq = new tenthQuestion();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (!soldOut) {
                    lock.lock();
                    try {
                        if (remainingTickets > 0) {
                            tq.buyTicket();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        try {
                            //买完票后执行了一些操作
                            Thread.sleep((long) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "Thread-" + i).start();
        }
    }
}

