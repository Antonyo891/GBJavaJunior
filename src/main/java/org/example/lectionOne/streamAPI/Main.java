package org.example.lectionOne.streamAPI;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Привет","мир","я","родился","!");
        list.stream()
                .filter(s->s.length()<=4)
                .forEach(System.out::println);
        Arrays.asList(1,2,3).stream()
                .filter(i->i<3)
                .forEach(System.out::println);
        Stream.of(5,5,1, 2, 3)
                .sorted()
                .forEach(i -> System.out.println(i + " "));
        System.out.println();
        IntStream.of(1,2,3,4)
                .average()
                .ifPresent(System.out::println);
        List<String> list1 = Arrays.asList("Не", "заменят", "край", "родимый",
                "Никакие", "чудеса!", "Только", "здесь", "всё", "так", "любимо",
                "Реки", "горы", "и", "леса.");
        list1.stream()
                .filter(s->s.length()>4)
                .forEach(s-> System.out.print(s+" "));
        System.out.println();
        list1.stream()
                .filter(s->s.length()>4)
                .filter(c->c.toLowerCase().contains("а"))
                .forEach(n-> System.out.print(n + " "));
        System.out.println();
        list1.stream()
                .skip(list1.size()/2)
                .filter(s->s.length()>4)
                .filter(c->c.toLowerCase().contains("а"))
                .forEach(n-> System.out.print(n + " "));
        System.out.println();
        list1.stream()
                .limit(list1.size()/2)
                .filter(s->s.length()>4)
                .filter(c->c.toLowerCase().contains("а"))
                .forEach(n-> System.out.print(n + " "));
        System.out.println();
        List<String> list2 = Arrays.asList("а", "б", "а", "в", "а", "г", "а", "д");
        list2.stream()
                .distinct()
                .forEach(n -> System.out.print(n + " "));
        list1.stream()
                .sorted((s,t1)-> t1.length()-s.length())
                .forEach(System.out::println);
        System.out.println(list1.stream()
                .map(String::length)
                .filter(i -> i > 5)
                .findAny());
        System.out.println(list1.stream()
                .filter(s->s.length()>5)
                .findAny());
        System.out.println(list1.stream()
                .filter(s->s.length()>5)
                        .limit(3)
                .collect(Collectors.toList()));
//        DateTime dateTime = DateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");
//        System.out.println(dateTime.toString(dateTimeFormatter));
//        DateTime dateTime1 = new DateTime();
//        dateTime1 = DateTime.parse("14-05-1989",dateTimeFormatter);
//        System.out.println(dateTime1.toString(dateTimeFormatter));
//        Period period = new Period(dateTime1.getMillis(),dateTime.getMillis());
//        System.out.println(period.getYears());
//        System.out.println(dateTime1);
        List<PersonID> personIDList= new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            personIDList.add(new PersonID("Surname"+i,"Name" + i,
                    "Patronymic" + i,+
                    random.nextInt(10,28) +"-"+
                    random.nextInt(10,13) + "-" +
                    random.nextInt(1975,2015),
                    random.nextInt(300,5000)));
            System.out.println(personIDList.get(i));
        }
        List<String> birthList = personIDList.stream()
                .filter(s->s.getBirthDate()
                .compareTo(DateTime
                        .parse("1995-01-01",DateTimeFormat
                                .forPattern("yyyy-MM-dd")))>0)
                .map(PersonID::getBirthDateInString)
                .toList();
        System.out.println(birthList);
        List<String> fullNameList = personIDList.stream()
                .filter(s->s.getBirthDate()
                        .compareTo(DateTime
                                .parse("1995-01-01",DateTimeFormat
                                        .forPattern("yyyy-MM-dd")))>0)
                .sorted((a,b)->a.getSalary()-b.getSalary())
                .map(PersonID::getFullName)
                .toList();
        System.out.println(fullNameList);
        List<String> fullNameList1 = personIDList.stream()
                .filter(s->s.getBirthDate()
                        .compareTo(DateTime
                                .parse("1995-01-01",DateTimeFormat
                                        .forPattern("yyyy-MM-dd")))>0)
                .sorted((a,b)->b.getSalary()-a.getSalary())
                .map(p->p.getFullName()+"( " + p.getBirthDateInString() +  " )" + p.getSalary())
                .limit(3)
                .toList();
        System.out.println(fullNameList1);
    }
}
