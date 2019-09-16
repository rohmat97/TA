package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
import com.example.innstant.viewmodel.HostViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class GeneralDescriptionActivity extends AppCompatActivity {
    private HostViewModel mViewModel;
    Button next;
    EditText location;
    EditText roomName;
    EditText roomType;
    EditText description;
    CheckBox Editext_shower;
    CheckBox Editext_food;
    CheckBox Editext_wifi;
    CheckBox Editext_AcFan;
    CheckBox Editext_parking;
    CheckBox Editext_security;
    String shower;
    String food;
    String wifi;
    String AcFan;
    String parking;
    String security;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_description);

        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(GeneralDescriptionActivity.this).get(HostViewModel.class);

        Bundle bundle = getIntent().getExtras();
        String loc = bundle.getString("location");
//        Toast.makeText(GeneralDescriptionActivity.this,loc,Toast.LENGTH_LONG).show();
        location =(EditText) findViewById(R.id.location);
        roomName =(EditText) findViewById(R.id.roomName);
        roomType = (EditText) findViewById(R.id.roomType);
        description =(EditText) findViewById(R.id.description);
        Editext_shower =(CheckBox) findViewById(R.id.shower);
        Editext_food   =(CheckBox) findViewById(R.id.food);
        Editext_wifi =(CheckBox) findViewById(R.id.wifi);
        Editext_AcFan=(CheckBox) findViewById(R.id.acorfan);
        Editext_parking=(CheckBox) findViewById(R.id.parking);
        Editext_security=(CheckBox) findViewById(R.id.security);

        location.setText(loc);
        next = (Button) findViewById(R.id.Save);

        next.setOnClickListener(new View.OnClickListener() {
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
        if(Editext_AcFan.isChecked()){
            AcFan ="Ac/Fan";
        }else{
            AcFan=null;
        }
        if(Editext_shower.isChecked()){
            shower="Shower";
        }else{
            shower=null;
        }
        if(Editext_wifi.isChecked()){
            wifi="Wifi";
        }else{
            wifi=null;
        }
        if(Editext_food.isChecked()){
            food ="Food";
        }else{
            food =null;
        }
        if(Editext_parking.isChecked()){
            parking="Parking";
        }else{
            parking=null;
        }
        if(Editext_security.isChecked()){
            security ="Security";
        }else{
            security=null;
        }

        ameni.add(shower);
        ameni.add(food);
        ameni.add(wifi);
        ameni.add(AcFan);
        ameni.add(parking);
        ameni.add(security);

        room.setAmenities(ameni);
        Gson gson = new Gson();
        String paramString = gson.toJson(room);
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");

        Intent intent =new Intent(GeneralDescriptionActivity.this, AddPictureActivity.class);
        intent.putExtra("dataRoom",paramString);
        intent.putExtra("email",json);
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
