package com.example.innstant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.innstant.R;
import com.example.innstant.ui.HostRoom.GeneralDescriptionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetLocationActivity extends FragmentActivity {

    @BindView(R.id.setloc)
    Button setloc;
    @BindView(R.id.findloc)
    SearchView findloc;
    @BindView(R.id.location)
    EditText location;
    @BindView(R.id.nextinput)
    Button nextinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        ButterKnife.bind(this);
//        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
//        if (mapFragment != null) {
//            mapFragment.getAsyncMap(onMapReadyCallback);
//        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);

//
//    private final OnMapReadyCallback onMapReadyCallback =
//            new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(TomtomMap map) {
//                    //Map is ready here
//                    tomtomMap = map;
//                    tomtomMap.getUiSettings().setMapTilesType(MapTilesType.VECTOR);
//                    tomtomMap.setMyLocationEnabled(true);

            };

    @OnClick({R.id.setloc, R.id.nextinput})
    public void onViewClicked(View view) {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
        switch (view.getId()) {
            case R.id.setloc:
                location.setText("ds. Cinunuk , kec. Wanaraja, kab. Garut , Jawa Barat");
                break;
            case R.id.nextinput:
                Intent intent = new Intent(SetLocationActivity.this, GeneralDescriptionActivity.class);
                location = (EditText) findViewById(R.id.location);
                String loc = location.getText().toString();
                intent.putExtra("location",loc);
                intent.putExtra("email",json);
                startActivity(intent);
                break;
        }
    }
}
