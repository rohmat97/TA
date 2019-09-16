package com.example.innstant.ui.Rent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.Room;
import com.example.innstant.data.model.Transaction;
import com.example.innstant.ui.DashboardActivity;
import com.example.innstant.ui.RentStatus.idleActivity;
import com.example.innstant.ui.RoomListed.Adapter.adapterListedRoom;
import com.example.innstant.ui.RoomListed.ListedRoomActivity;
import com.example.innstant.viewmodel.ApprovalViewModel;
import com.example.innstant.viewmodel.idleViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

public class ApprovalActivity extends AppCompatActivity {
    Button cancel, Approved;
    TextView namaKamar, namaUser, startBook, endBook, duration, status;
    private ApprovalViewModel mViewModel;
    Transaction transaksi = new Transaction();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        setTitle("Room Booking");
        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString("email");
        String json1 = bundle.getString("data");
        String jsonTransaksi = bundle.getString("dataTransaksi");
        String stat = bundle.getString("status");
        transaksi = gson.fromJson(jsonTransaksi, Transaction.class);
        Room room = gson.fromJson(json1, Room.class);

        Approved = (Button) findViewById(R.id.approved);
        cancel = (Button) findViewById(R.id.cancelBooking);
        namaKamar = (TextView) findViewById(R.id.namaKamar);
        namaUser = (TextView) findViewById(R.id.namaUser);
        startBook = (TextView) findViewById(R.id.checkin);
        endBook = (TextView) findViewById(R.id.checkout);
        duration = (TextView) findViewById(R.id.duration);
        status = (TextView) findViewById(R.id.status);


        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(ApprovalViewModel.class);
        if (stat.equals("pesan")) {
            transaksi.setGuestId(json);
            transaksi.setHostId(room.getOwnerId());
            transaksi.setRoomId(room.getRoomId());
            transaksi.setPaymentStatus("belumbayar");
            transaksi.setRoomName(room.getName());
//            Toast.makeText(ApprovalActivity.this,transaksi.toString(),Toast.LENGTH_LONG).show();
        }


        if (json.equals(transaksi.getGuestId())) {
            //
//            Toast.makeText(ApprovalActivity.this,"GUEST",Toast.LENGTH_LONG).show();
//            Approved.setVisibility(View.GONE);
            cancel.setText("Cancel");
            Approved.setText("Back");
            cancel.setBackgroundColor(Color.parseColor("#FF0000"));
            setData(transaksi);

            Approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
                    intent.putExtra("email", json);
                    startActivity(intent);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelTransaksi(transaksi);
                    Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
                    intent.putExtra("email", json);
                    startActivity(intent);
                }
            });
            //
        } else if (json.equals(transaksi.getHostId())) {
            //
//            Toast.makeText(ApprovalActivity.this, "HOST", Toast.LENGTH_LONG).show();

            setData(transaksi);
            if(transaksi.getPaymentStatus().equals("sudahbayar")){
                if(stat.equals("approval")){
                    cancel.setText("Selesai Transaksi");
                    Approved.setText("Back");
                }else{
                    cancel.setText("Cancel");
                    Approved.setText("Back");
                }

                cancel.setBackgroundColor(Color.parseColor("#FF0000"));
                Approved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
                        intent.putExtra("email", json);
                        startActivity(intent);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelTransaksi(transaksi);
                        Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
                        intent.putExtra("email", json);
                        startActivity(intent);
                    }
                });

            }else{
                Approved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateTransaksi(transaksi, json);

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelTransaksi(transaksi);
                        Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
                        intent.putExtra("email", json);
                        startActivity(intent);
                    }
                });
                //
            }

        }

    }

    // SET DATA ON PAGE
    public void setData(Transaction jsonTransaksi) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date(), date2 = new Date();
        try {
            date1 = formatter.parse(jsonTransaksi.getBookStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = formatter.parse(jsonTransaksi.getBookEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = date2.getTime() - date1.getTime();
        namaUser.setText(jsonTransaksi.getHostName());
        namaKamar.setText(jsonTransaksi.getRoomName());
//        Toast.makeText(ApprovalActivity.this,jsonTransaksi.toString(),Toast.LENGTH_LONG).show();
        startBook.setText(String.valueOf(jsonTransaksi.getBookStartDate()));
        endBook.setText(String.valueOf(jsonTransaksi.getBookEndDate()));
        duration.setText(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));

        if ((transaksi.getPaymentStatus().equals("sudahbayar"))) {
            status.setText("Booked");
        } else {
            status.setText("Waiting For Approval");
        }


    }

    //DELETE TRANSAKSI
    public void cancelTransaksi(Transaction transaksi) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/transactions/" + transaksi.getTransactionId();
        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(ApprovalActivity.this, " Cancel Book Berhasil", Toast.LENGTH_LONG).show();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                           Toast.makeText(ApprovalActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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

    //POST DATA
//    https://127.0.0.1:9029/transactions/5d5021ebc6036b197bbd7654/payment_status
    public void updateTransaksi(Transaction transaksi, String json) {
        mViewModel.openServerConnection();
        transaksi.setPaymentStatus("sudahbayar");
        transaksi.set_id(null);
        transaksi.setTransactionTimestamp(null);
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/transactions/" + transaksi.getTransactionId();
        JsonObjectRequest jsonobjdelete = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(ApprovalActivity.this, " Cancel Book Berhasil", Toast.LENGTH_LONG).show();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                           Toast.makeText(ApprovalActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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
        requstQueue.add(jsonobjdelete);
        String paramString = new GsonBuilder().create().toJson(transaksi);
        String urli = PreferenceHelper.getBaseUrl() + "/transactions";
        try {
            JSONObject param = new JSONObject(paramString);
            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, urli, param,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
//                            Toast.makeText(SelectDateActivity.this,"berhasil    :"+response.toString(),Toast.LENGTH_LONG).show();

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(SelectDateActivity.this,"gagal     :"+error.toString(),Toast.LENGTH_LONG).show();
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

            Log.d("ISINYA", transaksi.toString());
            Intent intent = new Intent(ApprovalActivity.this, DashboardActivity.class);
            intent.putExtra("email", json);
                    startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
