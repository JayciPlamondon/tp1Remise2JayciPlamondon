package cstjean.mobile.tp1remise2jayciplamondon.travail;

/**
 * La classe de test TestDame est utilisée pour tester les fonctionnalités de la classe Dame.
 *
 * @author Jayci Plamondon
 */
public class TestDame extends TestPion {

    /**
     * Teste la création d'instances de la classe {@code Dame} avec différentes couleurs.
     * Vérifie que la couleur de la dame par défaut est "Blanc", que la couleur de la dame noire est correcte,
     * et que la couleur de la dame blanche est correcte.
     */
    public void testCreer() {
        Dame dameDefaut = new Dame();
        Dame dameNoir = new Dame(Pion.Couleur.Noir);
        Dame dameBlanc = new Dame(Pion.Couleur.Blanc);

        // Vérifie que la couleur de la dame par défaut est "Blanc"
        assertEquals(Pion.Couleur.Blanc, dameDefaut.getCouleur());

        // Vérifie que la couleur de la dame noire est correcte
        assertEquals(Pion.Couleur.Noir, dameNoir.getCouleur());

        // Vérifie que la couleur de la dame blanche est correcte
        assertEquals(Pion.Couleur.Blanc, dameBlanc.getCouleur());
    }

    /**
     * Teste la méthode {@code getRepresentation()} de la classe {@code Dame}.
     * Vérifie que la représentation de la dame par défaut est 'd', que la représentation de la dame noire est 'D',
     * et que la représentation de la dame blanche est 'd'.
     */
    public void testGetRepresentation() {
        Dame dame = new Dame();
        Dame dameNoir = new Dame(Pion.Couleur.Noir);
        Dame dameBlanc = new Dame(Pion.Couleur.Blanc);

        assertEquals('d', dame.getRepresentation());
        assertEquals('D', dameNoir.getRepresentation());
        assertEquals('d', dameBlanc.getRepresentation());
    }
}
