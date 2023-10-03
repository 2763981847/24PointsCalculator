package com.example.calculate24points.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排列工具类
 */
public class PermutationsUtils {

    /**
     * 生成给定数据的全排列(带去重)
     *
     * @param data
     * @return
     */
    public static List<String[]> permuteUnique(String[] data) {
        return permuteUnique(data, data.length);
    }

    /**
     * 生成给定数据的排列（带去重）
     *
     * @param data
     * @param n
     * @return
     */
    public static List<String[]> permuteUnique(String[] data, int n) {
        List<List<String>> result = new ArrayList<>();
        Arrays.sort(data); // 对数据进行排序，以便后续去重
        boolean[] used = new boolean[data.length]; // 用于标记数据是否已使用
        List<String> current = new ArrayList<>();
        backtrack(data, n, used, current, result); // 调用回溯算法生成排列
        return result.stream().map(list -> list.toArray(new String[0])).collect(Collectors.toList());
    }

    /**
     * 回溯算法，生成排列
     *
     * @param data
     * @param n
     * @param used
     * @param current
     * @param result
     */
    private static void backtrack(String[] data, int n, boolean[] used, List<String> current, List<List<String>> result) {
        if (current.size() == n) {
            result.add(new ArrayList<>(current)); // 当生成了完整排列时，将其加入结果列表中
            return;
        }

        for (int i = 0; i < data.length; i++) {
            if (used[i] || (i > 0 && data[i].equals(data[i - 1]) && !used[i - 1])) {
                continue; // 跳过重复元素或者已经使用过的元素
            }
            current.add(data[i]); // 将当前元素加入当前排列
            used[i] = true; // 标记当前元素已使用
            backtrack(data, n, used, current, result); // 递归生成下一个元素的排列
            current.remove(current.size() - 1); // 回溯，移除最后一个元素，尝试其他元素的排列
            used[i] = false; // 标记当前元素未使用，以便尝试其他排列
        }
    }
}
