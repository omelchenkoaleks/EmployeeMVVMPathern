package com.omelchenkoaleks.employeemvvmpathern.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.omelchenkoaleks.employeemvvmpathern.pojo.Specialty;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    // метод возвращает строку (уже преобразованную из списка)
    @TypeConverter
    public String listSpecialtyToString(List<Specialty> specialties) {

        /*
            ПЕРВЫЙ СПОСОБ:
         */
//        JSONArray jsonArray = new JSONArray();
//        for (Specialty specialty : specialties) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("specialty_id", specialty.getSpecialtyId());
//                jsonObject.put("name", specialty.getName());
//                jsonArray.put(jsonObject);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return jsonArray.toString();

        /*
            НО, это все можно не делать руками, а позволить сделать это
            объекту Gson = эта одна строчка заменяет все, что мы сделали выше !!!
         */
        return new Gson().toJson(specialties);
    }

    // метод, который преобразовывает обратно строку в список
    @TypeConverter
    public List<Specialty> stringToListSpecialty(String specialtiesAsString) {
        Gson gson = new Gson(); // этот объек умеет преобразовывать объекты в JSON и обратно


        /*
            хотелось бы сделать так:
            ArrayList<Specialty> specialties = gson.fromJson(specialtiesAsString, ArrayList<Specialty>.class);

            Но, так ничего не получится! При преобразовании мы не можем указывать
            параметризированный тип: ArrayList<Specialty>

            ПОЭТОМУ:
                1, мы убираем параметры = в этом случае тоже будет создан ArrayList, но он будет
                уже не типа Specialty, а общего типа Object
                2, потом мы этот ArrayList приводим к типу ArrayList<Specialty>
                    сначала создаем новый ArrayList нужного типа, потом проходим по всем
                    элементам массива objects, добавляем каждый элемент (o, который
                    по сути является объектом JSON), приведенный к типу Specialty
         */

        /*
            это просто общий ArrayList, который содержит родительский тип Object (теперь в нем
            хранятся объекты JSON, приведенные к типу Object и теперь каждый этот объект нам нужно
            преобразовать в объект Specialty
         */
        ArrayList objects = gson.fromJson(specialtiesAsString, ArrayList.class);
        ArrayList<Specialty> specialties = new ArrayList<>();
        for (Object o : objects) {
            specialties.add(gson.fromJson(o.toString(), Specialty.class));
        }
        return specialties;
    }
}
