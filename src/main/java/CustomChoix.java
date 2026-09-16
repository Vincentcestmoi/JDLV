import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomChoix {
    private final boolean[] fromVivant = new boolean[9];
    private final boolean[] fromMort = new boolean[9];
    
    public void menu(StackPane root) {
        Rectangle fond = UI.prepareFond();
        root.getChildren().add(fond);
        
        
        VBox contenant = new VBox();
        contenant.setSpacing(5);
        contenant.setAlignment(Pos.CENTER);
        
        double largeur = UI.getLargeur();// * 0.3;
        double hauteur = UI.getHauteur();// / 11;
        
        
        RectangleText titre1 = new RectangleText(UI.COULEUR_RECTANGLE, hauteur, largeur, "nombre de voisin vivant", UI.COULEUR_TEXTE, UI.getTailleTexte());
        RectangleText titre2 = new RectangleText(UI.COULEUR_RECTANGLE, hauteur, largeur, "résultat si vivant", UI.COULEUR_TEXTE, UI.getTailleTexte());
        RectangleText titre3 = new RectangleText(UI.COULEUR_RECTANGLE, hauteur, largeur, "résultat si mort", UI.COULEUR_TEXTE, UI.getTailleTexte());
        
        HBox titre = new HBox();
        titre.setSpacing(5);
        titre.setAlignment(Pos.CENTER);
        titre.getChildren().addAll(titre1, titre2, titre3);
        contenant.getChildren().add(titre);
        
        for(int i = 0; i < 9; i++){
            RectangleText voisin = new RectangleText(UI.COULEUR_RECTANGLE, hauteur, largeur, String.valueOf(i), UI.COULEUR_TEXTE, UI.getTailleTexte());
            
            RectangleText siVivant = new RectangleText(fromVivant[i] ? Color.LIGHTBLUE : Color.DARKORANGE, hauteur, largeur, fromVivant[i] ? "vivant" : "mort", UI.COULEUR_TEXTE, UI.getTailleTexte());
            RectangleText siMort = new RectangleText(fromMort[i] ? Color.LIGHTBLUE : Color.DARKORANGE, hauteur, largeur, fromMort[i] ? "vivant" : "mort", UI.COULEUR_TEXTE, UI.getTailleTexte());
            
            final int local = i;
            siVivant.setEvent(ignored -> {
                fromVivant[local] = !fromVivant[local];
                siVivant.setTexte(fromVivant[local] ? "vivant" : "mort");
                siVivant.setColor(fromVivant[local] ? Color.LIGHTBLUE : Color.DARKORANGE);
            });
            siMort.setEvent(ignored -> {
                fromMort[local] = !fromMort[local];
                siMort.setTexte(fromMort[local] ? "vivant" : "mort");
                siMort.setColor(fromMort[local] ? Color.LIGHTBLUE : Color.DARKORANGE);
            });
            
            HBox box = new HBox();
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(voisin, siVivant, siMort);
            contenant.getChildren().add(box);
        }
        
        RectangleText retour = new RectangleText(Color.GREEN, hauteur, largeur, "retour", UI.COULEUR_TEXTE, UI.getTailleTexte());
        retour.setEvent(ignored -> root.getChildren().removeAll(fond, contenant));
        contenant.getChildren().add(retour);
        
        root.getChildren().add(contenant);
    }
    
    CustomChoix() {
        fromVivant[2] = true;
        fromVivant[3] = true;
        fromMort[3] = true;
    }
    
    public boolean get(boolean from, int i){
        if(from){
            return fromVivant[i];
        } else {
            return fromMort[i];
        }
    }
}
