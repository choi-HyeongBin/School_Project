package com.example.team;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class fragment3 extends Fragment {
    private ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();
    private ListView listView;
    RecipeAdapter adapter;
    EditText editText1;
    ImageButton imageButton;

    private final int GET_GALLERY_IMAGE = 200; //이미지 값
    Uri selectedImage;

    class RecipeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(RecipeItem item) {
            items.add(item);
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            RecipeItemView view = null;
            if(convertView == null) {
                view = new RecipeItemView(getActivity().getApplicationContext());
                Log.i("convertView", "재사용안됨");
            }else {
                view = (RecipeItemView) convertView;
                Log.i("convertView", "재사용됨");
            }

            RecipeItem item = items.get(position);
            view.setName(item.getName());
            view.setImage(item.getResId());

            return view;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3,container,false);

        listView = (ListView) rootView.findViewById(R.id.listView1);
        editText1 = (EditText) rootView.findViewById(R.id.et1);
        imageButton = (ImageButton) rootView.findViewById(R.id.imgView1);

        adapter = new RecipeAdapter();
        adapter.addItem(new RecipeItem("북어국", R.drawable.img1));
        adapter.addItem(new RecipeItem("닭볶음탕", R.drawable.img2));
        adapter.addItem(new RecipeItem("닭고기 수프", R.drawable.img3));
        adapter.addItem(new RecipeItem("닭가슴살 테린", R.drawable.img4));
        adapter.addItem(new RecipeItem("고구마 팬케이크", R.drawable.img5));
        adapter.addItem(new RecipeItem("단호박 퓨레", R.drawable.img6));

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                RecipeItem item = (RecipeItem) adapter.getItem(position);

                switch (position) {
                    case 0:
                        // 북어국 항목 클릭 이벤트
                        main activity = (main) getActivity();
                        activity.onFragmentChanged(5);
                        break;
                    case 1:
                        // 닭볶음탕 항목 클릭 이벤트
                        main activity1 = (main) getActivity();
                        activity1.onFragmentChanged(6);
                        break;
                    case 2:
                        // 닭고기 수프 항목 클릭 이벤트
                        main activity2 = (main) getActivity();
                        activity2.onFragmentChanged(7);
                        break;
                    case 3:
                        // 닭가슴살 테린 항목 클릭 이벤트
                        main activity3 = (main) getActivity();
                        activity3.onFragmentChanged(8);
                        break;
                    case 4:
                        // 고구마 팬케이크 항목 클릭 이벤트
                        main activity4 = (main) getActivity();
                        activity4.onFragmentChanged(9);
                        break;
                    case 5:
                        // 단호박 퓨레 항목 클릭 이벤트
                        main activity5 = (main) getActivity();
                        activity5.onFragmentChanged(10);
                        break;
                    case 6:
                        // 연어 스테이크 항목 클릭 이벤트
                        main activity6 = (main) getActivity();
                        activity6.onFragmentChanged(11);
                        break;
                }
            }
        });

        Button button = (Button) rootView.findViewById(R.id.btnWrite);
        button.setOnClickListener(new View.OnClickListener() { // 등록 버튼 클릭 시 이벤트
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString(); // editText1 입력된 제목 String 값 불러옴

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=2;
                String imagepath = "/sdcard/Pictures/";
                Bitmap bm = BitmapFactory.decodeFile(imagepath, options);
                imageButton.setImageBitmap(bm);

                adapter.addItem(new RecipeItem(name, R.drawable.img7)); // (제목, 이미지) 리스트뷰에 추가

                adapter.notifyDataSetChanged();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() { // 이미지버튼 등록 이벤트
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(); //이미지 불러오기
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage=data.getData();
            imageButton.setImageURI(selectedImage);
        }
    }
}
