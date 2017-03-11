package feevale_cg_circulo;

import java.awt.Color;

public class Ponto {

    public final double x;
    public final double y;
    public final Color color;

    public Ponto(double x, double y) {
        this(x, y, Color.RED);
    }

    public Ponto(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
}
