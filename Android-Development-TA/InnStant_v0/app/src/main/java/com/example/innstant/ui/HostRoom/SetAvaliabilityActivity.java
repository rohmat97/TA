package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.innstant.R;
import com.example.innstant.ui.DashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetAvaliabilityActivity extends AppCompatActivity {
    @BindView(R.id.datePicker1)
    DatePicker datePicker1;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.end)
    TextView end;
    @BindView(R.id.startDate)
    Button startDate;
    @BindView(R.id.endDate)
    Button endDate;
    @BindView(R.id.save)
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_avaliability);
        ButterKnife.bind(this);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setText("Selected Date: "+ datePicker1.getDayOfMonth()+"/"+ (datePicker1.getMonth() + 1)+"/"+datePicker1.getYear());
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end.setText("Selected Date: "+ datePicker1.getDayOfMonth()+"/"+ (datePicker1.getMonth() + 1)+"/"+datePicker1.getYear());
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAvaliabilityActivity.this, DashboardActivity.class);
                startActivity(intent);
                Bundle bundle = getIntent().getExtras();
                String json1 = bundle.getString("email");
                intent.putExtra("email", json1);
                finish();
            }
        });

    }
}
