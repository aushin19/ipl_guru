package com.onemosys.ipl.NetworkCalls;

import static com.onemosys.ipl.Fragments.Dashboard.sliderHandler;
import static com.onemosys.ipl.Fragments.Dashboard.viewPager2;
import static com.onemosys.ipl.Fragments.Dashboard.wormDotsIndicator;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.onemosys.ipl.Adapters.PosterAdapter;
import com.onemosys.ipl.Configs.LazyLoader;
import com.onemosys.ipl.Fragments.Dashboard;
import com.onemosys.ipl.Modals.PosterModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetPosters extends AsyncTask<String, String, String> {

    Context context;
    ArrayList<PosterModal> posterModalArrayList = new ArrayList<>();

    public GetPosters(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            if(LazyLoader.POSTER == null){
                Document document = Jsoup.connect("https://tataiplguru.blogspot.com/2023/03/banner.html").get();
                Elements element = document.getElementsByClass("poster_feed");

                LazyLoader.POSTER = Jsoup.parse(element.toString()).text();
                parseJSON(Jsoup.parse(element.toString()).text());
            }else{
                parseJSON(LazyLoader.POSTER);
            }

        }catch (Exception e){

        }

        return null;
    }

    public void parseJSON(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                posterModalArrayList.add(new PosterModal(
                        jsonArray.getJSONObject(i).getString("image_link"),
                        jsonArray.getJSONObject(i).getString("redirect_link")
                ));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(posterModalArrayList != null){
                    PosterAdapter posterAdapter = new PosterAdapter(context, posterModalArrayList);
                    posterAdapter.setHasStableIds(true);
                    viewPager2.setAdapter(new PosterAdapter(context, posterModalArrayList));
                    viewPager2.setClipToPadding(false);
                    viewPager2.setClipChildren(false);
                    viewPager2.setOffscreenPageLimit(3);
                    viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(0));
                    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {
                            float r = 1 - Math.abs(position);
                            page.setScaleY(0.85f + r * 0.15f);
                        }
                    });
                    viewPager2.setPageTransformer(compositePageTransformer);
                    viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            sliderHandler.removeCallbacks(sliderRunnable);
                            sliderHandler.postDelayed(sliderRunnable, 3000);
                        }
                    });
                    wormDotsIndicator.setViewPager2(viewPager2);

                    Dashboard.shimmerImgSlider.setVisibility(View.GONE);
                    viewPager2.setVisibility(View.VISIBLE);
                }else{
                    //new BottomSheetDialog(context).NoInternetBottomSheet(((Activity)context).getWindow().getDecorView());
                }
            }
        });

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (posterModalArrayList.size() == viewPager2.getCurrentItem() + 1)
                viewPager2.setCurrentItem(0);
            else
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

}
