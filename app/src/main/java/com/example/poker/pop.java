package com.example.poker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class pop extends AppCompatActivity {
    TextView wintext;
    Button button;
    ConstraintLayout lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);
        wintext=findViewById(R.id.playtext);
        button =findViewById(R.id.back);
        lLayout = (ConstraintLayout) findViewById(R.id.popLayout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.6),(int)(height*0.6));
        if(Game.iswin==1){
            wintext.setText("Congratulations You have Won");
            lLayout.setBackgroundColor(Color.GREEN);
        }
        else if(Game.iswin==2){
            wintext.setText("BLACKJACK!!");
            lLayout.setBackgroundColor(Color.GREEN);}
        else if(Game.iswin==0){
            wintext.setText("Oh oh, you lost.");
            lLayout.setBackgroundColor(Color.RED);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Game.class);
                startActivity(i);
            }
        });
    }
}
