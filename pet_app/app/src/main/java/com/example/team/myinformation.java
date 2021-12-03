package com.example.team;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class myinformation extends AppCompatActivity { //로그인한 아이디와 비밀번호 보여주는 클래스
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinformation);
        setTitle("내 정보");

        TextView t1=findViewById(R.id.myid);
        TextView t2=findViewById(R.id.mypassword);



        String a=goLogin.p;
        String b=goLogin.q;

        t1.setText(a);
        t2.setText(b);






    }
}
