package com.onemosys.ipl.Helper;

import android.content.Context;

public class isUserExists {
    public static boolean isUserExists(Context context) {
        return !UsersData.getUserName(context).equals("null");
    }
}
