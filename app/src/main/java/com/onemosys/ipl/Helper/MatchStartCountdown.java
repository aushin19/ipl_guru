package com.onemosys.ipl.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MatchStartCountdown {
    Context context;

    public MatchStartCountdown(Context context) {
        this.context = context;
    }

    public static String parseDate(int unixTime) {
        long dv = Long.valueOf(unixTime) * 1000;
        Date df = new java.util.Date(dv);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(df);
    }

    public static Long toUnixTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime() / 1000L;
    }

    public void countDownStart(String Time, TextView textView) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date futureDate = dateFormat.parse(Time);
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime() - currentDate.getTime();

                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        @SuppressLint("DefaultLocale") String daysLeft = "" + String.format("%02d", days);
                        @SuppressLint("DefaultLocale") String hoursLeft = "" + String.format("%02d", hours);
                        @SuppressLint("DefaultLocale") String minsLeft = "" + String.format("%02d", minutes);
                        @SuppressLint("DefaultLocale") String secondLeft = "" + String.format("%02d", seconds);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(daysLeft.equals("00")){
                                    textView.setText(hoursLeft + "h " + minsLeft + "m");
                                }
                                if (hoursLeft.equals("00")){
                                    textView.setText(minsLeft + "m " + secondLeft + "s");
                                }
                                if(minsLeft.equals("00")){
                                    textView.setText(secondLeft + "s");
                                }else{
                                    handler.removeMessages(0);
                                    if(daysLeft.equals("1") || daysLeft.equals("01"))
                                        textView.setText(daysLeft + " day");
                                    else
                                        textView.setText(daysLeft + " days");
                                }
                            }
                        });
                    } else {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("LIVE");
                                handler.removeMessages(0);
                            }
                        });
                    }
                } catch (Exception e) {
                    handler.removeMessages(0);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

}
