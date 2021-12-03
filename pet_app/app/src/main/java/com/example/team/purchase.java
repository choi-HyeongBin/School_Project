package com.example.team;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class purchase extends AppCompatActivity { //구매내역을 보여주는 클래스
    purchaseAdapter padapter=new purchaseAdapter();
    TextView tv1;
    DBgo dBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        setTitle("구매내역");

        tv1=findViewById(R.id.tv1);
        String a=goLogin.p;
        dBHelper=DBgo.getInstance(getApplicationContext()); //데이터 베이스 쓸수 있게
        sqlDB=dBHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM IdPassword WHERE gId='"+a+"';", null);
        String b="";
        while(cursor.moveToNext()){ //해당아이디와 비밀번호가 있으면 row 1줄을가져온다.
             b+= cursor.getString(2)+"\r\n";
        }
        tv1.setText(b);

    }
    class purchaseAdapter extends BaseAdapter {
        ArrayList<listitemInformation> items=new ArrayList<listitemInformation>();

        @Override
        public int getCount() {
            return items.size();
        }

        public  void addItem(listitemInformation item){
            items.add(item);
        }//아이템 추가
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            itemView view=null;
            if(convertView==null) {
                view=  new itemView(getApplicationContext());
            }else{
                view=(itemView)convertView;
            }
            listitemInformation item=items.get(position);
            view.setName(item.getName());
            view.setNum(item.getnum());
            view.setImage(item.getResID());

            return view;
        }
    }
}
