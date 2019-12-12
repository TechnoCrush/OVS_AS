package com.example.ovs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView6);
        String text = "'People' who elect corrupt 'Politicians' are not victims but 'Accomplices'";
        SpannableString ss = new SpannableString(text);
        StyleSpan bs = new StyleSpan(Typeface.BOLD);
        StyleSpan bs1 = new StyleSpan(Typeface.BOLD);
        StyleSpan bs2 = new StyleSpan(Typeface.BOLD);
        ss.setSpan(bs1,1,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bs2,28,39,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bs,62,73,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
