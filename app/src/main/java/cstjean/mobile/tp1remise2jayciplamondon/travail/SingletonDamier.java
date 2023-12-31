package cstjean.mobile.tp1remise2jayciplamondon.travail;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe Damier représente un damier de jeu pour un jeu de dames.
 *
 * @author Jayci Plamondon
 */
public class SingletonDamier {
    /**
     * Représente l'instance unique de la classe SingletonDamier.
     */
    private static SingletonDamier instance = null;

    /**
     * Tableau représentant les pions du damier. Chaque élément correspond à une position du damier.
     */
    private final Map<Integer, Pion> pionMap = new HashMap<>();

    /**
     * Une Map associant des entiers (positions) à des pions représentant les pions
     * qui ont été mangés ou pris lors du jeu. La clé de la map est la position où le pion
     * a été mangé, et la valeur associée est le pion mangé.
     */
    private final List<PionMange> pionMangeList = new ArrayList<>();

    /**
     * Indique le tour du joueur en cours. 1 pour le joueur blanc, 2 pour le joueur noir.
     */
    private int tourJoueur;

    /**
     * Liste contenant les logs des déplacements effectués pendant la partie.
     */
    private final ArrayList<String> logsList = new ArrayList<>();

    /**
     * Constructeur de la classe Damier.
     * Initialise le damier et les pions.
     */
    public SingletonDamier() {
        initialiser();
    }

    /**
     * Deuxième constructeur de la classe Damier.
     * Sert aux classes de test.
     * N'initialise pas le damier.
     *
     * @param estTest Si l'instance du singletonDamier est en mode test.
     */
    public SingletonDamier(boolean estTest) {
        if (estTest) {
            resetBoard();
            resetLogs();
            tourJoueur = 1;
        }
    }

    /**
     * Obtenez l'instance unique de la classe SingletonDamier.
     *
     * @return L'instance unique de la classe SingletonDamier.
     */
    public static SingletonDamier getInstance() {
        if (instance == null) {
            instance = new SingletonDamier();
        }
        return instance;
    }

    /**
     * Obtient la Map contenant les pions.
     *
     * @return La Map de pions, où la clé est l'index de l'emplacement et la valeur est le pion.
     */
    public Map<Integer, Pion> getPionMap() {
        return pionMap;
    }

    /**
     * Ajoute un pion à une position spécifiée sur le damier.
     *
     * @param position La position où ajouter le pion.
     * @param pion     Le pion à ajouter.
     */
    public void ajouterPion(int position, Pion pion) {
        pionMap.put(position, pion);
    }

    /**
     * Récupère le pion à une position spécifiée sur le damier.
     *
     * @param position La position du pion.
     * @return Le pion à la position spécifiée.
     */
    public Pion getPion(int position) {
        return pionMap.get(position);
    }

    /**
     * Récupère la ligne (rangée) d'une position donnée sur le damier.
     *
     * @param position La position sur le damier.
     * @return Le numéro de la ligne.
     */
    public int getRow(int position) {
        return (position - 1) / 5;
    }

    /**
     * Récupère la colonne d'une position donnée sur le damier.
     *
     * @param position La position sur le damier.
     * @return Le numéro de la colonne.
     */
    public int getCol(int position) {
        return (position - 1) % 5;
    }

    /**
     * Récupère le numéro du tour du joueur en cours.
     *
     * @return Le numéro du tour du joueur.
     */
    public int getTourJoueur() {
        return tourJoueur;
    }

    /**
     * Récupère le nombre de pions d'une couleur précisée en paramètre restants sur le damier.
     *
     * @param couleur La couleur des pions restants.
     * @return Le nombre de pions noirs restants.
     */
    public int getNombrePionsCouleur(Pion.Couleur couleur) {
        int compteurPionsCouleur = 0;

        for (Pion pion : pionMap.values()) {
            if (pion != null && pion.getCouleur() == couleur) {
                ++compteurPionsCouleur;
            }
        }

        return compteurPionsCouleur;
    }

    /**
     * Get l'ArrayList logsList.
     *
     * @return La liste des logs.
     */
    public ArrayList<String> getLogsList() {
        return logsList;
    }

    /**
     * Annule le dernier coup enregistré dans la liste des logs et récupère les informations associées.
     * Cette méthode permet de revenir en arrière dans le jeu en annulant le dernier coup joué.
     */
    public void retourArriere() {
        if (!logsList.isEmpty()) {
            String dernierCoup = logsList.remove(logsList.size() - 1); // Retirez le dernier coup enregistré

            // Utilisation de regex pour ne retenir que les chiffres
            Pattern pattern = Pattern.compile("\\d+"); // Correspond à un ou plusieurs chiffres
            Matcher matcher = pattern.matcher(dernierCoup);

            ArrayList<Integer> chiffres = new ArrayList<>();

            while (matcher.find()) {
                int chiffre = Integer.parseInt(matcher.group());
                chiffres.add(chiffre);
            }
            // Position précèdente
            int positionPrecedente = chiffres.get(0);

            // Position actuelle
            int positionActuelle = chiffres.get(1);

            // Est une prise
            boolean estPrise = dernierCoup.contains("x");

            // Si une prise, on retrouve le pion mangée
            PionMange pionMangee;

            if (estPrise) {
                // Va chercher le dernier pion mangé dans l'array de pion mangés
                pionMangee = pionMangeList.get(pionMangeList.size() - 1);
                int positionPionMangee = pionMangee.getPosition();
                pionMap.put(positionPionMangee, pionMangee.getPion());
                pionMangeList.remove(pionMangeList.size() - 1);
            }

            // Faire le retour en arrière
            Pion pionActuel = getPion(positionActuelle);
            pionMap.remove(positionActuelle);
            pionMap.put(positionPrecedente, pionActuel);
            changerTourJoueur();
        }
    }

    /**
     * Vérifie si un joueur a gagné la partie et affiche un message approprié.
     *
     * @return La couleur du joueur gagnant.
    */
    public WinningPlayer verifierSiGagnant() {
        WinningPlayer winner = WinningPlayer.NONE;

        // On vérifie si l'un des deux joueurs n'a plus de pions
        if (getNombrePionsCouleur(Pion.Couleur.Blanc) == 0) {
            winner = WinningPlayer.BLACK;
        } else if (getNombrePionsCouleur(Pion.Couleur.Noir) == 0) {
            winner = WinningPlayer.WHITE;
        }

        return winner;
    }

    /**
     * Représente le joueur gagnant dans un jeu.
     */
    public enum WinningPlayer {
        /**
         * Joueur avec les pièces noires.
         */
        BLACK,

        /**
         * Joueur avec les pièces blanches.
         */
        WHITE,

        /**
         * Indique que le jeu n'est pas encore gagné.
         */
        NONE
    }

    /**
     * Renvoie le nombre total de pions présents sur le damier, qu'ils soient blancs ou noirs.
     *
     * @return Le nombre de pions présents sur le damier.
     */
    public int getNombrePions() {
        int compteurPions = 0;

        for (Pion pion : pionMap.values()) {
            if (pion != null) {
                ++compteurPions;
            }
        }
        return compteurPions;
    }

    /**
     * Initialise le damier en plaçant les pions noirs aux positions 1 à 20
     * et les pions blancs aux positions 31 à 50 conformément aux règles du jeu.
     * Le joueur jouant les pions blancs commence.
     */
    public void initialiser() {
        // Les blancs commencent (tourJoueur = 1)
        resetBoard();
        resetLogs();

        tourJoueur = 1;

        for (int i = 1; i <= 20; i++) {
            ajouterPion(i, new Pion(Pion.Couleur.Noir));
        }
        for (int i = 31; i <= 50; i++) {
            ajouterPion(i, new Pion(Pion.Couleur.Blanc));
        }
    }

    /**
     * Vide le tableau de tout ses pions.
     */
    public void resetBoard() {
        for (int i = 1; i <= 50; i++) {
            ajouterPion(i, null);
        }

        pionMangeList.clear();
    }

    /**
     * Vérifie si une case spécifiée sur le damier est une bordure.
     *
     * @param positionDepart La position à vérifier.
     * @return Vrai si c'est une bordure, sinon faux.
     */
    public boolean estBordure(int positionDepart) {
        int col = getCol(positionDepart);
        int row = getRow(positionDepart);

        return (col == 0 && row % 2 != 0) || (col == 4 && row % 2 == 0);
    }

    /**
     * Vérifie si une case spécifiée sur le damier est vide.
     *
     * @param positionArrive La position à vérifier.
     * @return Vrai si la case est vide, sinon faux.
     */
    public boolean verifierSiCaseEstVide(int positionArrive) {
        // Vérifie si la position est dans la plage valide (entre 1 et 50) et si la case est vide
        return positionArrive >= 1 && positionArrive <= 50 && pionMap.get(positionArrive) == null;
    }

    /**
     * Ajoute un enregistrement de log pour un déplacement sur le damier.
     *
     * @param positionDepart La position de départ du déplacement.
     * @param positionArrive La position d'arrivée du déplacement.
     * @param estPrise       Vrai si c'est un déplacement de prise, sinon faux.
     */
    public void ajouterLogs(int positionDepart, int positionArrive, boolean estPrise) {
        Pion.Couleur couleur = getPion(positionDepart).getCouleur();
        StringBuilder sb = new StringBuilder();

        if (estPrise) {
            if (couleur == Pion.Couleur.Noir) {
                sb.append("(")
                    .append(positionDepart)
                    .append("x")
                    .append(positionArrive)
                    .append(")");

                logsList.add(sb.toString());
            } else {
                sb.append(positionDepart)
                    .append("x")
                    .append(positionArrive);
                logsList.add(sb.toString());
            }
        } else {
            // False
            // logs déplacement prise
            if (couleur == Pion.Couleur.Noir) {
                sb.append("(")
                    .append(positionDepart)
                    .append("-")
                    .append(positionArrive)
                    .append(")");

                logsList.add(sb.toString());
            } else {
                sb.append(positionDepart)
                    .append("-")
                    .append(positionArrive);
                logsList.add(sb.toString());
            }
        }
    }

    /**
     * Reset les logs.
     */
    public void resetLogs() {
        logsList.clear();
    }

    /**
     * Récupère les logs des déplacements.
     *
     * @return Les logs des déplacements.
     */
    public String getLogs() {
        StringBuilder sb = new StringBuilder();

        int compteurDeplacement = 0;
        for (String log : logsList) {
            compteurDeplacement++;
            sb.append(compteurDeplacement).append(". ").append(log).append("\n");
        }
        return sb.toString();
    }

    /**
     * Vérifie si un pion à la position spécifiée n'est pas nul.
     *
     * @param positionDepart La position du pion à vérifier.
     * @return Vrai si le pion n'est pas nul, sinon faux.
     */
    public boolean verifierSiPionNonNull(int positionDepart) {
        return positionDepart >= 1 && positionDepart <= 50 && pionMap.get(positionDepart) != null;
    }

    /**
     * Vérifie si un pion ennemi peut être pris dans une direction spécifiée.
     *
     * @param positionDepart La position de départ du déplacement.
     * @param direction      La direction du déplacement.
     * @return Vrai si un pion ennemi peut être pris, sinon faux.
     */
    public boolean verifierSiPionEnnemi(int positionDepart, Direction direction) {
        boolean pionEstEnnemi;

        Pion pionDepart = getPion(positionDepart);
        Pion.Couleur couleurPionDepart = pionDepart.getCouleur();
        int positionArrive = getCaseArrivee(positionDepart, direction);

        if (!verifierSiCaseEstVide(positionArrive)) {
            Pion pionArrive = pionMap.get(positionArrive);
            pionEstEnnemi = pionArrive != null && pionArrive.getCouleur() != couleurPionDepart;
        } else {
            pionEstEnnemi = false;
        }
        return pionEstEnnemi;
    }

    /**
     * Vérifie si un pion peut se déplacer en reculant dans une direction spécifiée.
     *
     * @param pionDeplace Le pion à déplacer.
     * @param direction   La direction du déplacement.
     * @return Vrai si le pion essais de se déplacer en reculant, sinon faux.
     */
    public boolean verifierSiReculons(@NonNull Pion pionDeplace, Direction direction) {
        Pion.Couleur couleur = pionDeplace.getCouleur();
        return (couleur == Pion.Couleur.Noir &&
                    (direction == Direction.HautGauche || direction == Direction.HautDroite)) ||
                (couleur == Pion.Couleur.Blanc &&
                        (direction == Direction.BasGauche || direction == Direction.BasDroite));
    }

    /**
     * Récupère la position d'arrivée d'un déplacement dans une direction donnée.
     *
     * @param positionDepart La position de départ du déplacement.
     * @param direction      La direction du déplacement.
     * @return La position d'arrivée du déplacement.
     */
    public int getCaseArrivee(int positionDepart, Direction direction) {
        int rowDepart = getRow(positionDepart);
        int positionArrive = 0;

        switch (direction) {
            case BasGauche:
                if (rowDepart % 2 == 0) {
                    positionArrive = positionDepart + 5;
                } else {
                    // Déplacement impossible basGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        positionArrive = positionDepart + 4;
                    }
                }
                break;
            case BasDroite:
                if (rowDepart % 2 == 0) {
                    // Déplacement impossible basDroite si col 4 et row pair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        positionArrive = positionDepart + 6;
                    }
                } else {
                    positionArrive = positionDepart + 5;
                }
                break;
            case HautGauche:
                if (rowDepart % 2 == 0) {
                    positionArrive = positionDepart - 5;
                } else {
                    // Déplacement impossible hautGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        positionArrive = positionDepart - 6;
                    }
                }
                break;
            case HautDroite:
                if (rowDepart % 2 == 0) {
                    // Déplacement impossible hautDroite si col 4 et row pair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        positionArrive = positionDepart - 4;
                    }
                } else {
                    positionArrive = positionDepart - 5;
                }
                break;
            default:
        }

        if (positionArrive > 50 || positionArrive < 1) {
            positionArrive = -1;
        }

        return positionArrive;
    }

    /**
     * Calcule la position de la case où un pion prendra un autre pion dans
     * une direction donnée à partir d'une position de départ.
     *
     * @param positionDepart La position de départ du pion.
     * @param direction La direction dans laquelle le pion se déplace.
     * @return La position de la case où le pion prendra un autre pion, ou -1 si le déplacement est impossible.
     */
    public int getCaseArrivePrise(int positionDepart, Direction direction) {
        int rowDepart = getRow(positionDepart);
        int colDepart = getCol(positionDepart);
        int positionArrivePrise;

        switch (direction) {
            case BasGauche:
                if (rowDepart % 2 == 0) {
                    // Déplacement impossible basGauche si col 0 et row pair
                    if (colDepart == 0) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart + 9;
                    }
                } else {
                    // Déplacement impossible basGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart + 9;
                    }
                }
                break;
            case BasDroite:
                // Si Row Pair
                if (rowDepart % 2 == 0) {
                    // Déplacement impossible basDroite si col 4 et row pair
                    if (estBordure(positionDepart)) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart + 11;
                    }
                } /* Si row impair */ else {
                    if (colDepart == 4) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart + 11;
                    }
                }
                break;
            case HautGauche:
                if (rowDepart % 2 == 0) {
                    if (colDepart == 0) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart - 11;
                    }
                } else {
                    // Déplacement impossible hautGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart - 11;
                    }
                }
                break;
            case HautDroite:
                if (rowDepart % 2 == 0) {
                    // Déplacement impossible hautDroite si col 4 et row pair
                    if (estBordure(positionDepart)) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart - 9;
                    }
                } else {
                    if (colDepart == 4) {
                        positionArrivePrise = -1;
                    } else {
                        positionArrivePrise = positionDepart - 9;
                    }
                }
                break;
            default:
                positionArrivePrise = -9999;
                break;
        }

        if (positionArrivePrise > 50 || positionArrivePrise < 1) {
            positionArrivePrise = -1;
        }

        return positionArrivePrise;
    }

    /**
     * Calcule la position de la case devant laquelle un pion
     * se déplacera dans une direction donnée à partir d'une position de départ.
     *
     * @param positionDepart La position de départ du pion.
     * @param positionArriveePrise La position d'arrivé à la prise.
     * @param direction La direction dans laquelle le pion se déplace.
     * @return La position de la case devant laquelle le pion se déplacera, ou -1 si le déplacement est impossible.
     */
    public int getCaseAvantPrise(int positionDepart, int positionArriveePrise, Direction direction) {
        int rowDepart = getRow(positionDepart);
        int rowArrivee = getRow(positionArriveePrise);
        int ecartRow = Math.abs(rowDepart - rowArrivee) - 1;
        int positionArrive = 0;
        boolean isPair = false;
        int addition = 0;

        switch (direction) {
            case BasGauche:
                if (positionArriveePrise == -1) {
                    break;
                }
                if (rowDepart % 2 != 0) {
                    // Déplacement impossible hautDroite si col 4 et row pair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        while (ecartRow > 0) {
                            addition += (isPair) ? 4 : 5;
                            isPair = !isPair;
                            ecartRow--;
                        }

                        positionArrive = positionDepart + addition;
                    }
                } else {
                    isPair = true;

                    while (ecartRow > 0) {
                        addition += (isPair) ? 5 : 4;
                        isPair = !isPair;
                        ecartRow--;
                    }

                    positionArrive = positionDepart + addition;
                }
                break;
            case BasDroite:
                if (positionArriveePrise == -1) {
                    break;
                }
                if (rowDepart % 2 != 0) {

                    while (ecartRow > 0) {
                        addition += (isPair) ? 6 : 5;
                        isPair = !isPair;
                        ecartRow--;
                    }

                    positionArrive = positionDepart + addition;

                } else {
                    // Déplacement impossible hautGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        isPair = true;

                        while (ecartRow > 0) {
                            addition += (isPair) ? 6 : 5;
                            isPair = !isPair;
                            ecartRow--;
                        }

                        positionArrive = positionDepart + addition;
                    }
                }
                break;
            case HautGauche:
                if (positionArriveePrise == -1) {
                    break;
                }
                if (rowDepart % 2 != 0) {
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        while (ecartRow > 0) {
                            addition += (isPair) ? -6 : -5;
                            isPair = !isPair;
                            ecartRow--;
                        }

                        positionArrive = positionDepart + addition;
                    }
                } else {
                    isPair = true;

                    while (ecartRow > 0) {
                        addition += (isPair) ? -5 : -6;
                        isPair = !isPair;
                        ecartRow--;
                    }

                    positionArrive = positionDepart + addition;
                }
                break;
            case HautDroite:
                if (positionArriveePrise == -1) {
                    break;
                }
                if (rowDepart % 2 != 0) {

                    while (ecartRow > 0) {
                        addition += (isPair) ? -4 : -5;
                        isPair = !isPair;
                        ecartRow--;
                    }

                    positionArrive = positionDepart + addition;
                } else {
                    // Déplacement impossible basGauche si col 0 et row impair
                    if (estBordure(positionDepart)) {
                        positionArrive = -1;
                    } else {
                        isPair = true;
                        while (ecartRow > 0) {
                            addition += (isPair) ? -5 : -4;
                            isPair = !isPair;
                            ecartRow--;
                        }

                        positionArrive = positionDepart + addition;
                    }
                }
                break;
            default:
                positionArrive = -9999;
                break;
        }

        if (positionArriveePrise == -1) {
            positionArrive = -1;
        }

        if (positionArrive > 50 || positionArrive < 1) {
            positionArrive = -1;
        }

        return positionArrive;
    }

    /**
     * Vérifie si un pion peut être transformé en dame à une position spécifiée.
     *
     * @param position La position du pion à vérifier.
     * @return Vrai si le pion peut être transformé en dame, sinon faux.
     */
    public boolean peutTransformer(int position) {
        int rowArrive = getRow(position);
        Pion pion = getPion(position);
        Pion.Couleur couleur = pion.getCouleur();

        // Condition de transformation du pion en dame
        // Si noir et rowArrive == 9 = true
        // Si blanc et rowArrive == 0 = true
        // Aucun des deux = false
        return (couleur == Pion.Couleur.Noir && rowArrive == 9) || (couleur == Pion.Couleur.Blanc && rowArrive == 0);
    }

    /**
     * Transforme un pion en dame à une position spécifiée.
     *
     * @param position La position du pion à transformer en dame.
     */
    public void transformerEnDame(int position) {
        int rowArrive = getRow(position);

        // Condition de transformation du pion en dame
        if (rowArrive == 9) {
            pionMap.put(position, new Dame(Dame.Couleur.Noir));
        } else if (rowArrive == 0) {
            pionMap.put(position, new Dame(Dame.Couleur.Blanc));
        }
    }

    /**
     * Change le tour du joueur actif. Si le tour du joueur courant est 1, il devient 2, et vice versa.
     */
    public void changerTourJoueur() {
        tourJoueur = tourJoueur == 1 ? 2 : 1;
    }

    /**
     * Vérifie si le joueur peut jouer à partir de la position de départ en fonction de la couleur de son pion
     * et du tour en cours.
     *
     * @param positionDepart La position de départ du joueur.
     * @return Vrai si le joueur peut jouer, sinon faux.
     */
    public boolean joueurPeutJouer(int positionDepart) {
        Pion.Couleur couleurPionDepart = getPion(positionDepart).getCouleur();

        return (tourJoueur == 1 && couleurPionDepart == Pion.Couleur.Blanc) ||
                (tourJoueur == 2 && couleurPionDepart == Pion.Couleur.Noir);
    }

    /**
     * Effectue un déplacement de pion sur le damier.
     *
     * @param positionDepart La position de départ du déplacement.
     * @param direction      La direction du déplacement.
     * @return Vrai si le déplacement a réussi, sinon faux.
     */
    public boolean deplacerPion(int positionDepart, Direction direction) {
        boolean peutDeplacerPion = false;
        int positionArrivee;
        int positionArriveePrise;
        Pion pionDeplace = getPion(positionDepart);

        if (verifierSiPionNonNull(positionDepart)) {
            // True
            if (joueurPeutJouer(positionDepart)) {
                // True
                positionArrivee = getCaseArrivee(positionDepart, direction);

                if (verifierSiCaseEstVide(positionArrivee) && positionArrivee != -1) {

                    if (!verifierSiReculons(pionDeplace, direction)) {
                        // true on effectue le déplacement car déplacement vers l'avant
                        // True déplacement normal
                        // imprimerLogs
                        ajouterLogs(positionDepart, positionArrivee, false);
                        // logique pour déplacer le pion
                        pionMap.put(positionArrivee, pionDeplace);
                        pionMap.remove(positionDepart);

                        // vérification si peutTransformer après le déplacement
                        if (peutTransformer(positionArrivee)) {
                            // true peut transformer
                            transformerEnDame(positionArrivee);
                        }
                        // changerLeTourDuJoueur
                        changerTourJoueur();
                        peutDeplacerPion = true;
                    }
                } else {
                    // False déplacement prise

                    if (verifierSiPionEnnemi(positionDepart, direction)) {
                        // True
                        positionArrivee = getCaseArrivee(positionDepart, direction);
                        positionArriveePrise = getCaseArrivePrise(positionDepart, direction);

                        //
                        if (verifierSiCaseEstVide(positionArriveePrise) && positionArriveePrise != -1) {
                            // imprimerLogs
                            ajouterLogs(positionDepart, positionArriveePrise, true);

                            // logique pour la prise
                            Pion pionMange = pionMap.get(positionArrivee);
                            pionMangeList.add(new PionMange(pionMange, positionArrivee));

                            pionMap.put(positionArriveePrise, pionDeplace);
                            pionMap.remove(positionArrivee);
                            pionMap.remove(positionDepart);

                            // vérification si peutTransformer après le déplacement de prise
                            if (peutTransformer(positionArriveePrise)) {
                                // true peut transformer
                                transformerEnDame(positionArriveePrise);
                            }

                            // changerLeTourDuJoueur
                            changerTourJoueur();
                            peutDeplacerPion = true;
                        }
                    }
                }
            }
        }

        return peutDeplacerPion;
    }

    /**
     * Vérifie si le déplacement du pion vers une position donnée dans une direction donnée est valide.
     *
     * @param positionArrivee La position d'arrivée du déplacement du pion.
     * @param direction        La direction du déplacement.
     * @param pionDeplace      Le pion déplacé.
     * @return true si le déplacement est valide, false sinon.
     */
    public boolean deplacementValidePion(int positionArrivee, Direction direction, Pion pionDeplace) {
        return !(positionArrivee == -1 ||
                !verifierSiCaseEstVide(positionArrivee) ||
                verifierSiReculons(pionDeplace, direction));
    }

    /**
     * Vérifie si le déplacement de la dame vers une position donnée dans une direction donnée est valide.
     *
     * @param positionArrivee La position d'arrivée du déplacement de la dame.
     * @return true si le déplacement est valide, false sinon.
     */
    public boolean deplacementValideDame(int positionArrivee) {
        return positionArrivee != -1;
    }

    /**
     * Calcule et retourne la direction à partir des positions de départ et d'arrivée.
     *
     * @param rowDepart    La ligne de départ.
     * @param rowArrive    La ligne d'arrivée.
     * @param colDepart    La colonne de départ.
     * @param colArrive    La colonne d'arrivée.
     * @return La direction calculée.
     */
    public Direction calculerDirection(int rowDepart, int rowArrive, int colDepart, int colArrive) {
        Direction direction;
        if (colArrive > colDepart || (colArrive == colDepart) && rowArrive < rowDepart) {
            direction = (rowArrive < rowDepart) ? Direction.HautDroite : Direction.BasDroite;
        } else {
            direction = (rowArrive < rowDepart) ? Direction.HautGauche : Direction.BasGauche;
        }
        return direction;
    }

    /**
    * Obtiens les cases disponibles pour un pion.
    *
    * @param positionDepart Représente la position de départ du pion déplacé.
    * @return Un array de booléen pour chaque position de pion.
    */
    public boolean[] caseDisponiblePion(int positionDepart) {
        boolean[] caseDisponiblePion = new boolean[50];
        int caseArrivee;
        int caseArrivePrise;

        for (Direction direction : Direction.values()) {
            caseArrivee = getCaseArrivee(positionDepart, direction);
            if (deplacementValidePion(caseArrivee, direction, getPion(positionDepart))) {
                caseDisponiblePion[caseArrivee - 1] = true;
            } else {
                // On vérifie pour la prise
                if (verifierSiPionEnnemi(positionDepart, direction)) {
                    caseArrivePrise = getCaseArrivePrise(positionDepart, direction);
                    if (verifierSiCaseEstVide(caseArrivePrise)) {
                        caseDisponiblePion[caseArrivePrise - 1] = true;
                    }
                }
            }
        }

        return caseDisponiblePion;
    }

    /**
     * Trouve et renvoie un tableau de cases disponibles pour un mouvement à partir de la position de départ.
     *
     * @param positionDepart La position de départ du mouvement.
     * @return Un tableau de cases disponibles pour le mouvement.
     */
    public boolean[] caseDisponibleDame(int positionDepart) {
        int positionArrivee;
        boolean[] caseDisponiblesDame = new boolean[50];

        for (Direction direction : Direction.values()) {

            // Pour chaque direction
            positionArrivee = getCaseArrivee(positionDepart, direction);
            verifierSiCaseEstVide(positionArrivee);
            int positionPrecedente = positionDepart;

            // jusqu'à ce que positionArrivé soit invalide (-1) dans chacune des directions
            for (;;) {
                if (positionArrivee != 0) {
                    positionArrivee = getCaseArrivee(positionPrecedente, direction);
                }

                if (!deplacementValideDame(positionArrivee)) {
                    break;
                }

                if (verifierSiCaseEstVide(positionArrivee)) {
                    caseDisponiblesDame[positionArrivee - 1] = true;
                    positionPrecedente = positionArrivee;
                } else {

                    // vérification si c'est un pion allié
                    Pion.Couleur couleurPionDepart = getPion(positionDepart).getCouleur();

                    if (pionMap.get(positionArrivee) != null) {
                        if (Objects.requireNonNull(pionMap.get(positionArrivee)).getCouleur() != couleurPionDepart) {
                            // Pion d'arrivé est ennemi, alors prise possible si la nouvelle case d'arrivé est null

                            // calcule la case d'arrivée en prise
                            int positionArriveePrise;
                            positionArriveePrise = getCaseArrivePrise(positionPrecedente, direction);

                            if (verifierSiCaseEstVide(positionArriveePrise) && positionArriveePrise != -1) {
                                caseDisponiblesDame[positionArriveePrise - 1] = true;
                            }
                        }
                    }

                    break;
                }
            }
        }
        return caseDisponiblesDame;
    }

    /**
     * Tente de déplacer une dame depuis une position de départ vers une position d'arrivée dans une direction donnée.
     *
     * @param positionDepart La position de départ de la dame.
     * @param positionArrivee La position d'arrivée souhaitée pour la dame.
     * @param direction La direction dans laquelle la dame doit se déplacer.
     * @return True si le déplacement a réussi, sinon False.
     */
    @SuppressWarnings("checkstyle:EmptyBlock")
    public boolean deplacerDame(int positionDepart, int positionArrivee, Direction direction) {

        boolean peutDeplacerDame = false;

        if (joueurPeutJouer(positionDepart)) {
            Pion dameDeplacee = getPion(positionDepart);
            boolean[] caseDisponiblesDame;
            int positionAvantArriveePrise = getCaseAvantPrise(positionDepart, positionArrivee, direction);
            // Pion.Couleur couleurDameDéplacée = dameDeplacee.getCouleur();

            // vérifie que c'est bien une dame que le joueur veut déplacé
            if (dameDeplacee instanceof Dame) {

                caseDisponiblesDame = caseDisponibleDame(positionDepart);

                if (caseDisponiblesDame[positionArrivee - 1]) {

                    // vérifier si ce n'est pas une prise
                    if (getPion(positionAvantArriveePrise) == null) {
                        // true

                        // imprimerLogs
                        ajouterLogs(positionDepart, positionArrivee, false);

                        // logique pour déplacer la dame
                        pionMap.put(positionArrivee, dameDeplacee);
                        pionMap.remove(positionDepart);

                        // changerLeTourDuJoueur
                        changerTourJoueur();
                    } else {
                        // false

                        // imprimerLogs
                        ajouterLogs(positionDepart, positionArrivee, true);

                        // logique pour la prise
                        Pion pionMange = pionMap.get(positionAvantArriveePrise);
                        pionMangeList.add(new PionMange(pionMange, positionAvantArriveePrise));

                        pionMap.put(positionArrivee, dameDeplacee);
                        pionMap.remove(positionDepart);
                        pionMap.remove(positionAvantArriveePrise);

                        // changerLeTourDuJoueur
                        changerTourJoueur();
                    }

                    peutDeplacerDame = true;
                }
            }
        }
        return peutDeplacerDame;
    }

    /**
     * Énumération des directions possibles pour les déplacements.
     */
    public enum Direction { BasGauche, BasDroite, HautGauche, HautDroite }
}