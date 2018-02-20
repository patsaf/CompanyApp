package com.example.patrycja.companyapp.company.employees.details;

import java.util.regex.Pattern;

public class LastName {
    private final String lastName;

    public LastName(String lastName) {
        if (Pattern.matches("[A-Z][a-z]+", lastName)) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String toString() { return lastName; }
}

