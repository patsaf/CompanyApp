package com.example.patrycja.companyapp.company.tasks;

import java.util.Random;

public class TaskFactory {

    private final String[] developerTasks = {"Implement a new bug", "Write code review", "Process data", "Write reports",
            "Test a new bug", "Implement a series of tests"};
    private final Random r = new Random();
    private final String taskName;

    public TaskFactory() {
        taskName = developerTasks[r.nextInt(developerTasks.length)];
    }

    public String getTaskName() {
        return taskName;
    }
}
