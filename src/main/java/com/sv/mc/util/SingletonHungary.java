package com.sv.mc.util;

import java.util.HashMap;
import java.util.Map;

public class SingletonHungary {
    private Map<Object,Object> map;

    private static SingletonHungary singletonHungary;

    private SingletonHungary(){
        map = new HashMap<Object,Object>();
    }

    public static SingletonHungary getSingleTon(){
        if(singletonHungary == null){
            singletonHungary = new SingletonHungary();
        }
        return singletonHungary;
    }

    public void put(Object key, Object value){
        map.put(key, value);
    }

    public Object get(Object key){
        return map.get(key);
    }

    public void remove(Object key){
        map.remove(key);
    }
}
