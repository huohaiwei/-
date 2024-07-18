package com.example.work.FactoryPattern;

/**
 * 车的抽象工厂
 * @author 陈翰垒
 */
public interface CarFactory {
    /**
     * 创建一辆汽车
     * @return
     */
    Car createCar();
}
