package com.example.calculate24points;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.calculate24points.util.LayoutHeightUtils;

public class ResultActivity extends AppCompatActivity {

    private ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultList = findViewById(R.id.result_list);
        Bundle bundle = getIntent().getExtras();
        resultList.setAdapter(new ArrayAdapter<>(this, R.layout.result, bundle.getStringArrayList("results")));
        LayoutHeightUtils.setListViewHeightBasedOnChildren(resultList);
        resultList.setEmptyView(findViewById(R.id.empty_view));
        findViewById(R.id.try_again_button).setOnClickListener(this::tryAgainListener);
    }

    private void tryAgainListener(View view) {
        finish();
    }
}