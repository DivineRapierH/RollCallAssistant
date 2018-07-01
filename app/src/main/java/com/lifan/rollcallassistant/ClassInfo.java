package com.lifan.rollcallassistant;

import java.io.Serializable;

public class ClassInfo implements Serializable{
    public StudentDetails Students[];
    private int StudentNum = 1;
    ClassInfo(int num){
        StudentNum = num;
        Students = new StudentDetails[StudentNum];
    }
    ClassInfo(){
        StudentNum = 0;
    }
    int getStudentNum(){
        return StudentNum;
    }
}
