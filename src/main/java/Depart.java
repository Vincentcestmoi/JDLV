import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Depart extends Application {
  private static double largeur;
  private static double hauteur;
  private static Partie partie = null;
  private static final Controller controller = new Controller();

  public static void main(String[] args) {
    launch(args);
  }
  
  public static void setPartie(Partie partie) {
    Depart.partie = partie;
  }
  
  @Override
  public void start(Stage primaryStage) {
    StackPane root = new StackPane();
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root);
    primaryStage.setTitle("PFC");
    primaryStage.setScene(scene);
    
    javafx.geometry.Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    largeur = bounds.getWidth();
    hauteur = bounds.getHeight();
    
    scene.setOnKeyPressed((KeyEvent event) -> {
      KeyCode code = event.getCode();
      
      if (code == KeyCode.Q) {
        root.getChildren().clear();
        if (partie != null) {
          partie.stop();
          partie = null;
        }
        controller.menu(root);
      } else if(code == KeyCode.ENTER) {
        if (partie == null) {
          partie = new Partie();
          partie.lancer(root);
        } else {
          controller.reprendreJeu();
        }
      } else if (code == KeyCode.SPACE) {
        if (partie != null) {
          partie.pauseReprise(controller.getDelai());
        }
      } else if (code == KeyCode.R) {
        if (partie != null) {
          partie = new Partie();
          partie.lancer(root);
        }
      } else if (code == KeyCode.M){
        if(partie != null) {
          partie.stop();
          controller.menuPartie(root, partie);
        }
      } else if(code == KeyCode.N) {
        if(partie != null) {
          partie.next();
        }
      } else if(code == KeyCode.ADD) {
        if(partie != null) {
          controller.getConfig().addDelai(null);
          partie.boucle(controller.getDelai());
        }
      } else if(code == KeyCode.SUBTRACT) {
        if(partie != null) {
          controller.getConfig().reduitDelai(null);
          partie.boucle(controller.getDelai());
        }
      } else if(code == KeyCode.C){
        if(partie != null) {
          partie.chaos();
        }
      }
      /* else {
        System.out.println("input sans valeur : " + code);
      }*/
    });
    
    controller.menu(root);
    primaryStage.show();
  }
  
  public static double getHauteur(){
    return hauteur;
  }
  
  public static double getLargeur(){
    return largeur;
  }
  
  public static Controller getController(){
    return controller;
  }
}
