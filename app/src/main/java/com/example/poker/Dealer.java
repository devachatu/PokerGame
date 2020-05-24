package com.example.poker;

/**
 * @author devac
 * @date 19-04-2020
 */
import java.util.ArrayList;

public class Dealer {

    private ArrayList<Card> hand; // the dealer's cards
    private int handTotal; // The total value of the hand
    public Dealer(){
        handTotal=0;
        hand = new ArrayList<Card>(52);
    }

    public Card deal(){
        return deal();
    }

    public void addCard(Card c){
        hand.add(c);
        handTotal += c.getValue();
        // add cards to the dealer
        // add the value of the cards to the handTotal
        int numberOfAce=0;
        if (c.getValue()==1){
            numberOfAce++;
        }
        if (handTotal<=11 && numberOfAce>0){
            handTotal=handTotal+10;
        }

    }

    public boolean play(){
        return (handTotal <= 16);
    }

    public int getTotal(){
        return handTotal;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

}

