package com.example.patrycja.companyapp.company.employees.details;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private final String email;

    public Email(String email) {
        if (Pattern.matches("[\\.a-z0-9_-]+@[a-z]+\\.[a-z]{2,3}", email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String toString() { return email; }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Email)){
            return false;
        }

        Email e = (Email) o;
        return Objects.equals(email, e.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
