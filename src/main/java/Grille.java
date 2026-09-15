import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Grille extends Canvas {
    private int nombreCases;
    private Cellule[][] couleurs;
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
            couleurs[i][j].inverse();
            render();
        });
        
    }
    
    private void render() {
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                Cellule cellule = couleurs[i][j];
                cellule.update();
                gc.setFill(couleurs[i][j].getCouleur());
                gc.fillRect(i * largeurCase, j * hauteurCase, largeurCase, hauteurCase);
            }
        }
    }
    
    public void reset(int nbCase) {
        nombreCases = nbCase;
        couleurs = new Cellule[nombreCases][nombreCases];
        
        largeurCase = Depart.getLargeur() / nombreCases;
        hauteurCase = Depart.getHauteur() / nombreCases;
        
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                couleurs[i][j] = new Cellule(false);
            }
        }
        render();
    }
    
    public void tour(){
        for (int i = 0; i < nombreCases; i++) {
            for (int j = 0; j < nombreCases; j++) {
                Cellule local = couleurs[i][j];
                int vivants = 0;
                for(Cellule voisin : voisins(i, j)) {
                    if(voisin.estVivant()){
                        vivants += 1;
                    }
                }
                switch (vivants) {
                    case 3 -> local.set(true);
                    case 0, 1, 4, 5, 6, 7, 8 -> local.set(false);
                    case 2 -> {} //vivant si vivant, morte si morte
                }
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
                cellules[index] = couleurs[l][c];
                index += 1;
            }
        }
        return cellules;
    }
}
