package com.example.ovs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity
{

   public String candidate,aadhar;
   public TextView cd1,cd2,cd3,cd4,cd5;

    public static final String AADHARNUMBER="com.example.ovs.AADHARNUMBER";
    public static final String CANDIDATE="com.example.ovs.CANDIDATE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        cd1=findViewById(R.id.party1);
        cd2=findViewById(R.id.party2);
        cd3=findViewById(R.id.party3);
        cd4=findViewById(R.id.party4);
        cd5=findViewById(R.id.party5);

        Intent intent=getIntent();
        aadhar=intent.getStringExtra(HomeActivity.AADHARNUMBER);

        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            openNextScreen("BJP");
            }
        });

        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextScreen("CONGRESS");
            }
        });

        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextScreen("JDS");
            }
        });

        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextScreen("ADMK");
            }
        });

        findViewById(R.id.imageButton5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextScreen("NOTA");
            }
        });



    }

    private void openNextScreen(String candidate)
    {
        Intent intent = new Intent(this, candidate_details.class);
        intent.putExtra(AADHARNUMBER,aadhar);
        intent.putExtra(CANDIDATE,candidate);
        startActivity(intent);
    }
}
