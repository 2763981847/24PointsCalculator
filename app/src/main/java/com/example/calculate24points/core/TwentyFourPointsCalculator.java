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

    private static final String[] OPERATORS = new String[]{"+", "-", "*", "/"}; // 可用的运算符数组
    private static final List<String[]> OPERATOR_PERMUTATIONS; // 运算符所有排列可能

    // 初始化运算符的所有排列
    static {
        // 因为单个运算符可重复使用且最多使用三次，所以将运算符数组复制三份
        String[] temp = new String[OPERATORS.length * 3];
        for (int i = 0; i < OPERATORS.length; i++) {
            temp[i] = temp[i + OPERATORS.length] = temp[i + 2 * OPERATORS.length] = OPERATORS[i];
        }
        OPERATOR_PERMUTATIONS = PermutationsUtils.permuteUnique(temp, 3); // 生成运算符的所有排列
    }

    /**
     * 计算24点游戏的结果
     *
     * @param numbers
     * @return
     */
    public static List<String> calculate(String[] numbers) {
        List<String[]> numberPermutations = PermutationsUtils.permuteUnique(numbers); // 生成数字的所有全排列
        // 使用并行流处理，加快运算速度
        return numberPermutations.parallelStream()
                .flatMap(numberPermutation ->
                        OPERATOR_PERMUTATIONS
                                .stream()
                                // 组合数字的某种排列和操作符的某种排列，生成逆波兰表达式
                                .flatMap(operatorPermutation -> Arrays.stream(RPNUtils.generateRPNs(numberPermutation, operatorPermutation)))
                ) // 生成所有可能的逆波兰表达式并合并为一个流
                .filter(RPNUtils::checkRPN) // 过滤出等于24的逆波兰表达式
                .map(RPNUtils::rpnToInfix) // 将逆波兰表达式转换为中缀表达式，便于展示结果
                .collect(Collectors.toList());
    }
}
