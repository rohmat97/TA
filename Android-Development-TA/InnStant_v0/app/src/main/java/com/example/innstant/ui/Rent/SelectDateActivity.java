package com.example.innstant.ui.Rent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.innstant.data.model.Transaction;
import com.example.innstant.data.model.User;
import com.example.innstant.viewmodel.ApprovalViewModel;
import com.example.innstant.viewmodel.DashboardViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;

public class SelectDateActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText awal,akhir;
    TextView price,dp,total;
    Gson gson = new Gson();
    Transaction transaksi = new Transaction();
    Button requestBook;
    Bundle bundle;
    String json,json1;

    private ApprovalViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        setTitle("Select Date");
         awal = (EditText) findViewById(R.id.awal);
         akhir = (EditText) findViewById(R.id.akhir);
         dp =(TextView) findViewById(R.id.Dp);
         price=(TextView) findViewById(R.id.price);
         total=(TextView) findViewById(R.id.totalPrice);
         requestBook = (Button) findViewById(R.id.requestBook);
         bundle = getIntent().getExtras();
         json = bundle.getString("data");
         json1 = bundle.getString("email");

        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this).get(ApprovalViewModel.class);


        Room room= gson.fromJson(json,Room.class);


//        dp.setText(room.getDpPercentage(), TextView.BufferType.NORMAL);
        price.setText(room.getPrice().toString());
        total.setText(room.getPrice());

        awal.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();

                }

                private void updateLabel(){
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                    awal.setText(sdf.format(myCalendar.getTime()));
                    transaksi.setBookStartDate(myCalendar.getTime().toString());
                  }

            };
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SelectDateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }

        });
        akhir.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();

                }

                private void updateLabel() {
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    akhir.setText(sdf.format(myCalendar.getTime()));
                    transaksi.setBookEndDate(myCalendar.getTime().toString());

                }

            };

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SelectDateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        for(int x =0;x<2;x++){
            getData(transaksi,json1,room);
        }
        requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    postData(transaksi,json,json1);


            }
        });
    }
    private void postData(Transaction transaksi, String json ,String json1){
        String paramString = new GsonBuilder().create().toJson(transaksi);
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/transactions";
        try {
            JSONObject param =  new JSONObject(paramString);
            JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url, param,
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String status = "pesan";
        Intent intent = new Intent(SelectDateActivity.this,ApprovalActivity.class);
        intent.putExtra("email",json1);
        intent.putExtra("data",json);
        intent.putExtra("dataTransaksi",paramString);
        intent.putExtra("status",status);
//        Toast.makeText(SelectDateActivity.this,"TEST   "+transaksi.toString(),Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    private void getData(Transaction transaksi, String json, Room room) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String urlUser = PreferenceHelper.getBaseUrl() + "/users";
        transaksi.setRoomId(room.getRoomId());
        transaksi.setRoomName(room.getName());
        transaksi.setHostId(room.getOwnerId());
        transaksi.setGuestId(json);
        transaksi.setPaymentStatus("belumbayar");
//        Toast.makeText(ApprovalActivity.this, json+"   :" + transaksi.toString(), Toast.LENGTH_LONG).show();
        String paramString = new GsonBuilder().create().toJson(transaksi);
            JsonArrayRequest jsonUser= new JsonArrayRequest(Request.Method.GET, urlUser, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jsonObject = new JSONObject();
                            User dataUser;
                            String userGuest = null,userHost;
                            //  Toast.makeText(SelectDateActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                            for (int i = 0; i < response.length(); i++) {

                                try {
                                    jsonObject = response.getJSONObject(i);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dataUser = new Gson().fromJson(String.valueOf(jsonObject), User.class);
//                            Log.d("TIME",String.valueOf( dateFormat.format(date)));
                                if(dataUser.getUserId().equals(json)){
                                    userGuest = dataUser.getFirstName();
                                    transaksi.setGuestName(userGuest);
                                }else{

                                }

                                if(dataUser.getUserId().equals(transaksi.getHostId())){
                                    userHost = dataUser.getFirstName();
                                    transaksi.setHostName(userHost);
                                    Log.d("IDUSER",userGuest+userHost);
                                }else {

                                }
                            }
                            //  Toast.makeText(SelectDateActivity.this, json+"   :" + transaksi.getGuestName()+transaksi.getHostName(), Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

            };
            requstQueue.add(jsonUser);

    }
}
