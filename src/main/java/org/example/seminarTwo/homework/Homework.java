package org.example.seminarTwo.homework;

import org.example.seminarTwo.Buyer;
import org.example.seminarTwo.homework.random.RandomDate;
import org.example.seminarTwo.homework.random.RandomDateProcessor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

public class Homework {
    /**
     *  1. Создать аннотацию RandomDate со следующими возможностями:
     *  1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
     *  1.2 Аннотация должна работать с полем типа java.util.Date.
     *  1.3 Должна генерить дату в диапазоне [min, max)
     *  1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
     *
     *  Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
     */
    public static void main(String[] args) {
        Buyer buyer = new Buyer("Anton", new Date(611160507000L));
        Buyer buyer1 = new Buyer("Dmitry");
        Buyer buyer2 = new Buyer("Volodya", new Date(61116050700L));
        Buyer buyer3 = new Buyer("Serafim");
        System.out.println(buyer);
        System.out.println(buyer1);
        System.out.println(buyer2);
        System.out.println(buyer3);
        try {
            RandomDateProcessor.processRandomData(buyer);
            RandomDateProcessor.processRandomData(buyer1);
            RandomDateProcessor.processRandomData(buyer2);
            RandomDateProcessor.processRandomData(buyer3);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage()+"Main");;
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        System.out.println(buyer);
        System.out.println(buyer1);
        System.out.println(buyer2);
        System.out.println(buyer3);
//        try {
//            Field field = Buyer.class.getDeclaredField("birthDate");
//            System.out.println(field.getType() + " - getType");
//            field.setAccessible(true);
//            System.out.println(field.get(buyer1) + "- get(buyer1)");
//            System.out.println(field.getAnnotation(RandomDate.class));
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e){
//            System.out.println(e.getMessage());
//        }
    }
}
