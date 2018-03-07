package com.example.patrycja.companyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.patrycja.companyapp.company.android.DeveloperAdapter;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class DeveloperListActivity extends AppCompatActivity {

    private final Context context = DeveloperListActivity.this;
    private TeamManager ceo;
    private TeamManager manager;
    private int index;
    private ListView lv;
    private Button hideTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        index = getIntent().getExtras().getInt("managerIndex");
        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);
        manager = (TeamManager) ceo.getListEmployee(index);

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
            Intent intent = new Intent(context, CompanyManagerActivity.class);
            intent.putExtra("ceoData", json);
            intent.putExtra("managerIndex", index);
            startActivity(intent);
        });
    }

    private void setupList() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        for (int i = 0; i < manager.getListSize(); i++) {
            employeeList.add(manager.getListEmployee(i));
        }
        DeveloperAdapter adapter = new DeveloperAdapter(context, employeeList);
        lv.setAdapter(adapter);
    }

}
