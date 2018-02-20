package com.example.patrycja.companyapp.company.reports;

import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.FirstName;
import com.example.patrycja.companyapp.company.employees.details.LastName;
import com.example.patrycja.companyapp.company.tasks.TaskList;
import com.google.common.collect.ComparisonChain;

public class Report implements Comparable<Report> {

    private final TaskList taskList;
    private final FirstName firstName;
    private final LastName lastName;
    private final EmployeeRole role;
    private final int unitsOfWork;
    private final ReportList reportList;

    public Report(Employee employee) {

        this.taskList = employee.getTaskList();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.role = employee.getRole();
        this.unitsOfWork = employee.getUnitsOfWork();
        reportList = new ReportList();
    }

    public Report(Employee employee, ReportList reportList) {
        this.taskList = employee.getTaskList();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.role = employee.getRole();
        this.unitsOfWork = employee.getUnitsOfWork();
        this.reportList = reportList;
    }

    public FirstName getFirstName() { return firstName; }

    public LastName getLastName() { return lastName; }

    public EmployeeRole getRole() { return role; }

    public int getUnitsOfWork() { return unitsOfWork; }

    @Override
    public String toString() {
        if(role == EmployeeRole.CEO) {
            return reportList.toString();
        } else {
            return firstName + " " + lastName + ", " + role.name() +
                    "\nAll work completed: " + unitsOfWork + " units\n" +
                    taskList.toString();
        }
    }

    @Override
    public int compareTo(Report r1) {
        return ComparisonChain.start()
                .compare(lastName.toString(), r1.getLastName().toString())
                .compare(firstName.toString(), r1.getFirstName().toString())
                .compare(getUnitsOfWork(), r1.getUnitsOfWork())
                .result();
    }
}

