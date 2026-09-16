import javafx.scene.paint.Color;

public class Cellule {
    private boolean vivant;
    private boolean temp;
    
    public Cellule(boolean vivant) {
        this.vivant = vivant;
        this.temp = vivant;
    }
    
    public void inverse(){
        this.vivant = !this.vivant;
        this.temp = this.vivant;
    }
    
    public void set(boolean vivant){
        this.temp = vivant;
    }
    
    public void update(){
        this.vivant = this.temp;
    }
    
    public boolean estVivant(){
        return this.vivant;
    }
    
    Color getCouleur(){
        if(estVivant()) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }
}
