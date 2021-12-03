package com.example.team;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.team.DBgo;

public class Buy extends AppCompatActivity { //구매창 클래스
    BuyAdapter adapter=new BuyAdapter();
    ImageView imageView;
    Bitmap image;
    TextView textView1,textView2,result;
    Button btn1,back,buy;
    EditText editText;
    DBgo dBHelper;
    SQLiteDatabase sqlDB,sqlDB1;
    Cursor cursor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        setTitle("구매창");

        imageView=findViewById(R.id.imageViewbuy);
        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        result=findViewById(R.id.result);
        btn1=findViewById(R.id.btn1);
        back=findViewById(R.id.btn2);
        buy=findViewById(R.id.btn3);
        editText=findViewById(R.id.edit1);
        final Intent intent=getIntent();
        final String name = intent.getExtras().getString("name");
        final String price = intent.getExtras().getString("price");
        textView1.setText(name);
        textView2.setText(price);
        byte[] arr = getIntent().getByteArrayExtra("ID");
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        imageView.setImageBitmap(image);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"수량을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    String i=editText.getText().toString();
                    String p=textView2.getText().toString();
                    int num=Integer.parseInt(p) * Integer.parseInt(i);
                    String number=Integer.toString(num);
                    result.setText(number+"원");

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소하였습니다.",Toast.LENGTH_SHORT).show();
                finish();



            }
        });



        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"금액은 "+result.getText()+"입니다. 구매해주셔서 감사합니다.",Toast.LENGTH_LONG).show();
                dBHelper=DBgo.getInstance(getApplicationContext()); //데이터 베이스 쓸수 있게
                sqlDB= dBHelper.getWritableDatabase();
                String purchase= goLogin.p;
                sqlDB.execSQL("UPDATE IdPassword SET  gbuy='"+name +"'WHERE gId='"+purchase+"';");

            }
        });



    }
    class BuyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
