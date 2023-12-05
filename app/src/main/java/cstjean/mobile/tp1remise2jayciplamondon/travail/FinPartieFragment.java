package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cstjean.mobile.tp1remise2jayciplamondon.R;

public class FinPartieFragment extends Fragment {

    private static final String JOUEUR_GAGNANT = "joueurGagnant";
    /**
     * Instance du jeu Damier.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();


    public FinPartieFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fin_partie, container, false);

        // Retrieve the arguments passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Extrait l'information du bundle
            String joueurGagnant = (String) bundle.getSerializable(JOUEUR_GAGNANT);

            if (joueurGagnant != null) {
                TextView winnerTextView = view.findViewById(R.id.joueurGagnantTextView);
                winnerTextView.setText("Le joueur gagnant est : " + joueurGagnant);
            }
        }



        Button restartGameButton = view.findViewById(R.id.restartGameButton);
        restartGameButton.setOnClickListener(v -> {
            // Démarrer la nouvelle activity
            Intent intent = new Intent(getActivity(), InscriptionActivity.class);
            startActivity(intent);
            singletonDamier.initialiser();

            // Terminer l'activité actuelle pour fermer ce fragment
            getActivity().finish();
        });

        return view;
    }

}

