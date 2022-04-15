package com.liang.regiee.common;


public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(){
        threadLocal.set(1L);
        threadLocal.set(2L);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
