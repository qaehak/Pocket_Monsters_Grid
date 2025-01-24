package com.example.shahpoketactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }
    //Button to Game Setup Screen
    public void gameSetupScreen (View view){
        Intent i = new Intent (this, GameSetup.class);
        startActivity(i);
    }
}