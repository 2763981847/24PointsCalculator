package com.example.calculate24points.core.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class RPNUtils {
    public static String[] generateDefaultRPN(String[] numbers, String[] operators) {
        return new String[]{
                numbers[0], numbers[1], operators[0],
                numbers[2], operators[1],
                numbers[3], operators[2]
        };
    }

    public static String[][] generateAllRPN(String[] numbers, String[] operators) {
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


    public static boolean checkRPN(String[] rpn) {
        Deque<Integer> stack = new LinkedList<>();
        for (String s : rpn) {
            if (Character.isDigit(s.charAt(0))) {
                stack.push(Integer.valueOf(s));
                continue;
            }
            int num2 = stack.pop(), num1 = stack.pop();
            if (s.equals("+")) {
                stack.push(num1 + num2);
            } else if (s.equals("-")) {
                stack.push(num1 - num2);
            } else if (s.equals("*")) {
                stack.push(num1 * num2);
            } else {
                if (num2 == 0 || num1 % num2 != 0) return false;
                stack.push(num1 / num2);
            }
        }
        return stack.pop() == 24;
    }

    public static String rpnToInfix(String[] rpn) {
        Deque<String> stack = new ArrayDeque<>();
        for (String s : rpn) {
            if (Character.isDigit(s.charAt(0))) {
                stack.push(s);
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String result = "(" + operand1 + s + operand2 + ")";
                stack.push(result);
            }
        }

        return stack.pop();
    }
}
