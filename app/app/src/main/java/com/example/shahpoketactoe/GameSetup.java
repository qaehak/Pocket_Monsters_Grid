package com.example.shahpoketactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSetup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
        addItemsOnSpinner();
    }
    //Holds random background integer
    int num = 0;
    //Holds spinner options and name input
    String option1, option2, name1, name2;
    //Spinner code: Lists options
    public void addItemsOnSpinner() {
        Spinner theSpinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner theSpinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Pick a Basic Pokemon:");
        list.add("Bulbasaur");
        list.add("Charmander");
        list.add("Oshawatt");
        list.add("Pichu");
        list.add("Piplup");
        list.add("Snivy");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theSpinner1.setAdapter(dataAdapter);
        theSpinner1.setOnItemSelectedListener(this);
        theSpinner2.setAdapter(dataAdapter);
        theSpinner2.setOnItemSelectedListener(this);
    }
    @Override
    //Code to handle the spinner options
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        option1 = spinner1.getSelectedItem().toString();
        option2 = spinner2.getSelectedItem().toString();
        //Error message when the pokemon option is not empty and matches Player 2
        if (option1.equals (option2) && !option1.equals ("Pick a Basic Pokemon:"))
            error("Same Pick");
        //Code to store spinner options
        try {
            FileOutputStream out = openFileOutput("options.txt", Activity.MODE_PRIVATE);
            out.write(option1.length());
            for (int m = 0; m < option1.length(); m++) {
                out.write((int) (option1.charAt(m)));
            }
            out.write(option2.length());
            for (int n = 0; n < option2.length(); n++) {
                out.write((int) (option2.charAt(n)));
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //If an option is not picked
    public void onNothingSelected(AdapterView<?> arg0) {
        if (option1.equals ("Pick a Basic Pokemon:"))
            error (name1);
        else if (option2.equals ("Pick a Basic Pokemon:"))
            error (name2);
    }
    //Generates random number to determine arena background
    public void arena (View view){
        num = (int) (Math.random()*5);
        ImageView empty = (ImageView) findViewById(R.id.empty);
        //Image that displays arena type
        if (num == 1)
            empty.setImageResource(R.drawable.mountain);
        else if (num == 2)
            empty.setImageResource(R.drawable.jungle);
        else if (num == 3)
            empty.setImageResource(R.drawable.forest);
        else
            empty.setImageResource(R.drawable.tundra);
    }
    //Button to game screen
    public void playGameScreen (View view){
        try{
            FileOutputStream out = openFileOutput("background.txt", Activity.MODE_PRIVATE);
            out.write(num);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        names();
        //Makes sure all information is entered
        if (num == 0 || name1.equals("") || name2.equals("") || option1.equals("Pick a Basic Pokemon:") || option2.equals("Pick a Basic Pokemon:"))
            error("Error");
        else {
            Intent i = new Intent(this, GameScreen.class);
            startActivity(i);
        }
    }
    //Method to get names from EditTexts
    public void names (){
        //Convert EditText to string
        EditText player1 = (EditText) findViewById(R.id.player1);
        EditText player2 = (EditText) findViewById(R.id.player2);
        name1 = player1.getText().toString();
        name2 = player2.getText().toString();
        //Code to store names from EditText
        try {
            FileOutputStream out = openFileOutput("names.txt", Activity.MODE_PRIVATE);
            out.write(name1.length());
            for (int i = 0; i < name1.length(); i++) {
                out.write((int) (name1.charAt(i)));
            }
            out.write(name2.length());
            for (int i = 0; i < name2.length(); i++) {
                out.write((int) (name2.charAt(i)));
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    //Spinner error message (if Pokemon has already been selected or if values are missing)
    public void error (String name){
        ImageView i = new ImageView(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", null);
        if (name.equals ("Same Pick")) {
            builder.setMessage("This Pokemon has already been selected. Please pick a different option.");
            builder.setTitle("Oh no!");
            i.setImageResource(R.drawable.ohho);
        }
        else if (option1.equals("Pick a Basic Pokemon:") || option2.equals("Pick a Basic Pokemon:")){
            builder.setMessage("Please pick a basic Pokemon");
            builder.setTitle("Empty Pokemon Selection");
            i.setImageResource(R.drawable.dialga);
        }
        else if (name.equals("Error")) {
            builder.setMessage("Please enter the required information");
            builder.setTitle("Missing Information");
            i.setImageResource(R.drawable.mewtwo);
        }
        builder.setView(i);
        builder.show();
    }
}