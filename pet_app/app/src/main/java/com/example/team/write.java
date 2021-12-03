package com.example.team;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team.R;

public class write extends AppCompatActivity { //분양->글쓰기 클래스

    Button btn1,btnBack;
    EditText etname,etmobile,etspecies;

    String name,mobile; //fragment2에서 준 name과 mobile 값을 변경하기 위해 선언
    String species;

    private final int GET_GALLERY_IMAGE = 200; //이미지 값
    ImageView imageView;
    Uri selectedImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // 갤러리에서 선택한 이미지를 보여줌.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage=data.getData();
            imageView.setImageURI(selectedImage);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write); //write.xml을 보여준다.

        etname=findViewById(R.id.etname);
        etmobile=findViewById(R.id.etmobile);
        etspecies=findViewById(R.id.etspecies);
        imageView=findViewById(R.id.imageView);

        btn1 = findViewById(R.id.btn1);
        btnBack=findViewById(R.id.btnback);
        final Intent intent=getIntent(); //fragment2에서 준 값을 받아온뒤

       name=intent.getStringExtra("name"); //fragment2 에서 전달해준 name과 mobile값들을 저장 후
       mobile=intent.getStringExtra("mobile");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=etname.getText().toString(); //name과 mobile 값을 텍스트 값으로 바꾼다.
                mobile=etmobile.getText().toString();
                species=etspecies.getText().toString(); //거기다가 종과 이미지 값들도 전달해준다.

                Intent intent1=new Intent(); //다시 인텐트로 fragment2로 전달
                intent1.putExtra("species",species);
                intent1.putExtra("name",name);
                intent1.putExtra("mobile",mobile);
                intent1.putExtra("image",selectedImage);
                    setResult(1001,intent1);
                    finish();

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name==null||mobile==null||species==null|selectedImage==null){
                    Intent intent1=new Intent();
                    setResult(1,intent1);
                    finish();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //이미지 불러오기
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

    }
}
