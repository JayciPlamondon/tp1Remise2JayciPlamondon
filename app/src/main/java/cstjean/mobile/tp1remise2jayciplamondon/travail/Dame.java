package cstjean.mobile.tp1remise2jayciplamondon.travail;

/**
 * La classe Dame représente une pièce de jeu de type Dame.
 * Elle vient de la classe abstraite Pion et hérite ainsi de ses propriétés et méthodes.
 *
 * @author Jayci Plamondon
 */
public class Dame extends Pion {

    /**
     * Constructeur de la classe Dame avec une couleur spécifiée.
     *
     * @param couleur La couleur de la Dame (Couleur.Noir ou Couleur.Blanc)
     */
    public Dame(Couleur couleur) {
        super(couleur);
    }

    /**
     * Constructeur par défaut de la classe Dame.
     * Crée une Dame avec la couleur par défaut "Blanc".
     */
    public Dame() {
        super();  // Creates a Dame with the default color "Blanc"
    }

    /**
     * Méthode qui retourne la représentation de la Dame en caractère.
     * Utilise 'D' pour les Dames de couleur noire et 'd' pour les Dames de couleur blanche.
     *
     * @return Le caractère représentant la Dame.
     */
    @Override
    public char getRepresentation() {
        char representation = ' ';

        if (getCouleur() == Couleur.Noir) {
            representation = 'D';
        } else {
            representation = 'd';
        }

        return representation;
    }
}
