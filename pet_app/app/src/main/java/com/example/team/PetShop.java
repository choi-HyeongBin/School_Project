package com.example.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PetShop extends Fragment { //애완용품카테고리를 보여주는 클래스
    MainActivity activity;
    ArrayAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.petshop,container,false);

        final  String[] store={"강아지배변패드","가슴/목줄","티셔츠/후드","영양제","인식표","배변판"};

        ListView listView1=rootView.findViewById(R.id.listView1);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,store);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent=null;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=position;
                switch (pos){
                    case 0:
                        intent=new Intent(getActivity(), DogPad.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(getActivity(), Chestline.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(getActivity(), Tshirt.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent=new Intent(getActivity(), Nutritional_supplements.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent=new Intent(getActivity(), Necklace.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent=new Intent(getActivity(), defecation_plate.class);
                        startActivity(intent);
                        break;
                }

            }

        });

        return rootView;
    }


}
