package com.curioso.curiosoapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.curioso.curiosoapp.Adapters.ViewPager.ViewPagerAdapter;
import com.curioso.curiosoapp.Fragment.FavoriteFragment;
import com.curioso.curiosoapp.Fragment.FeedFragment;
import com.curioso.curiosoapp.Fragment.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevItem;
    Fragment fragmentFeed,fragmentProfile;
    FrameLayout mFrameLayout;

    private FirebaseAuth mAuth;

    private Boolean pass = true;

    ProfileFragment profileFragment;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileFragment = (ProfileFragment) new ProfileFragment();


        mAuth = FirebaseAuth.getInstance();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

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
                            case R.id.action_profile:
                                viewPager.setCurrentItem(1);
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
        fragmentProfile = new ProfileFragment();
        viewPagerAdapter.addFragment(fragmentFeed);
        viewPagerAdapter.addFragment(fragmentProfile);
        viewPager.setAdapter(viewPagerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        updateUIDefault(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    private void updateUI(FirebaseUser user) {

        //signOut.setVisibility(View.VISIBLE);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null && pass == true) {
            final String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            final String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            ProfileFragment profileFragment = new ProfileFragment();

            Bundle bundle = new Bundle();
            bundle.putString("name", personName);
            profileFragment.setArguments(bundle);













/*            // Parseando os dados do usuário para tela de perfil
            nome.setText(personName);
            email.setText(personEmail);
           */
/* SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(IMG, personPhoto.toString());
            editor.putString(NAME, personName);
            editor.commit();*//*

            Picasso.get().load(personPhoto.toString()).resize(300, 300).into(perfil);

*/
            //Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();

           // Toast.makeText(this, "Usuário :" + personName + " Está conectado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIDefault (FirebaseUser user){
        if (user != null && pass == false) {

            String personEmail = user.getEmail();
            String personId = user.getUid();



            // Parseando os dados do usuário para tela de perfil

           /* email.setText(personEmail);
            nome.setText(personId);
*/

        } else {
           /* mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);*/
        }



    }





}
