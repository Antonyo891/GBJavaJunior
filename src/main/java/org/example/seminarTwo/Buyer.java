package org.example.seminarTwo;

import org.example.seminarTwo.annotRandom.RandomInteger;
import org.example.seminarTwo.homework.random.RandomDate;

import java.util.Date;
import java.util.Objects;

public class Buyer {
    private static int count = 1;
    private static final String EMPTY_PHONE_NUMBER = "-";
    private String name;
    @RandomInteger(min = 16,max = 80)
    private int age;

    private Gender gender;
    @RandomDate
    private Date birthDate;

    private int id;
    private String phone;

    public Buyer(int id, String name, int age, String phone,Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
    }
    public Buyer(String name, int age) {
        this(count++,name,age, EMPTY_PHONE_NUMBER,Gender.UNSPECIFIED);
    }

    public Buyer(String name, Date birthDate) {
        this(name,0);
        this.birthDate = birthDate;
    }

    public Buyer(String name) {
        this(name,0);
    }

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

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    private void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return age == buyer.age && Objects.equals(name, buyer.name) && Objects.equals(phone, buyer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, phone);
    }
}
