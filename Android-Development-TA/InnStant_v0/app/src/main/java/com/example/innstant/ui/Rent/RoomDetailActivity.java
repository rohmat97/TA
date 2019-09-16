package com.example.innstant.ui.Rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailActivity extends AppCompatActivity {

    TextView namaKamar,PriceTag,showerRent,foodRent,wifiRent,acfanRent,parkingRent,securityRent,ratings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_rent);
        setTitle("Room Detail");

        namaKamar=(TextView) findViewById(R.id.namaKamar);
        PriceTag=(TextView) findViewById(R.id.priceTag);
        showerRent=(TextView) findViewById(R.id.showerRent);
        foodRent=(TextView) findViewById(R.id.foodRent);
        wifiRent=(TextView) findViewById(R.id.wifiRent);
        acfanRent=(TextView) findViewById(R.id.acfanRent);
        parkingRent=(TextView) findViewById(R.id.parkingRent);
        securityRent=(TextView) findViewById(R.id.securityRent);
        ratings=(TextView) findViewById(R.id.ratings);

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("data");
        String json1 = bundle.getString("email");
        Gson gson = new Gson();
        Room room= gson.fromJson(json,Room.class);
        List amenis = new ArrayList<>() ;
        amenis = room.getAmenities();

        showerRent.setText("Not Available");
        foodRent.setText("Not Available");
        wifiRent.setText("Not Available");
        acfanRent.setText("Not Available");
        parkingRent.setText("Not Available");
        securityRent.setText("Not Available");

        PriceTag.setText(room.getPrice().toString());
        namaKamar.setText(room.getName());

        List finalAmenis = amenis;

        for(int x=0 ; finalAmenis.size()>x  ; x++){
            if(finalAmenis.get(x) == null){

            }else{

                if(finalAmenis.get(x).toString().equals("Shower")){
                    showerRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Food")){
                    foodRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Wifi")){
                    wifiRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Ac/Fan")){
                    acfanRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Parking")){
                    parkingRent.setText(finalAmenis.get(x).toString());
                }
                if (finalAmenis.get(x).toString().equals("Security")){
                    securityRent.setText(finalAmenis.get(x).toString());
                }
            }
        }

        Button selectdate = (Button) findViewById(R.id.bookRent);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomDetailActivity.this,SelectDateActivity.class);
                intent.putExtra("data",json);
                intent.putExtra("email",json1);
                startActivity(intent);
            }
        });
    }
}
