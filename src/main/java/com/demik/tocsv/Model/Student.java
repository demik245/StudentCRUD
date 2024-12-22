package com.demik.tocsv.Model;

import com.demik.tocsv.validation.EmailSufix;
import com.demik.tocsv.validation.FirstNameInJson;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @FirstNameInJson(message = "KŁAMCA")
    private String firstName;

    @Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters")
    private String lastName;

    @EmailSufix
    @Size(min = 2, max = 30, message = "Email must be between 2 and 30 characters")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 14, message = "Age must be greater than 14")
    @Max(value = 19, message = "Age must be less than 19")
    private Integer age;

    @Pattern(
            regexp = "^\\+48\\s\\d{3}([-\\s]?\\d{3}){2}$",
            message = "Phone number must be in format +48 XXX-XXX-XXX or similar"
    )
    private String phone;

    public String generateEmail() {
        // Get the first 2 letters of the first name in lowercase
        String firstPart = firstName.substring(0, 2).toLowerCase();

        // Get the last name in lowercase
        String secondPart = lastName.toLowerCase();

        // Calculate the email age-based code
        int code;
        switch (age) {
            case 14 -> code = 24;
            case 15 -> code = 23;
            case 16 -> code = 22;
            case 17 -> code = 21;
            case 18 -> code = 20;
            default -> throw new IllegalArgumentException("Age must be between 14 and 18");
        }

        // Combine parts to form the email
        email = firstPart + '.' + secondPart + code + "@loropczyce.pl";
        return email;
    }

    public Student(Integer id, String firstName, String lastName, String email, Integer age, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.phone = phone;
    }

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public @Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@Size(min = 2, max = 30, message = "Last Name must be between 2 and 30 characters") String lastName) {
        this.lastName = lastName;
    }

    public @Size(min = 2, max = 30, message = "Email must be between 2 and 30 characters") String getEmail() {
        return email;
    }

    public void setEmail(@Size(min = 2, max = 30, message = "Email must be between 2 and 30 characters") String email) {
        this.email = email;
    }

    public @NotNull(message = "Age is required") @Min(value = 14, message = "Age must be greater than 14") @Max(value = 19, message = "Age must be less than 19") Integer getAge() {
        return age;
    }

    public void setAge(@NotNull(message = "Age is required") @Min(value = 14, message = "Age must be greater than 14") @Max(value = 19, message = "Age must be less than 19") Integer age) {
        this.age = age;
    }

    public @Pattern(
            regexp = "^\\+48\\s\\d{3}([-\\s]?\\d{3}){2}$",
            message = "Phone number must be in format +48 XXX-XXX-XXX or similar"
    ) String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(
            regexp = "^\\+48\\s\\d{3}([-\\s]?\\d{3}){2}$",
            message = "Phone number must be in format +48 XXX-XXX-XXX or similar"
    ) String phone) {
        this.phone = phone;
    }
}