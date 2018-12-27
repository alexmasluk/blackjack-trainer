package com.example.alex.testproj;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private String suit; //H,D,S,C
    private String value; //A,1,2,...,Q,K

    Card(String s, String v)
    {
        suit = s; value = v;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        String s = value + suit;
        return s;
    }
    public static String getStringRep(List<Card> hand, boolean dealer)
    {
        String s = "";
        for (Card c : hand)
        {
            s += c.getValue();
            s += c.getSuit();
            s += " ";
        }
        if (dealer)
            s = "[]" + s.substring(hand.get(0).getValue().length() + hand.get(0).getSuit().length() , s.length());

        return s.substring(0, s.length() - 1);
    }

    public static Boolean isHandValueSoft(List<Card> h)
    {
        Boolean soft = false;
         int value = 0;
        int numAces = 0;
        List<Card> hand = new ArrayList<>(h);

        //don't include the dealer's first hand in the display



        for (int i = 0; i < hand.size(); i++)
        {
            String val = hand.get(i).getValue();
            if (val == "A")
                numAces += 1;
            else if (val == "J" || val == "Q" || val == "K")
                value += 10;
            else
                value += Integer.parseInt(val);

        }

        for (int i=0; i<numAces; i++)
            value += 1;

        for (int i=0; i<numAces; i++)
            if (value + 10 <= 21) {
                value += 10;
                soft = true;
            }

        Log.i("isHandValueSoft", "Calculated hand as " + soft);

        return soft;
    }

    public static int getHandValue(List<Card> h, boolean dealer)
    {
        int value = 0;
        int numAces = 0;
        List<Card> hand = new ArrayList<>(h);

        //don't include the dealer's first hand in the display



        for (int i = 0; i < hand.size(); i++)
        {
            if (dealer)
            {
                if (i != 1)
                {
                    String val = hand.get(i).getValue();
                    if (val == "A")
                        numAces += 1;
                    else if (val == "J" || val == "Q" || val == "K")
                        value += 10;
                    else
                        value += Integer.parseInt(val);
                }
            }
            else
            {
                String val = hand.get(i).getValue();
                if (val == "A")
                    numAces += 1;
                else if (val == "J" || val == "Q" || val == "K")
                    value += 10;
                else
                    value += Integer.parseInt(val);
            }

        }

        for (int i=0; i<numAces; i++)
            value += 1;

        for (int i=0; i<numAces; i++)
            if (value + 10 <= 21)
                value += 10;

        if (dealer)
            Log.i("getHandValue", "Calculated value as " + value);
        return value;
    }
}
