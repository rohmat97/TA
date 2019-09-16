package com.example.innstant.ui.RentStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.innstant.R;
import com.example.innstant.ui.DashboardActivity;

public class ReservedActivity extends AppCompatActivity {
    TextView checkin,duration,checkout,name,countingDuration;
    ImageView profileImage;
    Button checkIn,checkOut,chat,viewProfile,back;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved);
        checkin = (TextView) findViewById(R.id.checkInDate);
        checkout = (TextView) findViewById(R.id.checkout);
        duration = (TextView) findViewById(R.id.duration);
        countingDuration = (TextView) findViewById(R.id.countingDuration);
        name = (TextView) findViewById(R.id.nameReserved);
        profileImage = (ImageView) findViewById(R.id.pictureReserved);
        checkIn = (Button) findViewById(R.id.checkin);
        checkOut = (Button) findViewById(R.id.checkout);
        chat = (Button) findViewById(R.id.chat);
        viewProfile = (Button) findViewById(R.id.view);
        back = (Button) findViewById(R.id.back);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ReservedActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
