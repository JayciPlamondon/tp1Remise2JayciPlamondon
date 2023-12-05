package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * La classe Case représente une case sur un damier.
 *
 * @author Jayci Plamondon
 */
public class Case extends FrameLayout implements View.OnClickListener {

    /**
     * Couleur foncée pour la case jouable.
     */
    private static final int DARK_COLOR = Color.rgb(191, 144, 92);

    /**
     * Couleur claire pour la case non-jouable.
     */
    private static final int LIGHT_COLOR = Color.rgb(241, 209, 169);

    /**
     * Couleur verte pour la case sélectionnée.
     */
    private static final int SELECTED_COLOR = Color.rgb(0, 255, 0);
    /**
     * Id de la case jouable.
     */
    private int id = -1;

    /**
     * Le fragment damierFragment.
     */
    private DamierFragment damierFragment;

    /**
     * L'instance de damier (à remplacer par le futur Singleton).
     */
    private SingletonDamier singletonDamier;

    /**
     * La rangée de la case.
     */
    private int row;

    /**
     * La colonne de la case.
     */
    private int col;

    /**
     * La position de la case.
     */
    private int position;

    /**
     * Si la case est une case jouable (les cases brunes foncées).
     */
    private boolean isDark;

    /**
     * Si la case est sélectionnée.
     */
    private boolean isSelected = false;

    /**
     * Si la case contient une dame.
     */
    private boolean isDame = false;

    /**
     * Si la case sélectionnée contient un pion.
     */
    private Pion pion = null;

    /**
     * Constructeur de la classe Case.
     *
     * @param context le contexte.
     * @param row la rangée de la case.
     * @param col la colonne de la case.
     * @param position la position de la case.
     * @param isDark si la case est jouable (est foncée).
     * @param damierFragment le fragment damierFragment.
     * @param pion le pion sur la case.
     * @param id l'id de la case.
     * @param singletonDamier le singleton singleDamier.
     */
    public Case(Context context, int row, int col, int position, boolean isDark,
                DamierFragment damierFragment, Pion pion, int id, SingletonDamier singletonDamier) {
        super(context);

        this.row = row;
        this.col = col;
        this.position = position;
        this.isDark = isDark;
        this.damierFragment = damierFragment;
        this.pion = pion;
        this.id = id;
        this.singletonDamier = singletonDamier;
        this.isDame = pion instanceof Dame;

        init();
        setButtonColor();
    }

    /**
     * Le deuxième constructeur de la classe Case.
     * Représente une case sélectionnée ne contenant pas de pion.
     *
     * @param context le contexte.
     * @param row la rangée de la case.
     * @param col la colonne de la case.
     * @param position la position de la case.
     * @param isDark si la case est jouable (est foncée)
     * @param damierFragment le fragment damierFragment.
     * @param singletonDamier le singleton singleDamier.
     */
    public Case(Context context, int row, int col, int position, boolean isDark,
                DamierFragment damierFragment, SingletonDamier singletonDamier) {
        super(context);

        this.row = row;
        this.col = col;
        this.position = position;
        this.isDark = isDark;
        this.damierFragment = damierFragment;
        this.singletonDamier = singletonDamier;
        this.isDame = pion instanceof Dame;

        init();
        setButtonColor();
    }

    /**
     * Le troisième constructeur de la classe Case.
     *
     * @param context le contexte.
     * @param attrs les attributs.
     */
    public Case(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initialise la case sélectionnée en lui attribuant une couleur de background verte.
     * De plus on set le clickListener.
     */
    private void init() {
        // Set the background color to green
        setBackgroundColor(Color.GREEN);

        // Set click listener for the Bouton
        setOnClickListener(this);
    }

    /**
     * Lors de la création du damier, on set la couleur de background de la case.
     * Foncée si jouable.
     * Claire si non-jouable.
     */
    private void setButtonColor() {
        if (isDark) {
            setBackgroundColor(DARK_COLOR);
        } else {
            setBackgroundColor(LIGHT_COLOR);
        }
    }

    /**
     * Change la couleur de la case en couleur sélectionnée.
     */
    public void setTileToSelectedColor() {
        setBackgroundColor(SELECTED_COLOR);
        isSelected = true;
    }

    /**
     * Réinitialise la couleur de fond de la case.
     */
    private void resetBackgroundColor() {
        setBackgroundColor(DARK_COLOR);
        isSelected = false;
    }

    /**
     * Réinitialise la couleur de fond des cases sélectionnées.
     */
    private void reinitialisationCouleur() {
        // Réinitialise la couleur de la dernière case sélectionnée (si elle existe)
        if (damierFragment.lastSelectedTile != null) {
            damierFragment.lastSelectedTile.resetBackgroundColor();
        }

        // Réinitialise la couleur des dernières cases de déplacement possibles
        if (damierFragment.lastSelectedTiles != null) {
            damierFragment.lastSelectedTiles.forEach((key, caseDeJeu) -> caseDeJeu.resetBackgroundColor());
        }
    }

    /**
     * Gère l'action lorsqu'un clic est effectué sur une case.
     * Si la couleur de fond est claire, cela désactive le clic.
     * Sinon, vérifie s'il s'agit d'un pion ou d'une dame pour appeler
     * la méthode appropriée.
     *
     * @param view La vue sur laquelle le clic est effectué.
     */
    @Override
    public void onClick(View view) {

        // Vérifie si la couleur de fond est claire
        if (!isDark) {
            // Si la couleur est claire, ne fait rien (désactive le clic)
            return;
        }

        // Affiche des informations sur la case cliquée
        System.out.println("Clic : ligne -> " + row + ", colonne -> " + col +
                ", position -> " + position + ", estJouable -> " + isDark +
                ", id -> " + id + ", estDame -> " + isDame);

        // Vérifie si la case contient un pion ou une dame et appelle la méthode correspondante
        if (!isDame) {
            handleOnClickPion();
        } else {
            handleOnClickDame();
        }
    }

    /**
     * Gère le clic sur une case contenant un pion.
     */
    public void handleOnClickPion() {
        if (isSelected && pion == null) {
            System.out.println("On déplace le pion vers cette position!");
            damierFragment.deplacerPionLayout(row, col, position);
        }

        reinitialisationCouleur();
        mettreEnSurbrillance();
    }

    /**
     * Gère le clic sur une case contenant une dame.
     */
    public void handleOnClickDame() {
        System.out.println("Le pion cliqué est une dame!");
        if (isSelected && pion == null) {
            System.out.println("On déplace le pion vers cette position!");
            damierFragment.deplacerPionLayout(row, col, position);
        }

        reinitialisationCouleur();
        mettreEnSurbrillance();
    }

    /**
     * Met en surbrillance la case si elle contient un pion
     * et que c'est le tour du joueur correspondant.
     */
    public void mettreEnSurbrillance() {
        // Met la case en surbrillance si c'est un pion
        if (pion != null) {
            Pion.Couleur couleurPion = pion.getCouleur();
            if ((couleurPion == Pion.Couleur.Blanc && singletonDamier.getTourJoueur() == 1) ||
                    couleurPion == Pion.Couleur.Noir && singletonDamier.getTourJoueur() == 2) {

                setTileToSelectedColor();
                damierFragment.lastSelectedTile = this;

                // Surbrillance des cases disponibles pour déplacement
                damierFragment.displayAvailableTiles(position, isDame);
            }
        }
    }

    /**
     * Renvoie le pion contenu dans la case.
     *
     * @return Le pion sur la case.
     */
    public Pion getPion() {
        return pion;
    }

    /**
     * Renvoie le numéro de ligne de la case.
     *
     * @return La rangée de la case.
     */
    public int getRow() {
        return row;
    }

    /**
     * Renvoie le numéro de colonne de la case.
     *
     * @return La colonne de la case.
     */
    public int getCol() {
        return col;
    }

    /**
     * Renvoie la position de la case.
     *
     * @return La position de la case.
     */
    public int getPosition() {
        return position;
    }
}

