package com.example.patrycja.companyapp.company.employees.details;

import java.util.Objects;

public class Country {

    private final String country;

    public Country(String country) { this.country = country; }

    public String toString() { return country; }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Country)){
            return false;
        }

        Country c = (Country) o;
        return Objects.equals(country, c.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country);
    }
}
