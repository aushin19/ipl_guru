package com.onemosys.ipl.NetworkCalls;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onemosys.ipl.Configs.LazyLoader;
import com.onemosys.ipl.Fragments.Dashboard;
import com.onemosys.ipl.Helper.MatchStartCountdown;
import com.onemosys.ipl.Modals.TeamScheduleModal;
import com.onemosys.ipl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                                jsonArray.getJSONObject(i).getString("team1Short"),
                                jsonArray.getJSONObject(i).getString("team2"),
                                jsonArray.getJSONObject(i).getString("team2Short"),
                                jsonArray.getJSONObject(i).getString("matchDay"),
                                jsonArray.getJSONObject(i).getString("matchTime"),
                                jsonArray.getJSONObject(i).getBoolean("isTeamAvailable")));
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

                        team1_TV = view.findViewById(R.id.team1_TV);
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
                        team1Short_TV.setText(finalTeamScheduleModal.team1_short_name);
                        team2Short_TV.setText(finalTeamScheduleModal.team2_short_name);

                        setTeamImage(finalTeamScheduleModal.team1_short_name, team1_IMG);
                        setTeamImage(finalTeamScheduleModal.team2_short_name, team2_IMG);

                        if (finalTeamScheduleModal.isTeamAvailable)
                            teamsOut_CL.setVisibility(View.VISIBLE);

                        new MatchStartCountdown(context).countDownStart(finalTeamScheduleModal.matchTime, match_timer_TV);
                        setMatchTime(finalTeamScheduleModal.matchTime, match_date_TV);
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

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm a");
        dateTime = LocalDateTime.parse(dateString, formatter);
        String outputString = dateTime.format(outputFormatter);

        if (date.isEqual(today)) {
            textView.setText("Today, "  + outputString);
        } else if (date.isEqual(tomorrow)) {
            textView.setText("Tomorrow, "  + outputString);
        } else {
            outputFormatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm a");
            dateTime = LocalDateTime.parse(dateString, formatter);
            outputString = dateTime.format(outputFormatter);
            textView.setText(outputString);
        }
    }

    private void setTeamImage(String name, ImageView imageView) {
        int drawable = 0;
        switch (name) {
            case "MI":
                drawable = R.drawable.mi;
                break;
            case "CSK":
                drawable = R.drawable.csk;
                break;
            case "KKR":
                drawable = R.drawable.kkr;
                break;
            case "RR":
                drawable = R.drawable.rr;
                break;
            case "RCB":
                drawable = R.drawable.rcb;
                break;
            case "SRH":
                drawable = R.drawable.srh;
                break;
            case "DC":
                drawable = R.drawable.dc;
                break;
            case "GT":
                drawable = R.drawable.gt;
                break;
            case "LSG":
                drawable = R.drawable.lsg;
                break;
            case "PBKS":
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
