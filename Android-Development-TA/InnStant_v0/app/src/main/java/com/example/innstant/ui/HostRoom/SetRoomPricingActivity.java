package com.example.innstant.ui.HostRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.innstant.ui.DashboardActivity;
import com.example.innstant.ui.RoomListed.ListedRoomActivity;
import com.example.innstant.viewmodel.SetRoomPricingActivityViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class SetRoomPricingActivity extends AppCompatActivity {
    private SetRoomPricingActivityViewModel mViewModel;
    Button setAvaliablity ;
    Button saveRoom;
    EditText price;
    EditText fee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_room_pricing);
        setAvaliablity=(Button) findViewById(R.id.setavaliable);
        saveRoom= (Button) findViewById(R.id.saveroom);
        price =(EditText) findViewById(R.id.price);
        fee =(EditText) findViewById(R.id.fee);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(SetRoomPricingActivityViewModel.class);


        setAvaliablity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetRoomPricingActivity.this, SetAvaliabilityActivity.class);
                postData();
                Bundle bundle = getIntent().getExtras();
                String json1 = bundle.getString("email");
                intent.putExtra("email",json1);
                startActivity(intent);
            }
        });

        saveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postData();
                Intent intent = new Intent(SetRoomPricingActivity.this, DashboardActivity.class);
                Bundle bundle = getIntent().getExtras();
                String json1 = bundle.getString("email");
                intent.putExtra("email",json1);
                startActivity(intent);
                finish();
            }
        });

    }
    public void postData()  {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("dataRoom");
        String json1 = bundle.getString("email");
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users/"+json1+"/rooms";

        Gson gson = new Gson();
        Room room= gson.fromJson(json,Room.class);
        room.setPrice(price.getText().toString());
        room.setDpPercentage(Integer.parseInt(String.valueOf(fee.getText())));
        String paramString = new GsonBuilder().create().toJson(room);
//        Toast.makeText(SetRoomPricingActivity.this,paramString,Toast.LENGTH_LONG).show();
        try {
            JSONObject param = new JSONObject(paramString);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.PATCH, url,param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                              Toast.makeText(SetRoomPricingActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(SetRoomPricingActivity.this,"gagal     :"+error,Toast.LENGTH_LONG).show();
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
        }

    }
}
