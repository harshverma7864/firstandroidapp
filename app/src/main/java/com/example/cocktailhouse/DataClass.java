package com.example.cocktailhouse;

public class DataClass {

    String sno,lname,lid,location,rating,urltoimage;

    public DataClass(String sno , String lname, String location ,String lid , String rating ,String urltoimage){
        this.sno = sno;
        this.lname = lname;
        this.lid = lid;
        this.location = location;
        this.rating = rating;
        this.urltoimage = urltoimage;
    }

    public String getsno(){return sno;}

    public String getlname(){ return lname;}

    public String getLocation(){ return location;}

    public String getrating(){return rating;}
    public String getimageurl(){return urltoimage;}

    public String getLid(){return lid;}

}
