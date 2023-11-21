package cstjean.mobile.tp1remise2jayciplamondon.travail;

import junit.framework.TestSuite;

/**
 * La classe TestComplet représente une suite de tests complète
 * qui inclut les tests de la classe TestDamier et de la classe TestPion.
 *
 * @author Jayci Plamondon
 */
public class TestComplet {

    /**
     * Crée et retourne une suite de tests qui inclut les tests
     * de la classe TestDamier, TestDame et de la classe TestPion.
     *
     * @return Une suite de tests complète.
     */
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(TestDamier.class);
        suite.addTestSuite(TestPion.class);
        suite.addTestSuite(TestDame.class);
        return suite;
    }
}