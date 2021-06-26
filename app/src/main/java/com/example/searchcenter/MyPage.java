package com.example.searchcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
    }

    public void openShuQing(View view) {
        Intent shuqing = new Intent(this,ShuQingActivity.class);
        startActivity(shuqing);
    }

    public void openXieJing(View view) {
        Intent xiejing = new Intent(this,XieJingActivity.class);
        startActivity(xiejing);
    }
    public void openSongBie(View view) {
        Intent songbie = new Intent(this,SongBieActivity.class);
        startActivity(songbie);
    }
    public void openLiZhi(View view) {
        Intent lizhi = new Intent(this,LiZhiActivity.class);
        startActivity(lizhi);
    }

    public void openYongWu(View view) {
        Intent yongwu = new Intent(this,LiZhiActivity.class);
        startActivity(yongwu);
    }
    public void AiGuo(View view) {
        Intent aiguo = new Intent(this,LiZhiActivity.class);
        startActivity(aiguo);
    }
}
