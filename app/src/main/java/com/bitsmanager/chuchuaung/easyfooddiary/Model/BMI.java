package com.bitsmanager.chuchuaung.easyfooddiary.Model;

import java.io.Serializable;

public class BMI implements Serializable {
    int bmi_id;
    int user_id;
    double bmi_weight;
    double bmi_height;
    double result;
    int is_Kg, is_cm;

    public int getIs_Kg() {
        return is_Kg;
    }

    public void setIs_Kg(int is_Kg) {
        this.is_Kg = is_Kg;
    }

    public int getIs_cm() {
        return is_cm;
    }

    public void setIs_cm(int is_cm) {
        this.is_cm = is_cm;
    }

    public int getBmi_id() {
        return bmi_id;
    }

    public void setBmi_id(int bmi_id) {
        this.bmi_id = bmi_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getBmi_weight() {
        return bmi_weight;
    }

    public void setBmi_weight(double bmi_weight) {
        this.bmi_weight = bmi_weight;
    }

    public double getBmi_height() {
        return bmi_height;
    }

    public void setBmi_height(double bmi_height) {
        this.bmi_height = bmi_height;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

}
