package com.onemosys.ipl.Fragments;

import static android.content.Context.MODE_PRIVATE;

import static com.onemosys.ipl.Helper.isUserExists.isUserExists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.onemosys.ipl.BottomSheet.BottomSheetDialog;
import com.onemosys.ipl.Configs.android_configs;
import com.onemosys.ipl.Dialog.WaitingDialog;
import com.onemosys.ipl.Helper.CommaFormat;
import com.onemosys.ipl.Helper.UsersData;
import com.onemosys.ipl.MainActivity;
import com.onemosys.ipl.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class Profile extends Fragment {

    public static com.google.android.material.bottomsheet.BottomSheetDialog  dialog;
    Context context;
    public static WaitingDialog waitingDialog;
    public static BottomSheetDialog bottomSheetDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        init(view);
        if(isUserExists(context))
            getUserData(view);

    }

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        } else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void init(View view) {
        waitingDialog = new WaitingDialog(context);
        ImageView user_IMG = view.findViewById(R.id.user_IMG);
        MaterialButton profile_logout = view.findViewById(R.id.profile_logout);

        if(isUserExists(context)){
            Glide.with(context)
                    .load(UsersData.getAvatar(context))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(user_IMG);
        }else{
            profile_logout.setText("LOGIN ACCOUNT");
        }

        view.findViewById(R.id.profile_privacy_TV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.primary));
                openCustomTab(((Activity)context), customIntent.build(), Uri.parse(android_configs.APP_PRIVACY_POLICY));
            }
        });

        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserExists(context)){
                    Toast.makeText(context, "Long Press to Logout!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Long Press to Login!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profile_logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(isUserExists(context)){
                    SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                }

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("openFragment", "onBoarding");
                startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity)context).finish();
                return false;
            }
        });

        /*view.findViewById(R.id.notification_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingDialog.show();
                bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.notificationBottomSheet(getView());
            }
        });

        view.findViewById(R.id.transaction_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingDialog.show();
                new BottomSheetDialog(context).TransactionHistoryBottomSheet(getView());
            }
        });

        view.findViewById(R.id.withdraw_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUserExists(context))
                    new BottomSheetDialog(context).WithdrawBottomSheet(getView());
                else
                    new BottomSheetDialog(context).LoginBottomSheet(((Activity)context).getWindow().getDecorView());
            }
        });*/

        view.findViewById(R.id.help_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.support_email)});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
                intent.setPackage("com.google.android.gm");
                intent.putExtra(Intent.EXTRA_TEXT, "Your Query Goes Here!");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Share On "));
            }
        });

    }

    private void getUserData(View view){
        TextView profile_coins_TV = view.findViewById(R.id.profile_coins_TV);
        TextView profile_email_TV = view.findViewById(R.id.profile_email_TV);
        TextView profile_username_TV = view.findViewById(R.id.profile_username_TV);
        TextView profile_emailID_TV = view.findViewById(R.id.profile_emailID_TV);
        MaterialCardView profile_email_CV = view.findViewById(R.id.profile_email_CV);
        MaterialCardView email_CV = view.findViewById(R.id.email_CV);

        profile_username_TV.setText(UsersData.getUserName(context));
        profile_emailID_TV.setText(UsersData.getUserEmail(context));

        profile_coins_TV.setText(CommaFormat.coinsCommaFormat(UsersData.getCoins(context)));
        if(UsersData.getUserEmailVerify(context).equals("true")){
            email_CV.setVisibility(View.VISIBLE);
            profile_email_CV.setCardBackgroundColor(context.getColor(R.color.green_tick_background));
            profile_email_TV.setText("Verified");
            profile_email_TV.setTextColor(context.getColor(R.color.green_tick));
        }
    }


}