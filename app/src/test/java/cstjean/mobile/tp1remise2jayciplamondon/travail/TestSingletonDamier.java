package cstjean.mobile.tp1remise2jayciplamondon.travail;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 * La classe de test TestDamier contient des méthodes de test pour la classe Damier.
 */
public class TestSingletonDamier {

    /**
     * Teste la création d'un damier.
     */
    @Test
    public void testCreer() {
        SingletonDamier singletonDamierTest = new SingletonDamier();
        Map<Integer, Pion> pionMap = singletonDamierTest.getPionMap();

        for (int i = 1; i <= 50; i++) {
            if (i <= 20) {
                Assert.assertNotNull(pionMap.get(i));
            } else if (i >= 31) {
                Assert.assertNotNull(pionMap.get(i));
            } else {
                Assert.assertNull(pionMap.get(i));
            }
        }
    }

    /**
     * Teste l'initialisation d'un damier.
     */
    @Test
    public void testInitialiser() {
        SingletonDamier singletonDamierTestInitialiser = new SingletonDamier();
        singletonDamierTestInitialiser.initialiser();

        int nbDePion = 0;

        for (Pion pion : singletonDamierTestInitialiser.getPionMap().values()) {
            if (pion != null) {
                nbDePion++;
            }
        }
        Assert.assertEquals(40, nbDePion);
    }

    /**
     * Teste l'accesseur d'instance du singletonDamier.
     */
    @Test
    public void testGetInstance() {
        // Appel de getInstance pour obtenir la première instance
        SingletonDamier instance1 = SingletonDamier.getInstance();

        // Appel de getInstance pour obtenir une deuxième instance
        SingletonDamier instance2 = SingletonDamier.getInstance();

        // Vérification qu'il s'agit de la même instance
        Assert.assertSame(instance1, instance2);
    }

    /**
     * Teste l'accesseur de la map de pion.
     */
    @Test
    public void testGetPionMap() {
        SingletonDamier instance = SingletonDamier.getInstance();

        // Obtenir la pionMap depuis l'instance
        Map<Integer, Pion> pionMap = instance.getPionMap();

        // Vérifier que la pionMap n'est pas null
        Assert.assertNotNull(pionMap);
    }

    /**
     * Teste l'accesseur du tourJoueur.
     */
    @Test
    public void testGetTourJoueur() {
        SingletonDamier singletonDamier = SingletonDamier.getInstance();

        // Obtenir la valeur du tour du joueur depuis l'instance
        int tourJoueur = singletonDamier.getTourJoueur();

        Assert.assertEquals(1, tourJoueur);
    }

    /**
     * Teste l'ajout de pions au damier.
     */
    @Test
    public void testAjouterPion() {
        SingletonDamier singletonDamierTestAjoutPion = new SingletonDamier(true);

        // Création du pion blanc
        Pion pionTestBlanc = new Pion(Pion.Couleur.Blanc);

        // Ajout du pion blanc au damier test
        singletonDamierTestAjoutPion.ajouterPion(4, pionTestBlanc);

        Assert.assertEquals(1, singletonDamierTestAjoutPion.getNombrePions());

        // Création du pion noir
        Pion pionTestNoir = new Pion(Pion.Couleur.Noir);

        // Ajout du pion noir au damier test
        singletonDamierTestAjoutPion.ajouterPion(5, pionTestNoir);

        Assert.assertEquals(2, singletonDamierTestAjoutPion.getNombrePions());

        Assert.assertEquals(pionTestBlanc, singletonDamierTestAjoutPion.getPion(4));
    }

    /**
     * Teste le tour du joueur.
     */
    @Test
    public void testTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        Assert.assertTrue(singletonDamier.joueurPeutJouer(31));
        Assert.assertFalse(singletonDamier.joueurPeutJouer(16));

        // Déplacement == on change de tour de joueur
        singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautGauche);

        Assert.assertFalse(singletonDamier.joueurPeutJouer(26));
        Assert.assertTrue(singletonDamier.joueurPeutJouer(16));
    }

    /**
     * Teste l'obtention d'un pion du damier.
     */
    @Test
    public void testGetPion() {
        SingletonDamier singletonDamierTestGetPion = new SingletonDamier();
        Pion pionTestAcces = new Pion(Pion.Couleur.Noir);

        singletonDamierTestGetPion.ajouterPion(38, pionTestAcces);
        Assert.assertEquals(pionTestAcces, singletonDamierTestGetPion.getPion(38));
    }

    /**
     * Teste l'obtention de la colonne d'un pion.
     */
    @Test
    public void testGetCol() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair
        Assert.assertEquals(singletonDamier.getCol(1), 0);
        Assert.assertEquals(singletonDamier.getCol(2), 1);
        Assert.assertEquals(singletonDamier.getCol(3), 2);
        Assert.assertEquals(singletonDamier.getCol(4), 3);
        Assert.assertEquals(singletonDamier.getCol(5), 4);

        // Row Impair
        Assert.assertEquals(singletonDamier.getCol(46), 0);
        Assert.assertEquals(singletonDamier.getCol(47), 1);
        Assert.assertEquals(singletonDamier.getCol(48), 2);
        Assert.assertEquals(singletonDamier.getCol(49), 3);
        Assert.assertEquals(singletonDamier.getCol(50), 4);
    }

    /**
     * Teste l'obtention de la rangée d'un pion.
     */
    @Test
    public void testGetRow() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair même rangée
        Assert.assertEquals(0, singletonDamier.getRow(1));
        Assert.assertEquals(0, singletonDamier.getRow(2));
        Assert.assertEquals(0, singletonDamier.getRow(3));
        Assert.assertEquals(0, singletonDamier.getRow(4));
        Assert.assertEquals(0, singletonDamier.getRow(5));

        // Row impair même rangée
        Assert.assertEquals(1, singletonDamier.getRow(6));
        Assert.assertEquals(1, singletonDamier.getRow(7));
        Assert.assertEquals(1, singletonDamier.getRow(8));
        Assert.assertEquals(1, singletonDamier.getRow(9));
        Assert.assertEquals(1, singletonDamier.getRow(10));

        // Rows pair toutes les rangées
        Assert.assertEquals(2, singletonDamier.getRow(11));
        Assert.assertEquals(4, singletonDamier.getRow(21));
        Assert.assertEquals(6, singletonDamier.getRow(31));
        Assert.assertEquals(8, singletonDamier.getRow(41));

        // Rows impair toutes les rangées
        Assert.assertEquals(1, singletonDamier.getRow(6));
        Assert.assertEquals(3, singletonDamier.getRow(16));
        Assert.assertEquals(5, singletonDamier.getRow(26));
        Assert.assertEquals(7, singletonDamier.getRow(36));
        Assert.assertEquals(9, singletonDamier.getRow(46));
    }

    /**
     * Teste si un pion se situe en bordure de damier.
     */
    @Test
    public void testEstBordure() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Row Pair Col 0 ou 4
        Assert.assertFalse(singletonDamier.estBordure(1));
        Assert.assertTrue(singletonDamier.estBordure(5));

        // Row Impair Col 0 ou 4
        Assert.assertTrue(singletonDamier.estBordure(6));
        Assert.assertFalse(singletonDamier.estBordure(10));

        // Random
        Assert.assertTrue(singletonDamier.estBordure(16));
    }

    /**
     * Teste un déplacement simple de pion dans les quatres directions ainsi
     * qu'en rangée pair ou impair.
     */
    @Test
    public void testDeplacementSimple() {
        SingletonDamier singletonDamier = new SingletonDamier();

        singletonDamier.initialiser();

        // Test Row Pair -> Impair
        Assert.assertEquals(6, singletonDamier.getCaseArrivee(1, SingletonDamier.Direction.BasGauche));
        Assert.assertEquals(37, singletonDamier.getCaseArrivee(31, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(36, singletonDamier.getCaseArrivee(41, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(8, singletonDamier.getCaseArrivee(12, SingletonDamier.Direction.HautDroite));

        // Test Row Impair -> Pair
        Assert.assertEquals(11, singletonDamier.getCaseArrivee(7, SingletonDamier.Direction.BasGauche));
        Assert.assertEquals(42, singletonDamier.getCaseArrivee(37, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(41, singletonDamier.getCaseArrivee(47, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(4, singletonDamier.getCaseArrivee(9, SingletonDamier.Direction.HautDroite));
    }

    // Vérifie les déplacement, impossible si pion veut aller vers l'extérieur si en bordure

    /**
     * Teste les déplacement impossible si un pion veut aller vers l'extérieur du damier.
     */
    @Test
    public void testDeplacementVersBordure() {
        SingletonDamier singletonDamier = new SingletonDamier();

        singletonDamier.ajouterPion(15, new Pion());
        singletonDamier.ajouterPion(16, new Pion());

        singletonDamier.ajouterPion(1, new Pion());
        singletonDamier.ajouterPion(20, new Pion());

        // Déplacement invalide bas vers bordure
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(15, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(16, SingletonDamier.Direction.BasGauche));

        // Déplacement valide bas vers bordure
        Assert.assertEquals(25, singletonDamier.getCaseArrivee(20, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(6, singletonDamier.getCaseArrivee(1, SingletonDamier.Direction.BasGauche));

        singletonDamier.initialiser();

        singletonDamier.ajouterPion(35, new Pion());
        singletonDamier.ajouterPion(36, new Pion());

        singletonDamier.ajouterPion(31, new Pion());
        singletonDamier.ajouterPion(50, new Pion());

        // Déplacement invalide haut vers bordure
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(35, SingletonDamier.Direction.HautDroite));
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(36, SingletonDamier.Direction.HautGauche));

        // Déplacement valide haut vers bordure
        Assert.assertEquals(45, singletonDamier.getCaseArrivee(50, SingletonDamier.Direction.HautDroite));
        Assert.assertEquals(26, singletonDamier.getCaseArrivee(31, SingletonDamier.Direction.HautGauche));

    }

    /**
     * Teste la méthode vérifierSiCaseEstVide.
     */
    @Test
    public void testCaseVide() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Vérifie une case non-vide
        Assert.assertFalse(singletonDamier.verifierSiCaseEstVide(38));

        // Vérifie une case vide
        Assert.assertTrue(singletonDamier.verifierSiCaseEstVide(28));
    }

    /**
     * Teste si un pion en case d'arrivée est ennemi dans le but d'une prise.
     */
    @Test
    public void testSiPionEstEnnemi() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(19, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(22, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        // Vérifie un pion ennemi en case d'arrivée
        Assert.assertTrue(singletonDamier.verifierSiPionEnnemi(22, SingletonDamier.Direction.HautGauche));

        // Vérifie un pion allié en case d'arrivée
        Assert.assertFalse(singletonDamier.verifierSiPionEnnemi(24, SingletonDamier.Direction.HautGauche));

        // Vérifie un pion qui veut aller en case vide
        Assert.assertFalse(singletonDamier.verifierSiPionEnnemi(39, SingletonDamier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale bas gauche.
     */
    @Test
    public void testPriseBasGauche() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(12, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(21, singletonDamier.getCaseArrivePrise(12, SingletonDamier.Direction.BasGauche));

        // Prise valide row impair
        singletonDamier.ajouterPion(27, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(31, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(36, singletonDamier.getCaseArrivePrise(27, SingletonDamier.Direction.BasGauche));

        // Prise invalide row pair
        singletonDamier.ajouterPion(31, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(31, SingletonDamier.Direction.BasGauche));

        // Prise invalide row impair
        singletonDamier.ajouterPion(26, new Pion(Pion.Couleur.Noir));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(26, SingletonDamier.Direction.BasGauche));
    }

    /**
     * Teste une prise en direction diagonale bas droite.
     */
    @Test
    public void testPriseBasDroite() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(39, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(44, singletonDamier.getCaseArrivePrise(33, SingletonDamier.Direction.BasDroite));

        // Prise valide row impair
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(49, singletonDamier.getCaseArrivePrise(38, SingletonDamier.Direction.BasDroite));

        // Prise invalide row pair
        singletonDamier.ajouterPion(25, new Pion(Pion.Couleur.Noir));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(25, SingletonDamier.Direction.BasDroite));

        // Prise invalide row impair
        singletonDamier.ajouterPion(30, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(35, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(30, SingletonDamier.Direction.BasDroite));
    }

    /**
     * Teste une prise en direction diagonale haut gauche.
     */
    @Test
    public void testPriseHautGauche() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(42, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(37, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(31, singletonDamier.getCaseArrivePrise(42, SingletonDamier.Direction.HautGauche));

        // Prise valide row impair
        singletonDamier.ajouterPion(47, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(41, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(36, singletonDamier.getCaseArrivePrise(47, SingletonDamier.Direction.HautGauche));

        // Prise invalide row pair
        singletonDamier.ajouterPion(41, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(36, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(41, SingletonDamier.Direction.HautGauche));

        // Prise invalide row impair
        singletonDamier.ajouterPion(46, new Pion(Pion.Couleur.Noir));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(46, SingletonDamier.Direction.HautGauche));
    }

    /**
     * Teste une prise en direction diagonale haut droite.
     */
    @Test
    public void testPriseHautDroite() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Prise valide row pair
        singletonDamier.ajouterPion(24, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(20, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(15, singletonDamier.getCaseArrivePrise(24, SingletonDamier.Direction.HautDroite));

        // Prise valide row impair
        singletonDamier.ajouterPion(49, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(44, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(40, singletonDamier.getCaseArrivePrise(49, SingletonDamier.Direction.HautDroite));

        // Prise invalide row pair
        singletonDamier.ajouterPion(35, new Pion(Pion.Couleur.Noir));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(35, SingletonDamier.Direction.HautDroite));

        // Prise invalide row impair
        singletonDamier.ajouterPion(50, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(45, new Pion(Pion.Couleur.Blanc));

        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(50, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste si la case d'arrivée en prise sort du damier.
     */
    @Test
    public void testPriseDepasseDamier() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        singletonDamier.ajouterPion(47, new Pion());
        singletonDamier.ajouterPion(3, new Pion());

        // Vérification plus que 50
        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(47, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(47, SingletonDamier.Direction.BasDroite));

        // Vérification moins que 1
        Assert.assertEquals(-1, singletonDamier.getCaseArrivee(3, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(-1, singletonDamier.getCaseArrivePrise(3, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(3, -1, SingletonDamier.Direction.HautGauche));

    }

    /**
     * Teste la méthode changerTourJoueur.
     */
    @Test
    public void testChangerTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        Assert.assertEquals(1, singletonDamier.getTourJoueur());

        singletonDamier.changerTourJoueur();

        Assert.assertEquals(2, singletonDamier.getTourJoueur());
    }

    /**
     * Teste la méthode testLogs.
     */
    @Test
    public void testLogs() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Premier déplacement Blanc
        Pion pionDepartBlanc = singletonDamier.getPion(32);

        singletonDamier.deplacerPion(32, SingletonDamier.Direction.HautGauche);
        Assert.assertEquals(pionDepartBlanc, singletonDamier.getPion(27));
        Assert.assertNull(singletonDamier.getPion(32));

        // Premier déplacement Noir
        Pion pionDepartNoir = singletonDamier.getPion(16);

        singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite);
        Assert.assertEquals(pionDepartNoir, singletonDamier.getPion(21));
        Assert.assertNull(singletonDamier.getPion(16));

        // Deuxième déplacement Blanc
        Assert.assertTrue(singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(pionDepartBlanc, singletonDamier.getPion(16));
        Assert.assertNull(singletonDamier.getPion(21));

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
     * Teste si getLogsList renvoie correctement la liste des logs.
     */
    @Test
    public void testGetLogsList() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        // Obtenir la liste des logs depuis l'instance
        ArrayList<String> logs = singletonDamier.getLogsList();

        // Vérifier que la liste n'est pas null
        Assert.assertNotNull(logs);
    }

    /**
     * Teste lorsqu'un joueur dont ce n'est pas son tour essais de jouer.
     */
    @Test
    public void testMauvaisTourJoueur() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();
        Pion pionNoir = singletonDamier.getPion(16);

        Assert.assertFalse(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));
        Assert.assertEquals(pionNoir, singletonDamier.getPion(16));
        Assert.assertNull(singletonDamier.getPion(27));
    }

    /**
     * Teste si le pion que l'on tente de déplacer est en fait null.
     */
    @Test
    public void testPionDeplaceNull() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        Assert.assertFalse(singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste une tentative de déplacement en reculons pour un pion standard.
     */
    @Test
    public void testPionDeplacementReculons() {
        SingletonDamier singletonDamier = new SingletonDamier(true);

        System.out.println("Tour 1 : " + singletonDamier.getTourJoueur());
        singletonDamier.ajouterPion(32, new Pion(Pion.Couleur.Blanc));
        Assert.assertFalse(singletonDamier.deplacerPion(32, SingletonDamier.Direction.BasDroite));
        singletonDamier.changerTourJoueur();

        System.out.println("Tour 2 : " + singletonDamier.getTourJoueur());
        singletonDamier.ajouterPion(12, new Dame(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerDame(12, 8, SingletonDamier.Direction.HautDroite));
    }

    /**
     * Teste si un pion essais de se déplacer vers une case contenant un pion allié.
     */
    @Test
    public void testPionAllieCaseArrive() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        Assert.assertTrue(singletonDamier.deplacerPion(35, SingletonDamier.Direction.HautGauche));

        Assert.assertTrue(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));

        Assert.assertFalse(singletonDamier.deplacerPion(34, SingletonDamier.Direction.HautDroite));

        singletonDamier.resetBoard();
        singletonDamier.changerTourJoueur();

        singletonDamier.ajouterPion(16, new Pion(Pion.Couleur.Noir));
        Assert.assertFalse(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasGauche));

        singletonDamier.ajouterPion(2, new Pion(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerPion(2, SingletonDamier.Direction.BasDroite));
    }

    /**
     * Teste la prise si la case d'arrivée en prise est non-vide.
     */
    @Test
    public void testCaseArriveePriseNonVide() {
        SingletonDamier singletonDamier = new SingletonDamier();
        singletonDamier.initialiser();

        // Noir
        Assert.assertTrue(singletonDamier.deplacerPion(34, SingletonDamier.Direction.HautGauche));
        // Blanc
        Assert.assertTrue(singletonDamier.deplacerPion(20, SingletonDamier.Direction.BasGauche));

        // Random noir
        Assert.assertTrue(singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautGauche));

        Assert.assertEquals(2, singletonDamier.getTourJoueur());

        // Blanc
        Assert.assertTrue(singletonDamier.deplacerPion(17, SingletonDamier.Direction.BasDroite));

        // Noir random
        Assert.assertTrue(singletonDamier.deplacerPion(35, SingletonDamier.Direction.HautGauche));

        // Blanc move interdit car prise en bordure droite
        Assert.assertFalse(singletonDamier.deplacerPion(21, SingletonDamier.Direction.BasGauche));

        SingletonDamier singletonDamier2 = new SingletonDamier();

        singletonDamier2.ajouterPion(10, new Pion(Pion.Couleur.Blanc));
        singletonDamier2.ajouterPion(15, new Pion(Pion.Couleur.Noir));
        Assert.assertFalse(singletonDamier2.deplacerPion(10, SingletonDamier.Direction.BasDroite));
    }

    /**
     * Teste si un pion peut se transformer en dame s'il atteint l'extrémité opposé
     * du damier en fonction de sa couleur.
     */
    @Test
    public void testPeutTransformerEnDame() {
        SingletonDamier singletonDamier = new SingletonDamier(true);

        // Condition valide pour la transformation
        singletonDamier.ajouterPion(2, new Pion(Pion.Couleur.Blanc));
        Assert.assertTrue(singletonDamier.peutTransformer(2));
        singletonDamier.ajouterPion(9, new Pion(Pion.Couleur.Blanc));
        singletonDamier.deplacerPion(9, SingletonDamier.Direction.HautDroite);
        Assert.assertTrue(singletonDamier.getPion(4) instanceof Dame);

        // Condition valide avec prise
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        singletonDamier.deplacerPion(38, SingletonDamier.Direction.BasDroite);
        Assert.assertTrue(singletonDamier.getPion(49) instanceof Dame);

        // Condition invalide pour la transformation
        singletonDamier.ajouterPion(49, new Pion(Pion.Couleur.Blanc));
        Assert.assertFalse(singletonDamier.peutTransformer(49));
    }

    /**
     * Teste si un pion valide pour la transformation se transforme correctement en dame.
     */
    @Test
    public void testTransformerEnDame() {
        SingletonDamier singletonDamier = new SingletonDamier(true);

        // Tranformation blanc
        singletonDamier.ajouterPion(8, new Pion(Pion.Couleur.Blanc));
        Assert.assertTrue(singletonDamier.deplacerPion(8, SingletonDamier.Direction.HautDroite));
        Assert.assertTrue(singletonDamier.getPion(3) instanceof Dame);

        // Tranformation noir
        singletonDamier.ajouterPion(43, new Pion(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerPion(43, SingletonDamier.Direction.BasDroite));
        Assert.assertTrue(singletonDamier.getPion(49) instanceof Dame);

        // Transformation prise blanche
        singletonDamier.ajouterPion(11, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(7, new Pion(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerPion(11, SingletonDamier.Direction.HautDroite));
        Assert.assertTrue(singletonDamier.getPion(2) instanceof Dame);

        // Transformation prise noire
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(42, new Pion(Pion.Couleur.Blanc));
        Assert.assertTrue(singletonDamier.deplacerPion(38, SingletonDamier.Direction.BasGauche));
        Assert.assertTrue(singletonDamier.getPion(47) instanceof Dame);
    }

    /**
     * Teste la méthode caseDisponibleDame qui permet de trouver les cases disponibles
     * pour la dame.
     */
    @Test
    public void testTrouverCasesDisponiblesDame() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        singletonDamier.ajouterPion(17, new Dame(Pion.Couleur.Noir));
        singletonDamier.ajouterPion(33, new Dame(Pion.Couleur.Blanc));

        boolean[] expectedPositions = new boolean[50];

        // Mark the positions as true in the expected array
        int[] positionsTrue = {3, 6, 8, 11, 12, 21, 22, 26, 28, 39};
        for (int position : positionsTrue) {
            expectedPositions[position - 1] = true;
        }

        boolean[] actualPositions = singletonDamier.caseDisponibleDame(17);

        // Compare the content of the arrays using assertArrayEquals
        assertArrayEquals(expectedPositions, actualPositions);
    }

    /**
     * Teste la méthode trouverCasesDisponiblePion qui permet de trouver les cases disponibles
     * pour le pion.
     */
    @Test
    public void testTrouverCaseDisponiblePion() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        singletonDamier.ajouterPion(17, new Pion(Pion.Couleur.Noir));

        boolean[] expectedPositions = new boolean[50];

        // Mark the positions as true in the expected array
        int[] positionsTrue17 = {21, 22};
        for (int position : positionsTrue17) {
            expectedPositions[position - 1] = true;
        }

        boolean[] actualPositions17 = singletonDamier.caseDisponiblePion(17);

        // Compare the content of the arrays using assertArrayEquals
        assertArrayEquals(expectedPositions, actualPositions17);

        // Deuxième test
        singletonDamier.resetBoard();
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(38, new Pion(Pion.Couleur.Noir));

        expectedPositions = new boolean[50];

        int[] positionsTrue33 = {28, 29, 42};
        for (int position : positionsTrue33) {
            expectedPositions[position - 1] = true;
        }
        boolean[] actualPositions33 = singletonDamier.caseDisponiblePion(33);

        assertArrayEquals(expectedPositions, actualPositions33);
    }

    /**
     * Teste le déplacement normal d'une dame.
     */
    @Test
    public void testDeplacerDame() {
        SingletonDamier singletonDamier = new SingletonDamier(true);

        singletonDamier.ajouterPion(17, new Dame(Pion.Couleur.Blanc));
        singletonDamier.ajouterPion(33, new Pion(Pion.Couleur.Noir));

        Assert.assertTrue(singletonDamier.getPion(17) instanceof Dame);

        Assert.assertTrue(singletonDamier.deplacerDame(17, 39,
                SingletonDamier.Direction.BasDroite));

        Assert.assertNull(singletonDamier.getPion(17));
        Assert.assertTrue(singletonDamier.getPion(39) instanceof Dame);

        int positionArrivePrise = singletonDamier.getCaseArrivePrise(39,
                SingletonDamier.Direction.BasDroite);
        Assert.assertEquals(44, singletonDamier.getCaseAvantPrise(39,
                positionArrivePrise, SingletonDamier.Direction.BasDroite));

        // Ajout et déplacement d'un second pion noir
        singletonDamier.ajouterPion(32, new Pion(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerPion(32, SingletonDamier.Direction.BasDroite));

        // Déplacement simple de la dame
        Assert.assertTrue(singletonDamier.deplacerDame(39, 30, SingletonDamier.Direction.HautDroite));

        Assert.assertTrue(singletonDamier.deplacerPion(38, SingletonDamier.Direction.BasDroite));

        // Deuxième prise de la dame
        Assert.assertTrue(singletonDamier.deplacerDame(30, 48, SingletonDamier.Direction.BasGauche));
        Assert.assertTrue(singletonDamier.getPion(48) instanceof Dame);

        // assertNull(singletonDamier.getPion(43));
    }

    /**
     * Teste de la méthode getCaseAvantPrise.
     */
    @Test
    public void testGetCaseAvantPrise() {
        SingletonDamier singletonDamier = new SingletonDamier(true);

        // BAS GAUCHE
        // PAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(16, -1, SingletonDamier.Direction.BasGauche));
        Assert.assertEquals(18, singletonDamier.getCaseAvantPrise(9, 22, SingletonDamier.Direction.BasGauche));
        // IMPAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(1, -1, SingletonDamier.Direction.BasGauche));


        // BAS DROITE
        // PAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(10, -1, SingletonDamier.Direction.BasDroite));
        // IMPAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(15, -1, SingletonDamier.Direction.BasDroite));


        // HAUT GAUCHE
        // PAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(16, -1, SingletonDamier.Direction.HautGauche));

        // IMPAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(15, -1, SingletonDamier.Direction.HautGauche));
        Assert.assertEquals(7, singletonDamier.getCaseAvantPrise(18, 1, SingletonDamier.Direction.HautGauche));
        // IMPAIR BORDURE
        Assert.assertEquals(10, singletonDamier.getCaseAvantPrise(15, 4, SingletonDamier.Direction.HautGauche));



        // HAUT DROITE
        // PAIR
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(10, -1, SingletonDamier.Direction.HautDroite));
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(10, -1, SingletonDamier.Direction.HautDroite));

        // IMPAIR
        Assert.assertEquals(14, singletonDamier.getCaseAvantPrise(19, 10, SingletonDamier.Direction.HautDroite));




        // BAS GAUCHE

        /*
        // Test pour bordure gauche déplacement BasGauche/HautGauche
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(16, -1, SingletonDamier.Direction.BasGauche));

        // Test row impair non bordure
        // assertEquals(21, singletonDamier.getCaseAvantPrise(17, 26, SingletonDamier.Direction.BasGauche));

        // Test row pair BasGauche
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(1, -1, SingletonDamier.Direction.BasGauche));

        // BAS DROITE

        Assert.assertEquals(44, singletonDamier.getCaseAvantPrise(39, 50, SingletonDamier.Direction.BasDroite));

        // Test pour bordure droite déplacement BasDroite
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(5, -1, SingletonDamier.Direction.BasDroite));

        // Test row pair non bordure
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(10, -1, SingletonDamier.Direction.BasDroite));

        // Test row pair BasGauche
        Assert.assertEquals(10, singletonDamier.getCaseAvantPrise(4, 15, SingletonDamier.Direction.BasDroite));

        // HAUT GAUCHE

        // Test pour bordure gauche déplacement HautGauche
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(16, -1, SingletonDamier.Direction.HautGauche));

        // Test row pair non bordure HautGauche
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(11, -1, SingletonDamier.Direction.HautGauche));

        // Test row impair HautGauche bordure
        // assertEquals(11, singletonDamier.getCaseAvantPrise(17, 6, SingletonDamier.Direction.HautGauche));

        // HAUT DROITE

        // Test pour bordure droite déplacement HautDroite
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(15, -1, SingletonDamier.Direction.HautDroite));

        // Test row pair non bordure HautDroite
        Assert.assertEquals(-1, singletonDamier.getCaseAvantPrise(10, -1, SingletonDamier.Direction.HautDroite));

        // Test row pair HautDroite bordure
        // assertEquals(10, singletonDamier.getCaseAvantPrise(14, 5, SingletonDamier.Direction.HautDroite));

         */
    }




    /**
     * Teste une condition de fin de partie lorsqu'il n'y a plus de pion ennemi.
     */
    @Test
    public void testFinDePartie() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        singletonDamier.changerTourJoueur();

        Pion pionNoir1 = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc1 = new Pion(Pion.Couleur.Blanc);

        Pion pionNoir2 = new Pion(Pion.Couleur.Noir);
        Pion pionBlanc2 = new Pion(Pion.Couleur.Blanc);

        singletonDamier.ajouterPion(21, pionNoir1);
        singletonDamier.ajouterPion(32, pionBlanc1);

        singletonDamier.ajouterPion(22, pionNoir2);
        singletonDamier.ajouterPion(33, pionBlanc2);

        singletonDamier.deplacerPion(21, SingletonDamier.Direction.BasDroite);
        singletonDamier.deplacerPion(33, SingletonDamier.Direction.HautGauche);

        Assert.assertEquals(singletonDamier.getPion(22), pionNoir2);
        Assert.assertEquals(singletonDamier.getPion(28), pionBlanc2);
        singletonDamier.deplacerPion(22, SingletonDamier.Direction.BasDroite);

        Assert.assertEquals(singletonDamier.getPion(33), pionNoir2);
        Assert.assertNull(singletonDamier.getPion(28));

        Assert.assertTrue(singletonDamier.deplacerPion(32, SingletonDamier.Direction.HautGauche));

        Assert.assertEquals(singletonDamier.getPion(21), pionBlanc1);
        Assert.assertNull(singletonDamier.getPion(32));

        singletonDamier.verifierSiGagnant();

        singletonDamier.ajouterPion(16, new Pion(Pion.Couleur.Noir));

        singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite);

        singletonDamier.verifierSiGagnant();
    }

    @Test
    public void testRetourEnArriere() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Premier déplacement blanc
        singletonDamier.ajouterPion(26, new Pion(Pion.Couleur.Blanc));
        Assert.assertTrue(singletonDamier.deplacerPion(26, SingletonDamier.Direction.HautDroite));

        // Premier déplacement noir (est une prise du pion blanc)
        singletonDamier.ajouterPion(16, new Pion(Pion.Couleur.Noir));
        Assert.assertTrue(singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite));

        singletonDamier.retourArriere();
        // damier.imprimerLogs();
    }

    /**
     * Teste le getter de nombre de pions.
     */
    @Test
    public void testGetNombrePions() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Avec initialisation normale
        Assert.assertEquals(40, singletonDamier.getNombrePions());

        singletonDamier.resetBoard();

        // Avec aucun pions
        Assert.assertEquals(0, singletonDamier.getNombrePions());

        // Avec 1 ou plusieurs pions
        singletonDamier.ajouterPion(32, new Pion(Pion.Couleur.Blanc));
        Assert.assertEquals(1, singletonDamier.getNombrePions());
    }

    /**
     * Teste le getter de pions restants sur le jeu en fonction de leur couleur.
     */
    @Test
    public void testGetNombrePionsCouleur() {
        SingletonDamier singletonDamier = new SingletonDamier();

        // Vérification afin d'enlever des pions :
        Assert.assertEquals(20, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Noir));
        Assert.assertEquals(20, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Blanc));

        // Déplacement pour manger au moins un pion
        singletonDamier.deplacerPion(31, SingletonDamier.Direction.HautDroite);
        singletonDamier.deplacerPion(16, SingletonDamier.Direction.BasDroite);
        singletonDamier.deplacerPion(27, SingletonDamier.Direction.HautGauche);

        Assert.assertEquals(19, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Noir));

        // Avec aucun pion
        singletonDamier.resetBoard();
        Assert.assertEquals(0, singletonDamier.getNombrePionsCouleur(Pion.Couleur.Blanc));
    }

    /**
     * Teste le calcul de la direction.
     */
    @Test
    public void testCalculerDirection() {
        SingletonDamier singletonDamier = new SingletonDamier(true);
        Assert.assertEquals(SingletonDamier.Direction.BasGauche, singletonDamier.calculerDirection(1, 2, 1, 1));
        Assert.assertEquals(SingletonDamier.Direction.BasDroite, singletonDamier.calculerDirection(1, 2, 1, 2));
        Assert.assertEquals(SingletonDamier.Direction.HautGauche, singletonDamier.calculerDirection(2, 1, 2, 1));
        Assert.assertEquals(SingletonDamier.Direction.HautDroite, singletonDamier.calculerDirection(2, 1, 2, 2));
    }
}