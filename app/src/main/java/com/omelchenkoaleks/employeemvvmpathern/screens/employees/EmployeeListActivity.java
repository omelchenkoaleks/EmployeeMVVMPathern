package com.omelchenkoaleks.employeemvvmpathern.screens.employees;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omelchenkoaleks.employeemvvmpathern.R;
import com.omelchenkoaleks.employeemvvmpathern.adapters.EmployeeAdapter;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Specialty;

import java.util.ArrayList;
import java.util.List;

/*
    активность
        создает ссылку на ViewModel
        получает объекты на которые можно подписаться
        и подписывается на их изменение
 */
public class EmployeeListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EmployeeAdapter mEmployeeAdapter;
    private EmployeeViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.employees_recycler_view);
        mEmployeeAdapter = new EmployeeAdapter();
        mEmployeeAdapter.setEmployees(new ArrayList<Employee>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmployeeAdapter);

        mViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        // подписываемся на изменения к базе данных
        mViewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                mEmployeeAdapter.setEmployees(employees);

                // Test
                if (employees != null) {
                    for (Employee employee : employees) {
                        List<Specialty> specialties = employee.getSpecialty();
                        for (Specialty specialty : specialties) {
                            Log.i("Specialty", specialty.getName());
                        }
                    }
                }
            }
        });
        mViewModel.getErrors().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable throwable) {
                /*
                    очень важна проверка, чтобы приложение не зависло, потому-что
                    после очищения значения сообщения = Toast все равно получает значение, только
                    уже null )))
                  */
                if (throwable != null) {
                    Toast.makeText(EmployeeListActivity
                            .this, "Error", Toast.LENGTH_SHORT).show();
                    // теперь, чтобы сообщение снова не появилось, его нужно очистить во ViewModel
                    mViewModel.clearErrors();
                }
            }
        });
        mViewModel.loadData();
    }
}
