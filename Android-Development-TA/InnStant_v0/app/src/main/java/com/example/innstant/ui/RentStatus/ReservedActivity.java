package com.example.innstant.ui.RentStatus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innstant.R;
import com.example.innstant.ui.DashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservedActivity extends AppCompatActivity {


    @BindView(R.id.pictureReserved)
    ImageView pictureReserved;
    @BindView(R.id.nameReserved)
    TextView nameReserved;
    @BindView(R.id.chat)
    Button chat;
    @BindView(R.id.view)
    Button view;
    @BindView(R.id.checkInDate)
    TextView checkInDate;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.checkOut)
    TextView checkOut;
    @BindView(R.id.countingDuration)
    TextView countingDuration;
    @BindView(R.id.checkin)
    Button checkin;
    @BindView(R.id.checkout)
    Button checkout;
    @BindView(R.id.back)
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved);
        ButterKnife.bind(this);

        final Intent[] intent = {new Intent()};

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(ReservedActivity.this, DashboardActivity.class);
                startActivity(intent[0]);
            }
        });
    }
}
