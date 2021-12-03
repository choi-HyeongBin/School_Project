package com.example.team;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.team.R;

public class RecipeItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;
    ImageView imageView;

    public RecipeItemView(Context context) {
        super(context);

        init(context);
    }

    public RecipeItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipe_item, this, true);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setName(String name) {
        textView1.setText(name);
    }

    public void setRecipe(String recipe) {
        textView2.setText(recipe);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}
