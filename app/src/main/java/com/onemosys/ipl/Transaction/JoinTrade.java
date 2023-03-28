package com.onemosys.ipl.Transaction;

import android.content.Context;
import android.widget.Toast;
import com.onemosys.ipl.Helper.UsersData;
import com.onemosys.ipl.Helper.isUserExists;

public class JoinTrade {
    public static void JoinTrade(Context context, int EntryFee){
        if(isUserExists.isUserExists(context)){
            if(UsersData.getCoins(context) >= EntryFee){
                UsersData.setCoins(context, UsersData.getCoins(context) - EntryFee);
                Toast.makeText(context, "Joined", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Insufficient Balance", Toast.LENGTH_SHORT).show();
            }
        }else{
            
        }
    }
}
