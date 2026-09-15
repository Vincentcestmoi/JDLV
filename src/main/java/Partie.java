import javafx.scene.layout.StackPane;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Partie {
    private final Grille grille = new Grille();
    private Timeline timeline;
    private boolean enCours = false;
    
    public void lancer(StackPane root) {
        final Controller controller = Depart.getController();
        grille.reset(controller.getNbCaseSquared());
        root.getChildren().clear();
        root.getChildren().add(grille);
        boucle(controller.getDelai());
        stop(); //on commence immobile
    }
    
    private void boucle(double delai) {
        if(timeline != null){
            timeline.stop();
            timeline.getKeyFrames().setAll(new KeyFrame(
                    Duration.millis(delai),
                    ignored -> tour()
            ));
        } else {
            timeline = new Timeline(new KeyFrame(
                    Duration.millis(delai),
                    ignored -> tour()
            ));
            timeline.setCycleCount(Animation.INDEFINITE);
        }
        enCours = true;
        timeline.play();
    }
    
    private void tour(){
        grille.tour();
    }
    
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            enCours = false;
        }
    }
    
    public void pauseReprise(double delai){
        if(enCours){
            stop();
        } else if(timeline != null){
            reprise(delai);
        }
    }
    
    public void reprise(double delai){
        if(timeline != null){
            boucle(delai);
        }
    }
    
    public void reprise(StackPane root, double delai){
        if(timeline != null) {
            grille.tour();
            root.getChildren().clear();
            root.getChildren().add(grille);
            boucle(delai);
        }
    }
    
    public void next() {
        if(timeline != null){
            System.out.println(1);
            return;
        }
        System.out.println(2);
        tour();
    }
}
