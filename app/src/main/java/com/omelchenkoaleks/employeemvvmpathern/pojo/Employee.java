package com.omelchenkoaleks.employeemvvmpathern.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.omelchenkoaleks.employeemvvmpathern.converters.Converter;

import java.util.List;

@Entity(tableName = "employees")
@TypeConverters(value = Converter.class)
public class Employee {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("f_name")
    @Expose
    private String fName;
    @SerializedName("l_name")
    @Expose
    private String lName;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("avatr_url")
    @Expose
    private String avatrUrl;

    /*
        в базу данных мы не можем ложить любые объекты (можем числа, строки - все, что можно
        записать в виде строки
            Поэтому, чтобы сохранить список специальностей в базу - нужно иметь способ, который
        преобразовывает этот список в строку и, при этом, чтобы при получении данных
        мы могли снова эту строку преобразовать в список специальностей

        ЭТО нужно будет делать каждый раз, когда таблица в базе данных хранит любой объект,
        который не является примитивным типом или строкой (типом String) = нужно иметь конвертер,
        который преобразовывает это в строку и обратно !!!
     */
    @SerializedName("specialty")
    @Expose
    private List<Specialty> specialty = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatrUrl() {
        return avatrUrl;
    }

    public void setAvatrUrl(String avatrUrl) {
        this.avatrUrl = avatrUrl;
    }

    public List<Specialty> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<Specialty> specialty) {
        this.specialty = specialty;
    }
}
