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

import com.onemosys.ipl.Configs.LazyLoader;
import com.onemosys.ipl.Helper.MatchStartCountdown;
import com.onemosys.ipl.Modals.TeamScheduleModal;
import com.onemosys.ipl.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetTeamSchedule extends AsyncTask<Void, Void, Void> {
    Context context;
    View view;
    ArrayList<TeamScheduleModal> teamScheduleModalArrayList = new ArrayList<>();
    String content;
    long todayUNIX;

    public GetTeamSchedule(Context context, View view) {
        this.context = context;
        this.view = view;
        todayUNIX = System.currentTimeMillis() / 1000L;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            if (LazyLoader.TEAM_SCHEDULE_ARRAYLIST == null) {
                Document document = Jsoup.connect("https://tataiplguru.blogspot.com/2023/03/time-table.html").get();
                Elements element = document.getElementsByClass("team_schedule");

                content = Jsoup.parse(String.valueOf(element)).text();

                try {
                    JSONArray jsonArray = new JSONArray(content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (todayUNIX <= jsonArray.getJSONObject(i).getInt("matchTime")) {
                            teamScheduleModalArrayList.add(new TeamScheduleModal(jsonArray.getJSONObject(i).getString("team1"), jsonArray.getJSONObject(i).getString("team1Short"), jsonArray.getJSONObject(i).getString("team2"), jsonArray.getJSONObject(i).getString("team2Short"), jsonArray.getJSONObject(i).getString("matchDay"), jsonArray.getJSONObject(i).getInt("matchTime"), jsonArray.getJSONObject(i).getBoolean("isTeamAvailable")));
                        }
                    }
                    LazyLoader.TEAM_SCHEDULE_ARRAYLIST = teamScheduleModalArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                teamScheduleModalArrayList = LazyLoader.TEAM_SCHEDULE_ARRAYLIST;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (teamScheduleModalArrayList != null) {
                    TeamScheduleModal teamScheduleModal;
                    TextView team1_TV, team2_TV, team1Short_TV, team2Short_TV, match_timer_TV, match_date_TV;
                    ConstraintLayout team1_CL, team2_CL, teamsOut_CL;
                    ImageView team1_IMG, team2_IMG;

                    for (int i = 0; i < teamScheduleModalArrayList.size(); i++) {
                        teamScheduleModal = teamScheduleModalArrayList.get(i);

                        LinearLayout container = view.findViewById(R.id.team_schedule_container);
                        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.item_list_matches, container, false);

                        team1_TV = view.findViewById(R.id.team1_TV);
                        team2_TV = view.findViewById(R.id.team2_TV);
                        team1Short_TV = view.findViewById(R.id.team1Short_TV);
                        team2Short_TV = view.findViewById(R.id.team2Short_TV);
                        match_timer_TV = view.findViewById(R.id.match_timer_TV);

                        team1_CL = view.findViewById(R.id.team1_CL);
                        team2_CL = view.findViewById(R.id.team2_CL);
                        teamsOut_CL = view.findViewById(R.id.teamsOut_CL);

                        team1_IMG = view.findViewById(R.id.team1_IMG);
                        team2_IMG = view.findViewById(R.id.team2_IMG);

                        team1_TV.setText(teamScheduleModal.team1_name);
                        team2_TV.setText(teamScheduleModal.team2_name);
                        team1Short_TV.setText(teamScheduleModal.team1_short_name);
                        team2Short_TV.setText(teamScheduleModal.team2_short_name);

                        setTeamImage(teamScheduleModal.team1_short_name, team1_IMG);
                        setTeamImage(teamScheduleModal.team2_short_name, team2_IMG);

                        if (teamScheduleModal.isTeamAvailable)
                            teamsOut_CL.setVisibility(View.VISIBLE);

                        new MatchStartCountdown(context).countDownStart(teamScheduleModal.matchTime, match_timer_TV);

                        container.addView(view);
                    }
                } else {
                    //new BottomSheetDialog(context).NoInternetBottomSheet(((Activity)context).getWindow().getDecorView());
                }
            }
        });
    }

    private void setTeamImage(String name, ImageView imageView) {
        switch (name) {
            case "MI":
                imageView.setImageResource(R.drawable.mi);
                break;
            case "CSK":
                imageView.setImageResource(R.drawable.csk);
                break;
            case "KKR":
                imageView.setImageResource(R.drawable.kkr);
                break;
            case "RR":
                imageView.setImageResource(R.drawable.rr);
                break;
            case "RCB":
                imageView.setImageResource(R.drawable.rcb);
                break;
            case "SRH":
                imageView.setImageResource(R.drawable.srh);
                break;
            case "DC":
                imageView.setImageResource(R.drawable.dc);
                break;
            case "GT":
                imageView.setImageResource(R.drawable.gt);
                break;
            case "LSG":
                imageView.setImageResource(R.drawable.lsg);
                break;
            case "PBKS":
                imageView.setImageResource(R.drawable.pbks);
                break;
        }
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
