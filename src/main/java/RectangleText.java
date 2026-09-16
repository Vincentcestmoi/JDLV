import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class RectangleText extends StackPane {
    private final Rectangle rectangle;
    private Color couleurRectangle;
    private final Text text;
    
    private AnimationTimer timer = null;
        private int delai = 200;
    private long lastCall = 0;
    
    private static final int delaiBase = 200;
    private static final int delaiMin = 16; //capacité maximale de AnimationTimer
    
    RectangleText(Color couleur, double hauteur, double largeur, String texte, Color couleurText, double tailleTexte){
        text = Auxiliaire.specifiqueText(texte, couleurText, tailleTexte);
        rectangle = new Rectangle(largeur, hauteur, couleur);
        couleurRectangle = couleur;
        getChildren().addAll(rectangle, text);
    }
    
    void setEvent(EventHandler<? super MouseEvent> event){
        setOnMouseClicked(event);
        setOnMouseEntered(ignored -> rectangle.setFill(alterer(couleurRectangle)));
        setOnMouseExited(ignored -> rectangle.setFill(couleurRectangle));
    }
    
    <T> void setEventContinue(Consumer<T> event, T cible){
        setOnMouseEntered(ignored -> rectangle.setFill(alterer(couleurRectangle)));
        setOnMouseExited(ignored -> rectangle.setFill(couleurRectangle));
        
        setOnMousePressed(ignored -> setTimer(event, cible));
        setOnMouseReleased(ignored -> deleteTimer());
    }
    
    private <T> void setTimer(Consumer<T> event, T cible) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastCall >= delai * 1_000_000L) { // delai en ms, now en ns
                    event.accept(cible);
                    lastCall = now;
                    
                    if (delai > delaiMin) {
                        delai = (int)(delai * 0.9);
                    }
                }
            }
        };
        lastCall = 0;
        timer.start();
    }
    
    private void deleteTimer() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        delai = delaiBase;
    }
    
    public void setTexte(String texte) {
        text.setText(texte);
    }
    
    private boolean paraissentDifferent(Color color1, Color color2) {
        double red = (color1.getRed() - color2.getRed());
        double blue = (color1.getBlue() - color2.getBlue());
        double green = (color1.getGreen() - color2.getGreen());
        final double Y = Math.sqrt(0.3 * (red * red) + 0.59 * (green * green) + 0.11 * (blue * blue));
        return Y > 0.1;
    }
    
    
    public Paint alterer(Color base) {
        if (paraissentDifferent(base, base.darker())) {
            return base.darker();
        } else if (paraissentDifferent(base, base.brighter())) {
            return base.brighter();
        } else if (paraissentDifferent(base, base.desaturate())) {
            return base.desaturate();
        } else if (paraissentDifferent(base, base.saturate())) {
            return base.saturate();
        } else {
            return base.invert();
        }
    }
    
    public void setColor(Color color) {
        couleurRectangle = color;
        rectangle.setFill(color);
        if(getOnMouseClicked() != null){
            setOnMouseEntered(ignored -> rectangle.setFill(alterer(couleurRectangle)));
            setOnMouseExited(ignored -> rectangle.setFill(couleurRectangle));
        }
    }
}
