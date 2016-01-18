package com.lamfire.paladin;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.Maps;

import java.io.IOException;
import java.util.Map;
import java.util.Set;


public class NSRegistry {
    private static final Logger LOGGER = Logger.getLogger(NSRegistry.class);
    private Map<String,NSHandler> nsMap = Maps.newHashMap();

    public void mapping(Class<? extends NSHandler> nsClass){
        NS actionAnno = nsClass.getAnnotation(NS.class);
        if(actionAnno == null){
            LOGGER.warn("["+nsClass.getName() + "] is assignable from NSHandler,but not found 'NS' annotation.");
            return;
        }
        String name = actionAnno.name();
        try{
            NSHandler handler = nsClass.newInstance();
            nsMap.put(name,handler);
            LOGGER.info("NS["+name+"] : " + nsClass.getName());
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    public void mappingPackage(String packageName) throws Exception{
        Set<Class<?>> set = ClassLoaderUtils.getClasses(packageName);
        for(Class<?> clzz : set){
            if(!NSHandler.class.isAssignableFrom(clzz)){
                continue;
            }
            Class<? extends NSHandler> actionClass =  (Class<? extends NSHandler>)clzz;
            mapping(actionClass);
        }
    }

    public NSHandler lookup(String ns) {
        return nsMap.get(ns);
    }

    public boolean isEmpty(){
        return nsMap.isEmpty();
    }
}
