package com.example.patrycja.companyapp.company.employees;

import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.reports.Report;
import com.example.patrycja.companyapp.company.tasks.Task;

public class Developer extends AbstractEmployee implements Employee {


    public Developer(DeveloperBuilder builder) {
        super(builder);
    }

    @Override
    public void assign(Task task, Employee employee){
        getTaskList().add(task);
        setUnitsOfWork(task.getUnitsOfWork());
    }

    @Override
    public Report reportWork() {
        return new Report(this);
    }

    public static class DeveloperBuilder extends AbstractEmployee.Builder {

        public DeveloperBuilder(EmployeeRole role) {
            super(EmployeeType.DEVELOPER, role);
        }

        protected DeveloperBuilder self() {
            return this;
        }

        public Developer build() {
            return new Developer(this);
        }
    }
}

