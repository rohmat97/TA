package com.example.innstant.ui.Admin.listAccount;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.innstant.data.model.Transaction;
import com.example.innstant.data.model.User;
import com.example.innstant.ui.Admin.Adapter.AdapterListAccount;
import com.example.innstant.ui.Rent.Adapter.AdapterRoomRent;
import com.example.innstant.ui.Rent.RentRoomActivity;
import com.example.innstant.viewmodel.ListerRoomVewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity implements AdapterListAccount.OnItemClickListener {

    @BindView(R.id.ListAccount)
    RecyclerView ListAccount;
    @BindView(R.id.back)
    ImageView back;
    private ListerRoomVewModel mViewModel;
    RecyclerView recyclerView;
    AdapterListAccount adapter;
    ArrayList<User> list;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LIST ACCOUNT");
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        ListAccount.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new LinearLayoutManager(this);
        ListAccount.setLayoutManager(layoutManager);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(ListerRoomVewModel.class);

        GetData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void GetData() {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        list = new ArrayList<>();

        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    User use = new User();
                    JSONObject jsonObject;

                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            try {
                                jsonObject = response.getJSONObject(i);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            use = new Gson().fromJson(String.valueOf(jsonObject), User.class);
                            list.add(use);

                        }

                        adapter = new AdapterListAccount(ListActivity.this, list, ListActivity.this);
                        ListAccount.setAdapter(adapter);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RentRoomActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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
    public void onItemClick(User item) {
        Toast.makeText(ListActivity.this,item.toString(),Toast.LENGTH_LONG).show();
    }
}
