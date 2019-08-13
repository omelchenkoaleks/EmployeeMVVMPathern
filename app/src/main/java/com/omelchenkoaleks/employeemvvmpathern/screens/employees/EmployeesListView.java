package com.omelchenkoaleks.employeemvvmpathern.screens.employees;


import com.omelchenkoaleks.employeemvvmpathern.pojo.Employee;

import java.util.List;

/*
    ЗАЧЕМ ЭТОТ ИНТЕРФЕЙС
        без него презентер будет знать все об активности, а это не соотвествует принципу
        инкапуляции
        Presenter содержит сильную ссылку на Активити, которую мы
        хотим использовать - это не правильно!

        презентер должен иметь доступ только к тем методам активности, которые
        необходимы для взаимодействия
 */

// в этом интерфейсе объявляем все методы, о которых должен знать Presenter
public interface EmployeesListView {
    void showData(List<Employee> employees);
    void showError();
}
