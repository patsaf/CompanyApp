package com.example.patrycja.companyapp.company.employees.details;

import java.util.regex.Pattern;

public class FirstName {
    private final String firstName;

    public FirstName(String firstName) {
        if (Pattern.matches("[A-Z][a-z]+", firstName)) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String toString() { return firstName; }
}

