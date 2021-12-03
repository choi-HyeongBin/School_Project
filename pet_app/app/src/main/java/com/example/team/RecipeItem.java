package com.example.team;

import android.graphics.Bitmap;

public class RecipeItem {
    String name;
    int resId;

    public RecipeItem(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() { return resId; }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "RecipeItem{" +
                "name='" + name + '\'';
    }
}
