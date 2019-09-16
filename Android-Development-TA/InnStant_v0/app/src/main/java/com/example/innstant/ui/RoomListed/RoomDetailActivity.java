package com.example.innstant.ui.RoomListed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.example.innstant.ui.RentStatus.idleActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoomDetailActivity extends AppCompatActivity {
    Button viewstatus ;
    TextView desc,shower,food,wifi,acfan,parking,security,priceTag,namaKamar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");
        Gson gson = new Gson();
        Room room= gson.fromJson(json,Room.class);
        List amenis = new ArrayList<>() ;
        amenis = room.getAmenities();


        viewstatus =(Button) findViewById(R.id.viewStatusListed);
        desc = (TextView) findViewById(R.id.desc);
        shower = (TextView) findViewById(R.id.shower);
        food =(TextView) findViewById(R.id.food);
        wifi =(TextView) findViewById(R.id.wifi);
        acfan=(TextView) findViewById(R.id.acfan);
        parking=(TextView) findViewById(R.id.parking);
        security=(TextView) findViewById(R.id.security);
        priceTag=(TextView) findViewById(R.id.priceTag);
        namaKamar=(TextView) findViewById(R.id.namaKamar);

        shower.setText("Not Available");
        food.setText("Not Available");
        wifi.setText("Not Available");
        acfan.setText("Not Available");
        parking.setText("Not Available");
        security.setText("Not Available");

        priceTag.setText(room.getPrice().toString());
        namaKamar.setText(room.getName());

        List finalAmenis = amenis;

        for(int x=0 ; finalAmenis.size()>x  ; x++){
            if(finalAmenis.get(x) == null){

            }else{
                if(finalAmenis.get(x).toString().equals("Shower")){
                    shower.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Food")){
                    food.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Wifi")){
                    wifi.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Ac/Fan")){
                    acfan.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Parking")){
                    parking.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Security")){
                    security.setText(finalAmenis.get(x).toString());
                }
            }

        }
        viewstatus.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDetailActivity.this, idleActivity.class);
                intent.putExtra("data",json);
                intent.putExtra("email",json1);
                startActivity(intent);
                //Toast.makeText(RoomDetailActivity.this, finalAmenis.toString(),Toast.LENGTH_LONG).show();
//                Log.d("test", String.valueOf(finalAmenis.length));
            }
        });
    }
}
