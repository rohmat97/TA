package com.example.innstant.ui.RentStatus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innstant.R;
import com.example.innstant.ui.Rent.RoomDetailActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class idleActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.viewRequestIdle)
    Button viewRequestIdle;
    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.to)
    TextView to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");


        viewRequestIdle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(idleActivity.this, RoomDetailActivity.class);
                intent.putExtra("email", json1);
                intent.putExtra("data", json);
                startActivity(intent);
            }
        });

    }


}
