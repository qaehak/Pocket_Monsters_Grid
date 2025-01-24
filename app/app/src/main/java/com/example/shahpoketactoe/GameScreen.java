package com.example.shahpoketactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        //Code for arena background setup
        try {
            FileInputStream in = openFileInput("background.txt");
            num = in.read();
            in.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        bgSetup (num);
        //Code for Player 1 and 2 names
        try {
            FileInputStream in = openFileInput("names.txt");
            display1 = "";
            int nameLength1 = in.read();
            for (int i = 0; i < nameLength1; i++) {
                int data = in.read();
                char letter = (char) data;
                display1 += letter;
            }
            Toast.makeText(getApplicationContext(), "Player 1: "+display1, Toast.LENGTH_SHORT).show();
            int nameLength2 = in.read();
            display2 = "";
            for (int i = 0; i < nameLength2; i++) {
                int data = in.read();
                char letter = (char) data;
                display2 += letter;
            }
            Toast.makeText(getApplicationContext(), "Player 2: "+display2, Toast.LENGTH_SHORT).show();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Code for Player 1 and 2 spinner options
        try {
            FileInputStream in = openFileInput("options.txt");
            player1 = "";
            int nameLength3 = in.read();
            for (int i = 0; i < nameLength3; i++) {
                int data = in.read();
                char letter = (char) data;
                player1 += letter;
            }
            int nameLength4 = in.read();
            Toast.makeText(getApplicationContext(), display1+"'s Pokemon: "+player1, Toast.LENGTH_SHORT).show();
            player2 = "";
            for (int i = 0; i < nameLength4; i++) {
                int data = in.read();
                char letter = (char) data;
                player2 += letter;
            }
            Toast.makeText(getApplicationContext(), display2+"'s Pokemon: "+player2, Toast.LENGTH_SHORT).show();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView turnpic = (ImageView) findViewById(R.id.turn);
        turnPicture(turnpic, player1);
    }
    //Integer for background image
    int num = 1;
    //String to activate pokemon pictures
    String display1, display2, player1, player2;
    //boolean to control turn
    boolean turn = true;
    //ImageView, int and char for undo button code
    int temppiece, row, col;
    char temptype;
    ImageView temp;
    //char and int arrays to determine the player's evolution and to identify the button's side (Player 1 or 2)
    int piece[][] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    char type[][] =  {{'a', 'a', 'a'}, {'a', 'a', 'a'}, {'a', 'a', 'a'}};
    //Set turn method
    public void flip (ImageView i, int x, int y) {
        //Undo button code. This records values before the new values are set
        row = x;
        col = y;
        temppiece = piece[x][y];
        temptype = type[x][y];
        temp = i;
        //ImageView changes depending on turn
        ImageView turnpic = (ImageView) findViewById(R.id.turn);
        //When it's Player 1 turn. A player cannot upgrade their own piece and cannot evolve after third stage. They may only conquer the opposite player's spot
        if (turn) {
            if (type[x][y] == 'x')
                Toast.makeText(getApplicationContext(), "Cannot upgrade your own piece", Toast.LENGTH_SHORT).show();
            else if (piece[x][y] == 3)
                Toast.makeText(getApplicationContext(), "Cannot evolve any further", Toast.LENGTH_SHORT).show();
            else {//Upgrades if the evolution is less than 3
                if (piece[x][y]<3)
                    piece[x][y]++;
                pokemon(i, x, y, player1);
                turn = false;
                type[x][y] = 'x';
                turnPicture(turnpic, player2);
                win();
            }
        } //Else Player 2's turn
        else {
            if (type[x][y] == 'o')
                Toast.makeText(getApplicationContext(), "Cannot upgrade your own piece", Toast.LENGTH_SHORT).show();
            else if (piece[x][y] == 3)
                Toast.makeText(getApplicationContext(), "Cannot evolve any further", Toast.LENGTH_SHORT).show();
            else {//Upgrades if the evolution is less than 3
                if (piece[x][y]<3)
                    piece[x][y]++;
                pokemon(i, x, y, player2);
                turn = true;
                type[x][y] = 'o';
                turnPicture(turnpic, player1);
                win();
            }
        }//Calls win method at the end of the turn
        win();
    }
    public void bgSetup (int number){
        ImageView background = (ImageView) findViewById(R.id.background);
        switch (number){
            case 1:
                background.setImageResource(R.drawable.bg1);
                break;
            case 2:
                background.setImageResource(R.drawable.bg2);
                break;
            case 3:
                background.setImageResource(R.drawable.bg3);
                break;
            default:
                background.setImageResource(R.drawable.bg4);
        }
    }
    //The main method to change pictures
    public void pokemon (ImageView i, int x, int y, String player){
        switch (piece[x][y]) {
            case 0: {
                i.setImageResource(R.drawable.main);
                break;
            } case 1: {
                if (player.equals ("Bulbasaur"))
                    i.setImageResource(R.drawable.bulbasaur);
                else if (player.equals ("Charmander"))
                    i.setImageResource(R.drawable.charmander);
                else if (player.equals ("Oshawatt"))
                    i.setImageResource(R.drawable.oshawatt);
                else if (player.equals ("Pichu"))
                    i.setImageResource(R.drawable.pichu);
                else if (player.equals ("Piplup"))
                    i.setImageResource(R.drawable.piplup);
                else if (player.equals ("Snivy"))
                    i.setImageResource(R.drawable.snivy);
                break;
            } case 2: {
                if (player.equals ("Bulbasaur"))
                    i.setImageResource(R.drawable.ivysaur);
                else if (player.equals ("Charmander"))
                    i.setImageResource(R.drawable.charmeleon);
                else if (player.equals ("Oshawatt"))
                    i.setImageResource(R.drawable.dewott);
                else if (player.equals ("Pichu"))
                    i.setImageResource(R.drawable.pikachu);
                else if (player.equals ("Piplup"))
                    i.setImageResource(R.drawable.prinplup);
                else if (player.equals ("Snivy"))
                    i.setImageResource(R.drawable.servine);
                break;
            } case 3: {
                if (player.equals ("Bulbasaur"))
                    i.setImageResource(R.drawable.venusaur);
                else if (player.equals ("Charmander"))
                    i.setImageResource(R.drawable.charizard);
                else if (player.equals ("Oshawatt"))
                    i.setImageResource(R.drawable.samurott);
                else if (player.equals ("Pichu"))
                    i.setImageResource(R.drawable.raichu);
                else if (player.equals ("Piplup"))
                    i.setImageResource(R.drawable.empoleon);
                else if (player.equals ("Snivy"))
                    i.setImageResource(R.drawable.serperior);
                break;
            }
        }
    }
    //Sets turn picture for current turn
    public void turnPicture (ImageView i, String player){
        if (player.equals ("Bulbasaur"))
            i.setImageResource(R.drawable.turnbulbasaur);
        else if (player.equals ("Charmander"))
            i.setImageResource(R.drawable.turncharmander);
        else if (player.equals ("Oshawatt"))
            i.setImageResource(R.drawable.turnoshawott);
        else if (player.equals ("Pichu"))
            i.setImageResource(R.drawable.turnpichu);
        else if (player.equals ("Piplup"))
            i.setImageResource(R.drawable.turnpiplup);
        else if (player.equals ("Snivy"))
            i.setImageResource(R.drawable.turnsnivy);
    }
    //Undo method, calls on previous values and feeds them into the current values. Only stores one previous value.
    public void undo (View view){
        ImageView turnpictemp = (ImageView) findViewById(R.id.turn);
        piece[row][col] = temppiece;
        type[row][col] = temptype;
        //Calls picture changing method. If the value in the temp values is Player 2, player2 is inputted into the method.
        if (type[row][col] == 'o')
            pokemon(temp, row, col, player2);
        else
            pokemon(temp, row, col, player1);
        turn = !turn;
        //Calls method to switch player turn image
        if (turn)
            turnPicture(turnpictemp, player1);
        else
            turnPicture(turnpictemp, player2);
    }
    //Determines winner
    public void win() {
        char winner = '0';
        //Win conditions, if the same player has pieces in a row, column or diagonally
        if (type[0][0] == type[0][1] && type[0][0] == type[0][2] && type[0][0] != 0)
            winner = type[0][0];
        else if (type[1][0] == type[1][1] && type[1][0] == type[1][2] && type[1][0] != 0)
            winner = type[1][0];
        else if (type[2][0] == type[2][1] && type[2][0] == type[2][2] && type[2][0] != 0)
            winner = type[2][0];
        else if (type[0][0] == type[1][0] && type[0][0] == type[2][0] && type[0][0] != 0)
            winner = type[0][0];
        else if (type[0][1] == type[1][1] && type[0][1] == type[2][1] && type[0][1] != 0)
            winner = type[0][1];
        else if (type[0][2] == type[1][2] && type[0][2] == type[2][2] && type[0][2] != 0)
            winner = type[0][2];
        else if (type[0][0] == type[1][1] && type[0][0] == type[2][2] && type[0][0] != 0)
            winner = type[0][0];
        else if (type[0][2] == type[1][1] && type[0][2] == type[2][0] && type[0][2] != 0)
            winner = type[0][2];
        else if (type[0][0] != 'a' && type[0][1] != 'a' && type[0][2] != 'a' &&
                type[1][0] != 'a' && type[1][1] != 'a' && type[1][2] != 'a' &&
                type[2][0] != 'a' && type[2][1] != 'a' && type[2][2] != 'a')
            winner = 'z';
        //Outputs winning message
        if (winner == 'x') {
            Toast.makeText(getApplicationContext(), display1+" Wins", Toast.LENGTH_SHORT).show();
        } else if (winner == 'o') {
            Toast.makeText(getApplicationContext(), display2+" Wins", Toast.LENGTH_SHORT).show();
        } else if (winner == 'z') {
            Toast.makeText(getApplicationContext(), "Cat's game", Toast.LENGTH_SHORT).show();
        }
    }
    //Button codes. Call on flip method. Button 1
    public void aClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.a);
        int x = 0;
        int y = 0;
        flip(i, x, y);
    }//Button 2
    public void bClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.b);
        int x = 0;
        int y = 1;
        flip(i, x, y);
    }//Button 3
    public void cClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.c);
        int x = 0;
        int y = 2;
        flip(i, x, y);
    }//Button 4
    public void dClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.d);
        int x = 1;
        int y = 0;
        flip(i, x, y);
    }//Button 5
    public void eClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.e);
        int x = 1;
        int y = 1;
        flip(i, x, y);
    }//Button 6
    public void fClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.f);
        int x = 1;
        int y = 2;
        flip(i, x, y);
    }//Button 7
    public void gClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.g);
        int x = 2;
        int y = 0;
        flip(i, x, y);
    }//Button 8
    public void hClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.h);
        int x = 2;
        int y = 1;
        flip(i, x, y);
    }//Button 9
    public void iClick(View view) {
        ImageView i = (ImageView) findViewById(R.id.i);
        int x = 2;
        int y = 2;
        flip(i, x, y);
    }
    //Reset button code, resets screen and sets turnpic to Player 1's pokemon choice
    public void reset(View view){
        ImageView a = (ImageView) findViewById(R.id.a);
        a.setImageResource(R.drawable.main);
        ImageView b = (ImageView) findViewById(R.id.b);
        b.setImageResource(R.drawable.main);
        ImageView c = (ImageView) findViewById(R.id.c);
        c.setImageResource(R.drawable.main);
        ImageView d = (ImageView) findViewById(R.id.d);
        d.setImageResource(R.drawable.main);
        ImageView e = (ImageView) findViewById(R.id.e);
        e.setImageResource(R.drawable.main);
        ImageView f = (ImageView) findViewById(R.id.f);
        f.setImageResource(R.drawable.main);
        ImageView g = (ImageView) findViewById(R.id.g);
        g.setImageResource(R.drawable.main);
        ImageView h = (ImageView) findViewById(R.id.h);
        h.setImageResource(R.drawable.main);
        ImageView i = (ImageView) findViewById(R.id.i);
        i.setImageResource(R.drawable.main);
        turn = true;
        ImageView turnpic = (ImageView) findViewById(R.id.turn);
        turnPicture(turnpic, player1);
        for(int k=0; k<3; k++){
            for(int j=0; j<3; j++){
                piece[k][j]= 0;
                type[k][j]='a';
            }
        }
    }
    //Home button: rests code and goes to main screen
    public void home (View view){
        Intent i = new Intent (this, MainActivity.class);
        reset(view);
        startActivity(i);
    }
}