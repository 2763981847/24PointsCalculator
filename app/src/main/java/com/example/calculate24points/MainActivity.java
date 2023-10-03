package com.example.calculate24points;

import static com.example.calculate24points.entity.PokerCard.ALL_POKER_CARDS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculate24points.adapter.PokerCardAdapter;
import com.example.calculate24points.core.TwentyFourPointsCalculator;
import com.example.calculate24points.entity.PokerCard;
import com.example.calculate24points.util.ViewHeightUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * app主页面
 */
public class MainActivity extends AppCompatActivity {

    // 声明视图组件
    private GridView pokerGrid;
    private GridView selectedPokerList;

    private Button calculateButton;

    private Button clearButton;
    // 声明适配器
    private PokerCardAdapter selectedPokerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化视图
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 查找视图组件
        findViews();
        // 设置适配器
        setAdapters();
        // 设置事件监听器
        setListeners();
    }

    /**
     * 设置事件监听器
     */
    private void setListeners() {
        // 设置扑克牌选择事件监听器
        pokerGrid.setOnItemClickListener(this::select);
        // 设置已选扑克牌取消选择事件监听器
        selectedPokerList.setOnItemClickListener(this::cancel);
        // 设置清除已选扑克牌按钮点击事件监听器
        clearButton.setOnClickListener(v -> clearSelect());
        // 设置计算按钮点击事件监听器
        calculateButton.setOnClickListener(v -> calculate());
    }

    /**
     * 设置适配器
     */
    private void setAdapters() {
        // 设置扑克牌展示的GridView的适配器
        pokerGrid.setAdapter(new PokerCardAdapter(this, ALL_POKER_CARDS));
        // 根据子项高度设置GridView的高度
        ViewHeightUtils.setGirdViewHeightBasedOnChildren(pokerGrid);
        // 设置已选扑克牌列表的适配器
        selectedPokerAdapter = new PokerCardAdapter(this, new ArrayList<>());
        selectedPokerList.setAdapter(selectedPokerAdapter);
        // 设置空视图
        selectedPokerList.setEmptyView(findViewById(R.id.empty_view));
    }

    /**
     * 查找视图组件
     */
    private void findViews() {
        calculateButton = findViewById(R.id.calculate_button);
        pokerGrid = findViewById(R.id.poker_grid);
        clearButton = findViewById(R.id.clear_button);
        selectedPokerList = findViewById(R.id.selected_poker_list);
    }

    /**
     * 取消选择
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    private void cancel(AdapterView<?> parent, View view, int position, long id) {
        // 获取已选扑克牌列表
        List<PokerCard> selectedPokerCards = selectedPokerAdapter.getPokerCards();
        // 移除选定位置的扑克牌
        selectedPokerCards.remove(position);
        // 通知适配器数据已更改
        selectedPokerAdapter.notifyDataSetChanged();
        // 禁用计算按钮
        calculateButton.setEnabled(false);
    }

    /**
     * 选择扑克牌
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void select(AdapterView<?> parent, View view, int position, long id) {
        // 获取已选扑克牌列表
        List<PokerCard> selectedPokerCards = selectedPokerAdapter.getPokerCards();
        // 如果已选择了四张卡牌
        if (selectedPokerCards.size() >= 4) {
            Toast toast = Toast.makeText(this, "已选择四张卡牌", Toast.LENGTH_SHORT);
            // 显示提示消息
            toast.show();
            // 返回，不再添加扑克牌
            return;
        }
        // 获取选中的扑克牌
        PokerCard pokerCard = ALL_POKER_CARDS.get(position);
        // 添加选中的扑克牌到已选列表
        selectedPokerCards.add(pokerCard);
        // 通知适配器数据已更改
        selectedPokerAdapter.notifyDataSetChanged();
        // 如果已选扑克牌数为4，启用计算按钮
        calculateButton.setEnabled(selectedPokerCards.size() == 4);
    }

    /**
     * 清空已选扑克牌列表
     */
    private void clearSelect() {
        // 清空已选扑克牌列表
        selectedPokerAdapter.getPokerCards().clear();
        // 禁用计算按钮
        calculateButton.setEnabled(false);
        // 通知适配器数据已更改
        selectedPokerAdapter.notifyDataSetChanged();
    }

    /**
     * 计算结果
     */
    private void calculate() {
        String[] numbers = selectedPokerAdapter.getPokerCards()
                .stream()
                .map(pokerCard -> String.valueOf(pokerCard.getNumber()))
                // 获取已选扑克牌的数字并转换为字符串数组
                .toArray(String[]::new);
        // 借助TwentyFourPointsCalculator计算结果
        List<String> results = TwentyFourPointsCalculator.calculate(numbers);
        // 创建Intent，跳转到ResultActivity
        Intent intent = new Intent(this, ResultActivity.class);
        // 创建Bundle来传递数据
        Bundle bundle = new Bundle();
        // 将结果列表放入Bundle中
        bundle.putStringArrayList("results", (ArrayList<String>) results);
        // 将Bundle添加到Intent中
        intent.putExtras(bundle);
        // 启动ResultActivity
        startActivity(intent);
        // 清空已选扑克牌列表
        clearSelect();
    }
}
