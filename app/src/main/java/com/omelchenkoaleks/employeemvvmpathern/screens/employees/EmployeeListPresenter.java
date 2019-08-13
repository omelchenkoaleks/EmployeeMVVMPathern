package com.omelchenkoaleks.employeemvvmpathern.screens.employees;


import com.omelchenkoaleks.employeemvvmpathern.api.ApiFactory;
import com.omelchenkoaleks.employeemvvmpathern.api.ApiService;
import com.omelchenkoaleks.employeemvvmpathern.pojo.EmployeeResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EmployeeListPresenter {
    private CompositeDisposable mCompositeDisposable;

    /*
        ЗАМЕТКА:
            такой жесткой ссылки на конкретную активность в Презентере не должно быть,
            получается, что теперь Презентер знает все об активности :) А, ему нужно
            знать только те методы, с помощью которых он взаимодействует

            ПОТОК ДЕЙСТВИЙ ;)
            поэтому создается интерфейс в котором объявляются нужные методы:
                и уже в Презентере создается ссылка на этот интерфейс + инициализацию
                в конструкторе + в Активити нужно реализовать, созданный интерфейс + и переопределить
                нужные методы !!!
     */
//    private EmployeeListActivity mEmployeeListActivity;

//    public EmployeeListPresenter(EmployeeListActivity employeeListActivity) {
//        mEmployeeListActivity = employeeListActivity;
//    }

    private EmployeesListView mView;

    public EmployeeListPresenter(EmployeesListView view) {
        mView = view;
    }

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
                        mView.showData(employeeResponse.getResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
