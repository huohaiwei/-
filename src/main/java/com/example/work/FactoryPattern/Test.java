package com.example.work.FactoryPattern;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 陈翰垒
 */
public class Test {
    public static void main(String[] args) {
        HwCarFactory hwCarFactory = new HwCarFactory();
        Car car = hwCarFactory.createCar();
        car.run();
        car.stop();
        XmCarFactory xmCarFactory = new XmCarFactory();
        Car car1 = xmCarFactory.createCar();
        car1.run();
        car1.stop();
    }
}
