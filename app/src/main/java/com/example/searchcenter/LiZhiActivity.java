package com.example.searchcenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class LiZhiActivity extends ListActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private String TAG = "LiZhiActivity";
    Handler handler;
    private List<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initListView();

        this.setListAdapter(listItemAdapter);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(LiZhiActivity.this, listItems,
                            R.layout.listitem,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);

            }
        };

        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(this);
        MyLiZhi task = new MyLiZhi();
        task.setHandler(handler);
        Thread thread = new Thread(task);
        thread.start();

    }


    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 500; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("ItemTitle", "Title" + i);
            map.put("ItemDetail", "detail:" + i);
            listItems.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.listitem,
                new String[]{"ItemTitle", "ItemDetail"},
                new int[]{R.id.itemTitle, R.id.itemDetail}
        );
        setListAdapter(listItemAdapter);
        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        HashMap<String, String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请注意！").setMessage("请确认是否删除当前诗句").setPositiveButton("否", null)
                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(position);
                        listItemAdapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
        final TextView textView = (TextView) findViewById(R.id.itemTitle);
        final TextView textView2 = (TextView) findViewById(R.id.itemDetail);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请注意！");
        builder.setMessage("请确认是否复制当前诗句");
        builder.setPositiveButton("否", null);
        builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData,clipData2;// 把数据复到剪贴板
                clipData = ClipData.newPlainText(null,textView.getText());
                clipData2 = ClipData.newPlainText(null,textView2.getText());
                clipboard.setPrimaryClip(clipData);
                clipboard.setPrimaryClip(clipData2);


                Log.i(TAG,"dangqianzhi"+clipData+clipData2);

            }
        });
        builder.create().show();
        return true;
    }

}
