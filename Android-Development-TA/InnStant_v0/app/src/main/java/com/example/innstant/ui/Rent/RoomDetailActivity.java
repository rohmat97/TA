package com.example.innstant.ui.Rent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
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
    @BindView(R.id.showerRent)
    TextView showerRent;
    @BindView(R.id.foodRent)
    TextView foodRent;
    @BindView(R.id.wifiRent)
    TextView wifiRent;
    @BindView(R.id.acfanRent)
    TextView acfanRent;
    @BindView(R.id.parkingRent)
    TextView parkingRent;
    @BindView(R.id.securityRent)
    TextView securityRent;
    @BindView(R.id.ratings)
    TextView ratings;
    @BindView(R.id.reviewRent)
    RecyclerView reviewRent;
    @BindView(R.id.showAllReview)
    TextView showAllReview;
    @BindView(R.id.bookRent)
    Button bookRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_rent);
        ButterKnife.bind(this);
        setTitle("Room Detail");


        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");
        Gson gson = new Gson();
        Room room = gson.fromJson(json, Room.class);
        List amenis = new ArrayList<>();
        amenis = room.getAmenities();

        showerRent.setText("Not Available");
        foodRent.setText("Not Available");
        wifiRent.setText("Not Available");
        acfanRent.setText("Not Available");
        parkingRent.setText("Not Available");
        securityRent.setText("Not Available");

        priceTag.setText(room.getPrice().toString());
        namaKamar.setText(room.getName());

        List finalAmenis = amenis;

        for (int x = 0; finalAmenis.size() > x; x++) {
            if (finalAmenis.get(x) == null) {

            } else {

                if (finalAmenis.get(x).toString().equals("Shower")) {
                    showerRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Food")) {
                    foodRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Wifi")) {
                    wifiRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Ac/Fan")) {
                    acfanRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Parking")) {
                    parkingRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Security")) {
                    securityRent.setText(finalAmenis.get(x).toString());
                }
            }
        }

        Button selectdate = (Button) findViewById(R.id.bookRent);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDetailActivity.this, SelectDateActivity.class);
                intent.putExtra("data", json);
                intent.putExtra("email", json1);
                startActivity(intent);
            }
        });
    }
}
