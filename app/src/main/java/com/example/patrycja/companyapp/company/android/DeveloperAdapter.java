package com.example.patrycja.companyapp.company.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.patrycja.companyapp.R;
import com.example.patrycja.companyapp.company.employees.Employee;

import java.util.ArrayList;

public class DeveloperAdapter extends ArrayAdapter<Employee> {
    public DeveloperAdapter(Context context, ArrayList<Employee> employees) {
        super(context, 0, employees);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Employee employee = getItem(position);
        Display display = new Display();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.developer_list, parent, false);
        }
        TextView employeeBrief = convertView.findViewById(R.id.employee);
        TextView employeeDetails = convertView.findViewById(R.id.employee_details);

        employeeBrief.setText(display.displayBrief(employee));
        employeeDetails.setText(display.displayAllInfo(employee));
        return convertView;
    }
}
