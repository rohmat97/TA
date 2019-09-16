package com.example.innstant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.R;
import com.example.innstant.data.model.Room;
import com.example.innstant.data.model.Transaction;
import com.example.innstant.data.model.User;
import com.example.innstant.ui.Auth.LoginActivity;
import com.example.innstant.ui.Dashboard.ui.NotificationActivity;
import com.example.innstant.ui.HostRoom.Adapter.AdapterRoomHosting;
import com.example.innstant.ui.Rent.Adapter.AdapterRoomRent;
import com.example.innstant.ui.Rent.RentRoomActivity;
import com.example.innstant.viewmodel.DashboardViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rent)
    ImageView rent;
    @BindView(R.id.hosting)
    ImageView hosting;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    String json;
    String idUser ;
    ArrayList<User> user = new ArrayList<>();
    private DashboardViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("email");
        Toast.makeText(DashboardActivity.this,"SELAMAT DATANG : "+json,Toast.LENGTH_LONG).show();
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("email");
        //noinspection SimplifiableIfStatement
        if (id == R.id.notify) {
            getDataNotif(json);
        }
        if (id == R.id.logout) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
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

    @OnClick({R.id.rent, R.id.hosting})
    public void onViewClicked(View view) {

       GetDataUser(json, view);

    }
    private void getDataNotif(String email){
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    User users = new User();

                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(DashboardActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        Log.d("respon",response.toString());
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                jsonObject = response.getJSONObject(i);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            users = new Gson().fromJson(String.valueOf(jsonObject), User.class);
                            if(users.getEmail().equals(email)){
                                idUser = users.getUserId();
                                //                           Toast.makeText(DashboardActivity.this,id + " ------====="+users.getEmail()+users.getUserId()+email,Toast.LENGTH_LONG).show();
                            }else if (users.getUserId().equals(email)){
                                idUser =email;
                            }

                        }


                        Intent intent = new Intent(DashboardActivity.this, NotificationActivity.class);
                        intent.putExtra("email",idUser);
                        startActivity(intent);


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DashboardActivity.this, "gagal     :" + error.toString(), Toast.LENGTH_LONG).show();
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
    private void GetDataUser(String email, View view) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    User users = new User();

                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(DashboardActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        Log.d("respon",response.toString());
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                jsonObject = response.getJSONObject(i);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            users = new Gson().fromJson(String.valueOf(jsonObject), User.class);
                            if(users.getEmail().equals(email)){
                                idUser = users.getUserId();
     //                           Toast.makeText(DashboardActivity.this,id + " ------====="+users.getEmail()+users.getUserId()+email,Toast.LENGTH_LONG).show();
                            }else if (users.getUserId().equals(email)){
                                idUser =email;
                            }

                            Intent intent;
                            switch (view.getId()) {
                                case R.id.rent:
                                    intent = new Intent(DashboardActivity.this, RentRoomActivity.class);
//                                    Toast.makeText(DashboardActivity.this,id,Toast.LENGTH_LONG).show();
                                     intent.putExtra("email",idUser);
                                     startActivity(intent);
                                    break;
                                case R.id.hosting:
                                    intent = new Intent(DashboardActivity.this, RoomHostingActivity.class);
                                    intent.putExtra("email",idUser);
//                                    Toast.makeText(DashboardActivity.this,id,Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    break;
                            }


                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DashboardActivity.this, "gagal     :" + error.toString(), Toast.LENGTH_LONG).show();
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
