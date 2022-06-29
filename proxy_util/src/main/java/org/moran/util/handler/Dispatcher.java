package org.moran.util.handler;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher {

    public  static ConcurrentHashMap<Class<?>,ConcurrentHashMap<String,Object>> classMap = new ConcurrentHashMap<>();
    public void register(Class<?> clazz,String name,Object obj){
        name = name != null ? name : obj.getClass().getName();
        ConcurrentHashMap<String, Object> nameMap = classMap.get(clazz);
        if (nameMap == null) {
            nameMap = new ConcurrentHashMap<>();
            nameMap.put(name,obj);
            classMap.put(clazz,nameMap);
        } else {
            nameMap.put(name,obj);
        }
    }
    public void register(Class<?> clazz,Object obj){
        this.register(clazz,null,obj);
    }
    public void register(String k,Object obj){
        Class<?> clazz = obj.getClass();
        this.register(clazz,k,obj);
    }
    public Object get(Class<?> k){
        ConcurrentHashMap<String, Object> nameMap = classMap.get(k);
        if (nameMap == null || nameMap.size() < 1) {
            throw new RuntimeException("未找到实现类组");
        } else if(nameMap.size() > 1) {
            throw new RuntimeException("请指定实现类名称");
        } else {
            return nameMap.values().stream().findFirst().orElse(null);
        }
    }
    public Object get(Class<?> clazz,String k){
        ConcurrentHashMap<String, Object> nameMap = classMap.get(clazz);
        if (nameMap != null && nameMap.size() > 0) {
            return nameMap.get(k);
        }
        throw new RuntimeException("未找到实现类");
    }

}