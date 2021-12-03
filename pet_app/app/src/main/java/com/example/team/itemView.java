package com.example.team;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.team.R;

public class itemView extends LinearLayout {//리스트뷰 아이템 보여줄 클래스
    TextView textView;
    TextView textView2;
    ImageView imageView;

    public itemView(Context context) {
        super(context);
        init(context);
    }

    public itemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private  void init(Context context)
    {
       LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       inflater.inflate(R.layout.list_item,this,true);

       textView=findViewById(R.id.textView1);
       textView2=findViewById(R.id.textView2);
       imageView=findViewById(R.id.imageView);
    }

    public void setName(String name){
        textView.setText(name);
    }
    public void setNum(String Num){
        textView2.setText(Num);
    }
    public void setImage(int resID){
        imageView.setImageResource(resID);
    }
}
