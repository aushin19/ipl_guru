package com.onemosys.ipl.Adapters;

import static com.onemosys.ipl.Fragments.Profile.bottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.onemosys.ipl.BottomSheet.BottomSheetDialog;
import com.onemosys.ipl.Modals.TradeModal;
import com.onemosys.ipl.R;

import java.util.ArrayList;

public class TraderAdapter extends RecyclerView.Adapter<TraderAdapter.TradeViewHolder> {

    Context context;
    ArrayList<TradeModal> tradeModalArrayList = new ArrayList<>();

    public TraderAdapter(Context context, ArrayList<TradeModal> tradeModalArrayList){
        this.context = context;
        this.tradeModalArrayList = tradeModalArrayList;
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TradeViewHolder(layoutInflater.inflate(R.layout.item_list_trade, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        TradeModal tradeModal = tradeModalArrayList.get(holder.getAdapterPosition());

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.traders_TV.setText("232 traders");
                holder.question_TV.setText(tradeModal.question);
                holder.info_TV.setText(tradeModal.info);

                holder.trade_yes_TV.setText(tradeModal.yes + "");
                holder.trade_no_TV.setText(tradeModal.no + "");

                holder.trade_yes_MCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog = new BottomSheetDialog(context);
                        bottomSheetDialog.TradeBottomSheet(((Activity)context).getWindow().getDecorView(),
                                tradeModal.yes, tradeModal.prizePool);
                    }
                });

                holder.trade_no_MCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog = new BottomSheetDialog(context);
                        bottomSheetDialog.TradeBottomSheet(((Activity)context).getWindow().getDecorView(),
                                tradeModal.no, tradeModal.prizePool);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tradeModalArrayList.size();
    }

    public class TradeViewHolder extends RecyclerView.ViewHolder {
        TextView traders_TV, question_TV, info_TV, trade_yes_TV, trade_no_TV;
        MaterialCardView trade_no_MCV, trade_yes_MCV;
        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            traders_TV = itemView.findViewById(R.id.traders_TV);
            question_TV = itemView.findViewById(R.id.question_TV);
            info_TV = itemView.findViewById(R.id.info_TV);

            trade_yes_TV = itemView.findViewById(R.id.trade_yes_TV);
            trade_no_TV = itemView.findViewById(R.id.trade_no_TV);

            trade_no_MCV = itemView.findViewById(R.id.trade_no_MCV);
            trade_yes_MCV = itemView.findViewById(R.id.trade_yes_MCV);
        }
    }
}
