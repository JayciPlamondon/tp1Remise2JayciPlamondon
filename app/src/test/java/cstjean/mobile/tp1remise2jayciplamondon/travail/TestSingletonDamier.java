package cstjean.mobile.tp1remise2jayciplamondon.travail;

import java.util.Map;
import junit.framework.TestCase;

/**
 * La classe de test TestDamier contient des méthodes de test pour la classe Damier.
 */
public class TestSingletonDamier extends TestCase {

    /**
     * Teste la création d'un damier.
     */
    public void testCreer() {
        SingletonDamier singletonDamierTest = new SingletonDamier();
        Map<Integer, Pion> pionMap = singletonDamierTest.getPionMap();

        for (int i = 0; i < 50; i++) {
            assertNull(pionMap.get(i));
        }

    }

    /**
     * Teste l'initialisation d'un damier.
     */
    public void testInitialiser() {
        SingletonDamier singletonDamierTestInitialiser = new SingletonDamier();
        singletonDamierTestInitialiser.initialiser();

        int nbDePion = 0;

        for (Pion pion : singletonDamierTestInitialiser.getPionMap().values()) {
            if (pion != null) {
                nbDePion++;
            }
        }

        assertEquals(40, nbDePion);
    }

    /**
     * Teste l'ajout de pions au damier.
     */
    public void testAjouterPion() {
        SingletonDamier singletonDamierTestAjoutPion = new SingletonDamier();

        // Création du pion blanc
        Pion pionTestBlanc = new Pion(Pion.Couleur.Blanc);

        // Ajout du pion blanc au damier test
        singletonDamierTestAjoutPion.ajouterPion(4, pionTestBlanc);

        assertEquals(1, singletonDamierTestAjoutPion.getNombrePions());

        // Création du pion noir
        Pion pionTestNoir = new Pion(Pion.Couleur.Noir);

        // Ajout du pion noir au damier test
        singletonDamierTestAjoutPion.ajouterPion(5, pionTestNoir);

        assertEquals(2, singletonDamierTestAjoutPion.getNombrePions());

        assertEquals(pionTestBlanc, singletonDamierTestAjoutPion.getPion(4));
    }

    /**
     * Teste le tour du joueur.
     */
    public void testTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        assertTrue(singletonDamier.joueurPeutJouer(31));
        assertFalse(singletonDamier.joueurPeutJouer(16));

        // Déplacement == on change de tour de joueur
        singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautGauche);

        assertFalse(singletonDamier.joueurPeutJouer(26));
        assertTrue(singletonDamier.joueurPeutJouer(16));
    }

    /**
     * Teste l'obtention d'un pion du damier.
     */
    public void testGetPion() {
        SingletonDamier singletonDamierTestGetPion = new SingletonDamier();
        Pion pionTestAcces = new Pion(Pion.Couleur.Noir);

        singletonDamierTestGetPion.ajouterPion(38, pionTestAcces);
        assertEquals(pionTestAcces, singletonDamierTestGetPion.getPion(38));
    }

    /**
     * Teste l'obtention de la colonne d'un pion.
     */
    public void testGetCol() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair
        assertEquals(singletonDamier.getCol(1), 0);
        assertEquals(singletonDamier.getCol(2), 1);
        assertEquals(singletonDamier.getCol(3), 2);
        assertEquals(singletonDamier.getCol(4), 3);
        assertEquals(singletonDamier.getCol(5), 4);

        // Row Impair
        assertEquals(singletonDamier.getCol(46), 0);
        assertEquals(singletonDamier.getCol(47), 1);
        assertEquals(singletonDamier.getCol(48), 2);
        assertEquals(singletonDamier.getCol(49), 3);
        assertEquals(singletonDamier.getCol(50), 4);
    }

    /**
     * Teste l'obtention de la rangée d'un pion.
     */
    public void testGetRow() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair même rangée
        assertEquals(0, singletonDamier.getRow(1));
        assertEquals(0, singletonDamier.getRow(2));
        assertEquals(0, singletonDamier.getRow(3));
        assertEquals(0, singletonDamier.getRow(4));
        assertEquals(0, singletonDamier.getRow(5));

        // Row impair même rangée
        assertEquals(1, singletonDamier.getRow(6));
        assertEquals(1, singletonDamier.getRow(7));
        assertEquals(1, singletonDamier.getRow(8));
        assertEquals(1, singletonDamier.getRow(9));
        assertEquals(1, singletonDamier.getRow(10));

        // Rows pair toutes les rangées
        assertEquals(2, singletonDamier.getRow(11));
        assertEquals(4, singletonDamier.getRow(21));
        assertEquals(6, singletonDamier.getRow(31));
        assertEquals(8, singletonDamier.getRow(41));

        // Rows impair toutes les rangées
        assertEquals(1, singletonDamier.getRow(6));
        assertEquals(3, singletonDamier.getRow(16));
        assertEquals(5, singletonDamier.getRow(26));
        assertEquals(7, singletonDamier.getRow(36));
        assertEquals(9, singletonDamier.getRow(46));
    }

    /**
     * Teste si un pion se situe en bordure de damier.
     */
    public void testEstBordure() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair Col 0 ou 4
        assertFalse(singletonDamier.estBordure(1));
        assertTrue(singletonDamier.estBordure(5));

        // Row Impair Col 0 ou 4
        assertTrue(singletonDamier.estBordure(6));
        assertFalse(singletonDamier.estBordure(10));

        // Random
        assertTrue(singletonDamier.estBordure(16));
    }

    /**
     * Teste un déplacement simple de pion dans les quatres directions ainsi
     * qu'en rangée pair ou impair.
     */
    public void testDeplacementSimple() {
        SingletonDamier singletonDamier = new SingletonDamier();

        singletonDamier.initialiser();

        // Test Row Pair -> Impair
        assertEquals(6, singletonDamier.getCaseArrivee(1, SingletonDamier.Direction.BasGauche));
        assertEquals(37, singletonDamier.getCaseArrivee(31, SingletonDamier.Direction.BasDroite));
        assertEquals(36, singletonDamier.getCaseArrivee(41, SingletonDamier.Direction.HautGauche));
        assertEquals(8, singletonDamier.getCaseArrivee(12, SingletonDamier.Direction.HautDroite));

        // Test Row Impair -> Pair
        assertEquals(11, singletonDamier.getCaseArrivee(7, SingletonDamier.Direction.BasGauche));
        assertEquals(42, singletonDamier.getCaseArrivee(37, SingletonDamier.Direction.BasDroite));
        assertEquals(41, singletonDamier.getCaseArrivee(47, SingletonDamier.Direction.HautGauche));
        assertEquals(4, singletonDamier.getCaseArrivee(9, SingletonDamier.Direction.HautDroite));
    }

    // Vérifie les déplacement, impossible si pion veut aller vers l'extérieur si en bordure

    /**
     * Teste les déplacement impossible si un pion veut aller vers l'extérieur du damier.
     */
    public void testDeplacementVersBordure() {
        SingletonDamier singletonDamier = new SingletonDamier();

        singletonDamier.ajouterPion(15, new Pion());
        singletonDamier.ajouterPion(16, new Pion());

        singletonDamier.ajouterPion(1, new Pion());
        singletonDamier.ajouterPion(20, new Pion());

        // Déplacement invalide bas vers bordure
        assertEquals(-1, singletonDamier.getCaseArrivee(15, SingletonDamier.Direction.BasDroite));
        assertEquals(-1, singletonDamier.getCaseArrivee(16, SingletonDamier.Direction.BasGauche));

        // Déplacement valide bas vers bordure
        assertEquals(25, singletonDamier.getCaseArrivee(20, SingletonDamier.Direction.BasDroite));
        assertEquals(6, singletonDamier.getCaseArrivee(1, SingletonDamier.Direction.BasGauche));

        singletonDamier.initialiser();

        singletonDamier.ajouterPion(35, new Pion());
        singletonDamier.ajouterPion(36, new Pion());

        singletonDamier.ajouterPion(31, new Pion());
        singletonDamier.ajouterPion(50, new Pion());

        // Déplacement invalide haut vers bordure
        assertEquals(-1, singletonDamier.getCaseArrivee(35, SingletonDamier.Direction.HautDroite));
        assertEquals(-1, singletonDamier.getCaseArrivee(36, SingletonDamier.Direction.HautGauche));

        // Déplacement valide haut vers bordure
        assertEquals(45, singletonDamier.getCaseArrivee(50, SingletonDamier.Direction.HautDroite));
        assertEquals(26, singletonDamier.getCaseArrivee(31, SingletonDamier.Direction.HautGauche));

    }

    /**
     * Teste la méthode vérifierSiCaseEstVide.
     */
    public void testCaseVide() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Vérifie une case non-vide
        assertFalse(singletonDamier.verifierSiCaseEstVide(38));

        // Vérifie une case vide
        assertTrue(singletonDamier.verifierSiCaseEstVide(28));
    }

    /**
     * Teste si un pion en case d'arrivée est ennemi dans le but d'une prise.
     */
    public void testSiPionEstEnnemi() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(22, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        // Vérifie un pion ennemi en case d'arrivée
        assertTrue(singletonDamier.verifierSiPionEnnemi(22, SingletonDamier.Direction.HautGauche));

        // Vérifie un pion allié en case d'arrivée
        assertFalse(singletonDamier.verifierSiPionEnnemi(24, SingletonDamier.Direction.HautGauche));

        // Vérifie un pion qui veut aller en case vide
        assertFalse(singletonDamier.verifierSiPionEnnemi(39, SingletonDamier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale bas gauche.
     */
    public void testPriseBasGauche() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(12, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Blanc));

        assertEquals(21, singletonDamier.getCaseArrivePrise(12, SingletonDamier.Direction.BasGauche));

        // Prise valide row impair
        singletonDamier.ajouterPion(27, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(31, new Pion(Pion.Couleur.Blanc));

        assertEquals(36, singletonDamier.getCaseArrivePrise(27, SingletonDamier.Direction.BasGauche));

        // Prise invalide row pair
        singletonDamier.ajouterPion(31, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(31, SingletonDamier.Direction.BasGauche));

        // Prise invalide row impair
        singletonDamier.ajouterPion(26, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(26, SingletonDamier.Direction.BasGauche));
    }

    /**
     * Teste une prise en direction diagonale bas droite.
     */
    public void testPriseBasDroite() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        assertEquals(44, singletonDamier.getCaseArrivePrise(33, SingletonDamier.Direction.BasDroite));

        // Prise valide row impair
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));

        assertEquals(49, singletonDamier.getCaseArrivePrise(38, SingletonDamier.Direction.BasDroite));

        // Prise invalide row pair
        singletonDamier.ajouterPion(25, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(25, SingletonDamier.Direction.BasDroite));

        // Prise invalide row impair
        singletonDamier.ajouterPion(30, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(35, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(30, SingletonDamier.Direction.BasDroite));
    }

    /**
     * Teste une prise en direction diagonale haut gauche.
     */
    public void testPriseHautGauche() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(42, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(37, new Pion(Pion.Couleur.Blanc));

        assertEquals(31, singletonDamier.getCaseArrivePrise(42, SingletonDamier.Direction.HautGauche));

        // Prise valide row impair
        singletonDamier.ajouterPion(47, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(41, new Pion(Pion.Couleur.Blanc));

        assertEquals(36, singletonDamier.getCaseArrivePrise(47, SingletonDamier.Direction.HautGauche));

        // Prise invalide row pair
        singletonDamier.ajouterPion(41, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(41, SingletonDamier.Direction.HautGauche));

        // Prise invalide row impair
        singletonDamier.ajouterPion(46, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(46, SingletonDamier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale haut droite.
     */
    public void testPriseHautDroite() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(20, new Pion(Pion.Couleur.Blanc));

        assertEquals(15, singletonDamier.getCaseArrivePrise(24, SingletonDamier.Direction.HautDroite));

        // Prise valide row impair
        singletonDamier.ajouterPion(49, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(44, new Pion(Pion.Couleur.Blanc));

        assertEquals(40, singletonDamier.getCaseArrivePrise(49, SingletonDamier.Direction.HautDroite));

        // Prise invalide row pair
        singletonDamier.ajouterPion(35, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(35, SingletonDamier.Direction.HautDroite));

        // Prise invalide row impair
        singletonDamier.ajouterPion(50, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(45, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, singletonDamier.getCaseArrivePrise(50, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste si la case d'arrivée en prise sort du damier.
     */
    public void testPriseDepasseDamier() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.ajouterPion(47, new Pion());
        singletonDamier.ajouterPion(3, new Pion());

        // Vérification plus que 50
        assertEquals(-1, singletonDamier.getCaseArrivePrise(47, SingletonDamier.Direction.BasDroite));
        assertEquals(-1, singletonDamier.getCaseArrivee(47, SingletonDamier.Direction.BasDroite));

        // Vérification moins que 1
        assertEquals(-1, singletonDamier.getCaseArrivee(3, SingletonDamier.Direction.HautGauche));
        assertEquals(-1, singletonDamier.getCaseArrivePrise(3, SingletonDamier.Direction.HautGauche));

    }

    /**
     * Teste la méthode changerTourJoueur.
     */
    public void testChangerTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        assertEquals(1, singletonDamier.getTourJoueur());

        singletonDamier.changerTourJoueur();

        assertEquals(2, singletonDamier.getTourJoueur());
    }

    /**
     * Teste la méthode testLogs.
     */
    public void testLogs() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Premier déplacement Blanc
        Pion pionDepartBlanc = singletonDamier.getPion(32);

        singletonDamier.deplacerPion(32, SingletonDamier.Direction.HautGauche);
        assertEquals(pionDepartBlanc, singletonDamier.getPion(27));
        assertNull(singletonDamier.getPion(32));

        // Premier déplacement Noir
        Pion pionDepartNoir = singletonDamier.getPion(16);

        singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite);
        assertEquals(pionDepartNoir, singletonDamier.getPion(21));
        assertNull(singletonDamier.getPion(16));

        // Deuxième déplacement Blanc
        assertTrue(singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautGauche));
        assertEquals(pionDepartBlanc, singletonDamier.getPion(16));
        assertNull(singletonDamier.getPion(21));

        System.out.println(singletonDamier.getLogs());

        /*
        assertEquals("""
                1. 32-27
                2. (16-21)
                3. 27x16
                """, damier.getLogs());

         */
    }

    /**
     * Teste lorsqu'un joueur dont ce n'est pas son tour essais de jouer.
     */
    public void testMauvaisTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();
        Pion pionNoir = singletonDamier.getPion(16);

        assertFalse(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));
        assertEquals(pionNoir, singletonDamier.getPion(16));
        assertNull(singletonDamier.getPion(27));
    }

    /**
     * Teste si le pion que l'on tente de déplacer est en fait null.
     */
    public void testPionDeplaceNull() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        assertFalse(singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste une tentative de déplacement en reculons pour un pion standard.
     */
    public void testPionDeplacementReculons() {
        SingletonDamier singletonDamier = new SingletonDamier();


        System.out.println("Tour 1 : " + singletonDamier.getTourJoueur());
        singletonDamier.ajouterPion(32, new Pion(Pion.Couleur.Blanc));
        assertFalse(singletonDamier.deplacerPion(32, SingletonDamier.Direction.BasDroite));
        singletonDamier.changerTourJoueur();

        System.out.println("Tour 2 : " + singletonDamier.getTourJoueur());
        singletonDamier.ajouterPion(12, new Dame(Pion.Couleur.Noir));
        assertTrue(singletonDamier.deplacerDame(12, 8, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste si un pion essais de se déplacer vers une case contenant un pion allié.
     */
    public void testPionAllieCaseArrive() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        assertTrue(singletonDamier.deplacerPion(35, SingletonDamier.Direction.HautGauche));

        assertTrue(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));

        assertFalse(singletonDamier.deplacerPion(34, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste la prise si la case d'arrivée en prise est non-vide.
     */
    public void testCaseArriveePriseNonVide() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Noir
        assertTrue(singletonDamier.deplacerPion(34, SingletonDamier.Direction.HautGauche));
        // Blanc
        assertTrue(singletonDamier.deplacerPion(20, SingletonDamier.Direction.BasGauche));

        // Random noir
        assertTrue(singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautGauche));

        assertEquals(2, singletonDamier.getTourJoueur());

        // Blanc
        assertTrue(singletonDamier.deplacerPion(17, SingletonDamier.Direction.BasDroite));

        // Noir random
        assertTrue(singletonDamier.deplacerPion(35, SingletonDamier.Direction.HautGauche));

        // Blanc move interdit car prise en bordure droite
        assertFalse(singletonDamier.deplacerPion(21, SingletonDamier.Direction.BasGauche));

        SingletonDamier singletonDamier2 = new SingletonDamier();

        singletonDamier2.ajouterPion(10, new Pion(Pion.Couleur.Blanc));
        singletonDamier2.ajouterPion(15, new Pion(Pion.Couleur.Noir));
        assertFalse(singletonDamier2.deplacerPion(10, SingletonDamier.Direction.BasDroite));
    }

    /**
     * Teste si un pion peut se transformer en dame s'il atteint l'extrémité opposé
     * du damier en fonction de sa couleur.
     */
    public void testPeutTransformerEnDame() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Condition valide pour la transformation
        singletonDamier.ajouterPion(2, new Pion(Pion.Couleur.Blanc));
        assertTrue(singletonDamier.peutTransformer(2));
        singletonDamier.ajouterPion(9, new Pion(Pion.Couleur.Blanc));
        singletonDamier.deplacerPion(9, SingletonDamier.Direction.HautDroite);
        assertTrue(singletonDamier.getPion(4) instanceof Dame);

        // Condition valide avec prise
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        singletonDamier.deplacerPion(38, SingletonDamier.Direction.BasDroite);
        assertTrue(singletonDamier.getPion(49) instanceof Dame);

        // Condition invalide pour la transformation
        singletonDamier.ajouterPion(49, new Pion(Pion.Couleur.Blanc));
        assertFalse(singletonDamier.peutTransformer(49));
    }

    /**
     * Teste si un pion valide pour la transformation se transforme correctement en dame.
     */
    public void testTransformerEnDame() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Tranformation blanc
        singletonDamier.ajouterPion(8, new Pion(Pion.Couleur.Blanc));
        assertTrue(singletonDamier.deplacerPion(8, SingletonDamier.Direction.HautDroite));
        assertTrue(singletonDamier.getPion(3) instanceof Dame);

        // Tranformation noir
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Noir));
        assertTrue(singletonDamier.deplacerPion(43, SingletonDamier.Direction.BasDroite));
        assertTrue(singletonDamier.getPion(49) instanceof Dame);
    }

    /**
     * Teste la méthode trouverCasesDisponibles qui permet de trouver les cases disponibles
     * pour la dame.
     */
    public void testTrouverCasesDisponibles() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Blanc));

        System.out.println("Case disponible pour la dame : \n ");
        int compteur = 0;
        StringBuilder sb = new StringBuilder();

        for (boolean estDisponible : singletonDamier.caseDisponibleDame(17)) {
            compteur++;

            if (estDisponible) {
                System.out.println(compteur);
            }

            if (estDisponible) {
                sb.append(compteur);
            }
        }

        assertEquals("36811122122262839", sb.toString());
    }

    /**
     * Teste le déplacement normal d'une dame.
     */
    public void testDeplacerDame() {
        SingletonDamier singletonDamier = new SingletonDamier();

        singletonDamier.ajouterPion(17, new Dame(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Noir));

        assertTrue(singletonDamier.getPion(17) instanceof Dame);

        assertTrue(singletonDamier.deplacerDame(17, 39, SingletonDamier.Direction.BasDroite));

        assertNull(singletonDamier.getPion(17));
        assertTrue(singletonDamier.getPion(39) instanceof Dame);

        assertEquals(44, singletonDamier.getCaseAvantPrise(39, SingletonDamier.Direction.BasDroite));

        // Ajout et déplacement d'un second pion noir
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        assertTrue(singletonDamier.deplacerPion(38, SingletonDamier.Direction.BasDroite));

        // Deuxième prise de la dame
        assertTrue(singletonDamier.deplacerDame(39, 48, SingletonDamier.Direction.BasGauche));
        assertTrue(singletonDamier.getPion(48) instanceof Dame);

        assertNull(singletonDamier.getPion(43));

        singletonDamier.imprimerLogs();
    }

    /**
     * Teste de la méthode getCaseAvantPrise.
     */
    public void testGetCaseAvantPrise() {
        SingletonDamier singletonDamier = new SingletonDamier();

        assertEquals(44, singletonDamier.getCaseAvantPrise(39, SingletonDamier.Direction.BasDroite));


        // BAS GAUCHE

        // Test pour bordure gauche déplacement BasGauche/HautGauche
        assertEquals(-1, singletonDamier.getCaseAvantPrise(16, SingletonDamier.Direction.BasGauche));

        // Test row impair non bordure
        assertEquals(21, singletonDamier.getCaseAvantPrise(17, SingletonDamier.Direction.BasGauche));

        // Test row pair BasGauche
        assertEquals(6, singletonDamier.getCaseAvantPrise(1, SingletonDamier.Direction.BasGauche));


         // BAS DROITE

        // Test pour bordure droite déplacement BasDroite
        assertEquals(-1, singletonDamier.getCaseAvantPrise(5, SingletonDamier.Direction.BasDroite));

        // Test row pair non bordure
        assertEquals(15, singletonDamier.getCaseAvantPrise(10, SingletonDamier.Direction.BasDroite));

        // Test row pair BasGauche
        assertEquals(10, singletonDamier.getCaseAvantPrise(4, SingletonDamier.Direction.BasDroite));

        // HAUT GAUCHE

        // Test pour bordure gauche déplacement HautGauche
        assertEquals(-1, singletonDamier.getCaseAvantPrise(16, SingletonDamier.Direction.HautGauche));

        // Test row pair non bordure HautGauche
        assertEquals(6, singletonDamier.getCaseAvantPrise(11, SingletonDamier.Direction.HautGauche));

        // Test row impair HautGauche bordure
        assertEquals(1, singletonDamier.getCaseAvantPrise(7, SingletonDamier.Direction.HautGauche));

        // HAUT DROITE

        // Test pour bordure droite déplacement HautDroite
        assertEquals(-1, singletonDamier.getCaseAvantPrise(15, SingletonDamier.Direction.HautDroite));

        // Test row pair non bordure HautDroite
        assertEquals(5, singletonDamier.getCaseAvantPrise(10, SingletonDamier.Direction.HautDroite));

        // Test row pair HautDroite bordure
        assertEquals(10, singletonDamier.getCaseAvantPrise(14, SingletonDamier.Direction.HautDroite));
    }


    /**
     * Teste une condition de fin de partie lorsqu'il n'y a plus de pion ennemi.
     * Pas finie

    public void testFinDePartie() {
        Damier damierFinDePartie = new Damier();
        Pion pionNoir1 = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc1 = new Pion(Pion.Couleur.Blanc);

        Pion pionNoir2 = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc2 = new Pion(Pion.Couleur.Blanc);

        damierFinDePartie.ajouterPion(21, pionNoir1);
        damierFinDePartie.ajouterPion(32, pionBlanc1);

        damierFinDePartie.ajouterPion(22, pionNoir2);
        damierFinDePartie.ajouterPion(33, pionBlanc2);

        damierFinDePartie.deplacerPion(21, Damier.Direction.BasDroite);
        damierFinDePartie.deplacerPion(33, Damier.Direction.HautGauche);

        assertEquals(damierFinDePartie.getPion(22), pionNoir2);
        assertEquals(damierFinDePartie.getPion(28), pionBlanc2);
        damierFinDePartie.deplacerPion(22, Damier.Direction.BasDroite);

        assertEquals(damierFinDePartie.getPion(33), pionNoir2);
        assertNull(damierFinDePartie.getPion(28));

        damierFinDePartie.deplacerPion(32, Damier.Direction.HautGauche);

        assertEquals(damierFinDePartie.getPion(21), pionBlanc1);
        assertNull(damierFinDePartie.getPion(32));

        damierFinDePartie.imprimerLogs();

        damierFinDePartie.verifierSiGagnant();

        damierFinDePartie.ajouterPion(16, new Pion(Pion.Couleur.Noir));

        damierFinDePartie.deplacerPion(16, Damier.Direction.BasDroite);

        damierFinDePartie.verifierSiGagnant();

    }
     */

    public void testRetourEnArriere() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Premier déplacement blanc
        singletonDamier.ajouterPion(26, new Pion(Pion.Couleur.Blanc));
        assertTrue(singletonDamier.deplacerPion(26, SingletonDamier.Direction.HautDroite));

        // Premier déplacement noir (est une prise du pion blanc)
        singletonDamier.ajouterPion(16, new Pion(Pion.Couleur.Noir));
        assertTrue(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));

        singletonDamier.retourArriere();
        // damier.imprimerLogs();
    }

    /**
     * Teste le getter de pions restants sur le jeu en fonction de leur couleur.
     */
    public void testGetNombrePionsCouleur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Vérification afin d'enlever des pions :
        assertEquals(20, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Noir));
        assertEquals(20, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Blanc));

        // Déplacement pour manger au moins un pion
        singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautDroite);
        singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite);
        singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautGauche);

        assertEquals(19, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Noir));
    }
}