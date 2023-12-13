package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import cstjean.mobile.tp1remise2jayciplamondon.R;

/**
 * La classe FinPartieFragment représente le fragment qui affiche à la fin de la partie.
 *
 * @author Jayci Plamondon
 */
public class FinPartieFragment extends Fragment {

    /**
     * La clé pour le joueur gagnant.
     */
    private static final String JOUEUR_GAGNANT = "joueurGagnant";

    /**
     * Instance du jeu Damier.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fin_partie, container, false);

        // Va chercher les arguments passé par le fragment précèdent
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Extrait l'information du bundle
            String joueurGagnant = (String) bundle.getSerializable(JOUEUR_GAGNANT);

            if (joueurGagnant != null) {
                TextView winnerTextView = view.findViewById(R.id.joueurGagnantTextView);
                String winnerMessage = getResources().getString(R.string.winner_message, joueurGagnant);
                winnerTextView.setText(winnerMessage);
            }
        }

        Button restartGameButton = view.findViewById(R.id.restartGameButton);
        restartGameButton.setOnClickListener(v -> {
            // Démarrer la nouvelle activity
            Intent intent = new Intent(getActivity(), InscriptionActivity.class);
            startActivity(intent);
            singletonDamier.initialiser();

            // Terminer l'activité actuelle pour fermer ce fragment
            requireActivity().finish();
        });

        return view;
    }
}

