package cstjean.mobile.tp1remise2jayciplamondon.travail;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cstjean.mobile.tp1remise2jayciplamondon.travail.TestDame;
import cstjean.mobile.tp1remise2jayciplamondon.travail.TestPion;

/**
 * La classe TestComplet représente une suite de tests complète
 * qui inclut les tests de la classe TestDamier et de la classe TestPion.
 *
 * @author Jayci Plamondon
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestPion.class, TestDame.class})
public class TestComplet {
    // This class doesn't need any additional content when using JUnit 4 annotations
}
