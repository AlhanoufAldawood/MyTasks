package com.example.alhanoufaldawood.swe444;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ParentFragment extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_fragment);


//Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().show();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().hide();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         //Action item
        navigationView = (NavigationView) findViewById(R.id.NavigationView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected( MenuItem item) {


                switch(item.getItemId()){

                    case R.id.addChild:
                        Intent intent = new Intent(ParentFragment.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.signOut:
                        Intent intent1 = new Intent(ParentFragment.this, Log_in.class);
                        startActivity(intent1);
                        break;

                }


                return true;
            }
        });



        BottomNavigationView bottomNav = findViewById(R.id.navigationBottom);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                    new parentHome()).commit();
        }


}
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action));

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.notification:
                            selectedFragment = new Notification();
                            break;
                        case R.id.childList:
                            selectedFragment = new parentHome();
                            break;
                        case R.id.chat:
                            selectedFragment = new Chat();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                            selectedFragment).commit();

                    return true;
                }
            };
}
