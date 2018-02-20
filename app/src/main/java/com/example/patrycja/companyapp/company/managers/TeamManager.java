package com.example.patrycja.companyapp.company.managers;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.patrycja.companyapp.company.employees.AbstractEmployee;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.EmployeeList;
import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.University;
import com.example.patrycja.companyapp.company.predicates.PredicateFactory;
import com.example.patrycja.companyapp.company.predicates.PredicateInfo;
import com.example.patrycja.companyapp.company.predicates.Predicates;
import com.example.patrycja.companyapp.company.reports.Report;
import com.example.patrycja.companyapp.company.reports.ReportList;
import com.example.patrycja.companyapp.company.tasks.Task;

import java.util.List;
import java.util.function.Predicate;

public class TeamManager extends AbstractEmployee implements Manager {

    private final EmployeeList list;
    private final int capacity;
    private final PredicateInfo conditionInfo;

    public TeamManager(ManagerBuilder builder) {
        super(builder);
        list = new EmployeeList();
        this.capacity = builder.capacity;
        this.conditionInfo = builder.conditionInfo;
    }

    public int getListSize() { return list.getSize(); }

    public Employee getListEmployee(int i) { return list.getEmployee(i);   }

    public int getEmployeeIndex(Employee employee) { return list.getIndex(employee); }

    @Override
    public void assign(Task task, Employee employee) {
        list.sort();
        if(employee.getType() == EmployeeType.MANAGER) {
            //TeamManager manager = (TeamManager) list.getEmployee(0);
            TeamManager manager = (TeamManager) employee;
            manager.getTaskList().add(task);
            manager.setUnitsOfWork(task.getUnitsOfWork());
        } else {
            //Employee employee =  list.getEmployee(0);
            employee.assign(task, null);
        }
    }

    @Override
    public void hire(Employee employee) {
        if(canHire(employee)) {
            list.addEmployee(employee);
        }
    }

    @Override
    public void fire(Employee employee) {
        list.removeEmployee(employee);
    }

    @Override
    public boolean canHire(Employee employee) {
        return (list.getSize() < capacity) && (makePredicate().test(employee));
    }

    public int getCapacity() { return capacity; }

    public PredicateInfo getConditionInfo() { return conditionInfo; }

    @Override
    public Report reportWork() {
        TeamManager manager;
        Employee employee;
        ReportList reportList = new ReportList();
        if(getRole() == EmployeeRole.CEO) {
            for(int i=0; i<list.getSize(); i++) {
                manager = (TeamManager) list.getEmployee(i);
                reportList.add(new Report(manager));
                for(int j=0; j<manager.getListSize();j++) {
                    employee = manager.getListEmployee(j);
                    reportList.add(new Report(employee));
                }
            }
            return new Report(this, reportList);
        }
        return new Report(this);
    }

    public Predicate<Employee> makePredicate(){
        switch(conditionInfo.getCondition()) {

            case EMPTY:
                return PredicateFactory.noCondition();
            case GENDER:
                Gender g = null;
                for(Gender x : Gender.values()) {
                    if(x.name().equalsIgnoreCase(conditionInfo.getConditionDetails())) {
                        g = x;
                    }
                }
                return PredicateFactory.chooseGender(g);
            case COUNTRY:
                return PredicateFactory.chooseCountry(new Country(conditionInfo.getConditionDetails()));
            case UNIVERSITY:
                return PredicateFactory.chooseUniversity(new University(conditionInfo.getConditionDetails()));
            case EMAIL:
                return PredicateFactory.chooseEmailDomain(conditionInfo.getConditionDetails());
            default:
                return PredicateFactory.noCondition();
        }
    }

    @Override
    public String toString() {
        String output = "";
        output += super.toString();
        output += conditionInfo;
        output += list;
        return output;
    }

    public static class ManagerBuilder extends AbstractEmployee.Builder<ManagerBuilder> {

        private int capacity;
        private PredicateInfo conditionInfo;

        public ManagerBuilder(EmployeeRole role) {
            super(EmployeeType.MANAGER, role);
        }

        public ManagerBuilder self() {
            return this;
        }

        public ManagerBuilder capacity(int capacity) {
            this.capacity = capacity;
            return self();
        }

        public ManagerBuilder conditionInfo(PredicateInfo conditionInfo) {
            this.conditionInfo = conditionInfo;
            return self();
        }

        public TeamManager build() {
            return new TeamManager(this);
        }
    }
}

