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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Tshirt extends AppCompatActivity { //티셔츠클래스

    tAdapter adapter=new tAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogpad);
        setTitle("티셔츠/후드");
        ListView listView=findViewById(R.id.dogpad);

        adapter.addItem(new listitemInformation("자외선차단 아이스 스킨 대형견쿨조끼","23400",R.drawable.t1));
        adapter.addItem(new listitemInformation("강아지옷 이월특가전 96종","3900",R.drawable.t2));
        adapter.addItem(new listitemInformation("투스투스 강아지 쿨조끼 크롭 나시티","14000",R.drawable.t3));
        adapter.addItem(new listitemInformation("아리코 고양이 앤 강아지 코튼 스트라이프 티셔츠","3550",R.drawable.t4));
        adapter.addItem(new listitemInformation("펫델 강아지옷 지지미 여름 체크 크롭 끈나시","16000",R.drawable.t5));
        adapter.addItem(new listitemInformation("유앤펫 강아지옷 로맨스 체크 세일러 셔츠","16000",R.drawable.t6));
        adapter.addItem(new listitemInformation("플로트 민소매 스트라이프 티셔츠","14000",R.drawable.t7));
        adapter.addItem(new listitemInformation("울리 래그런 티셔츠","7060",R.drawable.t8));
        adapter.addItem(new listitemInformation("울리 세인트 티셔츠","7900",R.drawable.t9));
        adapter.addItem(new listitemInformation("토토앤로이 로젠 프릴카라 티셔츠","16038",R.drawable.t10));

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
    class tAdapter extends BaseAdapter{

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
