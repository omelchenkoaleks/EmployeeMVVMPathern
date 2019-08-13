package com.omelchenkoaleks.employeemvvmpathern.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employees")
    LiveData<List<Employee>> getAllEmployees();

    /*
        может быть такая ситуация, когда id в базе данных соответствует id вставляемоу
        сотруднику, можно добавить в анотацию это условие - это заменит запись в таблице и не будет
        краха приложения ... (onConflict = OnConflictStrategy.REPLACE)

        в этом методе можно добавлять сразу всех сотрудников = не по одному
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEmployees(List<Employee> employees);

    // есть анотация @Delete, но она позволяет удалять только по одному
    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}
