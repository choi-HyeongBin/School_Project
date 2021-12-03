package com.example.team;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team.R;

public class goLogin extends AppCompatActivity { //로그인화면
    Button btn1,btn2;
    EditText editText1,editText2;

    String edtID,edtPass;

    DBgo dBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    static String p,q;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("로그인창");

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        editText1=findViewById(R.id.ID);
        editText2=findViewById(R.id.Password);


        dBHelper=DBgo.getInstance(getApplicationContext());



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtID=editText1.getText().toString().trim();
                edtPass=editText2.getText().toString().trim();

                p=edtID;
                q=edtPass;

                //아이디 비밀번호
                sqlDB=dBHelper.getReadableDatabase();
                cursor = sqlDB.rawQuery("SELECT * FROM IdPassword where gId='"+edtID+"' AND gPassword='"+edtPass+"';", null); //데이터베이스 안 내용조회
                if(cursor.getCount()==0){ //해당아이디와 비밀번호가 있으면 row 1줄을가져온다.
                    Toast.makeText(getApplicationContext(), "ID 또는 PASSWORD가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "로그인 ok.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),main.class);

                    startActivity(intent);
                }


            }
        });





    }

}
