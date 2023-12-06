package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cstjean.mobile.tp1remise2jayciplamondon.DamierActivity;
import cstjean.mobile.tp1remise2jayciplamondon.R;

/**
 * La classe InscriptionActivity représente l'activity Inscription.
 *
 * @author Jayci Plamondon
 */
public class InscriptionActivity extends AppCompatActivity {

    /**
     * Nom de la clé pour stocker le nom du joueur 1.
     */
    private static final String PLAYER1NAME = "player1name";

    /**
     * Nom de la clé pour stocker le nom du joueur 2.
     */
    private static final String PLAYER2NAME = "player2name";

    /**
     * Représente le TextView pour le nom du joueur 1.
     */
    private EditText editPlayer1Name;

    /**
     * Représente le TextView pour le nom du joueur 2.
     */
    private EditText editPlayer2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Va chercher les TextView pour les noms des joueurs
        editPlayer1Name = findViewById(R.id.editTextPlayer1);
        editPlayer2Name = findViewById(R.id.editTextPlayer2);

        // Va chercher startGameButton

        Button startGameButton = findViewById(R.id.startGameButton);

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