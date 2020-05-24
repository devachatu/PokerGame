package com.example.poker;

/**
 * @author devac
 * @date 19-04-2020
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    public ArrayList<Card> hand; // the player's cards
    private int handTotal; // The total value of the hand
    private Scanner input;
    public boolean iswin;
    public boolean active=true;
    public int money;
    public Player(){
        handTotal=0;
        money=10000;
        setHand(new ArrayList<Card>(52));
    }

    public void addCard(Card c){
        hand.add(c);
        handTotal += c.getValue();

        int numberOfAce=0;
        if (c.getValue()==1){
            numberOfAce++;
        }

        if (handTotal<=11 && numberOfAce>0){
            handTotal=handTotal+10;
        }
        // add cards to player
        // add value to hand total
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

}


