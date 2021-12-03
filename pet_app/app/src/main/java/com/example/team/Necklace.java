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

public class Necklace extends AppCompatActivity { //인식표 클래스
    neAdapter adapter=new neAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogpad);
        setTitle("인식표");
        ListView listView=findViewById(R.id.dogpad);


        adapter.addItem(new listitemInformation("굿마이펫 강아지 고양이 애견 인식표 이름표 목걸이","3900",R.drawable.r1));
        adapter.addItem(new listitemInformation("왈가독 강아지 고양이 무료각인 키링 인식표 이름표 D형고리형 (뼈다귀형)","6900",R.drawable.r2));
        adapter.addItem(new listitemInformation("써지컬체인 강아지 고양이 목걸이 인식표","9500",R.drawable.r3));
        adapter.addItem(new listitemInformation("미소펫 인디칼라 가벼운 목줄 이름표 인식표 5색상","9900",R.drawable.r4));
        adapter.addItem(new listitemInformation("2+1펫츠룩 가볍고예쁜 강아지 고양이 인식표 목걸이","10900",R.drawable.r5));
        adapter.addItem(new listitemInformation("은가비펫 써지컬스틸 반려동물 애견목걸이 인식표","9900",R.drawable.r6));
        adapter.addItem(new listitemInformation("강아지 고양이 인식표","6900",R.drawable.r7));
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
    class neAdapter extends BaseAdapter {

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
