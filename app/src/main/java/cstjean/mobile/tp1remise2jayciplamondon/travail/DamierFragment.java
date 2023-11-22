package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import cstjean.mobile.tp1remise2jayciplamondon.R;

public class DamierFragment extends Fragment {

    /**
     * Représente la grille de jeu.
     */
    private GridLayout gridBoutons;

    /**
     * Représente le linearLayout.
     */
    private LinearLayout linearLayout;

    /**
     * Instance du jeu Notakto.
     */
    private final Damier game = new Damier(); // à remplacer par getInstance pour le Singleton

    /**
     * Tableau contenant les références des boutons dans une grille de 10x10.
     */
    private final Bouton[][] buttons = new Bouton[10][10];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_damier, container, false);

        // Initialisation des éléments de l'interface utilisateur
        initializeUiElements(view);

        return view;
    }

    /**
     * Initialise les éléments de l'interface utilisateur.
     */
    private void initializeUiElements(View view) {
        linearLayout = view.findViewById(R.id.linearLayout);

        gridBoutons = new GridLayout(getActivity());

        // Set the layout parameters with a marginBottom
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Adjust the width as needed
                LinearLayout.LayoutParams.WRAP_CONTENT // Adjust the height as needed
        );

        gridBoutons.setLayoutParams(layoutParams);
        gridBoutons.setColumnCount(10);
        gridBoutons.setOrientation(GridLayout.HORIZONTAL);
        gridBoutons.setPadding(0, 0, 0, 0); // Remove the bottom margin

        linearLayout.addView(gridBoutons);

        // Initialise le tableau avec les références des boutons
        initializeButtonsArray();
    }

    /**
     * Initialise le tableau contenant les références des boutons dans une grille de 3x3.
     */
    private void initializeButtonsArray() {
        int compteur = 0;
        int darkColor = Color.rgb(118, 150, 86); // Dark color (adjust values as needed)
        int lightColor = Color.rgb(238, 238, 210); // Light color (adjust values as needed)
        boolean isDark = false;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                compteur++;

                Bouton bouton = new Bouton(getActivity());
                bouton.setId(compteur);

                // Set square attributes (size, etc.)
                bouton.setLayoutParams(new GridLayout.LayoutParams());
                bouton.getLayoutParams().width = 100; // Set width (in dp) as needed
                bouton.getLayoutParams().height = 100; // Set height (in dp) as needed

                // Alternate background colors for a chessboard pattern
                if (isDark) {
                    bouton.setBackgroundColor(darkColor);
                } else {
                    bouton.setBackgroundColor(lightColor);
                }
                isDark = !isDark; // Toggle between dark and light colors

                gridBoutons.addView(bouton); // Add the square to the GridLayout
                buttons[i][j] = bouton; // Store the square reference in your buttons array
            }
            // Adjust isDark for each row to create the checkerboard pattern
            isDark = !isDark;
        }
    }

}