package com.example.work.FactoryPattern;

/**
 * 华为汽车工厂
 *
 * @author 陈翰垒
 */
public class HwCarFactory implements CarFactory {
    @Override
    public Car createCar() {
        System.out.println("华为问界7生产中！");
        return new HW7();
    }
}
