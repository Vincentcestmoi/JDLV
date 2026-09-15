import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.function.Consumer;

public class UI {
    private static double hauteur;
    private static double largeur;
    private static double tailleTexte;
    private static double tailleTexteRectangle;
    
    private static final Color COULEUR_RECTANGLE = Color.GRAY;
    static final Color COULEUR_FOND = Color.DARKGRAY;
    static final Color COULEUR_TEXTE = Color.WHITE;
    private static final Color COULEUR_PLUS = Color.DARKBLUE;
    private static final Color COULEUR_MOINS = Color.DARKRED;
    
    static void init(double[] valeurs){
        hauteur = valeurs[0];
        largeur = valeurs[1];
        tailleTexte = valeurs[2];
        tailleTexteRectangle = valeurs[3];
    }
    
    static Rectangle prepareFond(){
        Rectangle fond = new Rectangle(0, 0, Depart.getLargeur(), Depart.getHauteur());
        fond.setFill(COULEUR_FOND);
        return fond;
    }
    
    static HBox prepare(Object contenue, Consumer<RectangleText> ajoute, Consumer<RectangleText> reduit) {
        HBox ligne = new HBox();
        ligne.setSpacing(5);
        ligne.setAlignment(Pos.CENTER);
        
        RectangleText plus = new RectangleText(COULEUR_RECTANGLE, hauteur, largeur, "+", COULEUR_PLUS, tailleTexte);
        RectangleText rectangle = new RectangleText(COULEUR_RECTANGLE, hauteur, largeur, Auxiliaire.toString(contenue), COULEUR_TEXTE, tailleTexte);
        RectangleText moins = new RectangleText(COULEUR_RECTANGLE, hauteur, largeur, "−", COULEUR_MOINS, tailleTexte);
        
        plus.setEventContinue(ajoute, rectangle);
        moins.setEventContinue(reduit, rectangle);
        
        ligne.getChildren().addAll(moins, rectangle, plus);
        
        return ligne;
    }
    
    public static double getHauteur() {
        return hauteur;
    }
    
    public static double getLargeur() {
        return largeur;
    }
    
    public static double getTailleTexte() {
        return tailleTexte;
    }
    
    public static double getTailleTexteRectangle() {
        return tailleTexteRectangle;
    }
}
