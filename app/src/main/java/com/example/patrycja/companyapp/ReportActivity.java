package com.example.patrycja.companyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrycja.companyapp.company.android.Display;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReportActivity extends AppCompatActivity {

    private final Context context = ReportActivity.this;
    private TeamManager ceo;
    private TextView displayReport;
    private TextView ceoDetails;
    private Button backButton;
    private final Display display = new Display();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);

        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);

        initialize();
        setupBack();
    }

    private void initialize() {
        ceoDetails = findViewById(R.id.ceo);
        ceoDetails.setText("Report gathered by: " + display.displayManagerBrief(ceo));
        displayReport = findViewById(R.id.report);
        displayReport.setText(ceo.reportWork().toString());
        backButton = findViewById(R.id.back_button);
    }

    private void setupBack() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                        .create();
                String json = gson.toJson(ceo);
                Intent intent = new Intent(context, CompanyMainActivity.class);
                intent.putExtra("ceoData", json);
                startActivity(intent);
            }
        });
    }
}
