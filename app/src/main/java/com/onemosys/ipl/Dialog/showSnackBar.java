package com.onemosys.ipl.Dialog;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.onemosys.ipl.R;

public class showSnackBar {

    public static void build(String message, int color, Context context, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(context.getColor(R.color.dialog_background))
                .setTextColor(context.getColor(color))
                .show();
    }

}
