package com.example.team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DogPad extends AppCompatActivity { //배변패드클래스

    PadAdapter adapter=new PadAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogpad);
        setTitle("강아지배변패드");
        ListView listView=findViewById(R.id.dogpad);

        adapter.addItem(new listitemInformation("펫클럽 벨버드 강아지 배변패드 100매","7900",R.drawable.pad1));
        adapter.addItem(new listitemInformation("요요쉬 배변패드 플로랄와인향 소형 50매","6870",R.drawable.pad2));
        adapter.addItem(new listitemInformation("울강지 절약형 패드 100매","5500",R.drawable.pad3));
        adapter.addItem(new listitemInformation("요요쉬 패드 플로랄와인향 대형 20매","6670",R.drawable.pad4));
        adapter.addItem(new listitemInformation("애견산업 아몬스 명품 패드 50매","6290",R.drawable.pad5));
        adapter.addItem(new listitemInformation("노마진 반려견 배변패드 100매입","20805",R.drawable.pad6));
        adapter.addItem(new listitemInformation("내생애 첫 작품 대용량패드 100매","9900",R.drawable.pad7));
        adapter.addItem(new listitemInformation("피마트 절약형 패드 100매","6440",R.drawable.pad8));
        adapter.addItem(new listitemInformation("퍼피맘스 애견패드100매+100매 소형견부터 대형견까지","12150",R.drawable.pad9));
        adapter.addItem(new listitemInformation("아몬스 절약형 패드 소형 400매","24860",R.drawable.pad10));

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
    class PadAdapter extends BaseAdapter{

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
