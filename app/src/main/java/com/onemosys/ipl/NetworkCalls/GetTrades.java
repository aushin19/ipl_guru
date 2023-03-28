package com.onemosys.ipl.NetworkCalls;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.onemosys.ipl.Adapters.TraderAdapter;
import com.onemosys.ipl.Fragments.Trade;
import com.onemosys.ipl.Modals.TradeModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetTrades extends AsyncTask<Void, Void, Void> {
    Context context;
    View view;
    ArrayList<TradeModal> tradeModalArrayList = new ArrayList<>();
    String content;

    public GetTrades(Context context, View view){
        this.context = context;
        this.view = view;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://tataiplguru.blogspot.com/2023/03/trades.html").get();
            Elements element = document.getElementsByClass("trade_list");

            content = Jsoup.parse(String.valueOf(element)).text();

            try {
                JSONArray jsonArray = new JSONArray(content);

                for (int i = 0; i < jsonArray.length(); i++) {
                    if (true) {
                        tradeModalArrayList.add(new TradeModal(
                                jsonArray.getJSONObject(i).getString("question"),
                                jsonArray.getJSONObject(i).getString("info"),
                                jsonArray.getJSONObject(i).getLong("prizepool"),
                                jsonArray.getJSONObject(i).getInt("yes"),
                                jsonArray.getJSONObject(i).getInt("no")));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TraderAdapter traderAdapter = new TraderAdapter(context, tradeModalArrayList);
                traderAdapter.setHasStableIds(true);
                Trade.trade_RCV.setHasFixedSize(true);
                Trade.trade_RCV.setItemViewCacheSize(tradeModalArrayList.size());
                Trade.trade_RCV.setAdapter(traderAdapter);

                Trade.trade_shimmer.setVisibility(View.GONE);
                Trade.trade_RCV.setVisibility(View.VISIBLE);
            }
        });
    }
}
