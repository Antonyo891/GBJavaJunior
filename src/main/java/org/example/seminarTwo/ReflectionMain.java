package org.example.seminarTwo;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class ReflectionMain {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
    Buyer buyer = new Buyer("Senior",22);
        System.out.println(buyer);
        Class<? extends Buyer> personsClass = Buyer.class;//
        System.out.println(personsClass);
        System.out.println(buyer.getClass());

        Constructor<?>[] constructors = personsClass.getDeclaredConstructors();
        System.out.println(Arrays.toString(constructors));
        Constructor<?> constructorBuyer2 = (Arrays.stream(constructors)
                .filter(s-> (s.getParameterCount() == Arrays.stream(constructors)
                            .mapToInt(Constructor::getParameterCount)
                            .min()
                        .orElse(0)))
                .toList()
                .get(0));
        Object buyer2 = constructorBuyer2.newInstance("Anton",34);
        System.out.println(buyer2.toString() + buyer2.hashCode());
        ((Buyer) buyer2).setName("Antoshka");
        System.out.println(buyer2.toString() + buyer2.hashCode());
//        Constructor constructor = Arrays.stream(constructors)
//                .collect(Collectors.toMap(s->s,s->s.getParameterCount()));
//        for (Constructor<?> constructor : constructors) {
//            System.out.println(constructor);
//            for (Parameter parameter : constructor.getParameters()) {
//                System.out.println(parameter.getType());
//            }
//        }
        System.out.println("****************");
//        System.out.println(Arrays.toString(ReflectionMain.class.getNestMembers()));
        Method [] methods = Buyer.class.getDeclaredMethods();
//        System.out.println(Arrays.toString(methods));
        List<String> methodsList = Arrays.stream(methods)
                .filter(s->s.toString().contains("setName"))
                .map(Method::getName)
                .toList();
//        System.out.println(methodsList);
        Method method = Arrays.stream(methods)
                .filter(s->s.toString().contains("setName"))
                .toList()
                .get(0);
        method.invoke(buyer2,"Antonyo");
        System.out.println(buyer2.toString() + buyer2.hashCode());
        Method privatMethod = Arrays.stream(Buyer.class.getDeclaredMethods())
                .filter(s->s.toString().contains("setGender"))
                .toList()
                .get(0);
        privatMethod.setAccessible(true);
        privatMethod.invoke(buyer2,Gender.MAN);
        System.out.println(buyer2.toString() + buyer2.hashCode());
        System.out.println("Field");
        Field[] fields = Buyer.class.getDeclaredFields();
        Field field = Arrays.stream(fields)
                        .filter(s->s.toString().contains("phone"))
                                .toList()
                                        .get(0);
        field.setAccessible(true);
        field.set(buyer2,"+375297446847");
        System.out.println(buyer2);
    }
}
