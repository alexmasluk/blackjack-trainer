package com.example.alex.testproj;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Deck deck;
    private TextView txtPlayerVal;
    private TextView txtDealerVal;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private ImageView dealerC1;
    private ImageView dealerC2;
    private ImageView playerC1;
    private ImageView playerC2;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.alex.testproj";
    private Map<String, Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("start","onCreate");
        super.onCreate(savedInstanceState);

        Log.i("start","setContentView");
        setContentView(R.layout.activity_main);

        Log.i("start","get Toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Log.i("start","setSupportActionBar");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
            }
        });

        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = android.support.v7.preference.PreferenceManager
                .getDefaultSharedPreferences(this);
        Boolean soft17SwitchPref = sharedPref.getBoolean
                (SettingsActivity.SOFT_17_SWITCH, false);

        Boolean doubleAfterSplitPref = sharedPref.getBoolean
                (SettingsActivity.DOUBLE_SPLIT_SWITCH, false);

        Boolean showValPref = sharedPref.getBoolean
                (SettingsActivity.SHOW_VAL_SWITCH, true);


        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        map = new HashMap<String, Integer>();
        //Map image resources to card names"
        map.put("AC", R.drawable.cac);
        map.put("AD", R.drawable.cad);
        map.put("AH", R.drawable.cah);
        map.put("AS", R.drawable.cas);
        map.put("2C", R.drawable.c2c);
        map.put("2D", R.drawable.c2d);
        map.put("2H", R.drawable.c2h);
        map.put("2S", R.drawable.c2s);
        map.put("3C", R.drawable.c3c);
        map.put("3D", R.drawable.c3d);
        map.put("3H", R.drawable.c3h);
        map.put("3S", R.drawable.c3s);
        map.put("4C", R.drawable.c4d);
        map.put("4D", R.drawable.c4d);
        map.put("4H", R.drawable.c4h);
        map.put("4S", R.drawable.c4s);
        map.put("5C", R.drawable.c5c);
        map.put("5D", R.drawable.c5d);
        map.put("5H", R.drawable.c5h);
        map.put("5S", R.drawable.c5s);
        map.put("6C", R.drawable.c6c);
        map.put("6D", R.drawable.c6d);
        map.put("6H", R.drawable.c6h);
        map.put("6S", R.drawable.c6s);
        map.put("7C", R.drawable.c7c);
        map.put("7D", R.drawable.c7d);
        map.put("7H", R.drawable.c7h);
        map.put("7S", R.drawable.c7s);
        map.put("8C", R.drawable.c8c);
        map.put("8D", R.drawable.c8d);
        map.put("8H", R.drawable.c8h);
        map.put("8S", R.drawable.c8s);
        map.put("9C", R.drawable.c9c);
        map.put("9D", R.drawable.c9d);
        map.put("9H", R.drawable.c9h);
        map.put("9S", R.drawable.c9s);
        map.put("10C", R.drawable.c0c);
        map.put("10D", R.drawable.c0d);
        map.put("10H", R.drawable.c0h);
        map.put("10S", R.drawable.c0s);
        map.put("AC", R.drawable.cac);
        map.put("AD", R.drawable.cad);
        map.put("AH", R.drawable.cah);
        map.put("AS", R.drawable.cas);
        map.put("JC", R.drawable.cjc);
        map.put("JD", R.drawable.cjd);
        map.put("JH", R.drawable.cjh);
        map.put("JS", R.drawable.cjs);
        map.put("QC", R.drawable.cqc);
        map.put("QD", R.drawable.cqd);
        map.put("QH", R.drawable.cqh);
        map.put("QS", R.drawable.cqs);
        map.put("KC", R.drawable.ckc);
        map.put("KD", R.drawable.ckd);
        map.put("KH", R.drawable.ckh);
        map.put("KS", R.drawable.cks);

        Log.i("start","new Deck");

        txtDealerVal = findViewById(R.id.txtDealerVal);
        txtPlayerVal = findViewById(R.id.txtPlayerVal);
        dealerC1 = findViewById(R.id.dealerC1View);
        dealerC2 = findViewById(R.id.dealerC2View);
        playerC1 = findViewById(R.id.playerC1View);
        playerC2 = findViewById(R.id.playerC2View);

        if (!showValPref)
        {
            txtDealerVal.setVisibility(View.INVISIBLE);
            txtPlayerVal.setVisibility(View.INVISIBLE);
        }
        else
        {
            txtDealerVal.setVisibility(View.VISIBLE);
            txtPlayerVal.setVisibility(View.VISIBLE);
        }

        Log.i("start","deal");
        deck = new Deck(4);
        deal();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

    }
    public void determineWinner()
    {
        //dealer is finished. show entire hand
        int dealer_true_val = Card.getHandValue(dealerHand, false);
        dealerC1.setImageResource(map.get(dealerHand.get(0)));
        dealerC2.setImageResource(map.get(dealerHand.get(1)));
        txtDealerVal.setText("" + dealer_true_val);


        int player_val = Card.getHandValue(playerHand, false);
        String result = "";

        if (player_val > 21)
            result = "BUST!\n:(";
        else if (player_val == dealer_true_val)
            result = "PUSH!\n:/";
        else if (player_val > dealer_true_val || dealer_true_val > 21)
            result = "WIN!\n:D";
        else if (dealer_true_val > player_val)
            result = "LOSE!\n:(";

        if (player_val == 21 && playerHand.size() == 2 && !(dealer_true_val == 21 && dealerHand.size() != 2))
            result = "BLACKJACK!\n=D=D=D";

        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(result);
        myAlertBuilder.setMessage("");
        myAlertBuilder.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deal();

            }
        });

        AlertDialog dialog_card = myAlertBuilder.create();
        dialog_card.getWindow().setGravity(Gravity.BOTTOM);
        dialog_card.show();


    }

    public void deal()
    {


        playerHand = new ArrayList<Card>();
        dealerHand = new ArrayList<Card>();

        dealerHand.add(deck.pop());
        playerHand.add(deck.pop());
        dealerHand.add(deck.pop());
        playerHand.add(deck.pop());

        dealerC1.setImageResource(R.drawable.blue_back);
        dealerC2.setImageResource(map.get(dealerHand.get(1).getString()));
        playerC1.setImageResource(map.get(playerHand.get(0).getString()));
        playerC2.setImageResource(map.get(playerHand.get(1).getString()));


        int dealerVal = Card.getHandValue(dealerHand, true);
        int playerVal = Card.getHandValue(playerHand, false);

        txtDealerVal.setText("" + dealerVal);
        txtPlayerVal.setText("" + playerVal);

        if (playerVal == 21)
            determineWinner();

    }

    public void btnHitOnClick(View v) {
        playerHand.add(deck.pop());
        //txtPlayerHand.setText(Card.getStringRep(playerHand, false));
        int playerVal = Card.getHandValue(playerHand, false);

        txtPlayerVal.setText("" +  playerVal);

        if (playerVal > 21)
            determineWinner();

    }

    public void btnStayOnClick(View v) {
        //dealer plays
        int dealer_true_val = Card.getHandValue(dealerHand, false);
        //txtDealerHand.setText(Card.getStringRep(dealerHand, false));

        while (dealer_true_val < 17)
        {
            dealerHand.add(deck.pop());
            dealer_true_val = Card.getHandValue(dealerHand, false);
        }





        determineWinner();
    }
}
