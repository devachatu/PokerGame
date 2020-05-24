package com.example.poker;

/**
 * @author devac
 * @date 19-04-2020
 */
public class Deck {

    private  Card[] cards;
    private  int cardsDrawn;

    public Deck(){
        final int SUITS = 4;
        final int VALUES = 13;
        cards = new Card[52];

        String[] suits =
                {
                        "clubs",
                        "hearts",
                        "spades",
                        "diamonds"
                };

        for(int i=0; i<SUITS; i++)
        {
            for(int j=0; j<VALUES; j++)
            {
                int value;
                if(j>9){
                    value = 10;
                }
                else{
                    value=j+1;
                }
                Card c = new Card(value,suits[i]);
                cards[i*13+j]=c;
            }
        }
    }
    // assign the value to the cards

    public Card draw(){
        cardsDrawn++;
        return cards[cardsDrawn-1];
    }

    public  void shuffle(){
        for(int i=0; i < 100; i++)
        {
            int m = (int) (Math.random() * 50);
            int n = (int) (Math.random() * 50);

            Card temp = new Card(cards[m].getValue(),cards[m].getSuit());
            cards[m].setValue(cards[n].getValue());
            cards[m].setSuit(cards[n].getSuit());
            cards[n].setValue(temp.getValue());
            cards[n].setSuit(temp.getSuit());
        }
    }

    public  int getCardsDrawn(){
        return cardsDrawn;
    }

    public String toString(){
        String s = "";
        for(int i=0; i < 52; i++)
            s = s + cards[i].toString() + "\n";
        return s;
    }

}
