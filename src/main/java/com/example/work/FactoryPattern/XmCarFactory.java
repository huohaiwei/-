package com.example.work.FactoryPattern;

/**
 * 小米汽车工厂
 *
 * @author 陈翰垒
 */
public class XmCarFactory implements CarFactory {
    @Override
    public Car createCar() {
        System.out.println("小米苏7生产中！");
        return new XM7();
    }
}
