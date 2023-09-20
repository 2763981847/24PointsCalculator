package com.example.calculate24points;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate24points.adapter.PokerCardAdapter;
import com.example.calculate24points.bean.PokerCard;
import com.example.calculate24points.core.TwentyFourPointsCalculator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView pokerGrid;
    private GridView selectedPokerList;
    private PokerCardAdapter selectedPokerAdapter = new PokerCardAdapter(this, new ArrayList<>());
    private Button calculateButton;
    private List<PokerCard> allPokerCards;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViews();
        setAdapters();
        setClickListeners();
    }

    private void setClickListeners() {
        pokerGrid.setOnItemClickListener(this::select);
        selectedPokerList.setOnItemClickListener(this::cancel);
        clearButton.setOnClickListener(this::clearSelect);
        calculateButton.setOnClickListener(this::calculate);
    }

    private void setAdapters() {
        allPokerCards = PokerCard.ALL_POKER_CARDS;
        pokerGrid.setAdapter(new PokerCardAdapter(this, allPokerCards));
        selectedPokerList.setAdapter(selectedPokerAdapter);
    }

    private void findViews() {
        calculateButton = findViewById(R.id.calculate_button);
        pokerGrid = findViewById(R.id.poker_grid);
        clearButton = findViewById(R.id.clear_button);
        selectedPokerList = findViewById(R.id.selected_poker_list);
    }

    private void cancel(AdapterView<?> parent, View view, int position, long id) {
        List<PokerCard> selectedPokerCards = selectedPokerAdapter.getPokerCards();
        selectedPokerCards.remove(position);
        selectedPokerAdapter.notifyDataSetChanged();
        calculateButton.setEnabled(false);
    }

    public void select(AdapterView<?> parent, View view, int position, long id) {
        PokerCard pokerCard = allPokerCards.get(position);
        List<PokerCard> selectedPokerCards = selectedPokerAdapter.getPokerCards();
        if (selectedPokerCards.size() >= 4) {
            return;
        }
        selectedPokerCards.add(pokerCard);
        selectedPokerAdapter.notifyDataSetChanged();
        calculateButton.setEnabled(selectedPokerCards.size() == 4);
    }

    private void clearSelect(View view) {
        selectedPokerAdapter.getPokerCards().clear();
        calculateButton.setEnabled(false);
        selectedPokerAdapter.notifyDataSetChanged();
    }

    private void calculate(View view) {
        String[] numbers = selectedPokerAdapter.getPokerCards()
                .stream()
                .map(pokerCard -> String.valueOf(pokerCard.getNumber()))
                .toArray(String[]::new);
        List<String> results = TwentyFourPointsCalculator.calculate(numbers);
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("results", (ArrayList<String>) results);
        intent.putExtras(bundle);
        startActivity(intent);
        clearSelect(view);
    }

}