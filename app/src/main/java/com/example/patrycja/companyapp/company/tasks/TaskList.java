package com.example.patrycja.companyapp.company.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {

    private final List<Task> listOfTasks;

    public TaskList() {
        listOfTasks = new ArrayList<>();
    }

    public void add(Task task) {
        listOfTasks.add(task);
    }

    public String toString() {
        String output = "All tasks completed: \n";
        for(Task t: listOfTasks) {
            output += "\t *" + t + "\n";
        }
        return output;
    }
}

