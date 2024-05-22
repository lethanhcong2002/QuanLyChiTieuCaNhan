package com.example.doancuoiki;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.doancuoiki.fragment.AccountFragment;
import com.example.doancuoiki.fragment.GDDXFragment;
import com.example.doancuoiki.fragment.NewGiaoDichFragment;
import com.example.doancuoiki.fragment.BCTCFragment;
import com.example.doancuoiki.fragment.HomeFragment;
import com.example.doancuoiki.fragment.LKHFragment;
import com.example.doancuoiki.fragment.QLTCFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import BangDuLieu.KhachHang;
import Support.AppData;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dl;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    private int checkForAccount;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_QLTC = 1;
    private static final int FRAGMENT_BCTC = 2;
    private static final int FRAGMENT_GDDX = 3;
    private static final int FRAGMENT_LKH = 4;
    private static final int FRAGMENT_ACCOUNT = 5;

    private KhachHang kh;
    private int currentFragmentId = FRAGMENT_HOME;
    private int previousFragmentId = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kh = AppData.getInstance().getKhachHang();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, new HomeFragment());
        transaction.commit();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        View headerView = navigationView.getHeaderView(0);
        TextView txtUserName = headerView.findViewById(R.id.txtUserName);
        if (kh != null) {
            txtUserName.setText(kh.getName());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dl = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(toggle);
        toggle.syncState();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragmentId != -1) {
                    replaceFragment(new NewGiaoDichFragment(), -1);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            changeFragment(FRAGMENT_HOME);
            showHideFAB(true);
        } else if (id == R.id.nav_QlTC) {
            changeFragment(FRAGMENT_QLTC);
            showHideFAB(true);
        } else if (id == R.id.nav_BCTC) {
            changeFragment(FRAGMENT_BCTC);
            showHideFAB(true);
        } else if (id == R.id.nav_GDDX) {
            changeFragment(FRAGMENT_GDDX);
            showHideFAB(true);
        } else if (id == R.id.nav_LKH) {
            changeFragment(FRAGMENT_LKH);
            showHideFAB(true);
        } else if (id == R.id.nav_account) {
            changeFragment(FRAGMENT_ACCOUNT);
            showHideFAB(true);
        } else if (id == R.id.nav_logout) {
            finish();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            AppData.getInstance().ClearALl();
            startActivity(intent);
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                FragmentManager.BackStackEntry previousBackStackEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);// Đoạn này lấy thông tin về fragment trước đó (fragment đã được loại bỏ khỏi ngăn xếp).
                String previousFragment_id = previousBackStackEntry.getName();
                checkForAccount = currentFragmentId;
                if(previousFragment_id != null)
                {
                    currentFragmentId = Integer.parseInt(previousFragment_id);
                }
                changeFragment(currentFragmentId);
                showHideFAB(true);
                updateCheckedMenuItem(currentFragmentId, true);
            } else {
                AppData.getInstance().ClearALl();
                super.onBackPressed();
            }
        }
    }

    public void updateCheckedMenuItem(int fragmentId, boolean check) {
        navigationView.getMenu().findItem(R.id.nav_account).setChecked(false);
        MenuItem menuItem = null;
        switch (fragmentId) {
            case FRAGMENT_HOME:
                menuItem = navigationView.getMenu().findItem(R.id.nav_home);
                break;
            case FRAGMENT_QLTC:
                menuItem = navigationView.getMenu().findItem(R.id.nav_QlTC);
                break;
            case FRAGMENT_BCTC:
                menuItem = navigationView.getMenu().findItem(R.id.nav_BCTC);
                break;
            case FRAGMENT_GDDX:
                menuItem = navigationView.getMenu().findItem(R.id.nav_GDDX);
                break;
            case FRAGMENT_LKH:
                menuItem = navigationView.getMenu().findItem(R.id.nav_LKH);
                break;
            case FRAGMENT_ACCOUNT:
                updateCheckedMenuItem(checkForAccount, false);
                menuItem = navigationView.getMenu().findItem(R.id.nav_account);
                break;
        }
        if (menuItem != null) {
            menuItem.setChecked(check);
        }
    }
    private void changeFragment(int fragmentId) {
        navigationView.getMenu().findItem(R.id.nav_account).setChecked(false);
        if (currentFragmentId != fragmentId) {
            switch (fragmentId) {
                case FRAGMENT_HOME:
                    updateCheckedMenuItem(FRAGMENT_HOME, true);
                    replaceFragment(new HomeFragment(), FRAGMENT_HOME);
                    break;
                case FRAGMENT_QLTC:
                    updateCheckedMenuItem(FRAGMENT_QLTC, true);
                    replaceFragment(new QLTCFragment(), FRAGMENT_QLTC);
                    break;
                case FRAGMENT_BCTC:
                    updateCheckedMenuItem(FRAGMENT_BCTC, true);
                    replaceFragment(new BCTCFragment(), FRAGMENT_BCTC);
                    break;
                case FRAGMENT_GDDX:
                    updateCheckedMenuItem(FRAGMENT_GDDX, true);
                    replaceFragment(new GDDXFragment(), FRAGMENT_GDDX);
                    break;
                case FRAGMENT_LKH:
                    updateCheckedMenuItem(FRAGMENT_LKH, true);
                    replaceFragment(new LKHFragment(), FRAGMENT_LKH);
                    break;
                case FRAGMENT_ACCOUNT:
                    updateCheckedMenuItem(currentFragmentId, false);
                    navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                    replaceFragment(new AccountFragment(), FRAGMENT_ACCOUNT);
                    break;
            }
        }
    }

    private void replaceFragment(Fragment fragment, int id_fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment, fragment.getClass().getName());
        transaction.addToBackStack(String.valueOf(currentFragmentId));
        transaction.commit();
        currentFragmentId = id_fragment;
    }

    public void showHideFAB(boolean show) {
        if (show) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}
