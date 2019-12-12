package com.example.ovs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class candidate_details extends AppCompatActivity
{

    private DatabaseReference rootref2,childref2;
    public static final String AADHARNUMBER ="com.example.ovs.AADHARNUMBER" ;
    public String aadhar,verify;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_details);

        Intent intent=getIntent();
        aadhar=intent.getStringExtra(HomeActivity.AADHARNUMBER);

        rootref2= FirebaseDatabase.getInstance().getReference("VOTE");
        childref2=rootref2.child(aadhar);

        childref2.child("VERIFY").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                verify=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.buttonVote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(verify.equals("NO"))
                {
                    childref2=rootref2.child(aadhar);
                    childref2.child("VERIFY").setValue("YES");
                    Toast.makeText(candidate_details.this, "VOTE SUCCESS", Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(candidate_details.this, "SORRY YOU HAVE ALREADY VOTED", Toast.LENGTH_SHORT).show();
                }

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        VoteSuccess(aadhar);
                    }
                },2000);

            }
        });


    }

    private void VoteSuccess(String aadhar)
    {
        Intent intent = new Intent(this,endScreen.class);
        intent.putExtra(AADHARNUMBER,aadhar);
        startActivity(intent);

    }


}
