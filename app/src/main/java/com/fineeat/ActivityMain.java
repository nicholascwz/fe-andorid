package com.fineeat;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.fineeat.Adapters.ViewPagerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import data.ImportMethod;

public class ActivityMain extends AppCompatActivity {

    //cwz 11/10/16 Added to create tab test
    //Created development branch
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    int[] tabImages = {R.drawable.home, R.drawable.search, R.drawable.favorite, R.drawable.account};

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.title);

        //Creating tabs
        initTab();

        //Floating action button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test(); //TODO: Implement map view in the future
            }
        });

        //Loading data
        //Category
        ImportMethod.ImportCategories();
        //Cuisine
        ImportMethod.ImportCuisines();
        //Restaurant
        ImportMethod.ImportRestaurant();
        //Promo
        ImportMethod.ImportPromo();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void test(){
        ImportMethod.ImportCategories();
        ImportMethod.ImportCuisines();
        ImportMethod.ImportRestaurant();
        ImportMethod.ImportPromo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void initTab() {
        //cwz 11/10/16 Added to create tab
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), getSupportFragmentManager());

        //List of tabs
        viewPagerAdapter.addFragment(new FragmentMain(), "Home");
        viewPagerAdapter.addFragment(new FragmentSearch(), "Search");
        viewPagerAdapter.addFragment(new FragmentFavourite(), "Favourite");
        viewPagerAdapter.addFragment(new FragmentAccount(), "Account");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcon(tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Set filter to darken selected tab icon
                if (tab.getIcon() != null) {
                    tab.getIcon().setColorFilter(Color.parseColor("#181818"), PorterDuff.Mode.MULTIPLY);
                }

                //Reset app bar position on tab change
                CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.activityMainCoordinatorLayout);
                AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appBarLayout);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();

                behavior.onNestedFling(coordinator, appbar, null, 0, -1000, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null) {
                    tab.getIcon().clearColorFilter();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //not used
            }
        });
    }

    //Used in initTab
    public void setTabIcon(TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(tabImages[i]);
            //set selected colour filter for the first tab - might not be the best may to check again in the future
            if (i == 0) {
                tabLayout.getTabAt(i).getIcon().setColorFilter(Color.parseColor("#181818"), PorterDuff.Mode.MULTIPLY);
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
