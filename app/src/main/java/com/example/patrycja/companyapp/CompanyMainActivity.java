package com.example.patrycja.companyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrycja.companyapp.company.android.Display;
import com.example.patrycja.companyapp.company.android.EmployeeAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CompanyMainActivity extends AppCompatActivity{

    private final Context context = CompanyMainActivity.this;
    private final Activity activity = this;
    private TextView displayCeo;
    private TextView displayCeoDetails;
    private TeamManager ceo;
    private Spinner fireSpinner;
    private LinearLayout linearButtons;
    private Button showTeam;
    private Button hideTeam;
    private Button hire;
    private Button cancel;
    private Button fire;
    private Button assign;
    private Button report;
    private RelativeLayout relativeSpinner;
    private final Display display = new Display();
    private final String select = "[select employees]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_main_activity);

        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);

        initialize();
        setupShowTeam();
        setupHire();
        setupFire();
        setupAssign();
        setupReport();
    }

    private void initialize() {
        displayCeo = findViewById(R.id.ceo);
        displayCeo.setText(display.displayManagerBrief(ceo));
        displayCeoDetails = findViewById(R.id.ceo_details);
        displayCeoDetails.setText(display.displayManager(ceo));
        fireSpinner = findViewById(R.id.fire_spinner);
        linearButtons = findViewById(R.id.linear_buttons);
        showTeam = findViewById(R.id.show_team);
        hideTeam = findViewById(R.id.hide_team);
        hire = findViewById(R.id.hireButton);
        cancel = findViewById(R.id.cancel);
        fire = findViewById(R.id.fireButton);
        assign = findViewById(R.id.assignButton);
        report = findViewById(R.id.reportButton);
        relativeSpinner = findViewById(R.id.relativeSpinner);
        relativeSpinner.setVisibility(View.INVISIBLE);
    }

    private void setupShowTeam() {
        showTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ceo.getListSize()!=0) {
                    startMyActivity(ManagerListActivity.class);
                } else {
                    Toast.makeText(context, "Your team is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupHire() {
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ceo.getListSize() < ceo.getCapacity()) {
                    startMyActivity(HireManagerActivity.class);
                } else {
                    Toast.makeText(context, "Your team is full!", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    private void setupFire() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeSpinner.setVisibility(View.INVISIBLE);
                fireSpinner.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
                showTeam.setVisibility(View.VISIBLE);
            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ceo.getListSize()!= 0) {
                    relativeSpinner.setVisibility(View.VISIBLE);
                    fireSpinner.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    showTeam.setVisibility(View.INVISIBLE);

                    setSpinnerAdapter();
                    fireSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (!(adapterView.getItemAtPosition(i).equals(select))) {
                                Employee employee = ceo.getListEmployee(i-1);
                                String message = employee.getFirstName().toString() + " " + employee.getLastName().toString();
                                FirePopup firePopup = new FirePopup();
                                firePopup.display(activity, confirm_proc(employee), message);
                                adapterView.setSelection(0);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                } else {
                    Toast.makeText(context, "Your team is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupAssign() {
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ceo.getListSize()!=0) {
                    startMyActivity(TaskActivity.class);
                } else {
                    Toast.makeText(context, "Your team is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupReport() {
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ceo.getListSize()!=0) {
                    startMyActivity(ReportActivity.class);
                } else {
                    Toast.makeText(context, "Your team is empty!", Toast.LENGTH_SHORT).show();
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
        intent.putExtra("managerIndex", -1);
        startActivity(intent);
    }

    private void setSpinnerAdapter() {
        ArrayList<String> employees = new ArrayList<>();
        Employee employee;
        employees.add(select);
        for(int i=0; i<ceo.getListSize(); i++) {
            employee = ceo.getListEmployee(i);
            employees.add(employee.getFirstName() + " " + employee.getLastName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, employees);
        fireSpinner.setAdapter(spinnerAdapter);
    }

    private Runnable confirm_proc(Employee employee){
        return new Runnable() {
            public void run() {
                ceo.fire(employee);
                setSpinnerAdapter();
                Toast.makeText(context, "Fired successfully!", Toast.LENGTH_SHORT).show();
            }
        };
    }

}
