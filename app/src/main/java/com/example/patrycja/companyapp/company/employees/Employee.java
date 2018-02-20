package com.example.patrycja.companyapp.company.employees;

import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.Email;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.employees.details.FirstName;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.LastName;
import com.example.patrycja.companyapp.company.employees.details.University;
import com.example.patrycja.companyapp.company.reports.Report;
import com.example.patrycja.companyapp.company.tasks.Task;
import com.example.patrycja.companyapp.company.tasks.TaskList;

public interface Employee {

    EmployeeType getType();

    EmployeeRole getRole();

    int getUnitsOfWork();

    FirstName getFirstName();

    LastName getLastName();

    Email getEmail();

    Gender getGender();

    Country getCountry();

    University getUniversity();

    TaskList getTaskList();

    void assign(Task task, Employee employee);

    Report reportWork();
}
