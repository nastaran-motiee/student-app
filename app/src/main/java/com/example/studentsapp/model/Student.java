package com.example.studentsapp.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public String phone = "";
    public String address = "";
    public boolean cb = false;
    //public ImageView selectImage;

    public Student(){}

    public Student(String name, String id, String phone, String address, boolean cb, ImageView selectImage){
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.cb = cb;
        //this.selectImage = selectImage;
    }
    public Student(String name, String id, String phone, String address, boolean cb){
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.cb = cb;
    }


    public String getId(){
        return this.id;
    }


}
