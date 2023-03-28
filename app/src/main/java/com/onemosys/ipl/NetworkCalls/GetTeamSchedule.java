package com.onemosys.ipl.NetworkCalls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.onemosys.ipl.Fragments.Dashboard;
import com.onemosys.ipl.Helper.MatchStartCountdown;
import com.onemosys.ipl.Helper.UsersData;
import com.onemosys.ipl.Modals.TeamScheduleModal;
import com.onemosys.ipl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetTeamSchedule extends AsyncTask<Void, Void, Void> {
    Context context;
    View view;
    ArrayList<TeamScheduleModal> teamScheduleModalArrayList = new ArrayList<>();
    String content;
    long todayUNIX;
    public static int feedLimit = 6;
    public static int feedCounter = 0;
    LinearLayout container;

    public GetTeamSchedule(Context context, View view, LinearLayout container) {
        this.context = context;
        this.view = view;
        this.container = container;
        todayUNIX = System.currentTimeMillis() / 1000L;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://tataiplguru.blogspot.com/2023/03/time-table.html").get();
            Elements element = document.getElementsByClass("team_schedule");

            content = Jsoup.parse(String.valueOf(element)).text();

            try {
                JSONArray jsonArray = new JSONArray(content);

                for (int i = feedCounter; i < feedLimit; i++) {
                    if (true) { //todayUNIX <= jsonArray.getJSONObject(i).getInt("matchTime")
                        teamScheduleModalArrayList.add(new TeamScheduleModal(
                                jsonArray.getJSONObject(i).getString("team1"),
                                jsonArray.getJSONObject(i).getString("team2"),
                                jsonArray.getJSONObject(i).getString("matchTime"),
                                jsonArray.getJSONObject(i).getBoolean("isTeamAvailable"),
                                jsonArray.getJSONObject(i).getString("matchVenue")));
                    }
                }
                feedCounter = feedLimit;
                if((jsonArray.length() - feedLimit) <= 6){
                    feedLimit = feedLimit + (jsonArray.length() - feedLimit);
                }else{
                    if(jsonArray.length() >= (feedLimit + 6)){
                        feedLimit = feedLimit + 6;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        if (teamScheduleModalArrayList != null) {
            TeamScheduleModal teamScheduleModal;

            for (int i = 0; i < teamScheduleModalArrayList.size(); i++) {
                teamScheduleModal = teamScheduleModalArrayList.get(i);
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_list_matches, container, false);
                container.addView(view);

                TeamScheduleModal finalTeamScheduleModal = teamScheduleModal;
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView team1_TV, team2_TV, team1Short_TV, team2Short_TV, match_timer_TV, match_date_TV;
                        ConstraintLayout team1_CL, team2_CL, teamsOut_CL;
                        ImageView team1_IMG, team2_IMG;
                        MaterialCardView main_MCV;

                        main_MCV = view.findViewById(R.id.main_MCV);

                        team1_TV = view.findViewById(R.id.question_TV);
                        team2_TV = view.findViewById(R.id.team2_TV);
                        team1Short_TV = view.findViewById(R.id.team1Short_TV);
                        team2Short_TV = view.findViewById(R.id.team2Short_TV);
                        match_timer_TV = view.findViewById(R.id.match_timer_TV);
                        match_date_TV = view.findViewById(R.id.match_date_TV);

                        team1_CL = view.findViewById(R.id.team1_CL);
                        team2_CL = view.findViewById(R.id.team2_CL);
                        teamsOut_CL = view.findViewById(R.id.teamsOut_CL);

                        team1_IMG = view.findViewById(R.id.team1_IMG);
                        team2_IMG = view.findViewById(R.id.team2_IMG);

                        team1_TV.setText(finalTeamScheduleModal.team1_name);
                        team2_TV.setText(finalTeamScheduleModal.team2_name);

                        setShortName(finalTeamScheduleModal.team1_name, team1Short_TV);
                        setShortName(finalTeamScheduleModal.team2_name, team2Short_TV);

                        setTeamImage(finalTeamScheduleModal.team1_name, team1_IMG);
                        setTeamImage(finalTeamScheduleModal.team2_name, team2_IMG);

                        if (finalTeamScheduleModal.isTeamAvailable)
                            teamsOut_CL.setVisibility(View.VISIBLE);

                        if(finalTeamScheduleModal.matchTime.equals("LIVE")){
                            match_timer_TV.setText("LIVE");
                            match_date_TV.setVisibility(View.GONE);
                        } else if (finalTeamScheduleModal.matchTime.equals("COMPLETED")) {
                            match_timer_TV.setText("COMPLETED");
                            match_date_TV.setVisibility(View.GONE);
                        }else{
                            new MatchStartCountdown(context).countDownStart(finalTeamScheduleModal.matchTime, match_timer_TV);
                            setMatchTime(finalTeamScheduleModal.matchTime, match_date_TV);
                        }

                        main_MCV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UsersData.setCoins(context, UsersData.getCoins(context) + 100);
                            }
                        });

                    }
                });
            }

            Dashboard.shimmerMatch.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
        } else {
            //new BottomSheetDialog(context).NoInternetBottomSheet(((Activity)context).getWindow().getDecorView());
        }
    }

    private void setMatchTime(String dateString, TextView textView){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        LocalDate date = dateTime.toLocalDate();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        dateTime = LocalDateTime.parse(dateString, formatter);
        String outputString = dateTime.format(outputFormatter);

        if (date.isEqual(today)) {
            textView.setText("Today, "  + outputString);
        } else if (date.isEqual(tomorrow)) {
            textView.setText("Tomorrow, "  + outputString);
        } else {
            outputFormatter = DateTimeFormatter.ofPattern("dd MMM, hh:mm a");
            dateTime = LocalDateTime.parse(dateString, formatter);
            outputString = dateTime.format(outputFormatter);
            textView.setText(outputString);
        }
    }

    private void setShortName(String name, TextView textView){
        switch (name){
            case "Mumbai Indians" : textView.setText("MI"); break;
            case "Chennai Super Kings" : textView.setText("CSK"); break;
            case "Kolkata Knight Riders" : textView.setText("KKR"); break;
            case "Rajasthan Royals" : textView.setText("RR"); break;
            case "Royal Challengers Bangalore" : textView.setText("RCB"); break;
            case "Sunrisers Hyderabad" : textView.setText("SRH"); break;
            case "Delhi Capitals" : textView.setText("DC"); break;
            case "Gujarat Titans" : textView.setText("GT"); break;
            case "Lucknow Super Giants" : textView.setText("LSG"); break;
            case "Punjab Kings" : textView.setText("PBKS"); break;
        }
    }

    private void setTeamImage(String name, ImageView imageView) {
        int drawable = 0;
        switch (name) {
            case "Mumbai Indians":
                drawable = R.drawable.mi;
                break;
            case "Chennai Super Kings":
                drawable = R.drawable.csk;
                break;
            case "Kolkata Knight Riders":
                drawable = R.drawable.kkr;
                break;
            case "Rajasthan Royals":
                drawable = R.drawable.rr;
                break;
            case "Royal Challengers Bangalore":
                drawable = R.drawable.rcb;
                break;
            case "Sunrisers Hyderabad":
                drawable = R.drawable.srh;
                break;
            case "Delhi Capitals":
                drawable = R.drawable.dc;
                break;
            case "Gujarat Titans":
                drawable = R.drawable.gt;
                break;
            case "Lucknow Super Giants":
                drawable = R.drawable.lsg;
                break;
            case "Punjab Kings":
                drawable = R.drawable.pbks;
                break;
        }
        Glide.with(context)
                .load(context.getDrawable(drawable))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    private Bitmap getBitMap(Drawable drawable) {
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
