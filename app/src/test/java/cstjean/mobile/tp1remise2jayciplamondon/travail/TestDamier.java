package cstjean.mobile.tp1remise2jayciplamondon.travail;

import java.util.ArrayDeque;
import java.util.Map;
import junit.framework.TestCase;

/**
 * La classe de test TestDamier contient des méthodes de test pour la classe Damier.
 */
public class TestDamier extends TestCase {

    /**
     * Teste la création d'un damier.
     */
    public void testCreer() {
        Damier damierTest = new Damier();
        Map<Integer, Pion> pionMap = damierTest.getPionMap();

        for (int i = 0; i < 50; i++) {
            assertNull(pionMap.get(i));
        }

    }

    /**
     * Teste l'initialisation d'un damier.
     */
    public void testInitialiser() {
        Damier damierTestInitialiser = new Damier();
        damierTestInitialiser.initialiser();

        int nbDePion = 0;

        for (Pion pion : damierTestInitialiser.getPionMap().values()) {
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
        Damier damierTestAjoutPion = new Damier();

        // Création du pion blanc
        Pion pionTestBlanc = new Pion(Pion.Couleur.Blanc);

        // Ajout du pion blanc au damier test
        damierTestAjoutPion.ajouterPion(4, pionTestBlanc);

        assertEquals(1, damierTestAjoutPion.getNombrePions());

        // Création du pion noir
        Pion pionTestNoir = new Pion(Pion.Couleur.Noir);

        // Ajout du pion noir au damier test
        damierTestAjoutPion.ajouterPion(5, pionTestNoir);

        assertEquals(2, damierTestAjoutPion.getNombrePions());

        assertEquals(pionTestBlanc, damierTestAjoutPion.getPion(4));
    }

    /**
     * Teste le tour du joueur.
     */
    public void testTourJoueur() {
        Damier damier = new Damier();
        damier.initialiser();

        assertTrue(damier.joueurPeutJouer(31));
        assertFalse(damier.joueurPeutJouer(16));

        // Déplacement == on change de tour de joueur
        damier.deplacerPion(31, Damier.Direction.HautGauche);

        assertFalse(damier.joueurPeutJouer(26));
        assertTrue(damier.joueurPeutJouer(16));
    }

    /**
     * Teste l'obtention d'un pion du damier.
     */
    public void testGetPion() {
        Damier damierTestGetPion = new Damier();
        Pion pionTestAcces = new Pion(Pion.Couleur.Noir);

        damierTestGetPion.ajouterPion(38, pionTestAcces);
        assertEquals(pionTestAcces, damierTestGetPion.getPion(38));
    }

    /**
     * Teste l'obtention de la colonne d'un pion.
     */
    public void testGetCol() {
        Damier damier = new Damier();
        damier.initialiser();

        // Row Pair
        assertEquals(damier.getCol(1), 0);
        assertEquals(damier.getCol(2), 1);
        assertEquals(damier.getCol(3), 2);
        assertEquals(damier.getCol(4), 3);
        assertEquals(damier.getCol(5), 4);

        // Row Impair
        assertEquals(damier.getCol(46), 0);
        assertEquals(damier.getCol(47), 1);
        assertEquals(damier.getCol(48), 2);
        assertEquals(damier.getCol(49), 3);
        assertEquals(damier.getCol(50), 4);
    }

    /**
     * Teste l'obtention de la rangée d'un pion.
     */
    public void testGetRow() {
        Damier damier = new Damier();
        damier.initialiser();

        // Row Pair même rangée
        assertEquals(0, damier.getRow(1));
        assertEquals(0, damier.getRow(2));
        assertEquals(0, damier.getRow(3));
        assertEquals(0, damier.getRow(4));
        assertEquals(0, damier.getRow(5));

        // Row impair même rangée
        assertEquals(1, damier.getRow(6));
        assertEquals(1, damier.getRow(7));
        assertEquals(1, damier.getRow(8));
        assertEquals(1, damier.getRow(9));
        assertEquals(1, damier.getRow(10));

        // Rows pair toutes les rangées
        assertEquals(2, damier.getRow(11));
        assertEquals(4, damier.getRow(21));
        assertEquals(6, damier.getRow(31));
        assertEquals(8, damier.getRow(41));

        // Rows impair toutes les rangées
        assertEquals(1, damier.getRow(6));
        assertEquals(3, damier.getRow(16));
        assertEquals(5, damier.getRow(26));
        assertEquals(7, damier.getRow(36));
        assertEquals(9, damier.getRow(46));
    }

    /**
     * Teste si un pion se situe en bordure de damier.
     */
    public void testEstBordure() {
        Damier damier = new Damier();
        damier.initialiser();

        // Row Pair Col 0 ou 4
        assertFalse(damier.estBordure(1));
        assertTrue(damier.estBordure(5));

        // Row Impair Col 0 ou 4
        assertTrue(damier.estBordure(6));
        assertFalse(damier.estBordure(10));

        // Random
        assertTrue(damier.estBordure(16));
    }

    /**
     * Teste un déplacement simple de pion dans les quatres directions ainsi
     * qu'en rangée pair ou impair.
     */
    public void testDeplacementSimple() {
        Damier damier = new Damier();

        damier.initialiser();

        // Test Row Pair -> Impair
        assertEquals(6, damier.getCaseArrivee(1, Damier.Direction.BasGauche));
        assertEquals(37, damier.getCaseArrivee(31, Damier.Direction.BasDroite));
        assertEquals(36, damier.getCaseArrivee(41, Damier.Direction.HautGauche));
        assertEquals(8, damier.getCaseArrivee(12, Damier.Direction.HautDroite));

        // Test Row Impair -> Pair
        assertEquals(11, damier.getCaseArrivee(7, Damier.Direction.BasGauche));
        assertEquals(42, damier.getCaseArrivee(37, Damier.Direction.BasDroite));
        assertEquals(41, damier.getCaseArrivee(47, Damier.Direction.HautGauche));
        assertEquals(4, damier.getCaseArrivee(9, Damier.Direction.HautDroite));
    }

    // Vérifie les déplacement, impossible si pion veut aller vers l'extérieur si en bordure

    /**
     * Teste les déplacement impossible si un pion veut aller vers l'extérieur du damier.
     */
    public void testDeplacementVersBordure() {
        Damier damier = new Damier();

        damier.ajouterPion(15, new Pion());
        damier.ajouterPion(16, new Pion());

        damier.ajouterPion(1, new Pion());
        damier.ajouterPion(20, new Pion());

        // Déplacement invalide bas vers bordure
        assertEquals(-1, damier.getCaseArrivee(15, Damier.Direction.BasDroite));
        assertEquals(-1, damier.getCaseArrivee(16, Damier.Direction.BasGauche));

        // Déplacement valide bas vers bordure
        assertEquals(25, damier.getCaseArrivee(20, Damier.Direction.BasDroite));
        assertEquals(6, damier.getCaseArrivee(1, Damier.Direction.BasGauche));

        damier.initialiser();

        damier.ajouterPion(35, new Pion());
        damier.ajouterPion(36, new Pion());

        damier.ajouterPion(31, new Pion());
        damier.ajouterPion(50, new Pion());

        // Déplacement invalide haut vers bordure
        assertEquals(-1, damier.getCaseArrivee(35, Damier.Direction.HautDroite));
        assertEquals(-1, damier.getCaseArrivee(36, Damier.Direction.HautGauche));

        // Déplacement valide haut vers bordure
        assertEquals(45, damier.getCaseArrivee(50, Damier.Direction.HautDroite));
        assertEquals(26, damier.getCaseArrivee(31, Damier.Direction.HautGauche));

    }

    /**
     * Teste la méthode vérifierSiCaseEstVide.
     */
    public void testCaseVide() {
        Damier damier = new Damier();
        damier.initialiser();

        // Vérifie une case non-vide
        assertFalse(damier.verifierSiCaseEstVide(38));

        // Vérifie une case vide
        assertTrue(damier.verifierSiCaseEstVide(28));
    }

    /**
     * Teste si un pion en case d'arrivée est ennemi dans le but d'une prise.
     */
    public void testSiPionEstEnnemi() {
        Damier damier = new Damier();
        damier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(22, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        // Vérifie un pion ennemi en case d'arrivée
        assertTrue(damier.verifierSiPionEnnemi(22, Damier.Direction.HautGauche));

        // Vérifie un pion allié en case d'arrivée
        assertFalse(damier.verifierSiPionEnnemi(24, Damier.Direction.HautGauche));

        // Vérifie un pion qui veut aller en case vide
        assertFalse(damier.verifierSiPionEnnemi(39, Damier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale bas gauche.
     */
    public void testPriseBasGauche() {
        Damier damier = new Damier();

        // Prise valide row pair
        damier.ajouterPion(12, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(17, new Pion(Pion.Couleur.Blanc));

        assertEquals(21, damier.getCaseArrivePrise(12, Damier.Direction.BasGauche));

        // Prise valide row impair
        damier.ajouterPion(27, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(31, new Pion(Pion.Couleur.Blanc));

        assertEquals(36, damier.getCaseArrivePrise(27, Damier.Direction.BasGauche));

        // Prise invalide row pair
        damier.ajouterPion(31, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, damier.getCaseArrivePrise(31, Damier.Direction.BasGauche));

        // Prise invalide row impair
        damier.ajouterPion(26, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, damier.getCaseArrivePrise(26, Damier.Direction.BasGauche));
    }

    /**
     * Teste une prise en direction diagonale bas droite.
     */
    public void testPriseBasDroite() {
        Damier damier = new Damier();

        // Prise valide row pair
        damier.ajouterPion(33, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        assertEquals(44, damier.getCaseArrivePrise(33, Damier.Direction.BasDroite));

        // Prise valide row impair
        damier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));

        assertEquals(49, damier.getCaseArrivePrise(38, Damier.Direction.BasDroite));

        // Prise invalide row pair
        damier.ajouterPion(25, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, damier.getCaseArrivePrise(25, Damier.Direction.BasDroite));

        // Prise invalide row impair
        damier.ajouterPion(30, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(35, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, damier.getCaseArrivePrise(30, Damier.Direction.BasDroite));
    }

    /**
     * Teste une prise en direction diagonale haut gauche.
     */
    public void testPriseHautGauche() {
        Damier damier = new Damier();

        // Prise valide row pair
        damier.ajouterPion(42, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(37, new Pion(Pion.Couleur.Blanc));

        assertEquals(31, damier.getCaseArrivePrise(42, Damier.Direction.HautGauche));

        // Prise valide row impair
        damier.ajouterPion(47, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(41, new Pion(Pion.Couleur.Blanc));

        assertEquals(36, damier.getCaseArrivePrise(47, Damier.Direction.HautGauche));

        // Prise invalide row pair
        damier.ajouterPion(41, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, damier.getCaseArrivePrise(41, Damier.Direction.HautGauche));

        // Prise invalide row impair
        damier.ajouterPion(46, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, damier.getCaseArrivePrise(46, Damier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale haut droite.
     */
    public void testPriseHautDroite() {
        Damier damier = new Damier();

        // Prise valide row pair
        damier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(20, new Pion(Pion.Couleur.Blanc));

        assertEquals(15, damier.getCaseArrivePrise(24, Damier.Direction.HautDroite));

        // Prise valide row impair
        damier.ajouterPion(49, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(44, new Pion(Pion.Couleur.Blanc));

        assertEquals(40, damier.getCaseArrivePrise(49, Damier.Direction.HautDroite));

        // Prise invalide row pair
        damier.ajouterPion(35, new Pion(Pion.Couleur.Noir));

        assertEquals(-1, damier.getCaseArrivePrise(35, Damier.Direction.HautDroite));

        // Prise invalide row impair
        damier.ajouterPion(50, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(45, new Pion(Pion.Couleur.Blanc));

        assertEquals(-1, damier.getCaseArrivePrise(50, Damier.Direction.HautDroite));
    }

    /**
     * Teste si la case d'arrivée en prise sort du damier.
     */
    public void testPriseDepasseDamier() {
        Damier damier = new Damier();
        damier.ajouterPion(47, new Pion());
        damier.ajouterPion(3, new Pion());

        // Vérification plus que 50
        assertEquals(-1, damier.getCaseArrivePrise(47, Damier.Direction.BasDroite));
        assertEquals(-1, damier.getCaseArrivee(47, Damier.Direction.BasDroite));

        // Vérification moins que 1
        assertEquals(-1, damier.getCaseArrivee(3, Damier.Direction.HautGauche));
        assertEquals(-1, damier.getCaseArrivePrise(3, Damier.Direction.HautGauche));

    }

    /**
     * Teste la méthode changerTourJoueur.
     */
    public void testChangerTourJoueur() {
        Damier damier = new Damier();
        damier.initialiser();

        assertEquals(1, damier.getTourJoueur());

        damier.changerTourJoueur();

        assertEquals(2, damier.getTourJoueur());
    }

    /**
     * Teste la méthode testLogs.
     */
    public void testLogs() {
        Damier damier = new Damier();
        damier.initialiser();

        // Premier déplacement Blanc
        Pion pionDepartBlanc = damier.getPion(32);

        damier.deplacerPion(32, Damier.Direction.HautGauche);
        assertEquals(pionDepartBlanc, damier.getPion(27));
        assertNull(damier.getPion(32));

        // Premier déplacement Noir
        Pion pionDepartNoir = damier.getPion(16);

        damier.deplacerPion(16, Damier.Direction.BasDroite);
        assertEquals(pionDepartNoir, damier.getPion(21));
        assertNull(damier.getPion(16));

        // Deuxième déplacement Blanc
        assertTrue(damier.deplacerPion(27, Damier.Direction.HautGauche));
        assertEquals(pionDepartBlanc, damier.getPion(16));
        assertNull(damier.getPion(21));

        System.out.println(damier.getLogs());

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
        Damier damier = new Damier();
        damier.initialiser();
        Pion pionNoir = damier.getPion(16);

        assertFalse(damier.deplacerPion(16, Damier.Direction.BasDroite));
        assertEquals(pionNoir, damier.getPion(16));
        assertNull(damier.getPion(27));
    }

    /**
     * Teste si le pion que l'on tente de déplacer est en fait null.
     */
    public void testPionDeplaceNull() {
        Damier damier = new Damier();
        damier.initialiser();

        assertFalse(damier.deplacerPion(27, Damier.Direction.HautDroite));
    }

    /**
     * Teste une tentative de déplacement en reculons pour un pion standard.
     */
    public void testPionDeplacementReculons() {
        Damier damier = new Damier();


        System.out.println("Tour 1 : " + damier.getTourJoueur());
        damier.ajouterPion(32, new Pion(Pion.Couleur.Blanc));
        assertFalse(damier.deplacerPion(32, Damier.Direction.BasDroite));
        damier.changerTourJoueur();

        System.out.println("Tour 2 : " + damier.getTourJoueur());
        damier.ajouterPion(12, new Dame(Pion.Couleur.Noir));
        assertTrue(damier.deplacerDame(12, 8, Damier.Direction.HautDroite));
    }

    /**
     * Teste si un pion essais de se déplacer vers une case contenant un pion allié.
     */
    public void testPionAllieCaseArrive() {
        Damier damier = new Damier();
        damier.initialiser();

        assertTrue(damier.deplacerPion(35, Damier.Direction.HautGauche));

        assertTrue(damier.deplacerPion(16, Damier.Direction.BasDroite));

        assertFalse(damier.deplacerPion(34, Damier.Direction.HautDroite));
    }

    /**
     * Teste la prise si la case d'arrivée en prise est non-vide.
     */
    public void testCaseArriveePriseNonVide() {
        Damier damier = new Damier();
        damier.initialiser();

        // Noir
        assertTrue(damier.deplacerPion(34, Damier.Direction.HautGauche));
        // Blanc
        assertTrue(damier.deplacerPion(20, Damier.Direction.BasGauche));

        // Random noir
        assertTrue(damier.deplacerPion(31, Damier.Direction.HautGauche));

        assertEquals(2, damier.getTourJoueur());

        // Blanc
        assertTrue(damier.deplacerPion(17, Damier.Direction.BasDroite));

        // Noir random
        assertTrue(damier.deplacerPion(35, Damier.Direction.HautGauche));

        // Blanc move interdit car prise en bordure droite
        assertFalse(damier.deplacerPion(21, Damier.Direction.BasGauche));

        Damier damier2 = new Damier();

        damier2.ajouterPion(10, new Pion(Pion.Couleur.Blanc));
        damier2.ajouterPion(15, new Pion(Pion.Couleur.Noir));
        assertFalse(damier2.deplacerPion(10, Damier.Direction.BasDroite));
    }

    /**
     * Teste si un pion peut se transformer en dame s'il atteint l'extrémité opposé
     * du damier en fonction de sa couleur.
     */
    public void testPeutTransformerEnDame() {
        Damier damier = new Damier();

        // Condition valide pour la transformation
        damier.ajouterPion(2, new Pion(Pion.Couleur.Blanc));
        assertTrue(damier.peutTransformer(2));
        damier.ajouterPion(9, new Pion(Pion.Couleur.Blanc));
        damier.deplacerPion(9, Damier.Direction.HautDroite);
        assertTrue(damier.getPion(4) instanceof Dame);

        // Condition valide avec prise
        damier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));
        damier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        damier.deplacerPion(38, Damier.Direction.BasDroite);
        assertTrue(damier.getPion(49) instanceof Dame);

        // Condition invalide pour la transformation
        damier.ajouterPion(49, new Pion(Pion.Couleur.Blanc));
        assertFalse(damier.peutTransformer(49));
    }

    /**
     * Teste si un pion valide pour la transformation se transforme correctement en dame.
     */
    public void testTransformerEnDame() {
        Damier damier = new Damier();

        // Tranformation blanc
        damier.ajouterPion(8, new Pion(Pion.Couleur.Blanc));
        assertTrue(damier.deplacerPion(8, Damier.Direction.HautDroite));
        assertTrue(damier.getPion(3) instanceof Dame);

        // Tranformation noir
        damier.ajouterPion(43, new Pion(Pion.Couleur.Noir));
        assertTrue(damier.deplacerPion(43, Damier.Direction.BasDroite));
        assertTrue(damier.getPion(49) instanceof Dame);
    }

    /**
     * Teste la méthode trouverCasesDisponibles qui permet de trouver les cases disponibles
     * pour la dame.
     */
    public void testTrouverCasesDisponibles() {
        Damier damier = new Damier();
        damier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        damier.ajouterPion(33, new Pion(Pion.Couleur.Blanc));

        System.out.println("Case disponible pour la dame : \n ");
        int compteur = 0;
        StringBuilder sb = new StringBuilder();

        for (boolean estDisponible : damier.trouverCasesDisponibles(17)) {
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
        Damier damier = new Damier();

        damier.ajouterPion(17, new Dame(Pion.Couleur.Blanc));
        damier.ajouterPion(33, new Pion(Pion.Couleur.Noir));

        assertTrue(damier.getPion(17) instanceof Dame);

        assertTrue(damier.deplacerDame(17, 39, Damier.Direction.BasDroite));

        assertNull(damier.getPion(17));
        assertTrue(damier.getPion(39) instanceof Dame);

        assertEquals(44, damier.getCaseAvantPrise(39, Damier.Direction.BasDroite));

        // Ajout et déplacement d'un second pion noir
        damier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        assertTrue(damier.deplacerPion(38, Damier.Direction.BasDroite));

        // Deuxième prise de la dame
        assertTrue(damier.deplacerDame(39, 48, Damier.Direction.BasGauche));
        assertTrue(damier.getPion(48) instanceof Dame);

        assertNull(damier.getPion(43));

        damier.imprimerLogs();
    }

    /**
     * Teste de la méthode getCaseAvantPrise.
     */
    public void testGetCaseAvantPrise() {
        Damier damier = new Damier();

        assertEquals(44, damier.getCaseAvantPrise(39, Damier.Direction.BasDroite));


        // BAS GAUCHE

        // Test pour bordure gauche déplacement BasGauche/HautGauche
        assertEquals(-1, damier.getCaseAvantPrise(16, Damier.Direction.BasGauche));

        // Test row impair non bordure
        assertEquals(21, damier.getCaseAvantPrise(17, Damier.Direction.BasGauche));

        // Test row pair BasGauche
        assertEquals(6, damier.getCaseAvantPrise(1, Damier.Direction.BasGauche));


         // BAS DROITE

        // Test pour bordure droite déplacement BasDroite
        assertEquals(-1, damier.getCaseAvantPrise(5, Damier.Direction.BasDroite));

        // Test row pair non bordure
        assertEquals(15, damier.getCaseAvantPrise(10, Damier.Direction.BasDroite));

        // Test row pair BasGauche
        assertEquals(10, damier.getCaseAvantPrise(4, Damier.Direction.BasDroite));

        // HAUT GAUCHE

        // Test pour bordure gauche déplacement HautGauche
        assertEquals(-1, damier.getCaseAvantPrise(16, Damier.Direction.HautGauche));

        // Test row pair non bordure HautGauche
        assertEquals(6, damier.getCaseAvantPrise(11, Damier.Direction.HautGauche));

        // Test row impair HautGauche bordure
        assertEquals(1, damier.getCaseAvantPrise(7, Damier.Direction.HautGauche));

        // HAUT DROITE

        // Test pour bordure droite déplacement HautDroite
        assertEquals(-1, damier.getCaseAvantPrise(15, Damier.Direction.HautDroite));

        // Test row pair non bordure HautDroite
        assertEquals(5, damier.getCaseAvantPrise(10, Damier.Direction.HautDroite));

        // Test row pair HautDroite bordure
        assertEquals(10, damier.getCaseAvantPrise(14, Damier.Direction.HautDroite));
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
        Damier damier = new Damier();

        // Premier déplacement blanc
        damier.ajouterPion(26, new Pion(Pion.Couleur.Blanc));
        assertTrue(damier.deplacerPion(26, Damier.Direction.HautDroite));

        // Premier déplacement noir (est une prise du pion blanc)
        damier.ajouterPion(16, new Pion(Pion.Couleur.Noir));
        assertTrue(damier.deplacerPion(16, Damier.Direction.BasDroite));

        damier.retourArriere();
        // damier.imprimerLogs();
    }

    /**
     * Teste le getter de pions restants sur le jeu en fonction de leur couleur.
     */
    public void testGetNombrePionsCouleur() {
        Damier damier = new Damier();
        damier.initialiser();

        // Vérification afin d'enlever des pions :
        assertEquals(20, damier.getNombrePionsCouleur(Pion.Couleur.Noir));
        assertEquals(20, damier.getNombrePionsCouleur(Pion.Couleur.Blanc));

        // Déplacement pour manger au moins un pion
        damier.deplacerPion(31, Damier.Direction.HautDroite);
        damier.deplacerPion(16, Damier.Direction.BasDroite);
        damier.deplacerPion(27, Damier.Direction.HautGauche);

        assertEquals(19, damier.getNombrePionsCouleur(Pion.Couleur.Noir));
    }
}