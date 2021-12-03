package com.example.team;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class fragment2 extends Fragment {//분양화면클래스

    ImageButton ib; //리스트뷰의 오른쪽 글쓰기 그림 표시하기위해
    WriteAdapter adapter;
    String name="hi",mobile="hi2"; //fragment2에서 write 에게 값을 주기 위해
    public static final int sub = 1001; //frament2에서 write 갔다가 다시 fragment로 받기위해
    Bitmap bm;

    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment2,container,false);
        ib=rootView.findViewById(R.id.ibWrite);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //글쓰기 클릭 후에 이름과 전화번호를 받아오기 위해 아무값을 집어넣은 name과mobile을 write에게 전달해주고
                Intent intent=new Intent(getActivity(),write.class);
                intent.putExtra("name",name);
                intent.putExtra("mobile",mobile);
                startActivityForResult(intent,sub);
            }
        });

        //비트맵으로 변환한 사진들 (그래야 어댑터에 넣을 수 있다)
        Bitmap dog1=BitmapFactory.decodeResource(getResources(), R.drawable.dog1);
        Bitmap cat=BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        Bitmap hedgehog=BitmapFactory.decodeResource(getResources(), R.drawable.hedgehog);
        Bitmap rabbit=BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);
        Bitmap tiger=BitmapFactory.decodeResource(getResources(), R.drawable.tiger);

        ListView listView=rootView.findViewById(R.id.listView);
        adapter = new WriteAdapter();
        adapter.addItem(new WriteItem("강아지",dog1,"윤아","010-1111-1111"));
        adapter.addItem(new WriteItem("고양이",cat,"소원","010-2222-2222"));
        adapter.addItem(new WriteItem("고슴도치",hedgehog,"은하","010-3333-3333"));
      //  adapter.addItem(new WriteItem("토끼",rabbit,"아이린","010-4444-4444"));
     //   adapter.addItem(new WriteItem("호랑이",tiger,"슬기","010-5555-5555"));

        listView.setAdapter(adapter);

        //여기서 부터 어플 나갔다 들어와도 그대로 리스트뷰에 저장되어있게 하는 코드
        myDBHelper=new myDBHelper(getActivity());
        sqlDB=myDBHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null); //데이터베이스 안 내용조회

        if(sqlDB!=null){ //sqlDB가 null이 아니라면 아래 내용 실행
            String re ="";
            String re1 ="";
            String Spe="";
            String ima="";
            while ((cursor.moveToNext())){
                re+=cursor.getString(0);
                re1+=cursor.getString(1);
                Spe+=cursor.getString(2);
                ima+=cursor.getString(3);

                //아래 코드는 데이터베이스에 저장된 코드(ima) String 형을 bitmap으로 변환해주는 것
                byte[] decodedByteArray = Base64.decode(ima.trim(), Base64.NO_WRAP);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

                adapter.addItem(new WriteItem(Spe,decodedBitmap,re,re1));
               // Bitmap Img2= BitmapFactory.decodeByteArray(ima,0,ima.length());
                re="";
                re1="";
                Spe="";
                ima="";
            }
        }

        return rootView;
    }

     String rec,rec1,species;
     Uri uurr,uurr1;
     Bitmap bm1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //frament2>write>fragment2
        if(resultCode==sub){ //코드가 sub랑 같다면 그 값들로 아이템 생성 (write.class에서 준 값)

            species=data.getStringExtra("species");
            species.trim();
            rec=data.getStringExtra("name");
            rec.trim();
            rec1=data.getStringExtra("mobile");
            rec1.trim();
            uurr=data.getParcelableExtra("image"); //이미지를 uri로 받아와서
            uurr1=data.getParcelableExtra("image");

            try{
                //이미지를 uri로 받아와서 bitmap으로 변환
            bm= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uurr);
            bm1= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uurr1);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            adapter.addItem(new WriteItem(species,bm,rec,rec1));
            adapter.notifyDataSetChanged();

            String uurr2=getBase64String(bm1); //bm1(bitmap)을 String으로 변환후 uurr2에 저장(SQLite에 String 형으로 저장하려고)

            sqlDB= myDBHelper.getWritableDatabase();
            sqlDB.execSQL("INSERT INTO groupTBL VALUES('"+rec+"','"+rec1+"','"+species+"','"+uurr2+"');");
            sqlDB.close();

            }else {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getBase64String(Bitmap bitmap) //비트맵을 String 형으로 변환해주는 클래스
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }


    //-------------------------------------------------------------------어댑터 뷰
    class WriteAdapter extends BaseAdapter{

            ArrayList<WriteItem> items=new ArrayList<WriteItem>(); //아이템 저장

            public void addItem(WriteItem item){
                items.add(item);
            }

            @Override
            public int getCount() {
                return items.size();
            }

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
                WriteItemView view=null; //리스트뷰 데이터 절약을 위해
                if(convertView==null) {
                    view = new WriteItemView(getActivity());
                }
                else{
                    view=(WriteItemView)convertView;
                }
                WriteItem item=items.get(position); //이 아이템에 값이 저장되었다.
                view.setName(item.getName());
                view.setMobile(item.getMobile());
                view.setImage(item.getImage());
                view.setspecies(item.getSpecies());
                return view;
            }
    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context) {
            super(context,"groupDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL(gName CHAR(20) PRIMARY KEY,gNumber CHAR(30),gSpecies CHAR(20), gImage CHAR(70));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}
