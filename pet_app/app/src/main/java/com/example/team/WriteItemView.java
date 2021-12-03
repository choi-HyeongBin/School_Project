package com.example.team;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.team.R;

public class WriteItemView extends LinearLayout {
    TextView textView,textView2,textView3;
    ImageView imageView;
    public WriteItemView(Context context) {
        super(context);
        init(context);
    }

    public WriteItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.write_item,this,true);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        imageView=findViewById(R.id.wriiv);
    }
    public void setspecies(String species){
        textView.setText(species);
    }
    public void setName(String name){
        textView2.setText(name);
    }
    public void setMobile(String mobile){
        textView3.setText(mobile);
    }
    public void setImage(Bitmap image){
        imageView.setImageBitmap(image);
    }
}
