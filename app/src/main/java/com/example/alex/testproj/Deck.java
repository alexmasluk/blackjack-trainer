package com.example.alex.testproj;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Deck {
    private Stack<Card> cards;
    public Deck(int numDecks)
    {

        cards = new Stack<Card>();
        for (int i=0; i< numDecks; i++)
        {
            Stack<Card> shuf = shuffleDeck();
            while (!shuf.empty())
                cards.push(shuf.pop());
        }
    }

    public Stack<Card> shuffleDeck()
    {
        Random rand = new Random();
        List<Card> cardList = new ArrayList<Card>();
        String[] suites = {"H", "C", "S", "D"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String s : suites)
            for (String v : values)
                cardList.add(new Card(s, v));

        Stack<Card>  shuffledDeck = new Stack<Card>();


        while (!cardList.isEmpty())
        {
            Card c = cardList.remove(rand.nextInt(cardList.size()));
            shuffledDeck.push(c);
        }
        return shuffledDeck;
    }

    public Card pop()
    {
        Card card = null;
        if (!cards.empty())
            card = cards.pop();

        return card;
    }
}
