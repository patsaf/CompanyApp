package com.example.patrycja.companyapp.company.tasks;

public class Task {

    private final String taskName;
    private final int unitsOfWork;

    public Task(String taskName, int unitsOfWork) {
        this.taskName = taskName;
        this.unitsOfWork = unitsOfWork;
    }

    public int getUnitsOfWork() { return unitsOfWork; }

    @Override
    public String toString() {
        return taskName + ", unitsOfWork = " + unitsOfWork;
    }
}

