package com.example.ysg.app1;

public class ListItem {
    String engstr;
    String korstr;
    public void setEng(String eng){
        engstr=eng;
    }
    public void setKor(String kor){
        korstr=kor;
    }
    public String getEng(){
        return this.engstr;
    }
    public String getKor(){
        return this.korstr;
    }
}
