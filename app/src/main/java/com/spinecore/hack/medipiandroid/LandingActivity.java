package com.spinecore.hack.medipiandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.spinecore.hack.medipiandroid.store.CollectionPagerAdapter;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    BlankFragment.OnFragmentInteractionListener,
                    InstructionFragment.OnFragmentInteractionListener {

    private String reading_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView userSummary = (TextView) toolbar.findViewById(R.id.toolbar_login_user_summary);
        String userDetails = getIntent().getExtras().getString("userDetails");
        userSummary.setText(userDetails);

        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.info);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());


        return true;
    }
    public void displayView(int viewId) {

        Fragment fragment = null;
        boolean instructions = false;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.oximeter:
                reading_type="oximeter";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Oximeter";
                instructions = true;
                break;
            case R.id.weight:
                reading_type="weight";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Weight";
                instructions = true;
                break;
            case R.id.bp:
                reading_type="bp";
                fragment = BlankFragment.newInstance(reading_type);
                title  = "Blood Pressure";
                instructions = true;
                break;
            case R.id.questionnaire:
                fragment = new QuestionnaireFragment();
                title  = "Questionnaire";
                break;
            case R.id.share_reading:
                fragment = new ReadingFragment();
                title  = "Share Reading";
                break;
        }

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CollectionPagerAdapter mCollectionPagerAdapter =
                    new CollectionPagerAdapter(
                            getSupportFragmentManager());
            transaction.replace(R.id.content_frame, fragment);
            if (instructions) {
                ViewPager mViewPager = (ViewPager) findViewById(R.id.instruction_pager);
                mViewPager.setAdapter(mCollectionPagerAdapter);
            }
            transaction.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class CollectionPagerAdapter extends FragmentStatePagerAdapter {

        public CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            InstructionFragment fragment = InstructionFragment.newInstance();
            Bundle args = new Bundle();
            args.putString(InstructionFragment.ARG_READING_TYPE, reading_type);
            args.putInt(InstructionFragment.ARG_INSTRUCTION_STEP,i);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (reading_type == "oximeter") {
                return getResources().getStringArray(R.array.oximeter_instructions).length;
            }
            else if (reading_type == "weight"){
                return getResources().getStringArray(R.array.weight_instructions).length;
            }
            else if (reading_type == "bp"){
                return getResources().getStringArray(R.array.bp_instructions).length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

}

