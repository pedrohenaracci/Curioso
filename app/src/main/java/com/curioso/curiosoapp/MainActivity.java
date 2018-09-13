package com.curioso.curiosoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.curioso.curiosoapp.Adapters.ViewPager.ViewPagerAdapter;
import com.curioso.curiosoapp.Fragment.FavoriteFragment;
import com.curioso.curiosoapp.Fragment.FeedFragment;
import com.curioso.curiosoapp.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevItem;
    Fragment fragmentFeed, fragmentFavorite,fragmentProfile;
    FrameLayout mFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.my_viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        mFrameLayout = findViewById(R.id.my_framelayout);

                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_favorites:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                }
        );

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevItem != null) {
                    prevItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevItem = bottomNavigationView.getMenu().getItem(position);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ViewPagerSet(viewPager);

    }

    private void ViewPagerSet(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentFeed = new FeedFragment();
        fragmentFavorite = new FavoriteFragment();
        fragmentProfile = new ProfileFragment();
        viewPagerAdapter.addFragment(fragmentFeed);
        viewPagerAdapter.addFragment(fragmentFavorite);
        viewPagerAdapter.addFragment(fragmentProfile);
        viewPager.setAdapter(viewPagerAdapter);
    }





}
