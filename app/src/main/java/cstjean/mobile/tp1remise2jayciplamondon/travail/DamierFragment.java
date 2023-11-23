package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private final Damier damier = new Damier(); // à remplacer par getInstance pour le Singleton

    /**
     * Tableau contenant les références des boutons dans une grille de 10x10.
     */
    private final Bouton[][] buttons = new Bouton[10][10];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        damier.initialiser();
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
        int darkColor = Color.rgb(191, 144, 92); // Dark color (adjust values as needed)
        int lightColor = Color.rgb(241, 209, 169); // Light color (adjust values as needed)
        boolean isDark = false;
        int positionRéelle = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                compteur++;

                Bouton bouton = new Bouton(getActivity());
                bouton.setId(compteur);

                // Set square attributes (size, etc.)
                bouton.setLayoutParams(new GridLayout.LayoutParams());
                bouton.getLayoutParams().width = 100; // Set width (in dp) as needed
                bouton.getLayoutParams().height = 100; // Set height (in dp) as needed

                int position = i * 10 + j + 1; // Calculate position based on i and j indices

                Pion pion;

                // Alternate background colors for a chessboard pattern
                if (isDark) {
                    // Ajout de pions ici
                    bouton.setBackgroundColor(darkColor);
                    System.out.println("Position non-jouable" + position);
                    System.out.println("Position réelle" + positionRéelle);
                    positionRéelle++;

                    pion = damier.getPion(positionRéelle);

                    if (pion != null) {
                        // Add an ImageView to represent the pawn on the square
                        ImageView pawn = new ImageView(getActivity());
                        pawn.setImageResource(pion.getCouleur() == Pion.Couleur.Noir ?
                                R.drawable.pawn_image : R.drawable.pawn_image);
                        pawn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        // Add the pawn ImageView to the Bouton
                        bouton.addView(pawn);
                    }
                } else {
                    // Cases non-jouable
                    bouton.setBackgroundColor(lightColor);
                }

                gridBoutons.addView(bouton); // Add the square to the GridLayout
                buttons[i][j] = bouton; // Store the square reference in your buttons array

                isDark = !isDark; // Toggle between dark and light colors
            }
            // Toggle the starting color for each row to maintain the checkerboard pattern
            isDark = !isDark;
        }
    }
/*
    private void initializeButtonsArray() {
        int darkColor = Color.rgb(191, 144, 92); // Dark color (adjust values as needed)
        int lightColor = Color.rgb(241, 209, 169); // Light color (adjust values as needed)
        boolean isDark = false;

        int usefulPionSize = 5; // Size of useful pion map
        int layoutSize = 10; // Size of the layout

        for (Map.Entry<Integer, Pion> entry : damier.getPionMap().entrySet()) {
            int position = entry.getKey();
            Pion pion = entry.getValue();

            // Do something with position and pion
            System.out.println("Position: " + position + ", Pion: " + pion);
        }
        int compteurPositionPion = 0;

        for (int i = 0; i < layoutSize; i++) {
            for (int j = 0; j < layoutSize; j++) {
                Bouton bouton = new Bouton(getActivity());

                bouton.setLayoutParams(new GridLayout.LayoutParams());
                bouton.getLayoutParams().width = 100; // Set width (in dp) as needed
                bouton.getLayoutParams().height = 100; // Set height (in dp) as needed

                boolean isUsefulRow = i % 2 != 0;
                boolean isUsefulCol = j % 2 != 0;

                // For empty spaces (not in the useful pion positions)
                if ((isUsefulRow && !isUsefulCol) || (!isUsefulRow && isUsefulCol)) {
                    bouton.setBackgroundColor(lightColor);
                    System.out.println("NON");
                } else {

                    System.out.println(compteurPositionPion);
                    bouton.setBackgroundColor(darkColor);

                    // Calculate the position in the useful 5x5 grid based on layout indices
                    int usefulPionRow = i / 2;
                    int usefulPionCol = j / 2;
                    int position = usefulPionRow * usefulPionSize + usefulPionCol + 1;

                    // Get the pion from the useful map
                    Pion pion = damier.getPion(position);

                    if (pion != null) {
                    ImageView pawn = new ImageView(getActivity());
                    pawn.setImageResource(pion.getCouleur() == Pion.Couleur.Noir ?
                            R.drawable.pawn_image : R.drawable.pawn_image);
                    pawn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    bouton.addView(pawn);
                    }

                    compteurPositionPion = compteurPositionPion + 1;
                }

                gridBoutons.addView(bouton);
            }
        }
    }

 */
}