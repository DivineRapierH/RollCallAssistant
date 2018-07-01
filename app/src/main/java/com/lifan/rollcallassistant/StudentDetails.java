package com.lifan.rollcallassistant;

import java.io.Serializable;

public class StudentDetails implements Serializable {
    private String StuId = "";
    private String StuName = "";
    private int StuAbsence = 0;
    private int StuAnswer = 0;
    private double StuAverage = 0;
    private int TotalScore = 0;

    public String getStuId() {
        return StuId;
    }

    public String getStuName() {
        return StuName;
    }

    public int getStuAbsence() {
        return StuAbsence;
    }

    public int getStuAnswer() {
        return StuAnswer;
    }

    public double getStuAverage() {
        return StuAverage;
    }

    public void setStuId(String stuId) {
        StuId += stuId;
    }

    public void setStuName(String stuName) {
        StuName += stuName;
    }

    public void setStuAbsence(int stuAbsence) {
        StuAbsence = stuAbsence;
    }

    public void setStuAnswer(int stuAnswer) {
        StuAnswer = stuAnswer;
    }

    public void setStuAverage(double stuAverage) {
        StuAverage = stuAverage;
    }

    public void addAbsence (){
        StuAbsence++;
    }

    public void addAnswer(int score) {
        StuAnswer++;
        TotalScore += score;
        StuAverage = TotalScore/StuAnswer;
    }

}
