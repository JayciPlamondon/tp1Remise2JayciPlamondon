package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cstjean.mobile.tp1remise2jayciplamondon.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe DamierFragment représente le fragment du jeu de dame.
 *
 * @author Jayci Plamondon
 */
public class DamierFragment extends Fragment {
    private static final String JOUEUR_GAGNANT = "joueurGagnant";

    /**
     * Interface définissant les méthodes de rappel pour obtenir les noms des joueurs.
     */
    public interface Callbacks {
        /**
         * Renvoie le nom du joueur 1.
         *
         * @return Le nom du joueur 1.
         */
        String getPlayer1Name();

        /**
         * Renvoie le nom du joueur 2.
         *
         * @return Le nom du joueur 2.
         */
        String getPlayer2Name();
    }

    /**
     * Référence à l'interface de rappel pour obtenir les noms des joueurs.
     */
    private Callbacks callbacks = null;

    /**
     * Représente le nom du joueur 1.
     */
    private String player1Name;

    /**
     * Représente le nom du joueur 2.
     */
    private String player2Name;

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
    Case lastSelectedTile = null;

    /**
     * Dernières cases sélectionnées par disponibilité.
     */
    Map<Integer, Case> lastSelectedTiles = new HashMap<>();

    /**
     * Représente le bouton retour en arrière.
     */
    private Button goingBackButton;

    /**
     * Instance du jeu Damier.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();

    /**
     * Le joueur gagnant si la partie est terminée.
     */
    private SingletonDamier.WinningPlayer joueurGagnantSiFin = SingletonDamier.WinningPlayer.NONE;

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
        return v -> handlegoingBackButtonClick();
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
    public void handlegoingBackButtonClick() {
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

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            linearLayout.addView(goingBackButton);
        }

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

    /**
     * Affiche les cases disponibles pour le déplacement en mettant en surbrillance
     * les cases dans le damier.
     *
     * @param positionDepart La position de départ du déplacement.
     * @param isDame         Indique si le déplacement concerne une dame.
     */
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
     *
     * @param rowArrive      Ligne d'arrivée du déplacement.
     * @param colArrive      Colonne d'arrivée du déplacement.
     * @param positionArrive Position d'arrivée dans la grille de jeu.
     */
    public void deplacerPionLayout(int rowArrive, int colArrive, int positionArrive) {
        SingletonDamier.Direction direction;
        Pion pionDepart = lastSelectedTile.getPion();
        int positionDepart = lastSelectedTile.getPosition();
        int colDepart = lastSelectedTile.getCol();
        int rowDepart = lastSelectedTile.getRow();

        direction = singletonDamier.calculerDirection(rowDepart, rowArrive, colDepart, colArrive);

        try {
            if (pionDepart instanceof Dame) {
                singletonDamier.deplacerDame(positionDepart, positionArrive, direction);
            } else {
                singletonDamier.deplacerPion(positionDepart, direction);
            }

            updateLayout();
            joueurGagnantSiFin = singletonDamier.verifierSiGagnant();
            if (joueurGagnantSiFin == SingletonDamier.WinningPlayer.BLACK
                    || joueurGagnantSiFin == SingletonDamier.WinningPlayer.WHITE ) {
                terminerPartie();
            }
        } catch (Exception e) {
            System.out.println("Déplacement n'a pas fonctionné. Erreur : " + e.getMessage());
        }

    }

    /**
     * Termine la partie et change de fragment.
     */
    private void terminerPartie() {
        // On affiche un message signalant la fin de la partie
        Toast.makeText(getActivity(), "La partie est terminé !", Toast.LENGTH_SHORT).show();

        // On crée le bundle contenant le joueur gagnant et on change de fragment.

        Bundle bundle = new Bundle();
        if (joueurGagnantSiFin == SingletonDamier.WinningPlayer.WHITE) {
            bundle.putSerializable(JOUEUR_GAGNANT, player1Name);
        } else if (joueurGagnantSiFin == SingletonDamier.WinningPlayer.BLACK) {
            bundle.putSerializable(JOUEUR_GAGNANT, player2Name);
        }

        Fragment finDePartieFragment = new FinPartieFragment();
        finDePartieFragment.setArguments(bundle);

        FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.fragment_container, finDePartieFragment).commit();
    }

    /**
     * Update le layout.
     */
    private void updateLayout() {
        int compteur = 1;
        boolean isDark = false;
        int positionReelle = 1;

        // Enlève tous les boutons de la grille.
        gridBoutons.removeAllViews();

        // Vérifie si le tour du joueur a changé
        updateTitlePlayerTurn();

        updateTitleManouryNotation();
        updateGoingBackButton(true);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int position = i * 10 + j + 1; // Calculate position based on i and j indices
                // Création d'une case, si jouable, elle obtient la positionReelle(du pion)
                Case caseDamier;

                // Remplacer le paramètre lastSelectedTile par le Singleton!!!
                if (isDark) {
                    caseDamier = new Case(getActivity(), i, j, positionReelle, isDark,
                            this, singletonDamier.getPion(positionReelle), compteur, singletonDamier);
                    caseDamier.setId(compteur);
                    compteur++;
                } else {
                    caseDamier = new Case(getActivity(), i, j, position, isDark, this, singletonDamier);
                }

                // Set square attributes (size, etc.)
                caseDamier.setLayoutParams(new GridLayout.LayoutParams());
                caseDamier.getLayoutParams().width = 100; // Set width (in dp) as needed
                caseDamier.getLayoutParams().height = 100; // Set height (in dp) as needed

                Pion pion;

                // Alternate background colors for a chessboard pattern
                if (isDark) {
                    // Ajout de pions ici

                    pion = singletonDamier.getPion(positionReelle);

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
                        caseDamier.addView(pawn);
                    }
                    positionReelle++;

                }

                gridBoutons.addView(caseDamier); // Add the square to the GridLayout
                buttons[i][j] = caseDamier; // Store the square reference in your buttons array

                isDark = !isDark; // Toggle between dark and light colors
            }
            // Toggle the starting color for each row to maintain the checkerboard pattern
            isDark = !isDark;
        }
    }
}