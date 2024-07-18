package com.example.work.FactoryPattern;

/**
 * 小米su7
 *
 * @author 陈翰垒
 */
public class XM7 implements Car {

    @Override
    public void run() {
        System.out.println("小米苏7正在启动");
    }

    @Override
    public void stop() {
        System.out.println("小米苏7停止了");
    }
}
