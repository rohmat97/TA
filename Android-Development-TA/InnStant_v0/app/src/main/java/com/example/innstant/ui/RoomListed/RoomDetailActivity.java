package com.example.innstant.ui.RoomListed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.example.innstant.ui.RentStatus.idleActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomDetailActivity extends AppCompatActivity {
    @BindView(R.id.namaKamar)
    TextView namaKamar;
    @BindView(R.id.priceTag)
    TextView priceTag;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.shower)
    TextView shower;
    @BindView(R.id.food)
    TextView food;
    @BindView(R.id.wifi)
    TextView wifi;
    @BindView(R.id.acfan)
    TextView acfan;
    @BindView(R.id.parking)
    TextView parking;
    @BindView(R.id.security)
    TextView security;
    @BindView(R.id.ratings)
    TextView ratings;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.viewStatusListed)
    Button viewStatusListed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");
        Gson gson = new Gson();
        Room room = gson.fromJson(json, Room.class);
        List amenis = new ArrayList<>();
        amenis = room.getAmenities();




        shower.setText("Not Available");
        food.setText("Not Available");
        wifi.setText("Not Available");
        acfan.setText("Not Available");
        parking.setText("Not Available");
        security.setText("Not Available");

        priceTag.setText(room.getPrice().toString());
        namaKamar.setText(room.getName());

        List finalAmenis = amenis;

        for (int x = 0; finalAmenis.size() > x; x++) {
            if (finalAmenis.get(x) == null) {

            } else {
                if (finalAmenis.get(x).toString().equals("Shower")) {
                    shower.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Food")) {
                    food.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Wifi")) {
                    wifi.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Ac/Fan")) {
                    acfan.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Parking")) {
                    parking.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Security")) {
                    security.setText(finalAmenis.get(x).toString());
                }
            }

        }
        viewStatusListed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDetailActivity.this, idleActivity.class);
                intent.putExtra("data", json);
                intent.putExtra("email", json1);
                startActivity(intent);
                //Toast.makeText(RoomDetailActivity.this, finalAmenis.toString(),Toast.LENGTH_LONG).show();
//                Log.d("test", String.valueOf(finalAmenis.length));
            }
        });
    }
}
