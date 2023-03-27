package com.onemosys.ipl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.onemosys.ipl.Fragments.Onboarding;

public class MainActivity extends AppCompatActivity {

    /*static {
        System.loadLibrary("keys");
    }*/

    public static native String REGISTER();
    public static native String LOGIN();
    public static native String TOKEN();
    public static native String RECOVEREMAIL();

    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        context = this;

        Bundle bundle = getIntent().getExtras();

        getWindow().setStatusBarColor(context.getColor(R.color.background));

        if(bundle.getString("openFragment").equals("onBoarding"))
            openFragment(new Onboarding(), (FragmentActivity) activity);
    }

    public static void openFragment(Fragment fragment, FragmentActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .show(fragment);
        if (fragment != null) {
            transaction.remove(fragment);
        }
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

    }

}