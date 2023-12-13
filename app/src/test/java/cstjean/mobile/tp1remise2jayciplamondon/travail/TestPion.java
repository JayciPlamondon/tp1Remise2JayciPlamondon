package cstjean.mobile.tp1remise2jayciplamondon.travail;

import org.junit.Assert;
import org.junit.Test;

/**
 * La classe de test TestPion contient des méthodes de test pour la classe Pion.
 *
 * @author Jayci Plamondon
 */
public class TestPion {

    /**
     * Teste la création de pions avec différentes couleurs.
     */
    @Test
    public void testCreer() {
        Pion pionDefault = new Pion();
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);

        // Vérifie que la couleur du pion par défaut est "Blanc"
        Assert.assertEquals(Pion.Couleur.Blanc, pionDefault.getCouleur());

        // Vérifie que la couleur du pion noir est correcte
        Assert.assertEquals(Pion.Couleur.Noir, pionNoir.getCouleur());

        // Vérifie que la couleur du pion blanc est correcte
        Assert.assertEquals(Pion.Couleur.Blanc, pionBlanc.getCouleur());
    }

    /**
     * Teste l'obtention de la représentation d'un pion.
     */
    @Test
    public void testGetRepresentation() {
        Pion pion = new Pion();
        Pion pionNoir = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc = new Pion(Pion.Couleur.Blanc);

        Assert.assertEquals('p', pion.getRepresentation());
        Assert.assertEquals('P', pionNoir.getRepresentation());
        Assert.assertEquals('p', pionBlanc.getRepresentation());
    }
}
