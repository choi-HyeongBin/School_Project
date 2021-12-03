package com.example.team;

public class listitemInformation { //리스트 아이템 정보 보여주는 클래스

    String name;
    String num;
    int resID;
    public listitemInformation(String name, String num, int resID) {
        this.name = name;
        this.num = num;
        this.resID=resID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getnum() {
        return num;
    }

    public void num(String num) {
        this.num = num;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    @Override
    public String toString() {
        return "listitemInformation{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", resID=" + resID +
                '}';
    }
}
