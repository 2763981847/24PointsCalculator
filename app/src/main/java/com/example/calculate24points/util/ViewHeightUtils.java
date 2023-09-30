package com.example.calculate24points.util;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ViewHeightUtils {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setGirdViewHeightBasedOnChildren(GridView gridView) {
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int numColumns = gridView.getNumColumns();
                if (numColumns > 0) {
                    //adapter item 数据已填充
                    ListAdapter listAdapter = gridView.getAdapter();
                    if (listAdapter == null) {
                        // pre-condition
                        return;
                    }
                    int totalHeight = 0;
                    int count = listAdapter.getCount();
                    int numRows = (int) Math.ceil((double) count / numColumns);
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

                    ViewGroup.LayoutParams params = gridView.getLayoutParams();
                    int verticalSpacing = gridView.getVerticalSpacing();
                    params.height = totalHeight + (verticalSpacing * (numRows - 1));
                    gridView.setLayoutParams(params);
                    gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}