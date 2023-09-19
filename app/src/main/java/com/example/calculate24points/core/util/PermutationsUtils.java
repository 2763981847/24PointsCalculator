package com.example.calculate24points.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PermutationsUtils {
    public static List<String[]> permuteUnique(String[] data) {
        return permuteUnique(data, data.length);
    }

    public static List<String[]> permuteUnique(String[] data, int n) {
        List<List<String>> result = new ArrayList<>();
        Arrays.sort(data); // 排序，以便去重
        boolean[] used = new boolean[data.length];
        List<String> current = new ArrayList<>();
        backtrack(data, n, used, current, result);
        return result.stream().map(list -> list.toArray(new String[0])).collect(Collectors.toList());
    }

    private static void backtrack(String[] data, int n, boolean[] used, List<String> current, List<List<String>> result) {
        if (current.size() == n) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < data.length; i++) {
            if (used[i] || (i > 0 && data[i].equals(data[i - 1]) && !used[i - 1])) {
                continue; // 跳过重复元素或者已经使用过的元素
            }
            current.add(data[i]);
            used[i] = true;
            backtrack(data, n, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

}
