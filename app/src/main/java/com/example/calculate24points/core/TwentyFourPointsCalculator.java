package com.example.calculate24points.core;

import com.example.calculate24points.core.util.PermutationsUtils;
import com.example.calculate24points.core.util.RPNUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwentyFourPointsCalculator {
    private static final String[] OPERATORS = new String[]{"+", "-", "*", "/"};
    private static final List<String[]> OPERATOR_PERMUTATIONS;

    static {
        String[] temp = new String[OPERATORS.length * 3];
        for (int i = 0; i < OPERATORS.length; i++) {
            temp[i] = temp[i + OPERATORS.length] = temp[i + 2 * OPERATORS.length] = OPERATORS[i];
        }
        OPERATOR_PERMUTATIONS = PermutationsUtils.permuteUnique(temp, 3);
    }

    public static List<String> calculate(String[] numbers) {
        List<String[]> numberPermutations = PermutationsUtils.permuteUnique(numbers);
        List<String> results = numberPermutations.stream()
                .flatMap(numberPermutation ->
                        OPERATOR_PERMUTATIONS
                                .stream()
                                .flatMap(operatorPermutation -> Arrays.stream(RPNUtils.generateAllRPN(numberPermutation, operatorPermutation)))
                )
                .filter(RPNUtils::checkRPN)
                .map(RPNUtils::rpnToInfix)
                .collect(Collectors.toList());
        return results;
    }

}
