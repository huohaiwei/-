package com.example.work.SingletonPattern;

/**
 * 饿汉单例模式实现
 *
 * @author 陈翰垒
 */
public class SingletonHungry {
    /**
     * 类加载时创建，这也是饿汉的原因
     */
    private static SingletonHungry singletonHungry = new SingletonHungry();

    /**
     * 1.构造器方法私有化
     */
    private SingletonHungry() {
    }

    /**
     * 2.对外提供一个公共的静态方法获取对象
     */
    public static SingletonHungry getSingletonInstance() {
        return singletonHungry;
    }
}
