package com.example.team;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity { //회원가입 화면을 보여주는 클래스

    Button btn1,btn2;
    EditText editText1,editText2,editText3;

   // myDBHelper1 myDBHelper1;
  //  SQLiteDatabase sqlDB;
   // Cursor cursor;
    DBgo dBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;

    String Id,Password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setTitle("회원가입창");

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        editText1=findViewById(R.id.ID);
        editText2=findViewById(R.id.Password);

        dBHelper=DBgo.getInstance(getApplicationContext()); //데이터 베이스 쓸수 있게
        sqlDB=dBHelper.getWritableDatabase();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id=editText1.getText().toString().trim();
                Password=editText2.getText().toString().trim();

              //데이터 아이디 중복과 중복이 아니라면 아이디와 비밀번호 추가
                sqlDB=dBHelper.getReadableDatabase();
                cursor = sqlDB.rawQuery("SELECT * FROM IdPassword where gId='"+Id+"';", null);
                if(cursor.getCount()==0){
                    sqlDB= dBHelper.getWritableDatabase();
                    sqlDB.execSQL("INSERT INTO IdPassword VALUES('"+Id+"','"+Password+"',' ');");
                    Toast.makeText(signup.this, "회원가입되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(signup.this, "아이디가 중복되었습니다", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }
   /* public String IdMethod(){  //사용 불가한거같다

        return Id1;
    }
    public String PasswordMethod(){
       /* sqlDB=myDBHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM IdPassword;", null); //데이터베이스 안 내용조회
        String Password=cursor.getString(1);
        return Password1;
    }  */


}
