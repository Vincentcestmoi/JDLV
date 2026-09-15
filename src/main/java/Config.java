import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.function.Consumer;

public class Config {
    private int delai;
    private int indexNbCase;
    
    private Consumer<MouseEvent> repriseJeu = null;
    
    private final int[] nbPossible = {5, 10, 25, 40, 60, 75, 100, 125, 150, 200, 250, 300, 400, 500, 625, 750};
    
    double[] initialisationBase(){
        final double hauteur = Depart.getHauteur() * 0.05;
        final double largeur = Depart.getLargeur() * 0.3;
        final double tailleTexte = Math.min(hauteur / 2, largeur / 5);
        final double tailleTexteRectangle = tailleTexte * 2;
        repriseJeu = null;
        return new double[]{hauteur, largeur, tailleTexte, tailleTexteRectangle};
    }
    
    double[] initialisationPartie(StackPane root, Partie partie){
        repriseJeu = ignored -> {
            partie.reprise(root, getDelai());
            repriseJeu = null;
        };
        final double hauteur = Depart.getHauteur() * 0.07;
        final double largeur = Depart.getLargeur() * 0.3;
        final double tailleTexte = Math.min(hauteur / 2, largeur / 5);
        final double tailleTexteRectangle = tailleTexte * 2;
        return new double[]{hauteur, largeur, tailleTexte, tailleTexteRectangle};
    }

    public Config(int delai, int indexNbCase) {
        this.delai = delai;
        this.indexNbCase = indexNbCase;
    }
    
    public int getDelai() {
        return delai;
    }
    
    public void setDelai(int delai) {
        this.delai = delai;
    }
    
    public int getIndexNbCase() {
        return indexNbCase;
    }
    
    public void setIndexNbCase(int indexNbCase) {
        this.indexNbCase = indexNbCase;
    }
    
    public int getNbCase(){
        final int racine = nbPossible[getIndexNbCase()];
        return racine * racine;
    }
    
    public int getNbCaseSquared(){
        return nbPossible[getIndexNbCase()];
    }
    
    void addNbCase(RectangleText rectangle){
        if(getIndexNbCase() < nbPossible.length - 1){
            setIndexNbCase(getIndexNbCase() + 1);
            rectangle.setTexte(String.valueOf(getNbCase()));
        }
    }
    
    void reduitNbCase(RectangleText rectangle){
        if(getIndexNbCase() > 0){
            setIndexNbCase(getIndexNbCase() - 1);
            rectangle.setTexte(String.valueOf(getNbCase()));
        }
    }
    
    void reduitDelai(RectangleText rectangle){
        int delai = getDelai();
        int step;
        if(delai <= 1){
            step = 0;
        } else if(delai <= 25){
            step = 1;
        } else {
            step = 25;
        }
        delai -= step;
        setDelai(delai);
        rectangle.setTexte(String.valueOf(delai));
    }
    
    void addDelai(RectangleText rectangle){
        int delai = getDelai();
        int step;
        if(delai < 25){
            step = 1;
        } else {
            step = 25;
        }
        delai += step;
        setDelai(delai);
        rectangle.setTexte(String.valueOf(delai));
    }
    
    public void reprendreJeu(){
        if(repriseJeu != null){
            repriseJeu.accept(null);
        }
    }
    
    public Consumer<MouseEvent> getRepriseJeu() {
        return repriseJeu;
    }
}