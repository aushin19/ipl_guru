package com.onemosys.ipl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.onemosys.ipl.Fragments.Dashboard;
import com.onemosys.ipl.Fragments.News;

public class Home extends AppCompatActivity {

    public Context context;
    public Activity activity;
    public View view;
    public ChipNavigationBar chipNavigationBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        navigate();

    }

    private void init(){
        context = this;
        activity = this;
        view = getWindow().getDecorView();
        chipNavigationBar = findViewById(R.id.chipnav);
    }

    public void navigate() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.menu_news:
                        openFragment(new News(), (FragmentActivity) activity);
                        return;
                    default:
                        openFragment(new Dashboard(), (FragmentActivity) activity);
                }
            }
        });
        chipNavigationBar.setItemSelected(R.id.menu_dashboard, true);
    }

    public static void openFragment(Fragment fragment, FragmentActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .show(fragment);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.replace(R.id.pager_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}