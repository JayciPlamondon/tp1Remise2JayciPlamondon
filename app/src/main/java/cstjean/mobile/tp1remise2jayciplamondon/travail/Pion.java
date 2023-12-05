package cstjean.mobile.tp1remise2jayciplamondon.travail;

/**
 * La classe Pion représente un pion d'un jeu de société. Chaque pion a une couleur associée.
 *
 * @author Jayci Plamondon
 */
public class Pion {
    /**
     * La couleur du pion.
     */
    private final Couleur couleur;

    /**
     * Si c'est la première fois que le pion se rend au bout.a
     */
    private boolean premierFoisBout;

    /**
     * Constructeur de la classe Pion qui prend en paramètre la couleur du pion.
     *
     * @param couleur La couleur du pion.
     */
    public Pion(Couleur couleur) {
        this.couleur = couleur;
    }

    /**
     * Constructeur par défaut de la classe Pion. Crée un pion avec la couleur par défaut "Blanc".
     */
    public Pion() {
        // Crée un pion blanc par défaut
        this(Couleur.Blanc);

        premierFoisBout = true;
    }

    /**
     * Obtient la couleur du pion.
     *
     * @return La couleur du pion.
     */
    public Couleur getCouleur() {
        return couleur;
    }

    /**
     * Renvoie la représentation du pion sous forme de caractère.
     *
     * @return 'P' si la couleur du pion est Noir, 'p' sinon.
     */
    public char getRepresentation() {
        char representation = ' ';

        if (couleur == Couleur.Noir) {
            representation = 'P';
        } else {
            representation = 'p';
        }

        return representation;
    }

    /**
     * Set premierFoisAuBout à false.
     */
    public void setPremierFoisBoutFalse() {
        premierFoisBout = false;
    }

    /**
     * Get premierFoisAuBout.
     */
    public boolean getPremierFoisAuBout() {
        return premierFoisBout;
    }

    /**
     * Représente les couleurs des pions, soit Blanc soit Noir.
     */
    public enum Couleur { Blanc, Noir }
}
