package com.example.work;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 手写线程池
 *
 * @author 陈翰垒
 */
public class ThreadPoolWrite implements Executor {
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private final BlockingQueue<Runnable> workQueue;

    //线程池的线程数，初始化0
    private final AtomicInteger ctl = new AtomicInteger(0);

    public ThreadPoolWrite(int corePoolSize,
                           int maximumPoolSize,
                           BlockingQueue<Runnable> workQueue) {

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        int c = ctl.get();
        if (c < corePoolSize) {
            if (!addWorker(command)) {
                reject();
            }
            return;
        }
        //尝试将多余的任务放入队列
        if (!workQueue.offer(command)) {
            //队列已满尝试增加线程数
            if (!addWorker(command)) {
                //触发拒绝策略
                reject();
            }
        }
    }

    private void reject() {
        throw new RuntimeException("线程池线程数为"+ctl+" 队列中任务数为"+workQueue.size());
    }

    //用于生成一个线程
    private boolean addWorker(Runnable firstTask) {
        int c = ctl.get();
        if (c >= maximumPoolSize) {
            return false;
        }
        Work work = new Work(firstTask);
        work.thread.start();
        ctl.incrementAndGet();
        return true;
    }

    //内部线程类
    private final class Work implements Runnable {
        //线程成员变量
        final Thread thread;
        //任务
        Runnable firstTask;

        private Work(Runnable firstTask) {
            this.thread = new Thread(this::run);
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            Runnable task = firstTask;
            //使用try+while确保线程执行任务然后ctl-1
            try {
                while (task != null||(task=getTask())!=null) {
                    task.run();
                    if (ctl.get() > maximumPoolSize) {
                        break;
                    }
                    task = null;
                }
            } finally {
                ctl.decrementAndGet();
            }
        }

        //不断的获取任务，如果为空则阻塞等待
        private Runnable getTask() {
            for (; ; ) {
                try {
                    System.out.println("workQueue.Size: " + workQueue.size());
                    return workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadPoolWrite threadPoolWrite = new ThreadPoolWrite(5, 10,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 30; i++) {
            final int taskId = i;
            threadPoolWrite.execute(() -> {
                try {
                    Thread.sleep((long) (Math.random()*1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务编号 "+taskId);
            });
        }
    }

}
