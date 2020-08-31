package com.e.medicinearranger;

import java.util.ArrayList;

public class UserHelper {
    String name, doses, qauntett;
    private String spinnertype;
    private String spinnercolour;
    private String beforAfter;
    private String hour,min;
    private String format ;
    String duration;
    private ArrayList<String> day;

      public UserHelper(String hour, String min, String format, ArrayList<String> day,String duration) {
          this.duration = duration;
          this.hour = hour;
          this.min = min;
          this.format = format;
          this.day = day;
      }
    public UserHelper(){}

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ArrayList<String> getDay() {
        return day;
    }

    public void setDay(ArrayList<String> day) {
        this.day = day;
    }


    public UserHelper(String name, String doses, String qauntett, String spinnertype, String spinnercolour, String befAfter) {
        this.name = name;
        this.doses = doses;
        this.qauntett = qauntett;
        this.spinnertype = spinnertype;
        this.spinnercolour = spinnercolour;
        this.beforAfter=befAfter;

    }


    public String getBeforAfter() {
        return beforAfter;
    }

    public void setBeforAfter(String beforAfter) {
        this.beforAfter = beforAfter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoses() {
        return doses;
    }

    public void setDoses(String doses) {
        this.doses = doses;
    }

    public String getQauntett() {
        return qauntett;
    }

    public void setQauntett(String qauntett) {
        this.qauntett = qauntett;
    }

    public String getSpinnertype() {
        return spinnertype;
    }

    public void setSpinnertype(String spinnertype) {
        this.spinnertype = spinnertype;
    }

    public String getSpinnercolour() {
        return spinnercolour;
    }

    public void setSpinnercolour(String spinnercolour){this.spinnercolour = spinnercolour;}
}




