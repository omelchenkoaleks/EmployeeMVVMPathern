package com.omelchenkoaleks.employeemvvmpathern.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;

@Database(entities = {Employee.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "employees.db";
    private static AppDatabase sDatabase;

    private static final Object LOCK = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (sDatabase == null) {
                /*
                    .fallbackToDestructiveMigration() = говорит следующее,
                    если версия базы изменилась, то удилить все старые данные и создать новую базу
                  */
                sDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration().build();
            }
            return sDatabase;
        }
    }

    public abstract EmployeeDao employeeDao();
}
