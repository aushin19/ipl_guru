package com.onemosys.ipl.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.onemosys.ipl.Adapters.onBoardingAdapter;
import com.onemosys.ipl.Helper.UsersData;
import com.onemosys.ipl.Home;
import com.onemosys.ipl.MainActivity;
import com.onemosys.ipl.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Onboarding#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Onboarding extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    Context context;
    public ViewPager onboarding_VP;
    SignInButton signInButton;
    public WormDotsIndicator pageIndicator;
    public BottomSheetDialog dialog;

    int[] images = {R.drawable.more_task, R.drawable.read_task, R.drawable.quiz_task, R.drawable.refer_task, R.drawable.leadearboard};
    String[] subtitle;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    public Onboarding() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Onboarding.
     */
    // TODO: Rename and change types and number of parameters
    public static Onboarding newInstance(String param1, String param2) {
        Onboarding fragment = new Onboarding();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        context = getContext();
        init(view);
        getGoogleAuth(view);
    }

    private void init(View view) {

        subtitle = new String[]{"Daily New Tasks with Huge Rewards", "Now Earn More While Reading New Articles", "Play Daily New and Interesting Quiz to Earn More Exiting Rewards", "Refer Your Friends and Earn Huge Rewards!", "Compete With Other Players World Wide"};

        onboarding_VP = view.findViewById(R.id.onboarding_VP);
        pageIndicator = view.findViewById(R.id.pageIndicator);

        onBoardingAdapter onBoardingAdapter = new onBoardingAdapter(context, images, subtitle);
        onboarding_VP.setAdapter(onBoardingAdapter);

        pageIndicator.setViewPager(onboarding_VP);

        view.findViewById(R.id.get_started_CV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Home.class));
                ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                ((Activity) context).finish();
            }
        });

    }

    private void getGoogleAuth(View view) {
        firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(context).enableAutoManage((FragmentActivity) context, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        signInButton = view.findViewById(R.id.sign_in_button);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Sign in with Google");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            UsersData.setUserName(context, account.getDisplayName());
            UsersData.setUserEmail(context, account.getEmail());
            UsersData.setAvatar(context, String.valueOf(account.getPhotoUrl()));

            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuthWithGoogle(credential);
        } else {
            Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, Home.class));
                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    ((Activity) context).finish();
                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}