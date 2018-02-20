package com.example.patrycja.companyapp.company.managers;

import com.example.patrycja.companyapp.company.employees.Employee;

public interface Manager extends Employee {

    void hire(Employee employee);

    void fire(Employee employee);

    boolean canHire(Employee employee);

}


