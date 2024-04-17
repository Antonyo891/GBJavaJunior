package org.example.seminarOne.homework;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Homework {

    /**
     * Используя классы Person и Department, реализовать методы ниже:
     */

    public static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    public static class Department {
        private String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    static int countPersons(List<Person> persons, int minAge, double minSalary) {
        return (int) persons.stream()
                .filter(s->s.getAge()>minAge)
                .filter(s -> s.getSalary()>minSalary)
                .count();
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    static OptionalDouble averageSalary(List<Person> persons, int x) {
        // TODO: Реализовать метод
        return persons.parallelStream()
                .filter(s-> s.getDepartment().getName().contains("#" + x))
                .mapToDouble(Person::getSalary)
                .average();
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors
                        .groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, s->s.getValue().stream()
                                .mapToDouble(Person::getSalary)
                                .max()
                                .getAsDouble())
                );
    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
        // TODO: Реализовать метод
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, s->s.getValue().stream()
                                .map(Person::getName)
                                .collect(Collectors.toList())
                ));
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    static List<Person> minSalaryPersons(List<Person> persons) {
        // TODO: Реализовать метод
        // В каждом департаменте ищем сотрудника с минимальной зарплатой.
        return
        persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, s->s.getValue().stream()
                        .min(Comparator.comparingDouble(Person::getSalary))))
                .values().stream()
        // Всех таких сотрудников собираем в список и возвращаем из метода.
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
