package com.example.calculate24points.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.calculate24points.R;
import com.example.calculate24points.entity.PokerCard;

import java.util.List;
import java.util.Locale;

/**
 * 扑克牌视图适配器
 */

public class PokerCardAdapter extends BaseAdapter {

    // 上下文对象
    private final Context context;
    // 存储扑克牌数据的列表
    private final List<PokerCard> pokerCards;

    /**
     * 获取扑克牌数据列表
     */
    public List<PokerCard> getPokerCards() {
        return pokerCards;
    }

    /**
     * 构造函数，初始化适配器
     *
     * @param context
     * @param pokerCards
     */
    public PokerCardAdapter(Context context, List<PokerCard> pokerCards) {
        this.context = context;
        this.pokerCards = pokerCards;
    }

    @Override
    public int getCount() {
        // 获取扑克牌列表的大小
        return pokerCards.size();
    }

    @Override
    public Object getItem(int position) {
        // 获取指定位置的扑克牌对象
        return pokerCards.get(position);
    }

    @Override
    public long getItemId(int position) {
        // 获取指定位置的扑克牌对象的ID
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 如果convertView为空，表示需要创建一个新的列表项视图
            convertView = LayoutInflater.from(context).inflate(R.layout.poker_image, null);
            viewHolder = new ViewHolder();
            // 查找ImageView组件
            viewHolder.imageView = convertView.findViewById(R.id.poker_image);
            // 将ViewHolder对象存储在视图中，以便后续重用
            convertView.setTag(viewHolder);
        } else {
            // 如果convertView不为空，直接从视图中获取ViewHolder对象
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 获取当前位置的扑克牌对象
        PokerCard pokerCard = pokerCards.get(position);
        // 根据花色和数字构建扑克牌图片资源的名称
        String name = pokerCard.getSuit().name().toLowerCase(Locale.ROOT) + pokerCard.getNumber();
        // 获取对应资源的ID
        int resId = convertView.getResources().getIdentifier(name, "drawable", context.getPackageName());
        // 设置ImageView的图片资源
        viewHolder.imageView.setImageResource(resId);
        // 返回填充数据后的列表项视图
        return convertView;
    }

    /**
     * 内部类，用于缓存列表项视图中的ImageView组件
     */
    private static class ViewHolder {
        public ImageView imageView;
    }
}
