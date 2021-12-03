package com.example.team;
import android.graphics.Bitmap;

public class WriteItem { //이름과 전화번호 값을 위한 클래스

    String name;
    String mobile;
    String species;
    Bitmap image;

    public WriteItem(String species,Bitmap image, String name, String mobile) {
        this.species=species;
        this.image=image;
        this.name = name;
        this.mobile = mobile;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
