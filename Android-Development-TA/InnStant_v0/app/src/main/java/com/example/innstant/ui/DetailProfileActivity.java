package com.example.innstant.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.innstant.R;
import com.example.innstant.data.PreferenceHelper;
import com.example.innstant.data.model.User;
import com.example.innstant.viewmodel.DetailProfileViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProfileActivity extends AppCompatActivity {
    String json;
    User users;
    @BindView(R.id.header_cover_image)
    ImageView headerCoverImage;
    @BindView(R.id.user_profile_photo)
    ImageButton userProfilePhoto;
    @BindView(R.id.user_profile_name)
    TextView userProfileName;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.profile_layout)
    RelativeLayout profileLayout;
    @BindView(R.id.fullName)
    EditText fullName;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.idCard)
    EditText idCard;
    @BindView(R.id.phonenumber)
    EditText phonenumber;
    @BindView(R.id.selfie)
    ImageView selfie;
    @BindView(R.id.idCardImage)
    ImageView idCardImage;
    @BindView(R.id.selfieWithIDCardImage)
    ImageView selfieWithIDCardImage;

    private DetailProfileViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        mViewModel = ViewModelProviders.of(this).get(DetailProfileViewModel.class);
        ButterKnife.bind(this);
        setTitle("Detail Profile");
        Bundle bundle = getIntent().getExtras();
        json = bundle.getString("email");
        GetDataUser(json);
    }

    private void GetDataUser(String mail) {
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/users";
        JsonArrayRequest jsonobj = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(DashboardActivity.this,"berhasil    :"+response,Toast.LENGTH_LONG).show();
                        Log.d("respon", response.toString());
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                jsonObject = response.getJSONObject(i);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            users = new Gson().fromJson(String.valueOf(jsonObject), User.class);
                            if(users.getEmail().equals(mail)){
                                userProfileName.setText(users.getFirstName()+" "+users.getLastName(), TextView.BufferType.EDITABLE);
                                email.setText(users.getEmail(), TextView.BufferType.EDITABLE);
                                fullName.setText(users.getFirstName()+" "+users.getLastName(), TextView.BufferType.EDITABLE);
                                emailAddress.setText(users.getEmail(), TextView.BufferType.EDITABLE);
                                idCard.setText(users.getIdCardNumber(), TextView.BufferType.EDITABLE);
                                phonenumber.setText(users.getPhoneNumber(), TextView.BufferType.EDITABLE);
                                DownloadPhoto(users.getProfilePhoto(),"selfie");
                                DownloadPhoto(users.getIdCardPhoto(),"idcard");
                                DownloadPhoto(users.getUserWithIdCardPhoto(),"selfieidcard");
                            }else if (users.getUserId().equals(mail)) {
                                userProfileName.setText(users.getFirstName()+" "+users.getLastName(), TextView.BufferType.EDITABLE);
                                email.setText(users.getEmail(), TextView.BufferType.EDITABLE);
                                fullName.setText(users.getFirstName()+" "+users.getLastName(), TextView.BufferType.EDITABLE);
                                emailAddress.setText(users.getEmail(), TextView.BufferType.EDITABLE);
                                idCard.setText(users.getIdCardNumber(), TextView.BufferType.EDITABLE);
                                phonenumber.setText(users.getPhoneNumber(), TextView.BufferType.EDITABLE);
                                DownloadPhoto(users.getProfilePhoto(),"selfie");
                                DownloadPhoto(users.getIdCardPhoto(),"idcard");
                                DownloadPhoto(users.getUserWithIdCardPhoto(),"selfieidcard");
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

    private void DownloadPhoto(String uri, String loc){
        mViewModel.openServerConnection();
        RequestQueue requstQueue = Volley.newRequestQueue(this);
        String url = PreferenceHelper.getBaseUrl() + "/photos/download_photo/"+ uri;
        ImageRequest request = new ImageRequest(url,

                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        if(loc.equals("selfie")){
                            selfie.setImageBitmap(bitmap);
                        }else if(loc.equals("idcard")){
                            idCardImage.setImageBitmap(bitmap);
                        }else if(loc.equals("selfieidcard")){
                            selfieWithIDCardImage.setImageBitmap(bitmap);
                        }
//                        mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
//                        mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });
        requstQueue.add(request);
    }
}
