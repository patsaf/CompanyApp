package com.example.patrycja.companyapp.company.employees.details;

import java.util.Objects;

public class University {

    private final String university;

    public University(String university) { this.university = university; }

    public String toString() { return university; }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof University)){
            return false;
        }

        University u = (University) o;
        return Objects.equals(university, u.university);
    }

    @Override
    public int hashCode() {
        return Objects.hash(university);
    }
}

