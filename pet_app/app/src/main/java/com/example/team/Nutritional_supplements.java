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

public class Nutritional_supplements extends AppCompatActivity { //영양제 클래스
   nAdapter adapter=new nAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogpad);
        setTitle("영양제");
        ListView listView=findViewById(R.id.dogpad);

        adapter.addItem(new listitemInformation("인핸서 반려견 관절영양제 340g","26700",R.drawable.n1));
        adapter.addItem(new listitemInformation("포켄스 애견 영양제 뉴트리션 트릿 관절 앤 뼈","13500",R.drawable.n2));
        adapter.addItem(new listitemInformation("포켄스 애견 영양제 뉴트리션 트릿 피부&피모","13500",R.drawable.n3));
        adapter.addItem(new listitemInformation("리얼펫 생유산균 강아지 영양제 60포","19800",R.drawable.n4));
        adapter.addItem(new listitemInformation("리얼펫 조인트케어 강아지 영양제 60포","19800",R.drawable.n5));
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
    class nAdapter extends BaseAdapter {

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
