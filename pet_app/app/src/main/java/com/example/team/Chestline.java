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

public class Chestline extends AppCompatActivity {

    ChestLineAdapter adapter1=new ChestLineAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) { //가슴줄 클래스
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chestline);
        setTitle("가슴줄");
        ListView listView1=findViewById(R.id.chestline);

        adapter1.addItem(new listitemInformation("강아지하네스 카민프로젝트","37800",R.drawable.chestline1));
        adapter1.addItem(new listitemInformation("후르츠 수박 린넨, 강아지 하네스","28440",R.drawable.chestline2));
        adapter1.addItem(new listitemInformation("패리스독 에어 프레시 하네스 가슴줄","9880",R.drawable.chestline3));
        adapter1.addItem(new listitemInformation("강아지목줄 퍼피아 강아지하네스 애견가슴줄 다양한 색상","10600",R.drawable.chestline4));
        adapter1.addItem(new listitemInformation("엘씨케이글로벌 옥희독희 세이프 하네스 반려동물 가슴줄","13880",R.drawable.chestline5));
        adapter1.addItem(new listitemInformation("패리스독 가슴줄 펫스타일 GM 백팩 하네스 강아지줄","6140",R.drawable.chestline6));
        adapter1.addItem(new listitemInformation("강아지하네스 바커 이지가슴줄 세트","24900",R.drawable.chestline7));


        listView1.setAdapter(adapter1);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Buy.class);

                listitemInformation item=(listitemInformation) adapter1.getItem(position);
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
    class ChestLineAdapter extends BaseAdapter {

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
            itemView view=new itemView(getApplicationContext());

            listitemInformation item=items.get(position);
            view.setName(item.getName());
            view.setNum(item.getnum());
            view.setImage(item.getResID());

            return view;
        }
    }
}
