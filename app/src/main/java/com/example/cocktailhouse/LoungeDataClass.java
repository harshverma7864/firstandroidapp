package com.example.cocktailhouse;

public class LoungeDataClass {

    String sno,fname,lid;
    int price;

    public LoungeDataClass(String sno , String fname, String lid , int price){
        this.sno = sno;
        this.fname = fname;
        this.lid = lid;
        this.price = price;

    }

    public String getsno(){return sno;}

    public String getfname(){ return fname;}

    public String getlid(){ return lid;}

    public int getprice(){return price;}


}
