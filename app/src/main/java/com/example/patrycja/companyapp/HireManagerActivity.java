package com.example.patrycja.companyapp;

import android.app.Activity;
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

import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.employees.EmployeeFactory;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.details.Country;
import com.example.patrycja.companyapp.company.employees.details.Email;
import com.example.patrycja.companyapp.company.employees.details.EmployeeRole;
import com.example.patrycja.companyapp.company.employees.details.EmployeeType;
import com.example.patrycja.companyapp.company.employees.details.FirstName;
import com.example.patrycja.companyapp.company.employees.details.Gender;
import com.example.patrycja.companyapp.company.employees.details.LastName;
import com.example.patrycja.companyapp.company.employees.details.University;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.example.patrycja.companyapp.company.predicates.PredicateInfo;
import com.example.patrycja.companyapp.company.predicates.Predicates;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

public class HireManagerActivity extends AppCompatActivity{

    private final Context context = HireManagerActivity.this;
    private TeamManager ceo;

    private Gender genderItem;
    private Gender genderConditionItem;
    private Predicates predicateItem;

    private TextView firstNameLabel;
    private TextView lastNameLabel;
    private TextView emailLabel;
    private TextView universityLabel;
    private TextView countryLabel;
    private TextView conditionDetailLabel;
    private TextView capacityLabel;
    private TextView firstName;
    private TextView lastName;
    private Spinner gender;
    private TextView email;
    private TextView university;
    private TextView country;
    private Spinner hiringCondition;
    private Spinner conditionDetailSpinner;
    private TextView conditionDetail;
    private TextView capacity;
    private Button hire;
    private Button generateRandom;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_manager_activity);

        String ceoData = getIntent().getStringExtra("ceoData");
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        ceo = gson.fromJson(ceoData, TeamManager.class);

        initialize();
        setupForm();
        setupGenerateRandom();
        setupHire();
        setupCancel();
    }

    private void setupHire() {
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamManager manager;
                setTextBlack();
                if(checkForm()) {
                    try {
                        manager = new TeamManager.ManagerBuilder(EmployeeRole.DEVELOPMENT_MANAGER)
                                .name(new FirstName(firstName.getText().toString()), new LastName(lastName.getText().toString()))
                                .capacity(Integer.parseInt(capacity.getText().toString()))
                                .country(new Country(country.getText().toString()))
                                .university(new University(university.getText().toString()))
                                .email(new Email(email.getText().toString()))
                                .gender(genderItem)
                                .conditionInfo(new PredicateInfo(predicateItem,
                                        (predicateItem == Predicates.GENDER) ?
                                                genderConditionItem.name() : conditionDetail.getText().toString()))
                                .build();
                        if(ceo.makePredicate().test(manager)) {
                            ceo.hire(manager);
                            startMyActivity(CompanyMainActivity.class);
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

    private void setupCancel() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity(CompanyMainActivity.class);
            }
        });
    }

    private void startMyActivity(Class<?> activity) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                .create();
        String json = gson.toJson(ceo);
        Intent intent = new Intent(context, activity);
        intent.putExtra("ceoData", json);
        startActivity(intent);
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
                capacity.setText(Integer.toString(r.nextInt(5)+1));
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

        hiringCondition.setAdapter(createAdapter(R.array.predicate_array));
        hiringCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Resources res = getResources();
                String[] predicates = res.getStringArray(R.array.predicate_array);

                conditionDetailLabel.setVisibility(View.INVISIBLE);
                conditionDetail.setVisibility(View.INVISIBLE);
                conditionDetailSpinner.setVisibility(View.INVISIBLE);

                if(adapterView.getItemAtPosition(i).equals(predicates[0])){
                    predicateItem = Predicates.EMPTY;
                } else if(adapterView.getItemAtPosition(i).equals(predicates[1])) {
                    predicateItem = Predicates.UNIVERSITY;
                    conditionDetailLabel.setVisibility(View.VISIBLE);
                    conditionDetail.setVisibility(View.VISIBLE);
                } else if(adapterView.getItemAtPosition(i).equals(predicates[2])) {
                    predicateItem = Predicates.COUNTRY;
                    conditionDetailLabel.setVisibility(View.VISIBLE);
                    conditionDetail.setVisibility(View.VISIBLE);
                } else if(adapterView.getItemAtPosition(i).equals(predicates[3])) {
                    predicateItem = Predicates.GENDER;
                    conditionDetailLabel.setVisibility(View.VISIBLE);
                    conditionDetailSpinner.setVisibility(View.VISIBLE);
                    conditionDetailSpinner.setAdapter(createAdapter(R.array.gender_array));
                    conditionDetailSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Resources res = getResources();
                            String[] genders = res.getStringArray(R.array.gender_array);
                            if (adapterView.getItemAtPosition(i).equals(genders[0])) {
                                genderConditionItem = Gender.FEMALE;
                            } else if(adapterView.getItemAtPosition(i).equals(genders[1])) {
                                genderConditionItem = Gender.MALE;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            genderConditionItem = Gender.FEMALE;
                        }
                    });
                } else if(adapterView.getItemAtPosition(i).equals(predicates[4])) {
                    predicateItem = Predicates.EMAIL;
                    conditionDetailLabel.setVisibility(View.VISIBLE);
                    conditionDetail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                predicateItem = Predicates.EMPTY;
            }
        });
    }

    private void initialize() {
        firstNameLabel = findViewById(R.id.firstName);
        lastNameLabel = findViewById(R.id.lastName);
        emailLabel = findViewById(R.id.email);
        universityLabel = findViewById(R.id.university);
        countryLabel = findViewById(R.id.country);
        conditionDetailLabel = findViewById(R.id.conditionDetail);
        capacityLabel = findViewById(R.id.capacity);
        firstName = findViewById(R.id.editFirstName);
        lastName= findViewById(R.id.editLastName);
        gender = findViewById(R.id.editGender);
        email = findViewById(R.id.editEmail);
        university = findViewById(R.id.editUniversity);
        country = findViewById(R.id.editCountry);
        hiringCondition = findViewById(R.id.editHiringCondition);
        conditionDetailSpinner = findViewById(R.id.editConditionDetailSpinner);
        conditionDetail = findViewById(R.id.editConditionDetail);
        capacity = findViewById(R.id.editCapacity);
        hire = findViewById(R.id.hire);
        generateRandom = findViewById(R.id.generate_random);
        cancel = findViewById(R.id.cancel);

        conditionDetailLabel.setVisibility(View.INVISIBLE);
        conditionDetail.setVisibility(View.INVISIBLE);
        conditionDetailSpinner.setVisibility(View.INVISIBLE);
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
        capacityLabel.setTextColor(Color.BLACK);
        firstNameLabel.setTextColor(Color.BLACK);
        lastNameLabel.setTextColor(Color.BLACK);
        emailLabel.setTextColor(Color.BLACK);
        universityLabel.setTextColor(Color.BLACK);
        countryLabel.setTextColor(Color.BLACK);
        conditionDetail.setTextColor(Color.BLACK);
    }

    private boolean checkForm() {
        boolean canAddManager = true;
        if(isEmpty(capacity)) {
            capacityLabel.setTextColor(Color.RED);
            canAddManager = false;
        }
        if(isEmpty(firstName)) {
            firstNameLabel.setTextColor(Color.RED);
            canAddManager = false;
        }
        if(isEmpty(lastName)) {
            lastNameLabel.setTextColor(Color.RED);
            canAddManager = false;
        }
        if(isEmpty(email)) {
            emailLabel.setTextColor(Color.RED);
            canAddManager = false;
        }
        if(isEmpty(university)) {
            universityLabel.setTextColor(Color.RED);
            canAddManager = false;
        }
        if(isEmpty(country)) {
            countryLabel.setTextColor(Color.RED);
            canAddManager = false;
        } if(predicateItem!=Predicates.EMPTY && predicateItem!=Predicates.GENDER) {
            if(isEmpty(conditionDetail)) {
                conditionDetail.setTextColor(Color.RED);
                canAddManager = false;
            }
        }
        return canAddManager;
    }
}
