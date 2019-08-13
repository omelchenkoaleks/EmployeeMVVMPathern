package com.omelchenkoaleks.employeemvvmpathern.screens.employees;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.omelchenkoaleks.employeemvvmpathern.R;
import com.omelchenkoaleks.employeemvvmpathern.adapters.EmployeeAdapter;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity implements EmployeesListView {
    private RecyclerView mRecyclerView;

    private EmployeeAdapter mEmployeeAdapter;
    private EmployeeListPresenter mEmployeeListPresenter;

//    private Disposable mDisposable;
//    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmployeeListPresenter = new EmployeeListPresenter(this);

        mRecyclerView = findViewById(R.id.employees_recycler_view);
        mEmployeeAdapter = new EmployeeAdapter();
        // в адаптер нужно добавить вначале пустой список или будет ошибка null
        mEmployeeAdapter.setEmployees(new ArrayList<Employee>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEmployeeAdapter);

        mEmployeeListPresenter.loadData();


        /*
            БЫЛО НЕ ПРАВИЛЬНО = ПРИЧИНА:
                View все знает о Model (в данном случае в Активити происходит получение данных)
                оставлено в комментариях, чтобы сравнить
         */

//        /*
//            после этих двух строчек мы можем получать наши данные,
//            используя реализованные методы ApiService Ретрофитом ...
//          */
//        ApiFactory apiFactory = ApiFactory.getInstance();
//        ApiService apiService = apiFactory.getApiService();
//        mCompositeDisposable = new CompositeDisposable();
//
//        // ВОТ ТАК:
//
//        /*
//            первые две строчки стандартные:
//                1 = в каком потоке загружать данные
//                2 = в каком потоке выводить данные
//
//                теперь указать, что делать когда мы получим данные:
//                    используем метод subscribe с двумя параметрами, передаем два объекта
//                    анонимного класса Consumer (первый выполняется в случае, если данные загружены
//                    успешно, второй принимает исключение)
//
//
//            важное дополнение:
//                Disposable = нужный объект, чтобы избежать утечки памяти
//
//         */
//         mDisposable = apiService.getEmployees()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<EmployeeResponse>() {
//                    @Override
//                    public void accept(EmployeeResponse employeeResponse) throws Exception {
//                        mEmployeeAdapter.setEmployees(employeeResponse.getResponse());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(EmployeeListActivity.this, "Ошибка получения данных!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//         // после создания объект Disposable мы добавляем его в объект CompositeDisposable
//        mCompositeDisposable.add(mDisposable);
//
//        /*
//            ЗАМЕТКА КАК ПОЛУЧИТЬ ИНФОРМАЦИЮ ОБ ОШИБКЕ В ТОСТ:
//                вместо text: "Ошибка получения данных!" можно написать
//                throwable.getMessage() = и прочитать в тосте ошибку )
//         */
//
//
//        /* ------- Тест, чтобы проверить что работает: ------- */
////        List<Employee> employees = new ArrayList<>();
////        Employee employee1 = new Employee();
////        Employee employee2 = new Employee();
////        employee1.setFName("Aleksandr");
////        employee2.setFName("Inna");
////        employee1.setLName("Omelchenko");
////        employee2.setLName("Jaldack");
////        employees.add(employee1);
////        employees.add(employee2);
////        mEmployeeAdapter.setEmployees(employees);
    }

    // чтобы иметь возможность отображать данные созадем этот метод в Активити
//    public void showData(List<Employee> employees) {
//        mEmployeeAdapter.setEmployees(employees);
//    }
//
//    // метод отображает ошибку
//    public void showError() {
//        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void showData(List<Employee> employees) {
        mEmployeeAdapter.setEmployees(employees);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        // не забываем вызывать метод dispose(), чтобы освободить ресурсы
        mEmployeeListPresenter.disposeDisposable();
        super.onDestroy();
    }
}
