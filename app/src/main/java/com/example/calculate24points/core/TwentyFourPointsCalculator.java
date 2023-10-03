package com.example.calculate24points.core;

import com.example.calculate24points.core.util.PermutationsUtils;
import com.example.calculate24points.core.util.RPNUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 24点游戏计算器
 */
public class TwentyFourPointsCalculator {

    // 可用的运算符数组
    private static final String[] OPERATORS = new String[]{"+", "-", "*", "/"};
    // 运算符所有排列可能
    private static final List<String[]> OPERATOR_PERMUTATIONS;

    // 初始化运算符的所有排列
    static {
        // 因为单个运算符可重复使用且最多使用三次，所以将运算符数组复制三份
        String[] temp = new String[OPERATORS.length * 3];
        for (int i = 0; i < OPERATORS.length; i++) {
            temp[i] = temp[i + OPERATORS.length] = temp[i + 2 * OPERATORS.length] = OPERATORS[i];
        }
        // 生成运算符的所有排列
        OPERATOR_PERMUTATIONS = PermutationsUtils.permuteUnique(temp, 3);
    }

    /**
     * 计算24点游戏的结果
     *
     * @param numbers
     * @return
     */
    public static List<String> calculate(String[] numbers) {
        // 生成数字的所有全排列
        List<String[]> numberPermutations = PermutationsUtils.permuteUnique(numbers);
        // 使用并行流处理，加快运算速度
        return numberPermutations.parallelStream()
                // 生成所有可能的逆波兰表达式并合并为一个流
                .flatMap(numberPermutation ->
                        OPERATOR_PERMUTATIONS
                                .stream()
                                // 组合数字的某种排列和操作符的某种排列，生成逆波兰表达式
                                .flatMap(operatorPermutation -> Arrays.stream(RPNUtils.generateRPNs(numberPermutation, operatorPermutation)))
                )
                // 过滤出等于24的逆波兰表达式
                .filter(RPNUtils::checkRPN)
                // 将逆波兰表达式转换为中缀表达式，便于展示结果
                .map(RPNUtils::rpnToInfix)
                .collect(Collectors.toList());
    }
}
