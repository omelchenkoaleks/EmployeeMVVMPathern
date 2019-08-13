package com.omelchenkoaleks.employeemvvmpathern.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
    этот класс использует патерн Синглтон,
    чтобы объект этого класса был единственным
 */
public class ApiFactory {
    private static ApiFactory sApiFactory;
    private static Retrofit sRetrofit;

    /*
        базовый url, который испольуется в Retrofit обязательно заканчивается слешом -
        все остальное называется эндпоинт и указывается в другом месте !!!
     */
    public static final String BASE_URL = "https://gitlab.65apps.com/65gb/static/raw/master/";

    /*
        чтобы невозможно было создать объект этого класса, созадем private конструктор

        В ЭТОМ КОНСТРУКТОРЕ:
            нужно создать объект Retrofit

            сначала создаем ссылку на этот объект (поле класса)

            для того, чтобы создать объект Retrofit ему нужно указать каким образом
            преобразовывать JSON в объект (метод addConverterFactory(GsonConverterFactory.create()))

            2 метод - для использования RxJava, чтобы в процесе получения данных мы могли слушать
            события (addCallAdapterFactory(RxJava2CallAdapterFactory.create()))

            нужно добавить базовый url, который мы будем использовать
     */
    private ApiFactory() {
        sRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiFactory getInstance() {
        if (sApiFactory == null) {
            sApiFactory = new ApiFactory();
        }
        return sApiFactory;
    }

    // метод, который возвращает нам ApiService
    public ApiService getApiService() {
        return sRetrofit.create(ApiService.class);
    }
}
