package com.example.team;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class defecation_plate extends AppCompatActivity {//배변판 클래스
    dAdapter adapter=new dAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogpad);
        setTitle("배변판");
        ListView listView=findViewById(R.id.dogpad);


        adapter.addItem(new listitemInformation("초코펫하우스 논슬립 클린 사각 배변판 매트 중형","35990",R.drawable.d1));
        adapter.addItem(new listitemInformation("펫 페스터 배변판","8100",R.drawable.d2));
        adapter.addItem(new listitemInformation("펫토리아 한판뚝딱 그물망 토일렛 실속형 배변판","8070",R.drawable.d3));
        adapter.addItem(new listitemInformation("씬바이 배변 베리어 매트 중형","35200",R.drawable.d4));
        adapter.addItem(new listitemInformation("아이리스 기본형 간편 강아지 배변판 대형 FMT-635","13300",R.drawable.d5));
        adapter.addItem(new listitemInformation("레드퍼피 쉴 펫페스터 배변판","9010",R.drawable.d6));
        adapter.addItem(new listitemInformation("요기펫 반려견용 자이언트 요기 토일렛 660 x 450 x 35 mm","16150",R.drawable.d7));
        adapter.addItem(new listitemInformation("딩동펫 강아지 배변판","25900",R.drawable.d8));
        adapter.addItem(new listitemInformation("아가명가 대형견용 삼면 배변판 AMT-380","28710",R.drawable.d9));
        adapter.addItem(new listitemInformation("아가명가 그랜드 배변판 특대형 AMT-1100","44630",R.drawable.d10));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getApplicationContext(),Buy.class);

                listitemInformation item=(listitemInformation) adapter.getItem(position);
                Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(),item.resID);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("name",item.getName());
                intent.putExtra("price",item.getnum());
                intent.putExtra("ID",byteArray);
                startActivity(intent);
            }
        });

    }
    class dAdapter extends BaseAdapter {

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
