package com.example.ovs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.TwoStatePreference;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main3Activity extends AppCompatActivity {
    public TextView ts;
    public EditText es,otpVerify;
    private FirebaseAuth mAuth;
    private String verificationId;
    public String Contact,aadhar;
    public DatabaseReference rootRef,childRef;
    public static final String AADHARNUMBER="com.example.ovs.AADHARNUMBER";
            public CheckBox checkBox2;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        final Button button1 = findViewById(R.id.button5);
        Intent intent=getIntent();
        aadhar=intent.getStringExtra(HomeActivity.AADHARNUMBER);

        ts = findViewById(R.id.textView7);
        es = findViewById(R.id.editText3);
        mAuth= FirebaseAuth.getInstance();
        otpVerify=findViewById(R.id.editText3);
        final Button button3 =findViewById(R.id.button4);
        final CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);

        rootRef= FirebaseDatabase.getInstance().getReference("AADHAR");
        childRef=rootRef.child(aadhar);


       final Executor executor = Executors.newSingleThreadExecutor();

        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setNegativeButton("Cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).build();
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        checkBox.setChecked(true);
                    }
                });
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ts.setVisibility(View.VISIBLE);
                es.setVisibility(View.VISIBLE);
                button1.setVisibility(View.VISIBLE);
                sendVerificationCode(Contact);

            }
        });
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String otp=otpVerify.getText().toString();
                verifycode(otp);
            }
        });

        childRef.child("Contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Contact=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }







    //functions
    public void openActivity4(String aadhar)
    {
        checkBox2.setChecked(true);
        Intent intent = new Intent(this, Main4Activity.class);
        intent.putExtra(AADHARNUMBER,aadhar);
        startActivity(intent);
    }

    private void sendVerificationCode(String number)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBacks
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
            Toast.makeText(Main3Activity.this, "OTP"+verificationId, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
        {
            String code=phoneAuthCredential.getSmsCode();
            verifycode(code);

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e)
        {
            Toast.makeText(Main3Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };




    private void verifycode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {


                            openActivity4(aadhar);
                        }
                        else
                        {
                            Toast.makeText(Main3Activity.this, "UNAUTHORIZED ACCESS!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
