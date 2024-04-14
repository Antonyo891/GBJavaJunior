package org.example.lectionOne.streamAPI;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PersonID {
    private static Integer countId = 0;
    private final Integer ID;
    private final String SURNAME;
    private final String NAME;
    private final String PATRONYMIC;
    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("dd-MM-yyyy");
    private final DateTime birthDate;
    private Integer salary;
    private Integer age;

    /*
    birthDate in format dd-MM-yyyy
    for example 14-05-1989
     */
    public PersonID(String SURNAME, String NAME, String PATRONYMIC,
                    String birthDate, Integer salary) {
        this.ID = countId++;
        this.SURNAME = SURNAME;
        this.NAME = NAME;
        this.PATRONYMIC = PATRONYMIC;
        this.birthDate = DateTime.parse(birthDate,DATE_TIME_FORMATTER);
        this.age = new Period(this.birthDate
                .getMillis(),DateTime.now().getMillis()).getYears();
        this.salary = salary;
    }

    public Integer getID() {
        return ID;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPATRONYMIC() {
        return PATRONYMIC;
    }

    public DateTimeFormatter getDATE_TIME_FORMATTER() {
        return DATE_TIME_FORMATTER;
    }

    public String getBirthDateInString() {
        return birthDate.toString(DATE_TIME_FORMATTER);
    }
    public DateTime getBirthDate() {
        return birthDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getAge() {
        return age;
    }
    public String getFullName(){
        return getSURNAME()+" " +
                getNAME() + " " +
                getPATRONYMIC();
    }

    @Override
    public String toString() {
        return "PersonID{" +
                "ID=" + ID +
                ", SURNAME='" + SURNAME + '\'' +
                ", NAME='" + NAME + '\'' +
                ", PATRONYMIC='" + PATRONYMIC + '\'' +
                ", birthDate=" + getBirthDateInString() +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
