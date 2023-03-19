package com.onemosys.ipl.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.onemosys.ipl.Configs.LazyLoader;
import com.onemosys.ipl.MainActivity;
import com.onemosys.ipl.Modals.TeamScheduleModal;
import com.onemosys.ipl.NetworkCalls.GetPosters;
import com.onemosys.ipl.NetworkCalls.GetTeamSchedule;
import com.onemosys.ipl.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {

    public Context context;
    public View view;
    public static ViewPager2 viewPager2;
    public static ShimmerFrameLayout shimmerImgSlider;
    public static ShimmerFrameLayout shimmerMatch;
    LinearLayout container;
    public static Handler sliderHandler = new Handler();
    public static WormDotsIndicator wormDotsIndicator;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        this.view = view;

        getPoster(view);
        getTeamSchedule(view);
    }

    public void getPoster(View view) {
        viewPager2 = view.findViewById(R.id.image_slider);
        wormDotsIndicator = view.findViewById(R.id.indicator);
        shimmerImgSlider = view.findViewById(R.id.shimmer_img_slider);
        shimmerImgSlider.startShimmer();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new GetPosters(context).execute();
            }
        }).start();
    }

    private void getTeamSchedule(View view) {
        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);
        container = view.findViewById(R.id.team_schedule_container);
        shimmerMatch = view.findViewById(R.id.shimmer_match);

        new Thread(new Runnable() {
            @Override
            public void run() {
                new GetTeamSchedule(context, view, container).execute();
            }
        }).start();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            new GetTeamSchedule(context, view, container).execute();
                        }
                    }).start();
                }
            }
        });
    }

}