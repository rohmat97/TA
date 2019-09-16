package com.example.innstant.ui.RentStatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.Room;
import com.example.innstant.data.model.Transaction;
import com.example.innstant.ui.HostRoom.SetRoomPricingActivity;
import com.example.innstant.ui.Rent.RoomDetailActivity;
import com.example.innstant.viewmodel.ListerRoomVewModel;
import com.example.innstant.viewmodel.idleViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;

public class idleActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");

        TextView from= (TextView) findViewById(R.id.from);
        TextView to =(TextView) findViewById(R.id.to);
        Button viewRequest =(Button) findViewById(R.id.viewRequestIdle);


        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(idleActivity.this, RoomDetailActivity.class);
                intent.putExtra("email",json1);
                intent.putExtra("data",json);
                startActivity(intent);
            }
        });

    }


}
