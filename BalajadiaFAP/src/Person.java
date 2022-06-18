// Edriech G. Balajadia
// 1CSB
// FAP

import java.time.*;
import java.time.format.*;

public class Person {

    private String name;
    private LocalDate birthDay;
    private int age;

    public Person(String name, LocalDate birthDay) {
        this.name = name;
        this.birthDay = birthDay;
        computeAge(birthDay);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public int getAge() {
        return age;
    }

    // Computes age based on birthday and today's date
    public void computeAge(LocalDate birthDay) {
        Period prdBtwnBdyTdy = Period.between(birthDay, LocalDate.now());
        age = prdBtwnBdyTdy.getYears();
    }

    // Formats the birthday to the given String format pattern
    public String bdayFormat(LocalDate ld, String format) {
        DateTimeFormatter givenDateFormat = DateTimeFormatter.ofPattern(format);
        return givenDateFormat.format(ld);
    }

    // Overrides default toString method to show a person's name, computed age, and
    // formatted bday
    @Override
    public String toString() {
        return name + "\t" + bdayFormat(birthDay, "MM/dd/uuuu") + "\t" + age;
    }

}