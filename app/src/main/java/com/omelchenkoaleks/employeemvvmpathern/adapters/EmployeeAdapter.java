package com.omelchenkoaleks.employeemvvmpathern.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.omelchenkoaleks.employeemvvmpathern.R;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;

import java.util.List;

// в этом адаптере мы выведем сотрудников
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> mEmployees;

    public List<Employee> getEmployees() {
        return mEmployees;
    }

    public void setEmployees(List<Employee> employees) {
        mEmployees = employees;
        notifyDataSetChanged(); // не забываем известить адаптер
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = mEmployees.get(position);
        holder.mNameTextView.setText(employee.getFName());
        holder.mLastNameTextView.setText(employee.getLName());
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder{
        private TextView mNameTextView;
        private TextView mLastNameTextView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mLastNameTextView = itemView.findViewById(R.id.last_name_text_view);
        }
    }
}
