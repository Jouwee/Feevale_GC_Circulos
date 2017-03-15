package feevale_cg_circulo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Circulo {

    private static final Color[] COR_OCT = new Color[] {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};
    private final Ponto centro;
    private final double raio;

    public Circulo(Ponto centro, double raio) {
        this.centro = centro;
        this.raio = raio;
    }
    
    public List<Ponto> getPontos(double incrementoAngulo) {
        List<Ponto> pontos = new ArrayList<>();
        for (double theta = 0; theta < Math.PI/4; theta += incrementoAngulo) {
            Ponto ponto = getPonto(theta);
            pontos.add(ponto);
            pontos.addAll(getOctantes(ponto));
        }
        return pontos;
    }
    
    public Ponto getPonto(double angulo) {
        double x = centro.x + raio * Math.cos(angulo);
        double y = centro.y + raio * Math.sin(angulo);
        return new Ponto(x, y, Color.RED);
    }
    
    private List<Ponto> getOctantes(Ponto ponto) {
        List<Ponto> pontos = new ArrayList<>();
        double relX = Math.abs(ponto.x - centro.x);
        double relY = Math.abs(ponto.y - centro.y);
        pontos.add(new Ponto(centro.x + relX, centro.y - relY, COR_OCT[0]));
        pontos.add(new Ponto(centro.x - relX, centro.y + relY, COR_OCT[1]));
        pontos.add(new Ponto(centro.x - relX, centro.y - relY, COR_OCT[2]));
        pontos.add(new Ponto(centro.x + relY, centro.y + relX, COR_OCT[3]));
        pontos.add(new Ponto(centro.x + relY, centro.y - relX, COR_OCT[4]));
        pontos.add(new Ponto(centro.x - relY, centro.y + relX, COR_OCT[5]));
        pontos.add(new Ponto(centro.x - relY, centro.y - relX, COR_OCT[6]));
        return pontos;
    }
    
}
