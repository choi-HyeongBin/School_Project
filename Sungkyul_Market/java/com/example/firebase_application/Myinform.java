package com.example.firebase_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Myinform extends AppCompatActivity {
    Button btn1,btn2,btn3;
    TextView tv1,tv2,tv3,tv4,tv6;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Conditioninformation> arrayList1=new ArrayList<>();
    private String id;
    private ArrayList<Conditioninformation> myList3=new ArrayList<>();
    private  ArrayList myList4=new ArrayList<>();

    String uid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinform);

        btn1=findViewById(R.id.myinform_btn1);
        btn2=findViewById(R.id.myinform_btn2);
        tv1=findViewById(R.id.myinform_tv2);
        tv2=findViewById(R.id.myinform_tv4);
        tv3=findViewById(R.id.myinform_tv6);
        tv4=findViewById(R.id.myinform_tv8);

        tv6=findViewById(R.id.myinform_tv12);
        btn3=findViewById(R.id.myinform_btn3);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(getApplicationContext(), Myinformchange.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT).show();

                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(getApplicationContext(), Repass.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        id = user.getEmail();
                        database = FirebaseDatabase.getInstance();//?????????????????? ?????????????????? ??????
                        databaseReference = database.getReference("Trade state"); //DB????????? ??????
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arrayList1.clear();
                                myList3.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {//??????????????? ????????? List??? ????????????
                                    Conditioninformation users = snapshot.getValue(Conditioninformation.class); //??????????????? User????????? ???????????? ?????????.
                                    arrayList1.add(users); //?????? ??????????????? ?????????????????? ?????? ????????????????????? ????????????

                                }
                                for (Conditioninformation object : arrayList1) {
                                    if (object.getUseremail().contains(id)) {
                                        myList3.add(object);
                                    }
                                }
                                for (Conditioninformation object : arrayList1) {
                                    if (object.getBuyeremail().contains(id)) {
                                        myList4.add(object);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //????????? ??????????????? ?????? ?????????
                                Log.e("recycerview.java", String.valueOf(databaseError.toException())); //????????? ??????
                            }
                        });


                        AlertDialog.Builder dlg = new AlertDialog.Builder(Myinform.this);
                        dlg.setTitle("??????");
                        dlg.setMessage("??????????????? ???????????????????");
                        dlg.setIcon(R.mipmap.ic_launcher);
                        dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(Myinform.this);
                                dlg.setTitle("??????");
                                dlg.setMessage("????????? ??????????????? ???????????????????\n????????? ???????????? ??????????????? ???????????? ????????? ??????????????????.");

                                dlg.setIcon(R.mipmap.ic_launcher);
                                dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        if (myList3.isEmpty() && myList4.isEmpty()) {

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                            final String email = user.getEmail();
                                            final String uid = user.getUid();

                                            databaseReference = database.getReference("User_information").child(uid); //DB????????? ??????
                                            databaseReference.setValue(null);

                                            databaseReference = database.getReference("User").child(uid); //DB????????? ??????
                                            databaseReference.setValue(null);

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Product2");
                                            Query mQuery = databaseReference.orderByChild("useremail").equalTo(email);

                                            mQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagesend");
                                            Query mQuery2 = databaseReference.orderByChild("sendemail").equalTo(email);

                                            mQuery2.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Messagereceive");
                                            Query mQuery3 = databaseReference.orderByChild("receiveemail").equalTo(email);

                                            mQuery3.addListenerForSingleValueEvent(new ValueEventListener() {

                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                                        ds.getRef().removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });



                                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "??????.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                            AlertDialog.Builder dlg2 = new AlertDialog.Builder(Myinform.this);
                                            dlg2.setTitle("??????");
                                            dlg2.setMessage("??????????????? ???????????????.");
                                            dlg2.setIcon(R.mipmap.ic_launcher);
                                            dlg2.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent = new Intent(getApplicationContext(), Main.class);
                                                    startActivity(intent);

                                                }
                                            });
                                            dlg2.show();

                                            myList3.clear();
                                            arrayList1.clear();


                                        } else {
                                            AlertDialog.Builder dlg2 = new AlertDialog.Builder(Myinform.this);
                                            dlg2.setTitle("??????");
                                            dlg2.setMessage("????????? ????????? ????????? ??????????????? ??????????????????.");
                                            dlg2.setIcon(R.mipmap.ic_launcher);
                                            dlg2.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    myList3.clear();
                                                    arrayList1.clear();
                                                    Intent intent = new Intent(getApplicationContext(), Myinform.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);


                                                }
                                            });
                                            dlg2.show();


                                        }


                                    }


                                });
                                dlg.setNegativeButton("??????", null);
                                dlg.show();


                            }


                        });
                        dlg.setNegativeButton("??????", null);
                        dlg.show();
                    } else { Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        if (user != null) {

            String email = user.getEmail();
            tv6.setText(email);
        }


        if (user != null) {
            uid = user.getUid();//?????? ?????? UID ?????????
            database = FirebaseDatabase.getInstance();//?????????????????? ?????????????????? ??????


            databaseReference = database.getReference("User_information"); //DB????????? ??????
            databaseReference.child(uid).child("name").addValueEventListener(new ValueEventListener() {//??????????????? ???????????? ???????????? ?????????
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str = snapshot.getValue(String.class);
                    //?????? ?????? ???????????? ????????? ?????????
                    if (str != null) {
                        tv1.setText(str);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            databaseReference.child(uid).child("school").addValueEventListener(new ValueEventListener() {//??????????????? ???????????? ???????????? ?????????
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str2 = snapshot.getValue(String.class);
                    //?????? ?????? ???????????? ????????? ?????????
                    if (str2 != null) {
                        tv2.setText(str2);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            databaseReference.child(uid).child("major").addValueEventListener(new ValueEventListener() {//??????????????? ???????????? ???????????? ?????????
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str3 = snapshot.getValue(String.class);
                    //?????? ?????? ???????????? ????????? ?????????
                    if (str3 != null) {
                        tv3.setText(str3);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference.child(uid).child("number").addValueEventListener(new ValueEventListener() {//??????????????? ???????????? ???????????? ?????????
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String str4 = snapshot.getValue(String.class);
                    //?????? ?????? ???????????? ????????? ?????????
                    if (str4 != null) {
                        tv4.setText(str4);



                    } else {


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }

    }

}
