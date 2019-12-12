package com.example.ovs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity
{

    public static final String AADHARNUMBER="com.example.ovs.AADHARNUMBER";
    public DatabaseReference aadharRef,voterRef,childRef1,childRef2;

    private EditText ed;
    private EditText ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ed = findViewById(R.id.editText2);
        ed1 = findViewById(R.id.editText);

        aadharRef= FirebaseDatabase.getInstance().getReference("AADHAR");
        voterRef=FirebaseDatabase.getInstance().getReference("Voter");


        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                final String aadhar=ed.getText().toString().trim();
                String voter=ed1.getText().toString().trim().toUpperCase();

                if((aadhar.isEmpty() || aadhar.length()!=12 )) {
                    Toast.makeText(HomeActivity.this, "Please Enter Valid Aadhar Number", Toast.LENGTH_SHORT).show();
                }
                else{
                    if((voter.isEmpty()) || voter.length()!=8)
                    {
                        Toast.makeText(HomeActivity.this, "Enter Valid Voter ID", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        openActivity2(aadhar);
                    }


                }
            }
        });

    }

    public void openActivity2(String aadhar)
    {
        if(aadhar!=null){
            Intent intent = new Intent(this,Main2Activity.class);
            intent.putExtra(AADHARNUMBER,aadhar);

            startActivity(intent);
        }


    }
}
