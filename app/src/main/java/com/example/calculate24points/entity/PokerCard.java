package com.example.calculate24points.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 扑克牌实体类
 */
public class PokerCard {

    /**
     * 花色（枚举值）
     */
    private Suit suit;

    /**
     * 数字
     */
    private int number;

    public Suit getSuit() {
        return suit;
    }


    public int getNumber() {
        return number;
    }


    public PokerCard(Suit suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    /**
     * 所有52张扑克牌
     */
    public static final List<PokerCard> ALL_POKER_CARDS = new ArrayList<>();

    // 初始化所有52张扑克牌
    static {
        for (int i = 1; i <= 13; i++) {
            for (Suit suit : Suit.values()) {
                ALL_POKER_CARDS.add(new PokerCard(suit, i));
            }
        }
    }

    /**
     * 花色枚举类
     */
    public enum Suit {
        // 梅花，方块，红桃，黑桃
        CLUB, DIAMOND, HEART, SPADE;
    }


}
