package com.example.innstant.ui.RoomListed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.Room;
import com.example.innstant.ui.RoomHostingActivity;
import com.example.innstant.viewmodel.EditRoomViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditRoomActivity extends AppCompatActivity {

    ArrayList<Room> tempRoom = new ArrayList<>();

    List amenis = new ArrayList<>();
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
    @BindView(R.id.priceTag)
    EditText priceTag;
    @BindView(R.id.dp)
    EditText dp;
    @BindView(R.id.Delete)
    Button Delete;
    @BindView(R.id.Cancel)
    Button Cancel;
    @BindView(R.id.SaveEdit)
    Button SaveEdit;
    private EditRoomViewModel mViewModel;
    private String AcFanText;
    private String showerText;
    private String wifiText;
    private String foodText;
    private String parkingText;
    private String securityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(EditRoomViewModel.class);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("email");
        String kamar = bundle.getString("id");
        Room room = new Room();
//        Toast.makeText(EditRoomActivity.this,id.toString(),Toast.LENGTH_LONG).show();

        GetData(room, location, roomType, roomName, description, priceTag, dp, shower, food, wifi, acorfan, parking, security, kamar);


        SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(EditRoomActivity.this,amenis.toString(),Toast.LENGTH_LONG).show();
                Log.d("Amenisnya", amenis.toString());
                postEditData(room, location, roomType, roomName, description, priceTag, dp, shower, food, wifi, acorfan, parking, security);


            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRoomActivity.this, RoomHostingActivity.class);
                String id = bundle.getString("email");
                intent.putExtra("email", id);
                startActivity(intent);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRoom(id, kamar);
            }
        });
    }


    private void setFitur(Room room, EditText location, EditText roomType, EditText roomName, EditText desc, EditText fee, EditText downPayment) {
        location.setText(room.getLocation());
        roomType.setText(room.getType());
        roomName.setText(room.getName());
        desc.setText(room.getDescription());
        fee.setText(room.getPrice());
        downPayment.setText(String.valueOf(room.getDpPercentage()));
    }

    public void GetData(Room room, EditText location, EditText roomType, EditText roomName, EditText desc, EditText fee, EditText downPayment, CheckBox shower, CheckBox food, CheckBox wifi, CheckBox acfan, CheckBox parking, CheckBox security, String kamar) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/rooms/" + kamar;
        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    Room room1 = new Room();

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(EditRoomActivity.this,"ini idnya boys"+room.getRoomId(),Toast.LENGTH_LONG).show();
                        Log.d("RESPONBRO", response.toString());

                        JSONObject jsonObject = response;
                        room1 = new Gson().fromJson(String.valueOf(jsonObject), Room.class);

                        amenis = room1.getAmenities();
                        acfan.setChecked(false);
                        shower.setChecked(false);
                        wifi.setChecked(false);
                        food.setChecked(false);
                        parking.setChecked(false);
                        security.setChecked(false);
                        //setAllData
                        for (int x = 0; x < amenis.size(); x++) {
                            if (amenis.get(x) == null) {

                            } else if (amenis.get(x).equals("")) {

                            } else if (amenis.get(x).equals("Ac/Fan")) {
                                acfan.setChecked(true);
                            } else if (amenis.get(x).equals("Shower")) {
                                shower.setChecked(true);
                            } else if (amenis.get(x).equals("Wifi")) {
                                wifi.setChecked(true);
                            } else if (amenis.get(x).equals("Food")) {
                                food.setChecked(true);
                            } else if (amenis.get(x).equals("Parking")) {
                                parking.setChecked(true);
                            } else if (amenis.get(x).equals("Security")) {
                                security.setChecked(true);
                            }
                        }

                        setFitur(room1, location, roomType, roomName, desc, fee, downPayment);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditRoomActivity.this, "gagal  Edit   :" + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }

        ) {
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
    }

    public void postEditData(Room room, EditText location, EditText roomType, EditText roomName, EditText desc, EditText fee, EditText downPayment, CheckBox shower, CheckBox food, CheckBox wifi, CheckBox acfan, CheckBox parking, CheckBox security) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("email");
        String kamar = bundle.getString("id");
        String url = PreferenceHelper.getBaseUrl() + "/users/" + id + "/rooms/" + kamar;
        ArrayList<String> ameni = new ArrayList<String>();
        //setAmenities
        if (acfan.isChecked()) {
            AcFanText = "Ac/Fan";
        } else {
            AcFanText = "";
        }
        if (shower.isChecked()) {
            showerText = "Shower";
        } else {
            showerText = "";
        }
        if (wifi.isChecked()) {
            wifiText = "Wifi";
        } else {
            wifiText = "";
        }
        if (food.isChecked()) {
            foodText = "Food";
        } else {
            foodText = "";
        }
        if (parking.isChecked()) {
            parkingText = "Parking";
        } else {
            parkingText = "";
        }
        if (security.isChecked()) {
            securityText = "Security";
        } else {
            securityText = "";
        }

        ameni.add(showerText);
        ameni.add(foodText);
        ameni.add(wifiText);
        ameni.add(AcFanText);
        ameni.add(parkingText);
        ameni.add(securityText);
        room.setAmenities(ameni);
        room.setLocation(location.getText().toString());
        room.setType(roomType.getText().toString());
        room.setName(roomName.getText().toString());
        room.setDescription(desc.getText().toString());
        room.setPrice(fee.getText().toString());
        room.setDpPercentage(Integer.parseInt(String.valueOf(downPayment.getText())));
        String paramString = new Gson().toJson(room);
//        Toast.makeText(SetRoomPricingActivity.this,paramString,Toast.LENGTH_LONG).show();
        try {
            JSONObject param = new JSONObject(paramString);

            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.PUT, url, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                            Toast.makeText(EditRoomActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(EditRoomActivity.this,"gagal     :"+error,Toast.LENGTH_LONG).show();
                        }

                    }

            ) {
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
        Intent intent = new Intent(EditRoomActivity.this, RoomHostingActivity.class);
        intent.putExtra("email", id);
        startActivity(intent);
        finish();

    }

    public void DeleteRoom(String userId, String roomId) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users/" + userId + "/rooms/" + roomId;

        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(EditRoomActivity.this,"delete berhasil    :"+response,Toast.LENGTH_LONG).show();
//                        Log.d("respon",response.toString());
                        Intent intent = new Intent(EditRoomActivity.this, RoomHostingActivity.class);
                        Bundle bundle = getIntent().getExtras();
                        String id = bundle.getString("email");
                        intent.putExtra("email", id);
                        startActivity(intent);
                        finish();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EditRoomActivity.this, "delete gagal     :" + error.toString(), Toast.LENGTH_LONG).show();
                    }

                }

        ) {
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
    }

}


