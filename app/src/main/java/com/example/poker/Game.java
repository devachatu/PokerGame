package com.example.poker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {
    private Deck deck;
    private static Player player[]=new Player[MainActivity.players];
    private Hand hand[]=new Hand[MainActivity.players],besthand;
    private static Dealer dealer;
    ImageView pcard1,pcard2;
    ImageView[] tcard=new ImageView[5];
    Button foldbtn,raisebtn,callbtn,donebtn;
    TextView money,pot;
    static int winp,iswin;
    ArrayList<Card> cardpool=new ArrayList<Card>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        for(int i=0;i<MainActivity.players;i++)player[i]=new Player();
        start();
        }

        void gameover(int potamt){
                for(int i=0;i<MainActivity.players;i++){
                    if(player[i].active){
                        hand[i]=Hand.makeBestHand(cardpool,player[i].getHand());
                    }

                }
                besthand=hand[0];
                for(int i=0;i<MainActivity.players;i++){
                    if (player[i].active){
                        if(besthand.compareTo(hand[i])<0){
                            besthand=hand[i];
                            winp=i;
                        }
                    }

                }
                hand[winp].displayAll();
                hand[winp].display();
                player[winp].money=player[winp].money+potamt;



            System.out.println("PLayer: "+(winp)+" is the winner!!");

            new AlertDialog.Builder(this)
                    .setTitle("Congratulations")
                    .setMessage("Player: "+(winp+1)+" is the winner with "+hand[winp].display()+ "!!\n Do you want to play again")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            start();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .setIcon(R.drawable.winner)
                    .show();
        }

        void start(){
            setContentView(R.layout.activity_game);
            pcard1=findViewById(R.id.pcard1);
            pcard2=findViewById(R.id.pcard2);
            tcard[0]=findViewById(R.id.tcard1);
            tcard[1]=findViewById(R.id.tcard2);
            tcard[2]=findViewById(R.id.tcard3);
            tcard[3]=findViewById(R.id.tcard4);
            tcard[4]=findViewById(R.id.tcard5);
            callbtn=findViewById(R.id.callbtn);
            raisebtn=findViewById(R.id.raisebtn);
            foldbtn=findViewById(R.id.foldbtn);
            donebtn=findViewById(R.id.donebtn);
            pot=findViewById(R.id.pot);
            money=findViewById(R.id.money);
            deck= new Deck();
            final int[] potamt = {0};
            pot.setText(String.valueOf(potamt[0]));


            deck.shuffle();
            Card c = deck.draw();
            cardpool.add(c);
            c = deck.draw();
            cardpool.add(c);
            c = deck.draw();
            cardpool.add(c);
            c = deck.draw();
            cardpool.add(c);
            c = deck.draw();
            cardpool.add(c);
            final int[] roundamt = {0};
            final int[] turn = {0};
            final int[] T = {0};
            money.setText(String.valueOf(player[turn[0]].money));
            System.out.println(MainActivity.players);

            for(int i=0;i<MainActivity.players;i++){
                player[i].hand.clear();
                hand[i]=new Hand();
                c = deck.draw();
                player[i].addCard(c);
                c = deck.draw();
                player[i].addCard(c);
            }

            Toast toast = Toast.makeText(getApplicationContext(), "Player: " + (turn[0] + 1) + " turn.",     Toast.LENGTH_SHORT);
            toast.setMargin(50, 50);
            toast.show();

            pcard1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = player[turn[0]].getHand().get(0).getSuit() + "_" + player[turn[0]].getHand().get(0).getValue();
                    System.out.println(str);
                    Context context = pcard1.getContext();
                    int id = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
                    if (!player[turn[0]].getHand().get(0).active) {
                        pcard1.setImageResource(id);
                        player[turn[0]].getHand().get(0).active = true;
                    } else {
                        pcard1.setImageResource(R.drawable.backside);
                        player[turn[0]].getHand().get(0).active = false;
                    }
                }
            });

            pcard2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = player[turn[0]].getHand().get(1).getSuit() + "_" + player[turn[0]].getHand().get(1).getValue();
                    System.out.println(str);
                    Context context = pcard2.getContext();
                    int id = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
                    if (!player[turn[0]].getHand().get(1).active) {
                        pcard2.setImageResource(id);
                        player[turn[0]].getHand().get(1).active = true;
                    } else {
                        pcard2.setImageResource(R.drawable.backside);
                        player[turn[0]].getHand().get(1).active = false;
                    }
                }
            });



            raisebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                    builder.setTitle("Enter amount to raise (greater than: "+roundamt[0]+")");
                    final EditText input = new EditText(Game.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String amt = input.getText().toString();
                            int raise = Integer.parseInt(amt);
                            if(raise>player[turn[0]].money){
                                Toast toast = Toast.makeText(getApplicationContext(), "Insufficient funds" ,     Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                            }

                            else if(roundamt[0] <=raise) {
                                roundamt[0] =raise;
                                player[turn[0]].money=player[turn[0]].money-raise;
                                potamt[0] = potamt[0] +raise;
                                pot.setText(String.valueOf(potamt[0]));
                                money.setText(String.valueOf(player[turn[0]].money));
                                donebtn.setClickable(true);
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "amount should be equal or greater than: " + roundamt[0],     Toast.LENGTH_SHORT);
                                toast.setMargin(50, 50);
                                toast.show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });
            callbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player[turn[0]].money=player[turn[0]].money-roundamt[0];
                    potamt[0] = potamt[0] +roundamt[0];
                    pot.setText(String.valueOf(potamt[0]));
                    money.setText(String.valueOf(player[turn[0]].money));
                    donebtn.setClickable(true);
                }
            });


            int finalI = turn[0];
            foldbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player[finalI].active = false;
                    donebtn.setClickable(true);
                }
            });


            donebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(turn[0] ==MainActivity.players-1){
                        turn[0] =0;
                        roundamt[0]=0;
                        Context context = tcard[T[0]].getContext();
                        String str = cardpool.get(T[0]).getSuit() + "_" + cardpool.get(T[0]).getValue();
                        int id = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
                        tcard[T[0]].setImageResource(id);
                        T[0]++;
                    }
                    else{
                        turn[0]++;
                    }

                    if(player[turn[0]].money==0){
                        player[turn[0]].active=false;
                    }
                    if(!player[turn[0]].active){
                        foldbtn.setClickable(false);
                        callbtn.setClickable(false);
                        raisebtn.setClickable(false);
                        donebtn.setClickable(true);
                    }
                    else{
                        foldbtn.setClickable(true);
                        callbtn.setClickable(true);
                        raisebtn.setClickable(true);
                        donebtn.setClickable(false);
                    }
                    if(T[0]==5){
                        gameover(potamt[0]);
                    }


                    money.setText(String.valueOf(player[turn[0]].money));
                    Toast toast = Toast.makeText(getApplicationContext(), "Player: " + (turn[0] + 1) + " turn.",     Toast.LENGTH_SHORT);
                    toast.setMargin(50, 50);
                    toast.show();
                }
            });




            System.out.println(cardpool);

        }

        int getActivePlayers(){
            int i;
            for(i=0;i<MainActivity.players;i++){
                if(player[i].active)break;
            }
            return i;
        }
    }



