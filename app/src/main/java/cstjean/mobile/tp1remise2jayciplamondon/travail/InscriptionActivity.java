package cstjean.mobile.tp1remise2jayciplamondon.travail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cstjean.mobile.tp1remise2jayciplamondon.DamierActivity;
import cstjean.mobile.tp1remise2jayciplamondon.R;

public class InscriptionActivity extends AppCompatActivity {

    private static final String PLAYER1NAME = "player1name";
    private static final String PLAYER2NAME = "player2name";

    /**
     * Représente le bouton démarrer la partie.
     */
    Button startGameButton;

    /**
     * Représente le TextView pour le nom du joueur 1.
     */
    EditText editPlayer1Name;

    /**
     * Représente le TextView pour le nom du joueur 2.
     */
    EditText editPlayer2Name;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Va chercher les TextView pour les noms des joueurs
        editPlayer1Name = findViewById(R.id.editTextPlayer1);
        editPlayer2Name = findViewById(R.id.editTextPlayer2);

        // Va chercher startGameButton
        startGameButton = findViewById(R.id.startGameButton);

        // Crée un gestionnaire d'événements OnClickListener startGameButton
        View.OnClickListener startGameButtonClickListener = createStartGameButtonClickListener();

        // Définit le gestionnaire d'événements OnClickListener pour startGameButton
        startGameButton.setOnClickListener(createStartGameButtonClickListener());
    }

    /**
     * Crée un gestionnaire d'événements OnClickListener pour le bouton startGameButton.
     */
    private View.OnClickListener createStartGameButtonClickListener() {
        return v -> handleStartGameButtonClick();
    }

    /**
     * Vérifie si les noms sont valides et démarre la partie.
     */
    public void handleStartGameButtonClick() {
        String player1Name = editPlayer1Name.getText().toString().trim();
        String player2Name = editPlayer2Name.getText().toString().trim();

        if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
            Intent intent = new Intent(InscriptionActivity.this, DamierActivity.class);

            // Passe les deux nom à l'Intent
            intent.putExtra(PLAYER1NAME, player1Name);
            intent.putExtra(PLAYER2NAME, player2Name);

            startActivity(intent);
        } else {
            // Message d'erreur si les deux noms ne sont pas fournis
            Toast.makeText(this, "Veuillez entrer le nom des deux joueurs.", Toast.LENGTH_SHORT).show();
        }
    }
}