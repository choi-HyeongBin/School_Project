package com.example.team;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.team.R;

public class fragment1 extends Fragment {//홈화면
    main activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(main) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity=null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment1,container,false);

        Button button1=rootView.findViewById(R.id.button1);
        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChanged(2);
            }
        });
        Button button2=rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChanged(3);
            }
        });
        Button button3=rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChanged(4);
            }
        });
*/
        GridView gridView=rootView.findViewById(R.id.gridView1);

        gridView.setAdapter(new MyAdapter(getActivity()));
        return rootView;

    }

    class MyAdapter extends BaseAdapter {
        Context context;

        private  Integer[] images={R.drawable.cat,R.drawable.dog1,R.drawable.hedgehog,R.drawable.rabbit,R.drawable.tiger,R.drawable.dog2,
                R.drawable.dog3,R.drawable.dog4,R.drawable.dog5,R.drawable.squirrel,R.drawable.lizard
        ,R.drawable.cat2,R.drawable.cat3,R.drawable.cat4};

        public MyAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView==null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                imageView.setPadding(10,10,10,10);

            }else{
                imageView=(ImageView)convertView;
            }
            imageView.setImageResource(images[position]);

            return imageView;
        }
    }
}
