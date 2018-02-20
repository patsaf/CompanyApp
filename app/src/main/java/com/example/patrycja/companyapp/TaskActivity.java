package com.example.patrycja.companyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrycja.companyapp.company.android.Display;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.example.patrycja.companyapp.company.tasks.Task;
import com.example.patrycja.companyapp.company.tasks.TaskFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Random;

public class TaskActivity extends AppCompatActivity {

    private final Context context = TaskActivity.this;
    private TeamManager ceo;
    private TeamManager manager;
    private int index;
    private TextView title;
    private TextView taskTitle;
    private TextView taskNameLabel;
    private TextView unitsLabel;
    private Spinner chooseEmployee;
    private EditText taskName;
    private EditText units;
    private Button assignButton;
    private Button generateRandom;
    private Button doneButton;
    private Button cancelButton;
    private final String select = "[select employee]";
    private final Display display = new Display();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        index = getIntent().getExtras().getInt("managerIndex");
        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);
        if(index != -1) {
            manager = (TeamManager) ceo.getListEmployee(index);
        }

        initialize();
        if (index != -1) {
            title.setText("Tasks assigned by: " + display.displayManagerBrief(manager));
        } else {
            title.setText("Tasks assigned by: " + display.displayManagerBrief(ceo));
        }

        setupGenerateRandom();
        setSpinnerAdapter();
        setupSpinner();
        setupBack(doneButton);
        setupBack(cancelButton);
    }

    private  void initialize() {
        title = findViewById(R.id.title);
        taskTitle = findViewById(R.id.task_form_title);
        taskNameLabel = findViewById(R.id.task_name);
        unitsLabel = findViewById(R.id.units);
        chooseEmployee = findViewById(R.id.choose_employee);
        taskName = findViewById(R.id.edit_task_name);
        units = findViewById(R.id.edit_units);
        generateRandom = findViewById(R.id.generate_random);
        assignButton = findViewById(R.id.assign);
        doneButton = findViewById(R.id.done);
        cancelButton = findViewById(R.id.cancel);
    }

    private void setupGenerateRandom() {
        generateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskFactory taskItem = new TaskFactory();
                Random r = new Random();
                taskName.setText(taskItem.getTaskName());
                units.setText(Integer.toString(r.nextInt(20)+1));
            }
        });
    }

    private void setupSpinner() {
        chooseEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!(adapterView.getItemAtPosition(i).equals(select))) {
                    setFormVisible();
                    assignButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Task task;
                            setTextBlack();
                            if(checkForm()) {
                                try {
                                    task = new Task(taskName.getText().toString(), Integer.parseInt(units.getText().toString()));
                                    if(index != -1) {
                                        Employee employee = manager.getListEmployee(i-1);
                                        manager.assign(task, employee);
                                    } else {
                                        Employee employee = ceo.getListEmployee(i-1);
                                        ceo.assign(task, employee);
                                    }
                                    adapterView.setSelection(0);
                                    setupForm();
                                    Toast.makeText(context, "Assigned successfully!", Toast.LENGTH_SHORT).show();
                                } catch(IllegalArgumentException e) {
                                    Toast.makeText(context, "Invalid data!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                } else {
                    setFormInvisible();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void setupBack(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index != -1) {
                    startMyActivity(CompanyManagerActivity.class);
                } else {
                    startMyActivity(CompanyMainActivity.class);
                }
            }
        });
    }

    private void startMyActivity(Class<?> activity) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        String json = gson.toJson(ceo);
        Intent intent = new Intent(context, activity);
        intent.putExtra("ceoData", json);
        intent.putExtra("managerIndex", index);
        startActivity(intent);
    }

    private void setFormVisible() {
        taskTitle.setVisibility(View.VISIBLE);
        taskNameLabel.setVisibility(View.VISIBLE);
        taskName.setVisibility(View.VISIBLE);
        unitsLabel.setVisibility(View.VISIBLE);
        units.setVisibility(View.VISIBLE);
        assignButton.setVisibility(View.VISIBLE);
        generateRandom.setVisibility(View.VISIBLE);
    }

    private void setFormInvisible() {
        taskTitle.setVisibility(View.INVISIBLE);
        taskNameLabel.setVisibility(View.INVISIBLE);
        taskName.setVisibility(View.INVISIBLE);
        unitsLabel.setVisibility(View.INVISIBLE);
        units.setVisibility(View.INVISIBLE);
        assignButton.setVisibility(View.INVISIBLE);
        generateRandom.setVisibility(View.INVISIBLE);
    }

    private void setSpinnerAdapter() {
        ArrayList<String> employees = new ArrayList<>();
        Employee employee;
        employees.add(select);
        if(index != -1) {
            for (int i = 0; i < manager.getListSize(); i++) {
                employee = manager.getListEmployee(i);
                employees.add(employee.getFirstName() + " " + employee.getLastName());
            }
        } else {
            for (int i = 0; i < ceo.getListSize(); i++) {
                employee = ceo.getListEmployee(i);
                employees.add(employee.getFirstName() + " " + employee.getLastName());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, employees);
        chooseEmployee.setAdapter(spinnerAdapter);
    }

    private boolean isEmpty(TextView textView) {
        return textView.getText()
                .toString()
                .equals("");
    }

    private void setTextBlack() {
        taskNameLabel.setTextColor(Color.BLACK);
        unitsLabel.setTextColor(Color.BLACK);
    }

    private boolean checkForm() {
        boolean canAssign = true;
        if(isEmpty(taskName)) {
            taskNameLabel.setTextColor(Color.RED);
            canAssign = false;
        }
        if(isEmpty(units)) {
            unitsLabel.setTextColor(Color.RED);
            canAssign = false;
        }
        return canAssign;
    }

    private void setupForm() {
        taskName.setText("");
        units.setText("");
    }
}
