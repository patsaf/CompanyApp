package com.example.patrycja.companyapp.company.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.patrycja.companyapp.CompanyManagerActivity;
import com.example.patrycja.companyapp.R;
import com.example.patrycja.companyapp.company.android.Display;
import com.example.patrycja.companyapp.company.android.InterfaceAdapter;
import com.example.patrycja.companyapp.company.employees.Employee;
import com.example.patrycja.companyapp.company.managers.TeamManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    private TeamManager ceo;
    private Context context;

    public EmployeeAdapter(Context context, ArrayList<Employee> employees, TeamManager ceo) {
        super(context, 0, employees);
        this.ceo = ceo;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Employee employee = getItem(position);
        Display display = new Display();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.employee_list, parent, false);
        }
        TextView employeeBrief = (TextView) convertView.findViewById(R.id.employee);
        TextView employeeDetails = (TextView) convertView.findViewById(R.id.employee_details);
        Button showDetails = (Button) convertView.findViewById(R.id.show_details);

        employeeBrief.setText(display.displayManagerBrief(employee));
        employeeDetails.setText(display.displayManager(employee));
        showDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Employee.class, new InterfaceAdapter<Employee>())
                        .create();
                String json = gson.toJson(ceo);
                Intent intent = new Intent(context, CompanyManagerActivity.class);
                intent.putExtra("ceoData", json);
                intent.putExtra("managerIndex", position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
