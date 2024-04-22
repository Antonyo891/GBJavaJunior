package org.example.seminarTwo.annotRandom;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class RandomIntegerProcessor {
    public static void processRandomInteger(Object object){
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            RandomInteger annotation = declaredField.getAnnotation(RandomInteger.class);
            if(annotation!=null){
                int min = annotation.min();
                int max = annotation.max();
                declaredField.setAccessible(true);
                try {
                    declaredField.set(object, ThreadLocalRandom.current().nextInt(min,max));
                } catch (IllegalAccessException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
