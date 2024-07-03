package com.example.work;

/**
 * 线程A、B、C分别打印1、2、3，顺序执行10次
 *
 * @author 陈翰垒
 */
public class thirdQuestion {
    private int flag;
    private final Object lock = new Object();

    public void printNumber(int number) {
        while (flag < 30) {
            synchronized (lock) {
                while (flag % 3 != number - 1 || flag >= 30) {
                    try {
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread" + Thread.currentThread().getName() + "----" + number);
                if (number  == 3) {
                    System.out.println("第 " + (flag / 3 +1  ) + " 次结束");
                }
                flag++;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        thirdQuestion tq = new thirdQuestion();
        tq.flag = 0;
        //输出1  , 余数0
        Thread threadA = new Thread(() -> {
            tq.printNumber(1);
        }, "A");
        //输出2, 余数1
        Thread threadB = new Thread(() -> {
            tq.printNumber(2);
        }, "B");
        //输出3, 余数2
        Thread threadC = new Thread(() -> {
            tq.printNumber(3);
        }, "C");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
