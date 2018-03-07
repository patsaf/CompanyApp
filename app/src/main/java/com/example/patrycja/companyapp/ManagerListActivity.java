package com.example.patrycja.companyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.patrycja.companyapp.company.android.EmployeeAdapter;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


public class ManagerListActivity extends AppCompatActivity {

    private final Context context = ManagerListActivity.this;
    private TeamManager ceo;
    private ListView lv;
    private Button hideTeam;
    private ArrayList<Employee> employeeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);

        initialize();
        setupList();
        setupHideTeam();
    }

    private void initialize() {
        hideTeam = findViewById(R.id.hide_team);
        lv = findViewById(R.id.lv);
    }

    private void setupHideTeam() {
        hideTeam.setOnClickListener(view -> {
            Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                    .create();
            String json = gson.toJson(ceo);
            Intent intent = new Intent(context, CompanyMainActivity.class);
            intent.putExtra("ceoData", json);
            startActivity(intent);
        });
    }

    private void setupList() {
        employeeList = new ArrayList<>();
        for (int i = 0; i < ceo.getListSize(); i++) {
            employeeList.add(ceo.getListEmployee(i));
        }
        EmployeeAdapter adapter = new EmployeeAdapter(context, employeeList, ceo);
        lv.setAdapter(adapter);
    }
}
