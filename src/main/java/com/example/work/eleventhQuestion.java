package com.example.work;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 任务按批次执行，可以进行指定，剩下没有指定的任务就最后一个批次执行
 *
 * @author 陈翰垒
 */
public class eleventhQuestion {
    public void creatTasks() {
        List<RecursiveAction> tasks = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            tasks.add(new RecursiveAction() {
                @Override
                protected void compute() {
                    long startTime = System.currentTimeMillis();
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println( "任务"+ finalI +"耗时: " + (endTime - startTime) + "毫秒");
                }
            });
        }
    }
}
