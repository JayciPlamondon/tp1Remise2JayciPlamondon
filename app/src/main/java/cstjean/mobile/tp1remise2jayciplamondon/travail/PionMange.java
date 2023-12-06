package cstjean.mobile.tp1remise2jayciplamondon.travail;

/**
 * Cette classe représente un pion mangé avec sa position.
 */
public class PionMange {

    /**
     * Le pion mangé.
     */
    private final Pion pion;

    /**
     * La position du pion mangé.
     */
    private final int position;

    /**
     * Constructeur de la classe PionMange.
     *
     * @param pion     Le pion mangé.
     * @param position La position où le pion a été mangé.
     */
    public PionMange(Pion pion, int position) {
        this.pion = pion;
        this.position = position;
    }

    /**
     * Obtient la position où le pion a été mangé.
     *
     * @return La position où le pion a été mangé.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Obtient le pion mangé.
     *
     * @return Le pion mangé.
     */
    public Pion getPion() {
        return pion;
    }
}


