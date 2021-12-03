package com.example.team;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class fgm7 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V =inflater.inflate(R.layout.fgm7, container, false);

        Button button = V.findViewById(R.id.btn7);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로가기 클릭시 이벤트
                FragmentManager manager;
                manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.container, new fragment3());
                transaction.addToBackStack(null).commit();

            }
        });

        return V;
    }
}
