package com.example.poker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button newgame;
    Button rules;
    static int players;
    ElegantNumberButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newgame=findViewById(R.id.button);
        rules=findViewById(R.id.rules);
        btn=findViewById(R.id.players);

        btn.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                players=Integer.parseInt(btn.getNumber());
                Log.e("Num:",btn.getNumber());
            }
        });

        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Game.class);
                startActivity(i);
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Rules.class);
                startActivity(i);
            }
        });
    }
}

//    void checkRoyalFlush(){
//        System.out.println(player.getHand());
//        System.out.println(dealer.getHand());
//        List<List<Card>> combinations = Generator.combination(dealer.getHand())
//                .simple(3)
//                .stream()
//                .collect(Collectors.<List<Card>>toList());
//        for(List list : combinations){
//            hand.addAll(player.getHand());
//            hand.addAll(list);
//            System.out.println(hand);
//            if(hand.get(0).getSuit().equals(hand.get(1).getSuit()))
//
//                hand.clear();
//        }

