package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Map;

public class Bouton extends FrameLayout implements View.OnClickListener {

    private static final int DARK_COLOR = Color.rgb(191, 144, 92);
    private static final int LIGHT_COLOR = Color.rgb(241, 209, 169);
    private static final int SELECTED_COLOR = Color.rgb(100, 200, 255);
    /**
     * Id de la case jouable.
     */
    private int id = -1;

    /**
     * L'instance de lastSelectedTile (à remplacer par le futur Singleton).
     */
    private DamierFragment damierFragment;

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

    private Pion pion = null;

    public Bouton(Context context, int row, int col, int position, boolean isDark, DamierFragment damierFragment, Pion pion, int id) {
        super(context);

        this.row = row;
        this.col = col;
        this.position = position;
        this.isDark = isDark;
        this.damierFragment = damierFragment;
        this.pion = pion;
        this.id = id;

        init();
        setButtonColor();
    }

    public Bouton(Context context, int row, int col, int position, boolean isDark, DamierFragment damierFragment) {
        super(context);

        this.row = row;
        this.col = col;
        this.position = position;
        this.isDark = isDark;
        this.damierFragment = damierFragment;

        init();
        setButtonColor();
    }

    public Bouton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set the background color to green
        setBackgroundColor(Color.GREEN);

        // Set click listener for the Bouton
        setOnClickListener(this);
    }

    private void setButtonColor() {
        if (isDark) {
            setBackgroundColor(DARK_COLOR);
        } else {
            setBackgroundColor(LIGHT_COLOR);
        }
    }

    public void setTileToSelectedColor() {
        setBackgroundColor(SELECTED_COLOR);
        isSelected = true;
    }

    private void resetBackgroundColor() {
        setBackgroundColor(DARK_COLOR);
        isSelected = false;
    }

    private void reinitialisationCouleur() {
        // Réinitialise la couleur de la dernière case sélectionnée (si elle existe)
        if (damierFragment.lastSelectedTile != null) {
            damierFragment.lastSelectedTile.resetBackgroundColor();
        }

        // Réinitialise la couleur des dernière case déplacement possible
        if (damierFragment.lastSelectedTiles != null) {
            // Iterate through the map using a for-each loop
            damierFragment.lastSelectedTiles.forEach((key, caseDeJeu) -> caseDeJeu.resetBackgroundColor());

        }
    }

    public void mettreEnSurbrillance() {
        // Met la case en surbrillance si c'est un pion
        if (pion != null) {
            setTileToSelectedColor();
            damierFragment.lastSelectedTile = this;

            // Surbrillance des cases disponibles pour déplacement
            damierFragment.displayAvailableTiles(position);
        }
    }

    @Override
    public void onClick(View view) {

        // Check if the background color is light
        if (!isDark) {
            // If the color is light, do nothing (disable the click)
            return;
        }

        System.out.println("Click: row -> " + row + ", col -> " + col +
                            ", position -> " + position + ", isPlayable -> " + isDark +
                            ", id -> " + id);

        if (isSelected && pion == null) {
                System.out.println("On déplace le pion vers cette position!");
                damierFragment.deplacerPionLayout(col);
        }

        reinitialisationCouleur();

        mettreEnSurbrillance();
    }

    public Pion getPion() {
        return pion;
    }

    public int getCol() {
        return col;
    }

    public int getPosition() {
        return position;
    }
}

