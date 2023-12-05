package cstjean.mobile.tp1remise2jayciplamondon.travail;

/**
 * Cette classe représente un pion transformé avec sa position.
 */
public class PionTransforme {

    /**
     * Le pion transformé.
     */
    private Pion pion;

    /**
     * La position du pion transformé.
     */
    private int position;

    /**
     * La couleur du pion transformé.
     */
    private Pion.Couleur couleur;

    /**
     * Constructeur de la classe PionTransformé.
     *
     * @param pion     Le pion transformé.
     * @param position La position où le pion a été transformé.
     */
    public PionTransforme(Pion pion, int position) {
        this.pion = pion;
        this.position = position;
        couleur = pion.getCouleur();
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
     */
    public Pion getPion() {
        return pion;
    }

    /**
     * Obtient la couleur du pion transformé.
     */
    public Pion.Couleur getCouleur() {
        return couleur;
    }
}


