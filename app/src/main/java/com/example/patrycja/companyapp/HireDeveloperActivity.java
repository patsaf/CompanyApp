package com.example.patrycja.companyapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Developer;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.EmployeeFactory;
import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.Email;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.employees.details.FirstName;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.LastName;
import com.example.patrycja.companyapp.company.employees.details.University;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

public class HireDeveloperActivity extends AppCompatActivity {

    Context context = HireDeveloperActivity.this;
    private TeamManager ceo;
    private TeamManager manager;
    private int index;
    private Gender genderItem;
    private EmployeeRole roleItem;

    private TextView firstNameLabel;
    private TextView lastNameLabel;
    private TextView emailLabel;
    private TextView universityLabel;
    private TextView countryLabel;
    private TextView firstName;
    private TextView lastName;
    private Spinner gender;
    private TextView email;
    private TextView university;
    private TextView country;
    private Spinner role;
    private Button hire;
    private Button generateRandom;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_developer_activity);

        index = getIntent().getExtras().getInt("managerIndex");
        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);
        manager = (TeamManager) ceo.getListEmployee(index);

        initialize();
        setupForm();
        setupGenerateRandom();
        setupHire();
        setupCancel();
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

    private void setupCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity(CompanyManagerActivity.class);
            }
        });
    }

    private void setupGenerateRandom() {
        generateRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeFactory randomEmployee = new EmployeeFactory(EmployeeType.MANAGER, EmployeeRole.CEO, 2);
                Resources res = getResources();
                String[] genders = res.getStringArray(R.array.gender_array);
                Random r = new Random();
                lastName.setText(randomEmployee.getLastName());
                email.setText(randomEmployee.getEmail());
                university.setText(randomEmployee.getUniversity());
                country.setText(randomEmployee.getCountry());
                firstName.setText((gender.getSelectedItem().equals(genders[0])) ?
                        randomEmployee.getFemaleName() : randomEmployee.getMaleName());
            }
        });
    }

    private void setupHire() {
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Developer developer;
                setTextBlack();
                if(checkForm()) {
                    try {
                        developer = (Developer) new Developer.DeveloperBuilder(roleItem)
                                .name(new FirstName(firstName.getText().toString()), new LastName(lastName.getText().toString()))
                                .country(new Country(country.getText().toString()))
                                .university(new University(university.getText().toString()))
                                .email(new Email(email.getText().toString()))
                                .gender(genderItem)
                                .build();
                        if(manager.makePredicate().test(developer)) {
                            manager.hire(developer);
                            startMyActivity(CompanyManagerActivity.class);
                        } else {
                            Toast.makeText(context, ceo.getConditionInfo().toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch(IllegalArgumentException e) {
                        Toast.makeText(context, "Invalid data!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setupForm() {
        gender.setAdapter(createAdapter(R.array.gender_array));
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Resources res = getResources();
                String[] genders = res.getStringArray(R.array.gender_array);
                if (adapterView.getItemAtPosition(i).equals(genders[0])) {
                    genderItem = Gender.FEMALE;
                } else if(adapterView.getItemAtPosition(i).equals(genders[1])) {
                    genderItem = Gender.MALE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                genderItem = Gender.FEMALE;
            }
        });

        role.setAdapter(createAdapter(R.array.dev_role_array));
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Resources res = getResources();
                String[] roles = res.getStringArray(R.array.dev_role_array);

                if(adapterView.getItemAtPosition(i).equals(roles[1])){
                    roleItem = EmployeeRole.TESTER;
                } else if(adapterView.getItemAtPosition(i).equals(roles[0])) {
                    roleItem = EmployeeRole.DEVELOPER;
                } else if(adapterView.getItemAtPosition(i).equals(roles[2])) {
                    roleItem = EmployeeRole.CONTRIBUTOR;
                } else if(adapterView.getItemAtPosition(i).equals(roles[3])) {
                    roleItem = EmployeeRole.TEAM_LEADER;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                roleItem = EmployeeRole.DEVELOPER;
            }
        });
    }

    private void initialize() {
        firstNameLabel = findViewById(R.id.firstName);
        lastNameLabel = findViewById(R.id.lastName);
        emailLabel = findViewById(R.id.email);
        universityLabel = findViewById(R.id.university);
        countryLabel = findViewById(R.id.country);
        firstName = findViewById(R.id.editFirstName);
        lastName= findViewById(R.id.editLastName);
        gender = findViewById(R.id.editGender);
        email = findViewById(R.id.editEmail);
        university = findViewById(R.id.editUniversity);
        country = findViewById(R.id.editCountry);
        role = findViewById(R.id.edit_ole);
        hire = findViewById(R.id.hire);
        generateRandom = findViewById(R.id.generate_random);
        cancel = findViewById(R.id.cancel);
    }

    private ArrayAdapter<CharSequence> createAdapter(int textArrayResId) {
        return ArrayAdapter.createFromResource(this, textArrayResId, android.R.layout.simple_spinner_item);

    }

    private boolean isEmpty(TextView textView) {
        return textView.getText()
                .toString()
                .equals("");
    }

    private void setTextBlack() {
        firstNameLabel.setTextColor(Color.BLACK);
        lastNameLabel.setTextColor(Color.BLACK);
        emailLabel.setTextColor(Color.BLACK);
        universityLabel.setTextColor(Color.BLACK);
        countryLabel.setTextColor(Color.BLACK);
    }

    private boolean checkForm() {
        boolean canAddCeo = true;
        if(isEmpty(firstName)) {
            firstNameLabel.setTextColor(Color.RED);
            canAddCeo = false;
        }
        if(isEmpty(lastName)) {
            lastNameLabel.setTextColor(Color.RED);
            canAddCeo = false;
        }
        if(isEmpty(email)) {
            emailLabel.setTextColor(Color.RED);
            canAddCeo = false;
        }
        if(isEmpty(university)) {
            universityLabel.setTextColor(Color.RED);
            canAddCeo = false;
        }
        if(isEmpty(country)) {
            countryLabel.setTextColor(Color.RED);
            canAddCeo = false;
        }
        return canAddCeo;
    }
}
