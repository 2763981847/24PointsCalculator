package com.example.calculate24points;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate24points.util.ViewHeightUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResultActivity extends AppCompatActivity {

    private ListView resultList;
    private TextView resultSize;
    private List<String> results = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
    }

    private void initView() {
        findViews();
        initData();
        setAdapters();
        setListeners();
        setResultSize();
    }

    private void setResultSize() {
        if (results.isEmpty()) {
            resultSize.setVisibility(TextView.GONE);
            return;
        }
        String text = "共找到" + results.size() + "个结果";
        resultSize.setText(text);
    }

    private void setListeners() {
        findViewById(R.id.try_again_button).setOnClickListener(v -> finish());
    }

    private void setAdapters() {
        resultList.setAdapter(new ArrayAdapter<>(this, R.layout.result, results));
        ViewHeightUtils.setListViewHeightBasedOnChildren(resultList);
        resultList.setEmptyView(findViewById(R.id.empty_view));
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        results = Optional.ofNullable(bundle.getStringArrayList("results")).orElseGet(ArrayList::new);
    }

    private void findViews() {
        resultList = findViewById(R.id.result_list);
        resultSize = findViewById(R.id.result_size);
    }
}