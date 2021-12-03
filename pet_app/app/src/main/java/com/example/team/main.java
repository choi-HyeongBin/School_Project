package com.example.team;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.team.R;

public class main extends AppCompatActivity implements ActionBar.TabListener { //홈,용품,레시피,분양 화면인 프래그먼트들을 가지는 메인화면
    ActionBar.Tab tabHome, tabParcel, tabSupply, tabRecipe;

    PetShop petShop;
    DogPad shop;
    Fragment fragment1 = new fragment1();
    Fragment fragment2 = new fragment2();
    Fragment fragment3 = new fragment3();
    Fragment fgm1 = new fgm1();
    Fragment fgm2 = new fgm2();
    Fragment fgm3 = new fgm3();
    Fragment fgm4 = new fgm4();
    Fragment fgm5 = new fgm5();
    Fragment fgm6 = new fgm6();
    Fragment fgm7 = new fgm7();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("너 내 집사가 되어라");

        petShop = new PetShop();
        shop = new DogPad();
//------------------------------------------------------------------------------------------------
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tabHome = bar.newTab();
        tabHome.setText("홈");
        tabHome.setTabListener(this);
        bar.addTab(tabHome);

        tabParcel = bar.newTab();
        tabParcel.setText("애완용품");
        tabParcel.setTabListener(this);
        bar.addTab(tabParcel);

        tabSupply = bar.newTab();
        tabSupply.setText("레시피");
        tabSupply.setTabListener(this);
        bar.addTab(tabSupply);

        tabRecipe = bar.newTab();
        tabRecipe.setText("분양");
        tabRecipe.setTabListener(this);
        bar.addTab(tabRecipe);

        //bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
        //bar.setDisplayUseLogoEnabled(false);
        //bar.setDisplayShowHomeEnabled(false);

    }
    FragmentManager fragmentManager=getFragmentManager();


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        int position = tab.getPosition(); //탭의 위치를 받아와서 그 위치에 따라 프래그먼트를 보여준다.

        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();
                break;
             case 1:
                 getSupportFragmentManager().beginTransaction().replace(R.id.container,petShop).commit();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment3).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment2).commit();
                break;
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.my:
                Intent intent=new Intent(this,myinformation.class);
                startActivity(intent);
                break;

            case R.id.purchase:
                Intent intent1=new Intent(this, purchase.class);
                startActivity(intent1);
                break;

        }
        return true;
    }

    public void onFragmentChanged(int index) {
        if(index==4){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment3).commit();
            tabRecipe.select();
        }else if(index==5){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm1).commit();
        }else if(index==6){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm2).commit();
        }else if(index==7){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm3).commit();
        }else if(index==8){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm4).commit();
        }else if(index==9){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm5).commit();
        }else if(index==10){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm6).commit();
        }else if(index==11){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fgm7).commit();
        }
    }
}
