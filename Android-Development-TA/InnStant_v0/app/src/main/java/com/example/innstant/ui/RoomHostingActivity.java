package com.example.innstant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.Room;
import com.example.innstant.ui.HostRoom.Adapter.AdapterRoomHosting;
import com.example.innstant.ui.RoomListed.EditRoomActivity;
import com.example.innstant.viewmodel.ListerRoomVewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomHostingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterRoomHosting.OnItemClickListener {

    private ListerRoomVewModel mViewModel;
    RecyclerView recyclerView;
    AdapterRoomHosting adapter;
    ArrayList<Room> list = new ArrayList<Room>();
    RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dataroom)
    RecyclerView dataroom;
    @BindView(R.id.checkdata)
    TextView checkdata;
    @BindView(R.id.addroom)
    ImageButton addroom;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_hosting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
//        Toast.makeText(RoomHostingActivity.this,"berhasil    :"+json,Toast.LENGTH_LONG).show();
        navView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.dataroom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(ListerRoomVewModel.class);

        GetData(list);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.room_hosting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.addroom})
    public void onViewClicked(View view) {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");

        switch (view.getId()) {
            case R.id.addroom:
                Intent intent = new Intent(RoomHostingActivity.this, SetLocationActivity.class);
                intent.putExtra("email", json);
//                Toast.makeText(RoomHostingActivity.this,"berhasil    :"+json,Toast.LENGTH_LONG).show();
                startActivity(intent);
                break;
        }
    }

    public void GetData(ArrayList<Room> list) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/rooms";
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");

        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    Room room = new Room();

                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(RoomHostingActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
//                        Log.d("respon",response.toString());
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                 jsonObject = response.getJSONObject(i);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            room = new Gson().fromJson(String.valueOf(jsonObject), Room.class);

                                if(room.getOwnerId().equals(json)){
                                    list.add(room);
                                }
                        }
                        adapter = new AdapterRoomHosting(RoomHostingActivity.this, list, RoomHostingActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RoomHostingActivity.this, "gagal     :" + error.toString(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemClick(Room item) {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
            Intent intent = new Intent(RoomHostingActivity.this, EditRoomActivity.class);
            intent.putExtra("email", json);
            intent.putExtra("id", item.getRoomId());
            Log.d("ini idnya",json);
            Log.d("ID KAMAR",item.getRoomId());
//            Toast.makeText(RoomHostingActivity.this,list.get(0).toString(),Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }
