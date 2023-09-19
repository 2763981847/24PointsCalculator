package com.example.calculate24points;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate24points.adapter.PokerCardAdapter;
import com.example.calculate24points.bean.PokerCard;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private GridView pokerGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pokerGrid = findViewById(R.id.poker_grid);
        List<PokerCard> allPokerCards = PokerCard.ALL_POKER_CARDS;
        pokerGrid.setAdapter(new PokerCardAdapter(this, allPokerCards));
    }
}