import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class Controller {
    private final Config config = new Config(100, 2, Start.VIDE);
    
    public int getDelai() {
        return config.getDelai();
    }
    
    public int getNbCaseSquared() {
        return config.getNbCaseSquared();
    }
    
    public Start getStart(){
        return config.getType();
    }
    
    public Config getConfig(){
        return config;
    }
    
    public void menu(StackPane root) {
        UI.init(config.initialisationBase());
        
        root.getChildren().add(UI.prepareFond());
        
        VBox contenant = DefaultVBox();
        
        Text titreNbCase = Auxiliaire.specifiqueText("Nombre de cases (peut causer du lag)", UI.COULEUR_TEXTE, UI.getTailleTexteRectangle());
        contenant.getChildren().add(titreNbCase);
        
        contenant.getChildren().add(UI.prepare(config.getNbCase(), config::addNbCase, config::reduitNbCase));
        
        Text typeDepart = Auxiliaire.specifiqueText("type de partie à lancer", UI.COULEUR_TEXTE, UI.getTailleTexteRectangle());
        contenant.getChildren().add(typeDepart);
        
        contenant.getChildren().add(UI.prepare(config.getType(), config::addType, config::reduitType));
        
        Consumer<MouseEvent> event = ignored -> lancerPartie(root);
        
        communMenu(root, contenant, "Lancer", event);
        
        root.getChildren().add(contenant);
    }
    
    public void communMenu(StackPane root, VBox contenant, String textBoutonBas, Consumer<MouseEvent> eventBoutonBas){
        
        Text titreDelay = Auxiliaire.specifiqueText("Délai entre chaque frames (en millisecondes)", UI.COULEUR_TEXTE, UI.getTailleTexteRectangle());
        contenant.getChildren().add(titreDelay);
        
        contenant.getChildren().add(UI.prepare(config.getDelai(), config::addDelai, config::reduitDelai));
        
        Text espace = Auxiliaire.specifiqueText("I'm invisible", Color.TRANSPARENT, UI.getTailleTexteRectangle());
        contenant.getChildren().add(espace);
        
        RectangleText config = new RectangleText(UI.COULEUR_RECTANGLE, UI.getHauteur(), UI.getLargeur(), "personnaliser les règles", UI.COULEUR_TEXTE, UI.getTailleTexte());
        config.setEvent(ignored -> Depart.getController().getConfig().getRule().menu(root));
        
        contenant.getChildren().add(config);
        
        Text espace2 = Auxiliaire.specifiqueText("I'm invisible too", Color.TRANSPARENT, UI.getTailleTexteRectangle());
        contenant.getChildren().add(espace2);
        
        RectangleText reprise = new RectangleText(Color.GREEN, UI.getHauteur(), UI.getLargeur(), textBoutonBas, UI.COULEUR_TEXTE, UI.getTailleTexte());
        reprise.setEvent(eventBoutonBas::accept);
        
        contenant.getChildren().add(reprise);
    }
    
    void menuPartie(StackPane root, Partie partie){
        UI.init(config.initialisationPartie(root, partie));
        root.getChildren().add(UI.prepareFond());
        
        VBox contenant = DefaultVBox();
        
        communMenu(root, contenant, "Reprendre", config.getRepriseJeu());
        
        root.getChildren().add(contenant);
    }
    
    private void lancerPartie(StackPane root) {
        Partie partie = new Partie();
        Depart.setPartie(partie);
        partie.lancer(root);
    }
    
    public void reprendreJeu(){
        config.reprendreJeu();
    }
    
    private VBox DefaultVBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
