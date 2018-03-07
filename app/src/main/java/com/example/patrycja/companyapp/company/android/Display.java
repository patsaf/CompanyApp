package com.example.patrycja.companyapp.company.android;

import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.managers.TeamManager;

public class Display {

    public Display() {
    }

    public String displayBrief(Employee manager) {
        return manager.getRole().name() + " " + manager.getFirstName() + " " + manager.getLastName() + "\n";
    }

    public String displayAllInfo(Employee employee) {
        if (employee.getType().equals(EmployeeType.MANAGER)) {
            TeamManager manager = (TeamManager) employee;
            return "Email: " + manager.getEmail() + "\n" + "Gender: " + manager.getGender().name().toLowerCase() + "\n"
                    + "University: " + manager.getUniversity() + "\n" + "Country: " + manager.getCountry() + "\n\n" +
                    "Can hire up to " + manager.getCapacity() +
                    (manager.getRole().equals(EmployeeRole.CEO) ? " managers" : " developers")
                    + "\n" + manager.getConditionInfo();
        } else {
            return "Email: " + employee.getEmail() + "\n" + "Gender: " + employee.getGender().name().toLowerCase() + "\n"
                    + "University: " + employee.getUniversity() + "\n" + "Country: " + employee.getCountry() + "\n";
        }
    }
}
