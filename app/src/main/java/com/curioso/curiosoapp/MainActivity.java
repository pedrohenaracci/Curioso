package com.curioso.curiosoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.curioso.curiosoapp.Adapters.Recycler.ClickRecycler_Interface;
import com.curioso.curiosoapp.Adapters.Recycler.FeedRecyclerAdapter;
import com.curioso.curiosoapp.Adapters.ViewPager.ViewPagerAdapter;
import com.curioso.curiosoapp.Fragment.CategoryFragment;
import com.curioso.curiosoapp.Fragment.FeedFragment;
import com.curioso.curiosoapp.Fragment.ProfileFragment;
import com.curioso.curiosoapp.Model.News;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickRecycler_Interface {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevItem;
    Fragment fragmentFeed,fragmentCategory,fragmentProfile;
    private List<News> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_category:
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

    @Override
    public void onRecyclerClick(Object object) {
        News news = (News) object;
    }

    private void ViewPagerSet(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentFeed = new FeedFragment();
        fragmentCategory = new CategoryFragment();
        fragmentProfile = new ProfileFragment();
        viewPagerAdapter.addFragment(fragmentFeed);
        viewPagerAdapter.addFragment(fragmentCategory);
        viewPagerAdapter.addFragment(fragmentProfile);
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void RecyclerViewSet(){


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_feed);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FeedRecyclerAdapter adapter = new FeedRecyclerAdapter(this,newsList,this);
        mRecyclerView.setAdapter(adapter);
    }


}
