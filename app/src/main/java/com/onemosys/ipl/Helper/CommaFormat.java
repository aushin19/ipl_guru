package com.onemosys.ipl.Helper;

import java.text.DecimalFormat;

public class CommaFormat {

    public static String coinsCommaFormat(long coins){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(coins);
    }
    public static String walletCommaFormat(double wallet){
        return new DecimalFormat("##.##").format(wallet);
    }

}
