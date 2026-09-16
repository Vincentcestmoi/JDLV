import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Grille extends Canvas {
    private int nombreCases;
    private Cellule[][] cellules;
    private final GraphicsContext gc;
    private double largeurCase;
    private double hauteurCase;
    
    public Grille() {
        setWidth(Depart.getLargeur());
        setHeight(Depart.getHauteur());
        gc = getGraphicsContext2D();
        
        setOnMouseClicked(e -> {
            int i = (int)(e.getX() / largeurCase);
            int j = (int)(e.getY() / hauteurCase);
            cellules[i][j].inverse();
            render(i, j);
        });
        
    }
    
    private void render(int i, int j) {
        Cellule cellule = cellules[i][j];
        cellule.update();
        gc.setFill(cellules[i][j].getCouleur());
        gc.fillRect(i * largeurCase, j * hauteurCase, largeurCase, hauteurCase);
    }
    
    private void render() {
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                Cellule cellule = cellules[i][j];
                cellule.update();
                gc.setFill(cellules[i][j].getCouleur());
                gc.fillRect(i * largeurCase, j * hauteurCase, largeurCase, hauteurCase);
            }
        }
    }
    
    public void reset(Controller controller) {
        nombreCases = controller.getNbCaseSquared();
        cellules = new Cellule[nombreCases][nombreCases];
        
        largeurCase = Depart.getLargeur() / nombreCases;
        hauteurCase = Depart.getHauteur() / nombreCases;
        
        Random rand = new Random();
        
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                switch (controller.getStart()){
                    case VIDE -> cellules[i][j] = new Cellule(false);
                    case SOUPE -> cellules[i][j] = new Cellule(rand.nextBoolean());
                    case PLEIN -> cellules[i][j] = new Cellule(true);
                }
            }
        }
        render();
    }
    
    public void tour(){
        Controller controller = Depart.getController();
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                Cellule local = cellules[i][j];
                int vivants = 0;
                for(Cellule voisin : voisins(i, j)) {
                    if(voisin.estVivant()){
                        vivants += 1;
                    }
                }
                boolean vivre = controller.getConfig().getRule(local.estVivant(), vivants);
                local.set(vivre);
            }
        }
        render();
    }
    
    private Cellule[] voisins(int i, int j) {
        Cellule[] cellules = new Cellule[8];
        int haut = (i - 1 + nombreCases) % nombreCases;
        int bas = (i + 1) % nombreCases;
        int gauche = (j - 1 + nombreCases) % nombreCases;
        int droite = (j + 1) % nombreCases;
        
        int[] ligne = {bas, i, haut};
        int[] colonne = {gauche, j, droite};
        int index = 0;
        
        for(int l : ligne){
            for(int c : colonne){
                if(l == i && c == j){
                    continue;
                }
                cellules[index] = this.cellules[l][c];
                index += 1;
            }
        }
        return cellules;
    }
    
    public void chaos() {
        Random rand = new Random();
        int i = rand.nextInt(nombreCases);
        int j = rand.nextInt(nombreCases);
        Cellule cel = cellules[i][j];
        cel.inverse();
        render(i, j);
    }
}
