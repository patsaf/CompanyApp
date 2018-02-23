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

import com.example.patrycja.companyapp.company.android.DeveloperAdapter;
import com.example.patrycja.companyapp.company.android.Display;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CompanyManagerActivity extends AppCompatActivity {

    private final Context context = CompanyManagerActivity.this;
    private final Activity activity = this;
    private int index;
    private TeamManager ceo;
    private TeamManager manager;
    private TextView displayManager;
    private TextView displayManagerDetails;
    private final Display display = new Display();
    private ListView lv;
    private Button back;
    private Button hire;
    private Button showTeam;
    private Button hideTeam;
    private Button cancel;
    private Button fire;
    private Button assign;
    private LinearLayout linearButtons;
    private ArrayList<Employee> employeeList;
    private Spinner fireSpinner;
    private RelativeLayout relativeSpinner;
    private final String select = "[select employees]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_manager_activity);

        index = getIntent().getExtras().getInt("managerIndex");
        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);
        manager = (TeamManager) ceo.getListEmployee(index);

        initialize();
        setupShowTeam();
        setupHire();
        setupFire();
        setupAssign();
        setupBack();
    }

    private void initialize() {
        displayManager = findViewById(R.id.manager);
        displayManager.setText(display.displayManagerBrief(manager));
        displayManagerDetails = findViewById(R.id.manager_details);
        displayManagerDetails.setText(display.displayManager(manager));
        back = findViewById(R.id.back);
        lv = findViewById(R.id.lv);
        linearButtons = findViewById(R.id.linear_buttons);
        hire = findViewById(R.id.hireButton);
        showTeam = findViewById(R.id.show_team);
        hideTeam = findViewById(R.id.hide_team);
        fireSpinner = findViewById(R.id.fire_spinner);
        cancel = findViewById(R.id.cancel);
        fire = findViewById(R.id.fireButton);
        assign = findViewById(R.id.assignButton);
        relativeSpinner = findViewById(R.id.relativeSpinner);
    }

    private void setListVisible() {
        displayManagerDetails.setVisibility(View.INVISIBLE);
        linearButtons.setVisibility(View.INVISIBLE);
        showTeam.setVisibility(View.INVISIBLE);
        lv.setVisibility(View.VISIBLE);
        hideTeam.setVisibility(View.VISIBLE);
    }

    private void setListInvisible() {
        displayManagerDetails.setVisibility(View.VISIBLE);
        linearButtons.setVisibility(View.VISIBLE);
        showTeam.setVisibility(View.VISIBLE);
        lv.setVisibility(View.INVISIBLE);
        hideTeam.setVisibility(View.INVISIBLE);
    }

    private void setupBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity(CompanyMainActivity.class);
            }
        });
    }

    private void setupHire() {
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager.getListSize() < manager.getCapacity()) {
                    startMyActivity(HireDeveloperActivity.class);
                } else {
                    Toast.makeText(context, "Your team is full!", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    private void setupShowTeam() {
        showTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager.getListSize() != 0) {
                    employeeList = new ArrayList<>();
                    for (int i = 0; i < manager.getListSize(); i++) {
                        employeeList.add(manager.getListEmployee(i));
                    }
                    DeveloperAdapter adapter = new DeveloperAdapter(context, employeeList);
                    lv.setAdapter(adapter);
                    setListVisible();
                } else {
                    Toast.makeText(context, "Your team is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListInvisible();
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
                if (manager.getListSize() != 0) {
                    relativeSpinner.setVisibility(View.VISIBLE);
                    fireSpinner.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    showTeam.setVisibility(View.INVISIBLE);

                    setSpinnerAdapter();
                    fireSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (!(adapterView.getItemAtPosition(i).equals(select))) {
                                FirePopup firePopup = new FirePopup();
                                firePopup.display(activity, confirm_proc(i));
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
                if(manager.getListSize()!=0) {
                    startMyActivity(TaskActivity.class);
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
        intent.putExtra("managerIndex", index);
        startActivity(intent);
    }
    private void setSpinnerAdapter() {
        ArrayList<String> employees = new ArrayList<>();
        Employee employee;
        employees.add(select);
        for(int i=0; i<manager.getListSize(); i++) {
            employee = manager.getListEmployee(i);
            employees.add(employee.getFirstName() + " " + employee.getLastName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, employees);
        fireSpinner.setAdapter(spinnerAdapter);
    }

    private Runnable confirm_proc(int i){
        return new Runnable() {
            public void run() {
                Employee employee = manager.getListEmployee(i - 1);
                manager.fire(employee);
                setSpinnerAdapter();
                Toast.makeText(context, "Fired successfully!", Toast.LENGTH_SHORT).show();
            }
        };
    }
}