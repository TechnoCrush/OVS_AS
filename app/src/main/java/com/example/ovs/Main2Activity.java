package com.example.ovs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Main2Activity extends AppCompatActivity
{

    public DatabaseReference rootRef,childRef;
    public String Aadhar,Name,Contact;
    public TextView aadharText,nameText,contactText;
    public ImageView img;
    public static final String AADHARNUMBER="com.example.ovs.AADHARNUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
        final String aadhar=intent.getStringExtra(HomeActivity.AADHARNUMBER);

        aadharText=findViewById(R.id.textID);
        nameText=findViewById(R.id.textName);
        contactText=findViewById(R.id.textParty);

        rootRef= FirebaseDatabase.getInstance().getReference("AADHAR");
        childRef=rootRef.child(aadhar);

        childRef.child("Aadhar").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Aadhar=dataSnapshot.getValue(String.class);
                aadharText.setText(Aadhar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        childRef.child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name=dataSnapshot.getValue(String.class);
                nameText.setText(Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        childRef.child("Contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Contact=dataSnapshot.getValue(String.class);
                contactText.setText(Contact);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.buttonVote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               openActivity2(aadhar);
            }
        });
    }



    public void openActivity2(String aadhar) {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra(AADHARNUMBER,aadhar);
        startActivity(intent);
    }
}
