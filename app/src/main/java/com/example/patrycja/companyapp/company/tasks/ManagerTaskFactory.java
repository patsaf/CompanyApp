package com.example.patrycja.companyapp.company.tasks;

import java.util.Random;

public class ManagerTaskFactory {

    private final String[] developerTasks = {"Supervise work", "Distribute work", "Gather reports", "Write reports"};
    private final Random r = new Random();
    private final String taskName;

    public ManagerTaskFactory() {
        taskName = developerTasks[r.nextInt(developerTasks.length)];
    }

    public String getTaskName() {
        return taskName;
    }
}
