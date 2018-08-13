package com.curioso.curiosoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Fragment fragment = null;

                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                fragment = new FeedFragment();
                                break;
                            case R.id.action_category:
                                fragment = new CategoryFragment();
                                break;
                            case R.id.action_profile:
                                fragment = new ProfileFragment();
                                break;
                        }
                        return LoadFragment(fragment);
                    }
                }
        );

    }
    public boolean LoadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.my_framelayout,fragment)
                    .commit();
            return true;
        }
        return  false;
    }
}
