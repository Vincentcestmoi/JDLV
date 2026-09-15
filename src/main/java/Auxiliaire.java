import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Auxiliaire {
    private static DecimalFormat df = null;
    
    public static Text SpecifiqueText(String texte, Color couleur, double taille) {
        Text text = new Text(texte);
        text.setFont(javafx.scene.text.Font.font(taille));
        text.setFill(couleur);
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        return text;
    }
    
    public static String formatDecimal(double nombre){
        if(df == null){
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setDecimalSeparator('.');
            df = new DecimalFormat("0.00", symbols);
        }
        return df.format(nombre);
    }
    
    public static String toString(Object contenue) {
        if(contenue instanceof Double contenuD) {
            return formatDecimal(contenuD);
        } else {
            return String.valueOf(contenue);
        }
    }
}
