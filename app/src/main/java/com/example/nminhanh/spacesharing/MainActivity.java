package com.example.nminhanh.spacesharing;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.nminhanh.spacesharing.Fragment.AccountFragment;
import com.example.nminhanh.spacesharing.Fragment.AddAddressFragment;
import com.example.nminhanh.spacesharing.Fragment.FavoriteFragment;
import com.example.nminhanh.spacesharing.Fragment.PagerAdapter;
import com.example.nminhanh.spacesharing.Fragment.SearchFragment;
import com.example.nminhanh.spacesharing.Fragment.SpaceManagementFragment;
import com.example.nminhanh.spacesharing.Model.Space;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    static final int REQUEST_ADD = 1;

    Toolbar mToolbar;
    ViewPager mViewPager;
    BottomNavigationView mNavigationView;
    Menu mOptionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }

    void initialize() {
        // Initialize toolbar
        mToolbar = findViewById(R.id.main_toolbar);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initialize ViewPager
        mViewPager = findViewById(R.id.main_pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);

        mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = 0;
                switch (menuItem.getItemId()) {
                    case R.id.action_search:
                        id = 0;
                        break;
                    case R.id.action_space:
                        id = 1;
                        break;
                    case R.id.action_favor:
                        id = 2;

                        break;
                    case R.id.action_account:
                        id = 3;
                        break;
                }
                invalidateOptionsMenu();
                mViewPager.setCurrentItem(id, true);
                return true;
            }
        });
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mNavigationView.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mOptionMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem filterItem = menu.findItem(R.id.main_menu_item_filter);
        MenuItem addItem = menu.findItem(R.id.main_menu_item_add);
        switch (mViewPager.getCurrentItem()) {
            case 0:
                filterItem.setVisible(true);
                addItem.setVisible(false);
                break;
            case 1:
                filterItem.setVisible(false);
                addItem.setVisible(true);
                break;
            case 2:
            case 3:
                filterItem.setVisible(false);
                addItem.setVisible(false);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_item_filter:
                showFilterDialog();
                break;
            case R.id.main_menu_item_add:
                Intent intent = new Intent(MainActivity.this, AddSpaceActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog d = builder.setView(inflater.inflate(R.layout.layout_dialog_filter_search, null))
                .create();
//        Button mButtonCancel = d.findViewById(R.id.filter_dialog_cancel);
//        Button mButtonOK = d.findViewById(R.id.filter_dialog_ok);
//
//        mButtonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.cancel();
//            }
//        });
//
//        mButtonOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        d.show();
    }
}
