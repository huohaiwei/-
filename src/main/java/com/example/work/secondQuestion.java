package com.example.work;

/**
 * 三个线程交替顺序打印1-100
 *
 * @author 陈翰垒
 */
public class secondQuestion {
    private int x;
    private final Object lock = new Object();

    public void test() {
        secondQuestion sq = new secondQuestion();
        sq.x = 1;
        //打印3n+1;
        Thread threadA = new Thread(() -> {
            while (sq.x < 100) {
                synchronized (lock) {
                    while (sq.x % 3 != 1 || sq.x > 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程A----" + sq.x);
                    sq.x++;
                    lock.notifyAll();
                }

            }
        });

        //打印3n+2;
        Thread threadB = new Thread(() -> {
            while (sq.x < 100) {
                synchronized (lock) {
                    while (sq.x % 3 != 2 || sq.x > 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程B----" + sq.x);
                    sq.x++;
                    lock.notifyAll();
                }

            }
        });

        //打印3n;
        Thread threadC = new Thread(() -> {
            while (sq.x < 100) {
                synchronized (lock) {
                    while (sq.x % 3 != 0 || sq.x > 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("线程C----" + sq.x);
                    sq.x++;
                    lock.notifyAll();
                }
            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
    }

    public static void main(String[] args) {
        secondQuestion secondQuestion = new secondQuestion();
        secondQuestion.test();
    }
}
