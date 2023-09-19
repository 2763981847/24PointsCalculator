package com.example.calculate24points.bean;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokerCard {
    private Suit suit;
    private int number;

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PokerCard(Suit suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    public static final List<PokerCard> ALL_POKER_CARDS = new ArrayList<>();

    static {
        for (int i = 1; i <= 13; i++) {
            for (Suit suit : Suit.values()) {
                ALL_POKER_CARDS.add(new PokerCard(suit, i));
            }
        }
    }

    public enum Suit {
        // 梅花，方块，红桃，黑桃
        CLUB, DIAMOND, HEART, SPADE;
    }


}
