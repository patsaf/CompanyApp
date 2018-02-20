package com.example.patrycja.companyapp.company.predicates;

import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.University;

import java.util.function.Predicate;

public final class PredicateFactory {

    public static final Predicate<Employee> noCondition() {
        return employee -> true;
    }

    public static final Predicate<Employee> chooseUniversity(University university) {
        return employee -> employee.getUniversity().equals(university);
    }

    public static final Predicate<Employee> chooseGender(Gender gender) {
        return employee -> employee.getGender().equals(gender);
    }

    public static final Predicate<Employee> chooseCountry(Country country) {
        return employee -> employee.getCountry().equals(country);
    }

    public static final Predicate<Employee> chooseEmailDomain(String domain) {
        return employee -> employee.getEmail()
                .toString()
                .endsWith(domain);
    }
}
