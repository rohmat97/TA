package com.example.innstant.ui.HostRoom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.example.innstant.viewmodel.HostViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralDescriptionActivity extends AppCompatActivity implements LocationListener {
    @BindView(R.id.alamat)
    EditText alamat;
    @BindView(R.id.rt)
    EditText rt;
    @BindView(R.id.rw)
    EditText rw;
    @BindView(R.id.provinsi)
    EditText provinsi;
    @BindView(R.id.kota)
    EditText kota;
    @BindView(R.id.kelurahan)
    EditText kelurahan;
    @BindView(R.id.kodepos)
    EditText kodepos;
    @BindView(R.id.latitude)
    EditText latitude;
    @BindView(R.id.longitude)
    EditText longitude;
    @BindView(R.id.roomName)
    EditText roomName;
    @BindView(R.id.roomType)
    Spinner roomType;
    @BindView(R.id.shower)
    CheckBox shower;
    @BindView(R.id.food)
    CheckBox food;
    @BindView(R.id.wifi)
    CheckBox wifi;
    @BindView(R.id.acorfan)
    CheckBox acorfan;
    @BindView(R.id.parking)
    CheckBox parking;
    @BindView(R.id.security)
    CheckBox security;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.Save)
    Button Save;
    private HostViewModel mViewModel;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String lati, longi;
    protected boolean gps_enabled, network_enabled;
    String showert;
    String foodt;
    String wifit;
    String AcFant;
    String parkingt;
    String securityt;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_description);
        ButterKnife.bind(this);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        mViewModel = ViewModelProviders.of(GeneralDescriptionActivity.this).get(HostViewModel.class);
////        Toast.makeText(GeneralDescriptionActivity.this,loc,Toast.LENGTH_LONG).show();
//        location =(EditText) findViewById(R.id.location);
//        roomName =(EditText) findViewById(R.id.roomName);
//        roomType = (EditText) findViewById(R.id.roomType);
//        description =(EditText) findViewById(R.id.description);
//        Editext_shower =(CheckBox) findViewById(R.id.shower);
//        Editext_food   =(CheckBox) findViewById(R.id.food);
//        Editext_wifi =(CheckBox) findViewById(R.id.wifi);
//        Editext_AcFan=(CheckBox) findViewById(R.id.acorfan);
//        Editext_parking=(CheckBox) findViewById(R.id.parking);
//        Editext_security=(CheckBox) findViewById(R.id.security);
//retrieve a reference to an instance of TelephonyManager
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();

            }
        });
    }

    private void postData() {
        mViewModel.openServerConnection();
        Room room = new Room();
        room.setName(roomName.getText().toString());
        room.setType(String.valueOf(roomType.getSelectedItem()));
        room.setDescription(description.getText().toString());
        ArrayList<String> ameni = new ArrayList<String>();
        if (acorfan.isChecked()) {
            AcFant = "Ac/Fan";
        } else {
            AcFant = null;
        }
        if (shower.isChecked()) {
            showert = "Shower";
        } else {
            showert = null;
        }
        if (wifi.isChecked()) {
            wifit = "Wifi";
        } else {
            wifit = null;
        }
        if (food.isChecked()) {
            foodt = "Food";
        } else {
            foodt = null;
        }
        if (parking.isChecked()) {
            parkingt = "Parking";
        } else {
            parkingt = null;
        }
        if (security.isChecked()) {
            securityt = "Security";
        } else {
            securityt = null;
        }

        ameni.add(showert);
        ameni.add(foodt);
        ameni.add(wifit);
        ameni.add(AcFant);
        ameni.add(parkingt);
        ameni.add(securityt);

        room.setAmenities(ameni);
        Gson gson = new Gson();
        String paramString = gson.toJson(room);
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");

        Intent intent = new Intent(GeneralDescriptionActivity.this, AddPictureActivity.class);
        intent.putExtra("dataRoom", paramString);
        intent.putExtra("email", json);
        startActivity(intent);

    }


    @Override
    public void onLocationChanged(Location location) {
//        alamat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        latitude.setText(String.valueOf(location.getLatitude()));
        longitude.setText(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
}

