package cstjean.mobile.tp1remise2jayciplamondon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cstjean.mobile.tp1remise2jayciplamondon.travail.DamierFragment;

public class DamierActivity extends AppCompatActivity implements DamierFragment.Callbacks {

    /**
     * Représente le nom du joueur 1.
     */
    String player1Name;

    /**
     * Représente le nom du joueur 2.
     */
    String player2Name;

    private static final String PLAYER1NAME = "player1name";
    private static final String PLAYER2NAME = "player2name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Name = getIntent().getStringExtra(PLAYER1NAME);
        player2Name = getIntent().getStringExtra(PLAYER2NAME);
    }

    @Override
    public String getPlayer1Name() {
        return player1Name;
    }

    @Override
    public String getPlayer2Name() { return player2Name; }
}