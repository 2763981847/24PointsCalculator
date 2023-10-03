package com.example.calculate24points.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 视图高度设置工具类
 */
public class ViewHeightUtils {

    /**
     * 设置ListView的高度，基于其子项的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 累加子项的高度
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // 设置ListView的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 结果要加上子项的分割线的高度
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 设置GridView的高度，基于其子项的高度
     *
     * @param gridView
     */
    public static void setGirdViewHeightBasedOnChildren(GridView gridView) {
        gridView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int numColumns = gridView.getNumColumns();
                        // 适配器项数据未填充，直接返回
                        if (numColumns == 0) return;
                        ListAdapter listAdapter = gridView.getAdapter();
                        if (listAdapter == null) {
                            return;
                        }
                        int totalHeight = 0;
                        int count = listAdapter.getCount();
                        // 计算GridView行数
                        int numRows = (int) Math.ceil((double) count / numColumns);
                        // 累加每行高度
                        for (int i = 0; i < numRows; i++) {
                            int rowHeight = 0;
                            for (int j = 0; j < numColumns; j++) {
                                int index = i * numColumns + j;
                                if (index >= count) break;
                                View listItem = listAdapter.getView(index, null, gridView);
                                listItem.measure(0, 0);
                                rowHeight = Math.max(rowHeight, listItem.getMeasuredHeight());
                            }
                            totalHeight += rowHeight;
                        }
                        // 设置GridView的高度
                        ViewGroup.LayoutParams params = gridView.getLayoutParams();
                        // 结果需要加上GridView的垂直间距
                        params.height = totalHeight + (gridView.getVerticalSpacing() * (numRows - 1));
                        gridView.setLayoutParams(params);
                        // 设置完成，移除全局监听
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }
}
