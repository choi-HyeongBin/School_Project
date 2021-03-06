package com.example.firebase_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Signup1 extends AppCompatActivity {


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup1);


        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.signup_btn1).setOnClickListener(onClickListener);
        findViewById(R.id.signup_btn2).setOnClickListener(onClickListener);

    }
    @Override
    public void onStart(){
        super.onStart();

    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn1:
                    signup();
                    break;

                case R.id.signup_btn2:
                    Intent intent = new Intent(getApplication(),Main.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

            }
        }
    };

    private void signup(){
        final String email=((EditText)findViewById(R.id.signup_edt1)).getText().toString().trim()+"@sungkyul.ac.kr".trim();
        final String password=((EditText)findViewById(R.id.signup_edt3)).getText().toString();
        String passwordCheak=((EditText)findViewById(R.id.signup_edt4)).getText().toString();

        mAuth = FirebaseAuth.getInstance();
        if(email.length()>0 &&password.length()>0 && passwordCheak.length()>0){ //?????? ????????? ??????
            if (password.equals(passwordCheak)){//??????????????? ???????????????????????? ?????????
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mAuth.getCurrentUser().sendEmailVerification()//????????? ?????? ?????????
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //?????????
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        tostmsg("?????? ???????????? ??????????????? ???????????????.");
                                                        String uid=user.getUid();
                                                        Intent Signup_intent=new Intent(getApplicationContext(), Signup2.class);
                                                        Signup_intent.putExtra("uid",uid);
                                                        startActivity(Signup_intent);
                                                        finish();
                                                    }
                                                    else
                                                    {

                                                        Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    //?????????
                                    if(task.getException()!=null){
                                        tostmsg("??????????????? 6?????? ?????? ??????????????????.");
                                    }
                                }
                            }
                        });
            }
            else{
                tostmsg("??????????????? ???????????? ????????????");
            }
        }else{
            tostmsg("????????? ?????? ??????????????? ??????????????????.");
        }

    }
    private void tostmsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        /*super.onBackPressed();*/
    }





}