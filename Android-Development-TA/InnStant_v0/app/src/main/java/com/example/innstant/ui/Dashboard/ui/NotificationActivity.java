package com.example.innstant.ui.Dashboard.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.Transaction;
import com.example.innstant.ui.Rent.Adapter.AdapterRoomRent;
import com.example.innstant.ui.Rent.ApprovalActivity;
import com.example.innstant.ui.Rent.RentRoomActivity;
import com.example.innstant.viewmodel.DashboardViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity  implements AdapterRoomRent.OnItemClickListener {
    private DashboardViewModel mViewModel;
    RecyclerView recyclerView;
    AdapterRoomRent adapter;
    ArrayList<Transaction> list;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);


        recyclerView = findViewById(R.id.list_transaksi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GetData(json);
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
                        //Toast.makeText(ListedRoomActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                jsonObject = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            transaksi = new Gson().fromJson(String.valueOf(jsonObject), Transaction.class);
                            Log.d("TIME",String.valueOf( transaksi.toString()));
                            if(transaksi.getHostId().equals(json)){
                            list.add(transaksi);
                        }
                        }
                        if(list.size()<1){
                            Toast.makeText(NotificationActivity.this,"TIDAK ADA NOTIFIKASI",Toast.LENGTH_LONG).show();
                        }
                       // Toast.makeText(NotificationActivity.this,"berhasil    :"+json.toString(),Toast.LENGTH_LONG).show();
                        adapter = new AdapterRoomRent(NotificationActivity.this,list,NotificationActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotificationActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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
    public void onItemClick(Transaction item) {
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
        String status = "approval";
        Intent intent = new Intent(NotificationActivity.this, ApprovalActivity.class);
        String data = new Gson().toJson(item);
        intent.putExtra("email",json);
        intent.putExtra("status",status);
        intent.putExtra("dataTransaksi",data);
        startActivity(intent);
    }


}
