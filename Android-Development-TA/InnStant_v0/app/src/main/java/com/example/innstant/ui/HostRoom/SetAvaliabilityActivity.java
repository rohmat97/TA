package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innstant.R;
import com.example.innstant.ui.DashboardActivity;
import com.example.innstant.ui.RoomListed.ListedRoomActivity;

public class SetAvaliabilityActivity extends AppCompatActivity {
    Button set ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_avaliability);
        set = (Button) findViewById(R.id.setCalendar);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAvaliabilityActivity.this, DashboardActivity.class);
                startActivity(intent);
                Bundle bundle = getIntent().getExtras();
                String json1 = bundle.getString("email");
                intent.putExtra("email",json1);
                finish();
            }
        });

    }
}
