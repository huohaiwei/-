package com.example.work.SingletonPattern;

/**
 * 单例模式懒汉实现
 *
 * @author 陈翰垒
 */
public class SingletonLazy {
    /**
     * 成员变量
     */
    private volatile static SingletonLazy singletonLazy;

    /**
     * 1.私有化构造器
     */
    private SingletonLazy() {
    }

    /**
     * 2.对外暴露一个方法获取单例对象
     */
    public SingletonLazy getSingletonLazy() {
        //提升效率，如果全部都用同步代码块降低效率
        if (singletonLazy == null) {
            synchronized (SingletonLazy.class) {
                //如果没有这个判空，则会生成多个实例
                if (singletonLazy == null) {
                    singletonLazy = new SingletonLazy();
                }
            }
        }
        return singletonLazy;
    }
}
