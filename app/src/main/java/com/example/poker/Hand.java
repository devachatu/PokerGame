package com.example.poker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * @author devac
 * @date 22-04-2020
 */
public class Hand implements Comparable<Hand> {
    public Card[] cards;
    private int[] value;


    public Hand(Card[] cards) {
        value = new int[6];
        this.cards = cards;

        int[] ranks = new int[14];
        int[] orderedRanks = new int[5];
        boolean flush=true, straight=false;
        int sameCards=1,sameCards2=1;
        int largeGroupRank=0,smallGroupRank=0;
        int index=0;
        int topStraightValue=0;

        for (int x=0; x<=13; x++) {
            ranks[x]=0;
        }
        for (int x=0; x<=4; x++) {
            ranks[ cards[x].getValue() ]++;
        }
        for (int x=0; x<4; x++) {
            if (!cards[x].getSuit().equals(cards[x + 1].getSuit())) {
                flush = false;
                break;
            }
        }

        for (int x=13; x>=1; x--) {
            if (ranks[x] > sameCards) {
                if (sameCards == 1) {
                    largeGroupRank = x;
                } else {
                    sameCards2 = sameCards;
                    smallGroupRank = x;
                }
                sameCards = ranks[x];
            } else if (ranks[x] > sameCards2) {
                sameCards2 = ranks[x];
                smallGroupRank = x;
            }
        }

        if (ranks[1]==1) {
            orderedRanks[index]=14;
            index++;
        }

        for (int x=13; x>=2; x--) {
            if (ranks[x]==1) {
                orderedRanks[index]=x;
                index++;
            }
        }

        for (int x=1; x<=9; x++) {
            if (ranks[x]==1 && ranks[x+1]==1 && ranks[x+2]==1 && ranks[x+3]==1 && ranks[x+4]==1) {
                straight=true;
                topStraightValue=x+4;
                break;
            }
        }

        if (ranks[10]==1 && ranks[11]==1 && ranks[12]==1 && ranks[13]==1 && ranks[1]==1) {
            straight=true;
            topStraightValue=14;
        }

        for (int x=0; x<=5; x++) {
            value[x]=0;
        }


        if (sameCards == 1) {
            value[0]=1;
            value[1]=orderedRanks[0];
            value[2]=orderedRanks[1];
            value[3]=orderedRanks[2];
            value[4]=orderedRanks[3];
            value[5]=orderedRanks[4];
        }

        if (sameCards==2 && sameCards2==1)
        {
            value[0]=2;
            value[1]=largeGroupRank;
            value[2]=orderedRanks[0];
            value[3]=orderedRanks[1];
            value[4]=orderedRanks[2];
        }

        if (sameCards==2 && sameCards2==2)
        {
            value[0]=3;
            value[1]= Math.max(largeGroupRank, smallGroupRank);
            value[2]= Math.min(largeGroupRank, smallGroupRank);
            value[3]=orderedRanks[0];
        }

        if (sameCards==3 && sameCards2!=2)
        {
            value[0]=4;
            value[1]= largeGroupRank;
            value[2]=orderedRanks[0];
            value[3]=orderedRanks[1];
        }

        if (straight && !flush)
        {
            value[0]=5;
            value[1]=topStraightValue;
        }

        if (flush && !straight)
        {
            value[0]=6;
            value[1]=orderedRanks[0];
            value[2]=orderedRanks[1];
            value[3]=orderedRanks[2];
            value[4]=orderedRanks[3];
            value[5]=orderedRanks[4];
        }

        if (sameCards==3 && sameCards2==2)
        {
            value[0]=7;
            value[1]=largeGroupRank;
            value[2]=smallGroupRank;
        }

        if (sameCards==4)
        {
            value[0]=8;
            value[1]=largeGroupRank;
            value[2]=orderedRanks[0];
        }

        if (straight && flush)
        {
            value[0]=9;
            value[1]=topStraightValue;
        }


    }


    String display()
    {
        String s;
        switch( value[0] )
        {

            case 1:
                s="high card";
                break;
            case 2:
                s="pair ";
                break;
            case 3:
                s="two pair ";
                break;
            case 4:
                s="three of a kind ";
                break;
            case 5:
                s=" high straight";
                break;
            case 6:
                s="flush";
                break;
            case 7:
                s="full house ";
                break;
            case 8:
                s="four of a kind ";
                break;
            case 9:
                s="straight flush ";
                break;
            default:
                s="error in Hand.display: value[0] contains invalid value";
        }
        s = " " + s;
        System.out.println(s);
        return s;
    }

    void displayAll()
    {
        for (int x=0; x<5; x++)
            System.out.println(cards[x]);
    }

    public int compareTo(Hand that)
    {
        for (int x=0; x<6; x++)
        {
            if (this.value[x]>that.value[x])
                return 1;
            else if (this.value[x]<that.value[x])
                return -1;
        }
        return 0;
    }

    public static Hand makeBestHand(ArrayList<Card> list, Collection<Card> holeCards) {
        Set base=new HashSet(list);
        Vector<Card> pool = new Vector<Card>();
        pool.addAll(base);
        pool.addAll(holeCards);

        Set<Hand> hands = makeHands(pool);

        Iterator<Hand> it = hands.iterator();
        Hand bestHand = it.next();

        while (it.hasNext()) {
            Hand next = it.next();
            if (bestHand.compareTo(next) < 0)
                bestHand = next;
        }

        return bestHand;
    }

    private static Set<Hand> makeHands(Vector<Card> pool) {
        Set<Card[]> s = new HashSet<Card[]>();
        s.add(pool.toArray(new Card[pool.size()]));
        return makeHands(s, pool.size());
    }

    private static Set<Hand> makeHands(Set<Card[]> hands, int size) {
        if (size == 5) {
            Set<Hand> ret = new HashSet<Hand>();
            for (Card[] cards : hands)
                ret.add(new Hand(cards));
            return ret;
        } else {
            Set<Card[]> next = new HashSet<Card[]>();
            for (Card[] cards : hands) {
                Vector<Card> baseCards = new Vector<Card>();
                for (Card c : cards)
                    baseCards.add(c);

                for (int skip = 0; skip < size; skip++) {
                    @SuppressWarnings("unchecked")
                    Vector<Card> newCards = (Vector<Card>) baseCards.clone();
                    newCards.remove(skip);
                    next.add(newCards.toArray(new Card[size - 1]));
                }
            }
            return makeHands(next, size - 1);
        }
    }

    public int getValue() {
        return value[0];
    }
}