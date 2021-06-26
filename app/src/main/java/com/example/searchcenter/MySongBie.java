package com.example.searchcenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySongBie implements Runnable {
    Handler handler;
    String TAG;

    public void setHandler(Handler h) {
        handler = new Handler();
        this.handler = h;
    }
    @Override
    public void run() {
        Document document = null;
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        try {
            document = Jsoup.connect("https://so.gushiwen.cn/mingjus/default.aspx?tstr=%e9%80%81%e5%88%ab").get();
            Log.i(TAG,"run:title=" + document.title());

            //https://so.gushiwen.cn/mingjus/default.aspx?tstr=%e6%8a%92%e6%83%85
            //https://www.boc.cn/sourcedb/whpj/

            Elements divs = document.getElementsByTag("div");

            Element div28 = divs.get(27);
            // Log.i(TAG,"run : div28"+div28);

            Elements as = div28.getElementsByTag("a");
            for(int i=0;i<as.size();i+=2){
                Element a1 = as.get(i);
                Element a2 = as.get(i+1);

                String poem = a1.text();
                String name = a2.text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("ItemTitle",poem);
                map.put("ItemDetail",name);
                retList.add(map);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);

    }
}
