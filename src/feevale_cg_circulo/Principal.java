package feevale_cg_circulo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.NumberFormatter;

public class Principal extends JFrame {

    private Circulo circulo;
    private PanelDesenho panelDesenho;
    private PanelParametros panelParametros;

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }

    public Principal() throws HeadlessException {
        super();
        circulo = new Circulo(new Ponto(15, 15), 10);
        //
        setSize(800, 600);
        setTitle("Desenhando círculos :)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(5, 5));
        panelDesenho = new PanelDesenho();
        getContentPane().add(panelDesenho);
        panelParametros = new PanelParametros();
        getContentPane().add(panelParametros, BorderLayout.EAST);
    }

    private List<Ponto> getPontos() {
        double precisaoDiv = 1;
        try {
            precisaoDiv = ((Number) panelParametros.fPrecisao.getValue()).doubleValue();
        } catch (Exception e) {
            // Talvez não tenha inicializado ainda
        }
        double precisao = Math.PI / precisaoDiv;
        return circulo.getPontos(precisao);
    }

    private class PanelParametros extends JPanel {

        JFormattedTextField fCentroX;
        JFormattedTextField fCentroY;
        JFormattedTextField fRaio;
        JFormattedTextField fPrecisao;
        JTextArea fPontos;

        public PanelParametros() {

            setLayout(new BorderLayout(5, 5));

            JPanel parametros = new JPanel(new GridLayout(0, 2, 5, 5));
            parametros.add(new JLabel("Centro (X)"));
            fCentroX = criaCampo(10d);
            parametros.add(fCentroX);
            parametros.add(new JLabel("Centro (Y)"));
            fCentroY = criaCampo(10d);
            parametros.add(fCentroY);
            parametros.add(new JLabel("Raio"));
            fRaio = criaCampo(5);
            parametros.add(fRaio);
            parametros.add(new JLabel("Precisão"));
            fPrecisao = criaCampo(16);
            parametros.add(fPrecisao);
            updateCirculo();

            add(parametros, BorderLayout.NORTH);

            fPontos = new JTextArea();
            fPontos.setFont(new Font("Courier New", Font.PLAIN, 12));

            add(new JScrollPane(fPontos));

        }

        private void updateCirculo() {
            double centroX = ((Number) fCentroX.getValue()).doubleValue();
            double centroY = ((Number) fCentroY.getValue()).doubleValue();
            double raio = ((Number) fRaio.getValue()).doubleValue();
            circulo = new Circulo(new Ponto(centroX, centroY), raio);
            panelDesenho.repaint();
            if (fPontos != null) {
                fPontos.setText(getTextoPontos());
            }
        }

        public String getTextoPontos() {
            StringBuilder sb = new StringBuilder();
            DecimalFormat format = new DecimalFormat("#0.00");
            for (Ponto ponto : getPontos()) {
                sb.append(format.format(ponto.x)).append(", ").append(format.format(ponto.y)).append("\n");
            }
            return sb.toString();
        }

        private JFormattedTextField criaCampo(double v) {
            JFormattedTextField campo = new JFormattedTextField(new NumberFormatter(new DecimalFormat("#0.00")));
            campo.setValue(v);
            campo.addActionListener((e) -> {
                updateCirculo();
            });
            return campo;
        }

    }

    private class PanelDesenho extends JComponent {

        private static final int GRID = 10;

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Fundo
            g2d.setColor(new Color(51, 51, 51));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Círculo
            for (Ponto ponto : getPontos()) {
                g2d.setColor(ponto.color);
                int x = (int) (Math.round(ponto.x) * GRID);
                int y = (int) (Math.round(ponto.y) * GRID);
                g2d.fillRect(x, y, GRID, GRID);
            }

            // Desenha o grid
            g2d.setColor(new Color(61, 61, 61));
            for (int x = 0; x < getWidth(); x += GRID) {
                g2d.drawLine(x, 0, x, getHeight());
            }
            for (int y = 0; y < getHeight(); y += GRID) {
                g2d.drawLine(0, y, getWidth(), y);
            }

            g2d.dispose();
        }

    }

}
