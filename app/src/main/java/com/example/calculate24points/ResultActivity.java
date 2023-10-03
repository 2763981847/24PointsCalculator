package com.example.calculate24points;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate24points.util.ViewHeightUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 结果展示页面
 */
public class ResultActivity extends AppCompatActivity {
    // 定义视图
    private ListView resultList;
    private TextView resultSize;
    private List<String> results; // 存储计算结果的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView(); // 初始化视图
    }

    private void initView() {
        findViews(); // 查找视图组件
        initData(); // 初始化数据
        setAdapters(); // 设置适配器
        setListeners(); // 设置事件监听器
        renderViews(); // 渲染视图
    }

    /**
     * 渲染视图
     */
    private void renderViews() {
        // 渲染结果数量文本视图
        if (results.isEmpty()) { // 如果结果列表为空
            resultSize.setVisibility(TextView.GONE); // 隐藏结果数量文本视图
            return;
        }
        String text = "共找到" + results.size() + "个结果"; // 构建显示文本
        resultSize.setText(text); // 设置结果数量文本视图的文本
    }

    /**
     * 设置事件监听器
     */
    private void setListeners() {
        findViewById(R.id.try_again_button).setOnClickListener(v -> finish()); // 设置“再试一次”按钮的点击事件监听器，关闭当前Activity
    }

    /**
     * 设置适配器
     */
    private void setAdapters() {
        resultList.setAdapter(new ArrayAdapter<>(this, R.layout.result, results)); // 设置结果列表的适配器
        ViewHeightUtils.setListViewHeightBasedOnChildren(resultList); // 根据子项高度设置结果列表的高度
        resultList.setEmptyView(findViewById(R.id.empty_view)); // 设置空视图
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras(); // 获取传递的Bundle数据
        if (bundle == null) return; // 如果Bundle为空，直接返回
        results = Optional.ofNullable(bundle.getStringArrayList("results")).orElseGet(ArrayList::new); // 从Bundle中获取结果列表，如果为空则创建一个空列表
    }

    /**
     * 查找视图
     */
    private void findViews() {
        resultList = findViewById(R.id.result_list); // 查找结果列表视图
        resultSize = findViewById(R.id.result_size); // 查找结果数量文本视图
    }
}
