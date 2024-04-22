package org.example.seminarTwo.homework.random;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  1. Создать аннотацию RandomDate со следующими возможностями:
 *  1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
 *  1.2 Аннотация должна работать с полем типа java.util.Date.
 *  1.3 Должна генерить дату в диапазоне [min, max)
 *  1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
 *  Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
 */

public class RandomDateProcessor {
    public static void processRandomData(Object object) throws IllegalAccessException {
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            if (declaredField.getType().toString().toLowerCase().contains("date")){
                RandomDate annotation = declaredField.getAnnotation(RandomDate.class);
                if (annotation!=null) {
                    declaredField.setAccessible(true);
                    if (declaredField.get(object) == null){
                    long min = annotation.min();
                    long max = annotation.max();
                    try {
                        long randomTime = ThreadLocalRandom.current().nextLong(min, max);
                        declaredField.set(object, new Date(randomTime));
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getMessage());
                        ;
                    }
                }
                }
            }
        }

    }
}
