package cstjean.mobile.tp1remise2jayciplamondon.travail;

import androidx.annotation.Nullable;

/**
 * Cette classe représente un pion transformé avec sa position.
 */
public class PionTransforme {

    /**
     * Le pion transformé.
     */
    private final Pion pion;

    /**
     * La position du pion transformé.
     */
    private final int position;

    /**
     * La couleur du pion transformé.
     */
    private @Nullable Pion.Couleur couleur = null;

    /**
     * Constructeur de la classe PionTransformé.
     *
     * @param pion     Le pion transformé.
     * @param position La position où le pion a été transformé.
     */
    public PionTransforme(Pion pion, int position) {
        this.pion = pion;
        this.position = position;
        if (pion != null) {
            couleur = pion.getCouleur();
        }
    }

    /**
     * Obtient la position où le pion a été transformé.
     *
     * @return La position où le pion a été transformé.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obtient le pion transformé.
     *
     * @return Le pion transformé.
     */
    public Pion getPion() {
        return pion;
    }

    /**
     * Obtient la couleur du pion transformé.
     *
     * @return La couleur du pion transformé.
     */
    public Pion.Couleur getCouleur() {
        return couleur;
    }
}


