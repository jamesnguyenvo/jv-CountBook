package com.example.james.countbook;

import java.util.Date;

/**
 * Created by James on 2017-09-30.
 */

public class Counter {

    private Integer initValue;
    private Integer currValue;
    private Date date;
    private String name;
    private String comment;

    // Counter constructor without comment
    public Counter (String name, Integer initValue) {

        this.name = name;
        this.initValue = initValue;
        this.currValue = initValue;
        this.date = new Date();
        this.comment = "";

    }

    // Counter constructor with comment
    public Counter(String name, Integer initValue, String comment) {

        this.name = name;
        this.initValue = initValue;
        this.currValue = initValue;
        this.date = new Date();
        this.comment = comment;
    }

    public Integer getInitValue() {
        return this.initValue;
    }

    public void setInitValue(Integer initValue) {
        this.initValue = initValue;
    }

    public Integer getCurrValue() {
        return this.currValue;
    }

    public void setCurrValue(Integer currValue) {
        this.currValue = currValue;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void increment() {
        this.setCurrValue(getCurrValue() + 1);
        this.setDate(new Date());
    }

    public void decrement() {
        if (this.getCurrValue() > 0) {
            this.setCurrValue(getCurrValue() - 1);
            this.setDate(new Date());
        }
    }

    //  set currentValue to InitValue
    public void reset(){
        this.setCurrValue(this.getInitValue());
    }

    // format counter object to be added to list view
    @Override
    public String toString() {
        return this.name + "   |   " + this.getDate() + "   |   " + this.currValue;
    }
    // return formatted date string YYYY-MM-DD
    public String getDate() {
        String formattedDate = String.valueOf(this.date.getYear() + 1900) + "-";
        formattedDate += String.format("%02d", this.date.getMonth() + 1) + "-";
        formattedDate += String.format("%02d", this.date.getDate());
        return formattedDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
