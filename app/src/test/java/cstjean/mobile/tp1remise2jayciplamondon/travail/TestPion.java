package cstjean.mobile.tp1remise2jayciplamondon.travail;

import junit.framework.TestCase;

/**
 * La classe de test TestPion contient des méthodes de test pour la classe Pion.
 *
 * @author Jayci Plamondon
 */
public class TestPion extends TestCase {

    /**
     * Teste la création de pions avec différentes couleurs.
     */
    public void testCreer() {
        Pion pionDefault = new Pion();
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);

        // Vérifie que la couleur du pion par défaut est "Blanc"
        assertEquals(Pion.Couleur.Blanc, pionDefault.getCouleur());

        // Vérifie que la couleur du pion noir est correcte
        assertEquals(Pion.Couleur.Noir, pionNoir.getCouleur());

        // Vérifie que la couleur du pion blanc est correcte
        assertEquals(Pion.Couleur.Blanc, pionBlanc.getCouleur());
    }

    /**
     * Teste l'obtention de la représentation d'un pion.
     */
    public void testGetRepresentation() {
        Pion pion = new Pion();
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);

        assertEquals('p', pion.getRepresentation());
        assertEquals('P', pionNoir.getRepresentation());
        assertEquals('p', pionBlanc.getRepresentation());
    }
}
