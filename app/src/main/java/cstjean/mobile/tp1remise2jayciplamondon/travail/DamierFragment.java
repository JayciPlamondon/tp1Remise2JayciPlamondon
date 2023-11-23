package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

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
     * Représente le titre tour du joueur.
     */
    private TextView titlePlayerTurn;

    /**
     * Dernière case sélectionnée.
     */
    public Bouton lastSelectedTile = null;

    /**
     * Dernières cases sélectionnées par disponibilité.
     */
    public Map<Integer, Bouton> lastSelectedTiles = new HashMap<>();

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

        titlePlayerTurn = new TextView(getActivity());

        if (damier.getTourJoueur() == 1) {
            titlePlayerTurn.setText("C'est le tour au blanc");
        } else {
            titlePlayerTurn.setText("C'est le tour au noir");
        }

        // Modifie la taille du texte et le met au centre
        titlePlayerTurn.setTextSize(30);
        titlePlayerTurn.setGravity(Gravity.CENTER);

        linearLayout.addView(titlePlayerTurn);

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

        updateLayout();
    }

    public void displayAvailableTiles(int positionDepart) {
        boolean[] arrayCaseDisponible = damier.caseDisponiblePion(positionDepart);


        for (int i = 1; i < 50; i++) {
            if (arrayCaseDisponible[i]) {
                System.out.println("position : " + i);
                Bouton boutonRecupere = getActivity().findViewById(i);
                boutonRecupere.setTileToSelectedColor();
                lastSelectedTiles.put(i, boutonRecupere);
            }
        }
    }

    /**
     * Déplace le pion dans le layout.
     */
    public void deplacerPionLayout(int colArrive) {
        Damier.Direction direction;
        Pion pionDepart = lastSelectedTile.getPion();
        int positionDepart = lastSelectedTile.getPosition();
        int colDepart = lastSelectedTile.getCol();

        direction = calculerDirection(colArrive, colDepart, pionDepart);

        try {
            damier.deplacerPion(positionDepart, direction);
            // Remove all views from the layout
            gridBoutons.removeAllViews();
            updateLayout();
        } catch (Exception e) {
            System.out.println("Déplacement n'a pas fonctionné. Erreur : " + e.getMessage());
        }

    }

    /**
     * Détermine la direction
     */
    private Damier.Direction calculerDirection(int colArrive, int colDepart, Pion pionDepart) {
        Damier.Direction direction;

        if (colArrive > colDepart) {
            // Direction Gauche
            if (pionDepart.getCouleur() == Pion.Couleur.Blanc)
                direction = Damier.Direction.HautDroite;
            else
                direction = Damier.Direction.BasDroite;
        } else {
            // Direction Droite
            if (pionDepart.getCouleur() == Pion.Couleur.Blanc)
                direction = Damier.Direction.HautGauche;
            else
                direction = Damier.Direction.HautGauche;
        }

        return direction;
    }

    /**
     * Update le layout.
     */
    private void updateLayout() {
        int compteur = 1;
        boolean isDark = false;
        int positionRéelle = 1;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int position = i * 10 + j + 1; // Calculate position based on i and j indices

                // Création d'une case, si jouable, elle obtient la positionRéelle(du pion)
                Bouton bouton;

                // Remplacer le paramètre lastSelectedTile par le Singleton!!!
                if(isDark) {
                    bouton = new Bouton(getActivity(), i, j, positionRéelle, isDark, this, damier.getPion(positionRéelle), compteur);
                    bouton.setId(compteur);
                    compteur++;
                }
                else {
                    bouton = new Bouton(getActivity(), i, j, position, isDark, this);
                }

                // Set square attributes (size, etc.)
                bouton.setLayoutParams(new GridLayout.LayoutParams());
                bouton.getLayoutParams().width = 100; // Set width (in dp) as needed
                bouton.getLayoutParams().height = 100; // Set height (in dp) as needed


                Pion pion;

                // Alternate background colors for a chessboard pattern
                if (isDark) {
                    // Ajout de pions ici

                    pion = damier.getPion(positionRéelle);

                    if (pion != null) {
                        // Add an ImageView to represent the pawn on the square
                        ImageView pawn = new ImageView(getActivity());
                        pawn.setImageResource(pion.getCouleur() == Pion.Couleur.Noir ?
                                R.drawable.black_pawn : R.drawable.white_pawn);
                        pawn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        // Add the pawn ImageView to the Bouton
                        bouton.addView(pawn);
                    }
                    positionRéelle++;

                } else {
                    // Cases non-jouable
                }

                gridBoutons.addView(bouton); // Add the square to the GridLayout
                buttons[i][j] = bouton; // Store the square reference in your buttons array

                isDark = !isDark; // Toggle between dark and light colors
            }
            // Toggle the starting color for each row to maintain the checkerboard pattern
            isDark = !isDark;
        }
    }
}