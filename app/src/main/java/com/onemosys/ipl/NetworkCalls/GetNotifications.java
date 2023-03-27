package com.onemosys.ipl.NetworkCalls;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import androidx.recyclerview.widget.RecyclerView;

import com.onemosys.ipl.Adapters.NotificationsAdapter;
import com.onemosys.ipl.BottomSheet.BottomSheetDialog;
import com.onemosys.ipl.Configs.LazyLoader;
import com.onemosys.ipl.Configs.android_configs;
import com.onemosys.ipl.Fragments.Profile;
import com.onemosys.ipl.Modals.NotificationModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GetNotifications extends AsyncTask<String,String,String> {
    Context context;
    RecyclerView recyclerView;
    ArrayList<NotificationModal> notificationModalArrayList = new ArrayList<>();;
    NotificationsAdapter notificationsAdapter;
    String content;

    public GetNotifications(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            if(LazyLoader.NOTIFICATIONS == null){
                Document document = Jsoup.connect(android_configs.NOTIFICATIONS_URL).get();
                Elements element = document.getElementsByClass("notification_list");

                LazyLoader.NOTIFICATIONS  = content = Jsoup.parse(String.valueOf(element)).text();
            }else{
                content = LazyLoader.NOTIFICATIONS;
            }

            try {
                JSONArray jsonArray= new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    notificationModalArrayList.add(new NotificationModal(
                            jsonArray.getJSONObject(i).getString("title"),
                            jsonArray.getJSONObject(i).getString("content"),
                            jsonArray.getJSONObject(i).getString("date")
                    ));
                }

                Collections.reverse(notificationModalArrayList);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(notificationModalArrayList != null){
                    notificationsAdapter = new NotificationsAdapter(context, notificationModalArrayList);
                    notificationsAdapter.setHasStableIds(true);
                    recyclerView.setItemViewCacheSize(notificationModalArrayList.size());
                    recyclerView.setAdapter(notificationsAdapter);
                    Profile.waitingDialog.dismiss();
                    Profile.bottomSheetDialog.dialog.show();
                }else{
                    new BottomSheetDialog(context).NoInternetBottomSheet(((Activity)context).getWindow().getDecorView());
                }
            }
        });
    }
}
