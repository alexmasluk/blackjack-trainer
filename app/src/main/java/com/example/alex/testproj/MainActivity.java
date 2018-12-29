package com.example.alex.testproj;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Deck deck;
    private Button btnDouble, btnHit, btnStay, btnSplit;
    private TextView txtPlayerVal, txtDealerVal, txtPlayerFunds, txtCurrentBet;
    private List<Card> playerHand, dealerHand;
    private ImageView dealerC1, dealerC2, playerC1, playerC2;
    private List<ImageView> dealerCards, playerCards;
    private Map<String, Integer> map;
    private ConstraintLayout layout;
    private Boolean soft17SwitchPref, doubleAfterSplitPref, showValPref;
    private Double playerFunds = null;
    private Double initialFunds = 500.00;
    private Double betSize = 15.00;
    private Double currentBet = 0.0;
    public final int PLAYER=0;
    public final int DEALER=1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
            }
        });

        android.support.v7.preference.PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = android.support.v7.preference.PreferenceManager
                .getDefaultSharedPreferences(this);
        soft17SwitchPref = sharedPref.getBoolean
                (SettingsActivity.SOFT_17_SWITCH, false);

        doubleAfterSplitPref = sharedPref.getBoolean
                (SettingsActivity.DOUBLE_SPLIT_SWITCH, false);

        showValPref = sharedPref.getBoolean
                (SettingsActivity.SHOW_VAL_SWITCH, true);

        if (playerFunds == null)
            playerFunds = initialFunds;

        //mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        if (map == null)
        {
            map = new HashMap<>();
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

        }

        Log.i("start","new Deck");

        //grab references to views
        layout = findViewById(R.id.layout);
        txtDealerVal = findViewById(R.id.txtDealerVal);
        txtPlayerVal = findViewById(R.id.txtPlayerVal);
        txtPlayerFunds = findViewById(R.id.txtPlayerFunds);
        txtCurrentBet = findViewById(R.id.txtCurrentBet);
        dealerC1 = findViewById(R.id.dealerC1View);
        dealerC2 = findViewById(R.id.dealerC2View);
        playerC1 = findViewById(R.id.playerC1View);
        playerC2 = findViewById(R.id.playerC2View);
        btnDouble = findViewById(R.id.btnDouble);
        btnHit = findViewById(R.id.btnHit);
        btnStay = findViewById(R.id.btnStay);
        setFundsDisplay();

        playerC1.setVisibility(View.INVISIBLE);
        dealerC1.setVisibility(View.INVISIBLE);
        playerC2.setVisibility(View.INVISIBLE);
        dealerC2.setVisibility(View.INVISIBLE);
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
        toggleButtons(true);
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

    public void toggleButtons(Boolean enabled)
    {
        btnHit.setEnabled(enabled);
        btnDouble.setEnabled(enabled);
        btnStay.setEnabled(enabled);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFundsDisplay()
    {
        txtPlayerFunds.setText("" + NumberFormat.getCurrencyInstance(Locale.US).format(playerFunds));
        txtCurrentBet.setText("" + NumberFormat.getCurrencyInstance(Locale.US).format(currentBet));
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void determineWinner()
    {
        //dealer is finished. show entire hand
        int dealer_true_val = Card.getHandValue(dealerHand, false);
        dealerC1.setImageResource(map.get(dealerHand.get(0).toString()));
        Double winnings = 0.0;


        int player_val = Card.getHandValue(playerHand, false);
        String result = "";

        if (player_val > 21)
            result = getString(R.string.bust_result);
        else if (player_val == dealer_true_val) {
            result = getString(R.string.push_result);
            winnings = currentBet;
        }
        else if (player_val > dealer_true_val || dealer_true_val > 21) {
            result = getString(R.string.win_result);
            winnings = currentBet * 2;
        }
        else if (dealer_true_val > player_val)
            result = getString(R.string.lose_result);

        if (player_val == 21 && playerHand.size() == 2 && !(dealer_true_val == 21 && dealerHand.size() != 2))
        {
            result = getString(R.string.blackjack_result);
            winnings = currentBet + currentBet * 1.5;
        }

        if (result != getString(R.string.bust_result))
        {
            dealerC2.setImageResource(map.get(dealerHand.get(1).toString()));
            txtDealerVal.setText("" + dealer_true_val);
        }

        playerFunds += winnings;
        currentBet = 0.0;
        setFundsDisplay();

        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle(result + "     Payout: " + NumberFormat.getCurrencyInstance(Locale.US).format(winnings));
        myAlertBuilder.setMessage("");
        myAlertBuilder.setPositiveButton("NEXT HAND", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int which) {
                deal();
            }
        });

        AlertDialog dialog_card = myAlertBuilder.create();
        dialog_card.getWindow().setGravity(Gravity.BOTTOM);
        dialog_card.show();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deal()
    {
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        //remove previous hand
        if (dealerCards != null)
        {
            for (int i=2; i<dealerCards.size(); i++)
            {
                dealerCards.get(i).setVisibility(View.INVISIBLE);
                layout.removeView(dealerCards.get(i));
                set.clear(dealerCards.get(i).getId());

            }
        }

        if (playerCards != null)
        {
            for (int i=2; i<playerCards.size(); i++)
            {
                playerCards.get(i).setVisibility(View.INVISIBLE);
                layout.removeView(playerCards.get(i));
                set.clear(playerCards.get(i).getId());
            }
        }
        set.applyTo(layout);

        //if player has money, deal new cards
        if (playerFunds - betSize > 0)
        {
            playerFunds -= betSize;
            currentBet += betSize;
            setFundsDisplay();

            dealerCards = new ArrayList<>();
            dealerCards.add(dealerC1);
            dealerCards.add(dealerC2);

            playerCards = new ArrayList<>();
            playerCards.add(playerC1);
            playerCards.add(playerC2);




            playerHand = new ArrayList<Card>();
            dealerHand = new ArrayList<Card>();

            playerHand.add(deck.pop());
            dealerHand.add(deck.pop());
            playerHand.add(deck.pop());
            dealerHand.add(deck.pop());

            //manually deal cards
            //dealerHand.add(new Card("H", "A"));
            //dealerHand.add(new Card("S", "6"));


            dealerC1.setImageResource(map.get(dealerHand.get(0).toString()));
            dealerC2.setImageResource(R.drawable.red_back);
            playerC1.setImageResource(map.get(playerHand.get(0).toString()));
            playerC2.setImageResource(map.get(playerHand.get(1).toString()));


            playerC1.setVisibility(View.VISIBLE);
            dealerC1.setVisibility(View.VISIBLE);
            playerC2.setVisibility(View.VISIBLE);
            dealerC2.setVisibility(View.VISIBLE);
            int dealerVal = Card.getHandValue(dealerHand, true);
            int dealerTrueVal = Card.getHandValue(dealerHand, false);
            int playerVal = Card.getHandValue(playerHand, false);

            txtDealerVal.setText("" + dealerVal);
            txtPlayerVal.setText("" + playerVal);

            toggleButtons(true);
            //check for natural blackjack
            if (playerVal == 21 || dealerTrueVal == 21)
                determineWinner();
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void updateHandDisplay(Boolean double_down)
    {
        ImageView newPlayerCard = new ImageView(this);
        newPlayerCard.setRotation(90);

        newPlayerCard.setImageResource(map.get(playerHand.get(playerHand.size()-1).toString()));

        newPlayerCard.setLayoutParams(new ConstraintLayout.LayoutParams(playerC1.getLayoutParams()));
        newPlayerCard.setId(View.generateViewId());
        newPlayerCard.setVisibility(View.VISIBLE);
        newPlayerCard.setContentDescription("Player Hit Card");
        int newCardId = newPlayerCard.getId();
        playerCards.add(newPlayerCard);


        ConstraintSet set = new ConstraintSet();
        layout.addView(newPlayerCard);
        set.clone(layout);

        int startTopMargin = (int) (getResources().getDimension(R.dimen.player_c1_margintop));
        int startSideMargin = (int) (getResources().getDimension(R.dimen.player_c1_marginside));
        int cardSpread = (int) (getResources().getDimension(R.dimen.card_spread));
        int cardNum = playerCards.size()-1;

        int newCardTopMargin = startTopMargin + cardSpread * cardNum + 10;
        int newCardSideMargin = startSideMargin + cardSpread * cardNum - 30;

        set.connect(newCardId, ConstraintSet.TOP,
                layout.getId(), ConstraintSet.TOP, newCardTopMargin);
        set.connect(newCardId, ConstraintSet.LEFT,
                layout.getId(), ConstraintSet.LEFT, newCardSideMargin);
        set.applyTo(layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void updateHandDisplay(int player)
    {
        if (player == PLAYER)
        {
            ImageView newPlayerCard = new ImageView(this);

            newPlayerCard.setImageResource(map.get(playerHand.get(playerHand.size()-1).toString()));

            newPlayerCard.setLayoutParams(new ConstraintLayout.LayoutParams(playerC1.getLayoutParams()));
            newPlayerCard.setId(View.generateViewId());
            newPlayerCard.setVisibility(View.VISIBLE);
            newPlayerCard.setContentDescription("Player Hit Card");
            int newCardId = newPlayerCard.getId();
            playerCards.add(newPlayerCard);


            ConstraintSet set = new ConstraintSet();
            layout.addView(newPlayerCard);
            set.clone(layout);

            int startTopMargin = (int) (getResources().getDimension(R.dimen.player_c1_margintop));
            int startSideMargin = (int) (getResources().getDimension(R.dimen.player_c1_marginside));
            int cardSpread = (int) (getResources().getDimension(R.dimen.card_spread));
            int cardNum = playerCards.size()-1;

            int newCardTopMargin = startTopMargin + cardSpread * cardNum;
            int newCardSideMargin = startSideMargin + cardSpread * cardNum;

            set.connect(newCardId, ConstraintSet.TOP,
                    layout.getId(), ConstraintSet.TOP, newCardTopMargin);
            set.connect(newCardId, ConstraintSet.LEFT,
                    layout.getId(), ConstraintSet.LEFT, newCardSideMargin);
            set.applyTo(layout);
        }
        if (player == DEALER)
        {
            ImageView newDealerCard = new ImageView(this);
            newDealerCard.setImageResource(map.get(dealerHand.get(dealerHand.size()-1).toString()));

            newDealerCard.setLayoutParams(new ConstraintLayout.LayoutParams(dealerC1.getLayoutParams()));
            newDealerCard.setId(View.generateViewId());
            newDealerCard.setVisibility(View.VISIBLE);
            newDealerCard.setContentDescription("Dealer Hit Card");
            int newCardId = newDealerCard.getId();
            dealerCards.add(newDealerCard);


            ConstraintSet set = new ConstraintSet();
            layout.addView(newDealerCard);
            set.clone(layout);

            int startTopMargin = (int) (getResources().getDimension(R.dimen.dealer_c1_margintop));
            int startSideMargin = (int) (getResources().getDimension(R.dimen.dealer_c1_marginside));
            int cardSpread = (int) (getResources().getDimension(R.dimen.card_spread));
            int cardNum = dealerCards.size()-1;

            int newCardTopMargin = startTopMargin + cardSpread * cardNum;
            int newCardSideMargin = startSideMargin + cardSpread * cardNum;

            set.connect(newCardId, ConstraintSet.TOP,
                    layout.getId(), ConstraintSet.TOP, newCardTopMargin);
            set.connect(newCardId, ConstraintSet.LEFT,
                    layout.getId(), ConstraintSet.LEFT, newCardSideMargin);
            set.applyTo(layout);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void btnHitOnClick(View v) {
      Hit();

    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void Hit()
    {
        btnDouble.setEnabled(false);
        playerHand.add(deck.pop());

        updateHandDisplay(PLAYER);
        int playerVal = Card.getHandValue(playerHand, false);

        txtPlayerVal.setText("" +  playerVal);

        if (playerVal > 21)
            determineWinner();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void btnStayOnClick(View v) {
        Stay();

    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void Stay()
    {
        toggleButtons(false);
        //dealer plays
        int dealer_true_val = Card.getHandValue(dealerHand, false);
        Boolean softHand = Card.isHandValueSoft(dealerHand);
        while (dealer_true_val < 17 || (dealer_true_val < 18 && soft17SwitchPref && softHand))
        {
            dealerHand.add(deck.pop());
            softHand = Card.isHandValueSoft(dealerHand);
            dealer_true_val = Card.getHandValue(dealerHand, false);
            updateHandDisplay(DEALER);

        }
        determineWinner();
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void btnDoubleOnClick(View view) {
        if (playerFunds - betSize > 0)
        {
            playerFunds -= betSize;
            currentBet += betSize;
            playerHand.add(deck.pop());
            updateHandDisplay(true);
            int playerVal = Card.getHandValue(playerHand, false);
            txtPlayerVal.setText("" + playerVal);
            Stay();
        }
    }
}
