package com.onemosys.ipl.Helper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import com.onemosys.ipl.Configs.android_configs;

public class UsersData {

    public static boolean setUserName(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString("user_name", value).apply();
        return true;
    }

    public static String getUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString("user_name", "null");
    }

    public static boolean setUserEmail(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString("email", value).apply();
        return true;
    }

    public static String getUserEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString("email", "null");
    }

    public static boolean setUserEmailVerify(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString("emailVerify", value).apply();
        return true;
    }

    public static String getUserEmailVerify(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString("emailVerify", "null");
    }

    public static boolean setCheckInDate(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString("check_in_date", value).apply();
        return true;
    }

    public static String getCheckInDate(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString("check_in_date", "null");
    }

    public static boolean setReferralCode(Context context, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString("referral_code", value).apply();
        return true;
    }

    public static String getReferralCode(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getString("referral_code", "null");
    }

    public static boolean setCoins(Context context, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putInt("coins", value).apply();
        return true;
    }

    public static int getCoins(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt("coins", 0);
    }

    public static boolean setWallet(Context context, double value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putFloat("wallet", (float) value).apply();
        return true;
    }

    public static double getWallet(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getFloat("wallet", 0);
    }
    public static boolean setAvatar(Context context, int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putInt("avatar", value).apply();
        return true;
    }

    public static int getAvatar(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(android_configs.SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getInt("avatar", 0);
    }

}
