package org.example.seminarOne.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        Homework testing = new Homework();
        int[] array = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1000))
                .map(it -> it + 1)
                .map(it -> it * 5)
                .filter(it -> it > 10)
                .limit(100)
                .toArray();


        List<Homework.Department> departments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            departments.add(new Homework.Department("Department #" + i));
        }

        List<Homework.Person> persons = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Homework.Department department = departments.get(randomDepartmentIndex);

            Homework.Person person = new Homework.Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
            person.setDepartment(department);

            persons.add(person);
        }
        AtomicInteger counter = new AtomicInteger(1);
        persons.parallelStream().forEach(s->{
            System.out.print(s.toString());
            if (counter.get() %3==0) System.out.println();
            counter.getAndIncrement();
        });
        System.out.println(Homework.countPersons(persons,38,55000));
        System.out.println(Homework.averageSalary(persons, 7));
        Homework.groupByDepartment(persons).entrySet().stream()
                .forEach(System.out::println);
        System.out.println("**********************");
        System.out.println(Homework.maxSalaryByDepartment(persons));
        System.out.println(Homework.groupPersonNamesByDepartment(persons));
        System.out.println(Homework.minSalaryPersons(persons));
    }

}

