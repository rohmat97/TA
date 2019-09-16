package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.example.innstant.viewmodel.HostViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralDescriptionActivity extends AppCompatActivity {
    @BindView(R.id.location)
    EditText location;
    @BindView(R.id.roomName)
    EditText roomName;
    @BindView(R.id.roomType)
    EditText roomType;
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

    String showert;
    String foodt;
    String wifit;
    String AcFant;
    String parkingt;
    String securityt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_description);

        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(GeneralDescriptionActivity.this).get(HostViewModel.class);

        Bundle bundle = getIntent().getExtras();
        String loc = bundle.getString("location");
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

        location.setText(loc);

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
        room.setLocation(location.getText().toString());
        room.setType(roomType.getText().toString());
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
//        Toast.makeText(GeneralDescriptionActivity.this,paramString,Toast.LENGTH_LONG).show();
        /*  try {
            JSONObject param = new JSONObject(paramString);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.PATCH, url,param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(GeneralDescriptionActivity.this,"berhasil",Toast.LENGTH_LONG).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(GeneralDescriptionActivity.this,"gagal",Toast.LENGTH_LONG).show();
                        }

                    }

            ){
                //here I want to post data to sever
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    // Basic Authentication
                    //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

//                    headers.put("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6InJvaG1hdDY2MUBnbWFpbC5jb20iLCJleHAiOjE1NjI2NjM1NjksInJvbGUiOlsiVVNFUiJdfQ.6mGlnlu0lWHuOZLmy_I4IYOD5BJKc-22fbR0sWO-8j_KQ9Jkk4owJZqpP3yPtvBIiRhD_zRYKm-ew3DPqFrK_A");
                    return headers;
                }
            };
            requstQueue.add(jsonobj);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

}
