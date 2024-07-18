package com.example.work.FactoryPattern;

/**
 * 华为问界
 *
 * @author 陈翰垒
 */
public class HW7 implements Car {
    @Override
    public void run() {
        System.out.println("华为问界7正在启动");
    }

    @Override
    public void stop() {
        System.out.println("华为问界7停止了！");
    }
}
