package com.example.poker;

/**
 * @author devac
 * @date 19-04-2020
 */
public class Card {
    private String suit;
    private int value;
    public boolean active=false;
    private static String[] ranks  = { "a", "2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k" };
    static String rankAsString(int rank) {
        return ranks[rank];
    }

    public Card(int aValue, String aSuit){
        value=aValue;
        suit=aSuit;

    }

    public String toString(){
        String myCard;

        if(value==1)
            myCard="Ace of ";
        else if(value==11)//Jack
            myCard="Jack of ";
        else if(value==12)//Queen
            myCard="Queen of ";
        else if(value==13)//King
            myCard="King of ";
        else //any number/suit between 2-10
            myCard=value +" of ";

        //Adds the suit type to the card value
        myCard+=suit;

        return myCard;
    }

    public int getValue(){

        return value;
    }

    public String getSuit(){
        return suit;
    }

    public void setValue(int v){
        value = v;
    }

    public void setSuit(String s){
        suit = s;
    }


}
