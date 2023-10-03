package com.example.calculate24points.core.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 逆波兰（后缀）表达式工具类
 */
public class RPNUtils {

    /**
     * 生成 'a b op1 c op2 d op3' 和 'a b op1 c d op2 op3' 两种逆波兰表达式
     *
     * @param numbers   四个操作数的某种排列
     * @param operators 三个操作符的某种排列
     * @return 'a b op1 c op2 d op3' 和 'a b op1 c d op2 op3' 两种逆波兰表达式
     */
    public static String[][] generateRPNs(String[] numbers, String[] operators) {
        return new String[][]{
                new String[]{
                        numbers[0], numbers[1], operators[0],
                        numbers[2], operators[1],
                        numbers[3], operators[2]
                },
                new String[]{
                        numbers[0], numbers[1], operators[0],
                        numbers[2], numbers[3],
                        operators[1], operators[2]
                }
        };
    }

    /**
     * 检查逆波兰表达式结果是否等于24
     *
     * @param rpn 逆波兰表达式
     * @return
     */
    public static boolean checkRPN(String[] rpn) {
        Deque<Integer> stack = new LinkedList<>();
        for (String s : rpn) {
            if (Character.isDigit(s.charAt(0))) {
                // 如果是数字，将其压入栈
                stack.push(Integer.valueOf(s));
                continue;
            }
            // 如果是操作符，进行一次计算
            // 弹出栈顶元素作为第二个操作数
            int num2 = stack.pop();
            // 弹出栈顶元素作为第一个操作数
            int num1 = stack.pop();
            if (s.equals("+")) {
                // 执行加法并将结果压入栈
                stack.push(num1 + num2);
            } else if (s.equals("-")) {
                // 执行减法并将结果压入栈
                stack.push(num1 - num2);
            } else if (s.equals("*")) {
                // 执行乘法并将结果压入栈
                stack.push(num1 * num2);
            } else {
                // 避免除法中的除零错误
                if (num2 == 0 || num1 % num2 != 0) return false;
                // 执行除法并将结果压入栈
                stack.push(num1 / num2);
            }
        }
        // 判断结果是否为24
        return stack.pop() == 24;
    }

    /**
     * 将逆波兰表达式转换为中缀表达式
     *
     * @param rpn 逆波兰表达式
     * @return 中缀表达式
     */
    public static String rpnToInfix(String[] rpn) {
        Deque<String> stack = new ArrayDeque<>();
        for (String s : rpn) {
            if (Character.isDigit(s.charAt(0))) {
                // 如果是数字，将其压入栈
                stack.push(s);
            } else {
                // 弹出栈顶元素作为第二个操作数
                String operand2 = stack.pop();
                // 弹出栈顶元素作为第一个操作数
                String operand1 = stack.pop();
                // 构建中缀表达式
                String result = "(" + operand1 + s + operand2 + ")";
                // 将中缀表达式压入栈
                stack.push(result);
            }
        }
        // 最后的栈顶元素即为中缀表达式
        String infixExp = stack.pop();
        // 去除多余的括号并返回
        return infixExp.substring(1, infixExp.length() - 1);
    }
}
