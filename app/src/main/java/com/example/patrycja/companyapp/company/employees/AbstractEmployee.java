package com.example.patrycja.companyapp.company.employees;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.Email;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.employees.details.FirstName;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.LastName;
import com.example.patrycja.companyapp.company.employees.details.University;
import com.example.patrycja.companyapp.company.tasks.TaskList;

public abstract class AbstractEmployee implements Employee {

    private final EmployeeType type;
    private final EmployeeRole role;
    private final FirstName firstName;
    private final LastName lastName;
    private final University university;
    private final Gender gender;
    private final Country country;
    private final Email email;
    private int unitsOfWork;
    private final TaskList taskList;

    public AbstractEmployee(Builder builder) {
        this.type = builder.type;
        this.role = builder.role;
        this.university = builder.university;
        this.country = builder.country;
        this.gender = builder.gender;
        unitsOfWork = 0;
        taskList = new TaskList();
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
    }

    @Override
    public EmployeeType getType() {
        return type;
    }

    @Override
    public EmployeeRole getRole() {
        return role;
    }

    @Override
    public int getUnitsOfWork() {
        return unitsOfWork;
    }

    protected void setUnitsOfWork(int i) {
        unitsOfWork += i;
    }

    @Override
    public TaskList getTaskList() { return taskList;  }

    @Override
    public FirstName getFirstName() { return firstName; }

    @Override
    public LastName getLastName() { return lastName; }

    @Override
    public University getUniversity() { return university; }

    @Override
    public Gender getGender() { return gender; }

    @Override
    public Country getCountry() { return country; }

    @Override
    public Email getEmail() { return email; }

    @Override
    public String toString() {
        return role.name() + " { "  + firstName +
                " " + lastName + ", " + gender + ", " + email +
                ", " + country + ", " + university + " }\n";
    }

    public static abstract class Builder<T extends Builder<T>> {

        private  EmployeeType type;
        private  EmployeeRole role;
        private  FirstName firstName;
        private  LastName lastName;
        private  University university;
        private  Gender gender;
        private  Country country;
        private  Email email;

        protected abstract T self();

        public Builder(EmployeeType type, EmployeeRole role) {
            this.role = role;
            this.type = type;
        }

        public T name(FirstName firstName, LastName lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            return self();
        }

        public T university(University university) {
            this.university = university;
            return self();
        }

        public T gender(Gender gender) {
            this.gender = gender;
            return self();
        }

        public T country(Country country) {
            this.country = country;
            return self();
        }

        public T email(Email email) {
            this.email = email;
            return self();
        }

        public abstract Employee build();
    }
}


