package com.example.pcuc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PagerAdapter pagerAdapter = new PagerAdapter(
                getSupportFragmentManager()
        );
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(viewPager);


    }
    //-------------

    public class PagerAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;
        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fm = fragmentManager;

        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "홈";
                case 1:
                    return "작성";
                case 2:
                    return "검색";
                case 3:
                    return "알람";
                default:
                    return null;
            }
        }

        @Override
        public Fragment getItem(int position){
            Fragment fragment = fm.findFragmentByTag("android:switcher:"+ viewPager .getId()+":"+getItemId((position)));
            if(fragment!=null)
                return fragment;
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return WriteFragment.newInstance();
                case 2:
                    return SearchFragment.newInstance();
                case 3:
                    return AlarmFragment.newInstance();
                default:
                    return null;
            }
        }



        @Override
        public int getCount(){
            return 4;
        }
    }






    //--------------
    private  long time = 0;
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if(System.currentTimeMillis() - time < 2000){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int curId = item.getItemId();


        switch(curId){

            case R.id.logout :

                SharedPreferences getPre = getSharedPreferences("setting",MODE_PRIVATE);
                SharedPreferences.Editor e_getPre = getPre.edit();
                System.out.println("pw: "+getPre.getString("PW", ""));
                e_getPre.putBoolean("Auto_Login_enabled",false);
                e_getPre.putString("PW","");
                e_getPre.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);


                finish();
                break;


            case R.id.password_modify:

                Intent pw_intent = new Intent(MainActivity.this, pw_change.class);
                startActivity(pw_intent);

                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_layout1) {
            manager.beginTransaction().replace(R.id.content_main, new Layout1()).commit();
        } else if (id == R.id.nav_layout2) {
            manager.beginTransaction().replace(R.id.content_main, new Layout2()).commit();
        } else if (id == R.id.nav_layout3) {
            manager.beginTransaction().replace(R.id.content_main, new Layout3()).commit();
        } else if (id == R.id.nav_layout4) {
            manager.beginTransaction().replace(R.id.content_main, new Layout4()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
