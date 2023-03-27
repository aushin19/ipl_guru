package com.onemosys.ipl.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.onemosys.ipl.Modals.PosterModal;
import com.onemosys.ipl.R;

import java.util.ArrayList;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.SliderViewHolder> {

    private ArrayList<PosterModal> posterModalArrayList;
    public Context context;

    //this is a constructor to get values
    public PosterAdapter(Context context, ArrayList<PosterModal> posterModalArrayList){
        this.context = context;
        this.posterModalArrayList = posterModalArrayList;
    }

    //this is where the items will show
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_container,parent,false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //to hold the views
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int mPosition = holder.getAdapterPosition();

                PosterModal get = posterModalArrayList.get(mPosition);
                context = holder.imageView.getContext();
                Glide.with(context)
                        .load(get.image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageView);

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(get.link.contains("https://")){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(get.link));
                            context.startActivity(intent);
                        }else{
                            /*switch (get.link){
                                case "refer" : Home.chipNavigationBar.setItemSelected(R.id.menu_refer, true);
                                    break;
                            }*/
                        }
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return posterModalArrayList.size();
    }

    //we created a class to which holder get the view
    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

    }

}

