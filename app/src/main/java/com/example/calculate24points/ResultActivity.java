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
    // 存储计算结果的列表
    private List<String> results;

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
        if (results.isEmpty()) {
            // 如果结果列表为空，隐藏结果数量文本视图
            resultSize.setVisibility(TextView.GONE);
            return;
        }
        // 构建显示文本
        String text = "共找到" + results.size() + "个结果";
        // 设置结果数量文本视图的文本
        resultSize.setText(text);
    }

    /**
     * 设置事件监听器
     */
    private void setListeners() {
        // 设置“再试一次”按钮的点击事件监听器，关闭当前Activity
        findViewById(R.id.try_again_button).setOnClickListener(v -> finish());
    }

    /**
     * 设置适配器
     */
    private void setAdapters() {
        // 设置结果列表的适配器
        resultList.setAdapter(new ArrayAdapter<>(this, R.layout.result, results));
        // 根据子项高度设置结果列表的高度
        ViewHeightUtils.setListViewHeightBasedOnChildren(resultList);
        // 设置空视图
        resultList.setEmptyView(findViewById(R.id.empty_view));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 获取传递的Bundle数据
        Bundle bundle = getIntent().getExtras();
        // 如果Bundle为空，直接返回
        if (bundle == null) return;
        // 从Bundle中获取结果列表，如果为空则创建一个空列表
        results = Optional.ofNullable(bundle.getStringArrayList("results")).orElseGet(ArrayList::new);
    }

    /**
     * 查找视图
     */
    private void findViews() {
        resultList = findViewById(R.id.result_list);
        resultSize = findViewById(R.id.result_size);
    }
}
