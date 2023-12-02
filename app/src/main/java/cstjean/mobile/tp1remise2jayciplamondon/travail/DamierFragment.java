package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cstjean.mobile.tp1remise2jayciplamondon.R;

public class DamierFragment extends Fragment {
    public interface Callbacks {
        String getPlayer1Name();
        String getPlayer2Name();
    }

    public Callbacks callbacks = null;

    /**
     * Représente le nom du joueur 1.
     */
    String player1Name;

    /**
     * Représente le nom du joueur 2.
     */
    String player2Name;

    /**
     * Représente le linearLayout.
     */
    private LinearLayout linearLayout;

    /**
     * Représente le titre tour du joueur.
     */
    private TextView titlePlayerTurn;

    /**
     * Représente les déplacements en notation Manoury.
     */
    private TextView titleManouryNotation;

    /**
     * Représente la grille de jeu.
     */
    private GridLayout gridBoutons;

    /**
     * Dernière case sélectionnée.
     */
    public Case lastSelectedTile = null;

    /**
     * Dernières cases sélectionnées par disponibilité.
     */
    public Map<Integer, Case> lastSelectedTiles = new HashMap<>();

    /**
     * Représente le bouton retour en arrière.
     */
    private Button goingBackButton;

    /**
     * Instance du jeu Notakto.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();

    /**
     * Tableau contenant les références des boutons dans une grille de 10x10.
     */
    private final Case[][] buttons = new Case[10][10];

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // On va chercher les noms des deux joueurs
        player1Name = callbacks.getPlayer1Name();
        player2Name = callbacks.getPlayer2Name();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_damier, container, false);

        // Initialisation des éléments de l'interface utilisateur
        initializeUiElements(view);

        goingBackButton.setEnabled(false);
        // Crée un gestionnaire d'événements OnClickListener bouton reset
        View.OnClickListener goingBackButtonClickListener = createGoingBackButtonClickListener();

        // Définit le gestionnaire d'événements OnClickListener pour tous les boutons
        setButtonOnClickListeners(goingBackButtonClickListener);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isGoingBackButtonEnabled", goingBackButton.isEnabled());
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isGoingBackButtonEnabled = savedInstanceState.getBoolean("isGoingBackButtonEnabled", false);
            updateGoingBackButton(isGoingBackButtonEnabled);
        }
    }


    /**
     * Crée un gestionnaire d'événements OnClickListener pour le bouton goignBackButton.
     */
    private View.OnClickListener createGoingBackButtonClickListener() {
        return v -> handleResetButtonClick();
    }

    /**
     * Définit le gestionnaire d'événements OnClickListener le bouton goingBackButton.
     *
     * @param listener Le gestionnaire d'événements OnClickListener à définir.
     */
    private void setButtonOnClickListeners(View.OnClickListener listener) {
        goingBackButton.setOnClickListener(listener);
    }

    /**
     * Retourne un déplacement en arrière.
     */
    public void handleResetButtonClick() {
        singletonDamier.retourArriere();
        updateLayout();

        if (singletonDamier.getLogsList().size() == 0) {
            goingBackButton.setEnabled(false);
        }
    }


    /**
     * Initialise les éléments de l'interface utilisateur.
     */
    private void initializeUiElements(View view) {
        linearLayout = view.findViewById(R.id.linearLayout);

        // Inside your Activity class
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("PORTRAIT");

            titlePlayerTurn = new TextView(getActivity());
            titleManouryNotation = new TextView(getActivity());
            goingBackButton = new Button(getActivity());
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("LAND");

            titlePlayerTurn = view.findViewById(R.id.titlePlayerTurn);
            titleManouryNotation = view.findViewById(R.id.titleManouryNotation);
            goingBackButton = view.findViewById(R.id.goingBackButton);
        }

        // Modifie la taille du texte et le met au centre
        titlePlayerTurn.setTextSize(35);
        titlePlayerTurn.setGravity(Gravity.CENTER);
        titleManouryNotation.setTextSize(30);
        titleManouryNotation.setGravity(Gravity.CENTER);

        updateGoingBackButton(false);
        goingBackButton.setTextSize(30);
        goingBackButton.setText("Retour en arrière");
        goingBackButton.setGravity(Gravity.CENTER);

        updateTitlePlayerTurn();
        updateTitleManouryNotation();

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            linearLayout.addView(titlePlayerTurn);
            linearLayout.addView(titleManouryNotation);
        }

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

        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            linearLayout.addView(goingBackButton);

        // Initialise le tableau avec les références des boutons
        initializeButtonsArray();
    }

    /**
     * Update le TextView titlePlayerTurn.
     */
    private void updateTitlePlayerTurn() {
        if (singletonDamier.getTourJoueur() == 1) {
            titlePlayerTurn.setText("C'est le tour à " + player1Name);
        } else {
            titlePlayerTurn.setText("C'est le tour à " + player2Name);
        }
    }

    /**
     * Update le TextView titleManouryNotation.
     */
    private void updateTitleManouryNotation() {

        ArrayList<String> logsList = singletonDamier.getLogsList();

        if (logsList.size() > 0) {
            String lastItem = logsList.get(logsList.size() - 1);
            titleManouryNotation.setText(lastItem);
        } else {
            titleManouryNotation.setText("-------");
        }
    }

    /**
     * Update le bouton retour en arrière.
     */
    private void updateGoingBackButton(boolean isEnabled) {
        goingBackButton.setEnabled(isEnabled);
    }

    /**
     * Initialise le tableau contenant les références des boutons dans une grille de 3x3.
     */
    private void initializeButtonsArray() {

        updateLayout();
    }

    public void displayAvailableTiles(int positionDepart, boolean isDame) {
        boolean[] arrayCaseDisponible;

        arrayCaseDisponible = isDame ? singletonDamier.caseDisponibleDame(positionDepart)
                                     : singletonDamier.caseDisponiblePion(positionDepart);

        for (int i = 1; i <= 50; i++) {
            if (arrayCaseDisponible[i - 1]) {
                System.out.println("position : " + i);
                Case caseRecupere = getActivity().findViewById(i);
                caseRecupere.setTileToSelectedColor();
                lastSelectedTiles.put(i, caseRecupere);
            }
        }
    }

    /**
     * Déplace le pion dans le layout.
     */
    public void deplacerPionLayout(int rowArrive, int colArrive, int positionArrive) {
        SingletonDamier.Direction direction;
        Pion pionDepart = lastSelectedTile.getPion();
        int positionDepart = lastSelectedTile.getPosition();
        int colDepart = lastSelectedTile.getCol();
        int rowDepart = lastSelectedTile.getRow();

        direction = singletonDamier.calculerDirection(rowDepart, rowArrive, colDepart, colArrive);

        try {
            if (pionDepart instanceof Dame)
                singletonDamier.deplacerDame(positionDepart, positionArrive, direction);
            else
                singletonDamier.deplacerPion(positionDepart, direction);

            updateLayout();

        } catch (Exception e) {
            System.out.println("Déplacement n'a pas fonctionné. Erreur : " + e.getMessage());
        }

    }

    /**
     * Update le layout.
     */
    private void updateLayout() {
        int compteur = 1;
        boolean isDark = false;
        int positionRéelle = 1;

        // Enlève tous les boutons de la grille.
        gridBoutons.removeAllViews();

        // Vérifie si le tour du joueur a changé
        updateTitlePlayerTurn();

        updateTitleManouryNotation();
        updateGoingBackButton(true);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int position = i * 10 + j + 1; // Calculate position based on i and j indices
                // Création d'une case, si jouable, elle obtient la positionRéelle(du pion)
                Case aCase;

                // Remplacer le paramètre lastSelectedTile par le Singleton!!!
                if(isDark) {
                    aCase = new Case(getActivity(), i, j, positionRéelle, isDark, this, singletonDamier.getPion(positionRéelle), compteur, singletonDamier);
                    aCase.setId(compteur);
                    compteur++;
                }
                else {
                    aCase = new Case(getActivity(), i, j, position, isDark, this, singletonDamier);
                }

                // Set square attributes (size, etc.)
                aCase.setLayoutParams(new GridLayout.LayoutParams());
                aCase.getLayoutParams().width = 100; // Set width (in dp) as needed
                aCase.getLayoutParams().height = 100; // Set height (in dp) as needed

                Pion pion;

                // Alternate background colors for a chessboard pattern
                if (isDark) {
                    // Ajout de pions ici

                    pion = singletonDamier.getPion(positionRéelle);

                    if (pion != null) {
                        // Add an ImageView to represent the pawn on the square
                        ImageView pawn = new ImageView(getActivity());
                        if (pion instanceof Dame) {
                            pawn.setImageResource(pion.getCouleur() == Pion.Couleur.Noir ?
                                    R.drawable.black_queen : R.drawable.white_queen);
                        } else {
                            pawn.setImageResource(pion.getCouleur() == Pion.Couleur.Noir ?
                                    R.drawable.black_pawn : R.drawable.white_pawn);
                        }
                        pawn.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        // Add the pawn ImageView to the Bouton
                        aCase.addView(pawn);
                    }
                    positionRéelle++;


                } else {
                    // Cases non-jouable
                }

                gridBoutons.addView(aCase); // Add the square to the GridLayout
                buttons[i][j] = aCase; // Store the square reference in your buttons array

                isDark = !isDark; // Toggle between dark and light colors
            }
            // Toggle the starting color for each row to maintain the checkerboard pattern
            isDark = !isDark;
        }
    }
}