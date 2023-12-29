package com.example.panda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.panda.fragment.HomeFrag;
import com.example.panda.fragment.OderFrag;
import com.example.panda.fragment.ProfileFrag;
import com.example.panda.fragment.GioHangFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Trang chủ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout,new HomeFrag()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.giohang:
                        item.setChecked(true);
//                       startActivity(new Intent(MainActivity.this,GioHangActivity.class));
                        fragment = new GioHangFrag();
                        break;
                    case R.id.oder:
                        item.setChecked(true);
                        fragment = new OderFrag();
                        break;
                    case R.id.profile:
                        item.setChecked(true);
                        fragment = new ProfileFrag();
                        break;
                    case R.id.home:
                        item.setChecked(true);
                        fragment = new HomeFrag();
                        break;
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit();
                    toolbar.setTitle(item.getTitle());
                }

                return false;
            }
        });
    }






}