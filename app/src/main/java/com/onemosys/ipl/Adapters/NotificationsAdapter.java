package com.onemosys.ipl.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.onemosys.ipl.Modals.NotificationModal;
import com.onemosys.ipl.R;

import java.util.ArrayList;
import java.util.Random;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsAdapterViewHolder> {

    Context context;
    ArrayList<NotificationModal> notificationModalArrayList;
    boolean isTextShrink = true;
    int avatar[] = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6
            , R.drawable.avatar7, R.drawable.avatar8, R.drawable.avatar9, R.drawable.avatar10};

    public NotificationsAdapter(Context context, ArrayList<NotificationModal> notificationModalArrayList){
        this.context = context;
        this.notificationModalArrayList = notificationModalArrayList;
    }

    @NonNull
    @Override
    public NotificationsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NotificationsAdapterViewHolder(layoutInflater.inflate(R.layout.item_list_notification, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapterViewHolder holder, int position) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int mPosition = holder.getAdapterPosition();
                NotificationModal notificationModal = notificationModalArrayList.get(mPosition);
                holder.notification_title.setText(notificationModal.title);
                holder.notification_date.setText(notificationModal.date);
                holder.notification_IMG.setBackground(context.getDrawable(avatar[genRandomNumber()]));

                holder.notification_body.setText(notificationModal.content);

                holder.notification_body.resetState(true);
                holder.notification_body.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
                    @Override
                    public void onStateChange(boolean isShrink) {
                        isTextShrink = isShrink;
                    }
                });

                holder.notification_body.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isTextShrink){
                            holder.notification_body.resetState(false);
                        }else{
                            holder.notification_body.resetState(true);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationModalArrayList.size();
    }

    public class NotificationsAdapterViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView notification_CV;
        TextView notification_title, notification_date;
        ExpandableTextView notification_body;
        ImageView notification_IMG;

        public NotificationsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_CV = itemView.findViewById(R.id.notification_CV);
            notification_body = itemView.findViewById(R.id.notification_body);
            notification_title = itemView.findViewById(R.id.notification_title);
            notification_date = itemView.findViewById(R.id.notification_date);
            notification_IMG = itemView.findViewById(R.id.notification_IMG);
        }
    }

    private int genRandomNumber(){
        final int min = 0;
        final int max = 9;
        return new Random().nextInt((max - min) + 1) + min;
    }

}
