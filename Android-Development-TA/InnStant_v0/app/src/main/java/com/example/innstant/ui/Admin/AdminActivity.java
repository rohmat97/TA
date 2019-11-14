package com.example.innstant.ui.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.innstant.R;
import com.example.innstant.ui.Admin.listAccount.ListActivity;
import com.example.innstant.ui.Admin.listKamar.ListKamarActivity;
import com.example.innstant.ui.Admin.validasiAccount.ValidasiAccountActivity;
import com.example.innstant.ui.Admin.validasiKamar.ValidasiKamarActivity;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.validasiAccount)
    Button validasiAccount;
    @BindView(R.id.validasiKamar)
    Button validasiKamar;
    @BindView(R.id.dataKamar)
    Button dataKamar;
    @BindView(R.id.dataAccount)
    Button dataAccount;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        validasiAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, ValidasiAccountActivity.class);
                startActivity(intent);
            }
        });
        validasiKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, ValidasiKamarActivity.class);
                startActivity(intent);
            }
        });
        dataKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, ListKamarActivity.class);
                startActivity(intent);
            }
        });
        dataAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
