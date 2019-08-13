package com.omelchenkoaleks.employeemvvmpathern.screens.employees;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.omelchenkoaleks.employeemvvmpathern.api.ApiFactory;
import com.omelchenkoaleks.employeemvvmpathern.api.ApiService;
import com.omelchenkoaleks.employeemvvmpathern.data.AppDatabase;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;
import com.omelchenkoaleks.employeemvvmpathern.pojo.EmployeeResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
    класс ViewModel работает с Model (т.е. с данными):
        у него есть ссылка на базу данных и есть методы по работе с базой данных
        еще он хранит два (в данном случае) объекта на которые можно подписаться

        итак: ViewModel полностью работает с данными и сохраняет данные в объекты, на которые
        можно подписаться )))
 */
public class EmployeeViewModel extends AndroidViewModel {
    private static AppDatabase db;

    // ссылка на объект, на который будет подписываться наше View
    private LiveData<List<Employee>> mEmployees;

    private CompositeDisposable mCompositeDisposable;

    /*
        хотим отслеживать ошибки, которые происходят при загрузке данных:
            класс MutableLiveData реализует LiveData (это нужно потому-что класс LiveData
            является абстрактным классом, поэтому мы не можем просто написать new ...)
     */
    private MutableLiveData<Throwable> mErrors;


    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        mEmployees = db.employeeDao().getAllEmployees();
        mErrors = new MutableLiveData<>();
    }
    // чтобы Активити могла подписаться на объект

    public LiveData<List<Employee>> getEmployees() {
        return mEmployees;
    }

    /*
        оставляем для метода тип LiveData, чтобы в активности мы могли подписаться на этот
        объект но не имели возсожность его изменить (это было бы возможно, если мы установим
        тип MutableLiveData
      */
    public LiveData<Throwable> getErrors() {
        return mErrors;
    }

    /*
        этот метод нужен, чтобы предотвратить сообщение в Toast (потому-что оно
        сохраняется во ViewModel)
      */
    public void clearErrors() {
        mErrors.setValue(null);
    }

    private void insertEmployees(List<Employee> employees) {
        new InsertEmployeesTask().execute(employees);
    }
    private static class InsertEmployeesTask extends AsyncTask<List<Employee>, Void, Void> {
        @Override
        protected Void doInBackground(List<Employee>... lists) {
            if (lists != null && lists.length > 0) {
                db.employeeDao().insertEmployees(lists[0]);
            }
            return null;
        }
    }

    private void deleteAllEmployees() {
        new DeleteAllEmployeesTask().execute();
    }
    private static class DeleteAllEmployeesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            db.employeeDao().deleteAllEmployees();
            return null;
        }
    }

    // метод, который загружает данные из Интернета
    public void loadData() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        mCompositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        deleteAllEmployees();
                        insertEmployees(employeeResponse.getEmployees());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mErrors.setValue(throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    // этот метод вызывается при уничтожении ViewModel
    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }
}
