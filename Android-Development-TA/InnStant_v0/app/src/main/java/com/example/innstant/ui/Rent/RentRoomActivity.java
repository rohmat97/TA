package com.example.innstant.ui.Rent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.innstant.data.model.Transaction;
import com.example.innstant.ui.HostRoom.Adapter.AdapterRoomHosting;
import com.example.innstant.ui.Rent.Adapter.AdapterRoomRent;
import com.example.innstant.ui.RoomHostingActivity;
import com.example.innstant.ui.RoomListed.Adapter.adapterListedRoom;
import com.example.innstant.ui.RoomListed.ListedRoomActivity;
import com.example.innstant.viewmodel.ListerRoomVewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class RentRoomActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterRoomRent.OnItemClickListener {
    private ListerRoomVewModel mViewModel;
    Button rent;
    RecyclerView recyclerView;
    AdapterRoomRent adapter;
    ArrayList<Transaction> list;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_room);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
//        Toast.makeText(RentRoomActivity.this,"berhasil    :"+json,Toast.LENGTH_LONG).show();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        rent =(Button) findViewById(R.id.addRent);

        recyclerView = findViewById(R.id.rentRoom);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(ListerRoomVewModel.class);


        GetData(json);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RentRoomActivity.this, ListedRoomActivity.class);
                intent.putExtra("email",json);
                startActivity(intent);
            }
        });
    }


    public void  GetData(String json)  {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/transactions";
        list = new ArrayList<>();

        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    Transaction transaksi = new Transaction();
                    JSONObject jsonObject;
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            try {
                                 jsonObject = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            transaksi = new Gson().fromJson(String.valueOf(jsonObject), Transaction.class);
//                            Log.d("TIME",String.valueOf( dateFormat.format(date)));
                            if(transaksi.getGuestId().equals(json)){
                                list.add(transaksi);
                            }
                        }

                        adapter = new AdapterRoomRent(RentRoomActivity.this,list,  RentRoomActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RentRoomActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rent_room, menu);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(Transaction item) {
        String status = "check";
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
        Intent intent = new Intent(RentRoomActivity.this,ApprovalActivity.class);
        String data = new Gson().toJson(item);
        intent.putExtra("email",json);
        intent.putExtra("dataTransaksi",data);
        intent.putExtra("status",status);
        startActivity(intent);
    }
}
