import java.awt.*;
import javax.swing.*;


public class Main {

    static final int WIDTH = 20;
    static final int HEIGHT = 20;
    static final int CELL_SIZE = 30;


    enum CellType {
        EMPTY , FOOD , WALL , COLONY
    }
    
    static CellType[][] grid = new CellType[WIDTH][HEIGHT];
    static Pheromone<Float>[][] pheromoneGrid = new Pheromone[WIDTH][HEIGHT];

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ant Colony");
        JPanel panel = new JPanel() {
            @Override 
            protected void paintComponent(Graphics g) {
                drawGrid(g);
            }
        };


        panel.setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(100, e -> panel.repaint());
        timer.start();
    }

    static void drawGrid(Graphics g) {
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0 ; y < HEIGHT; y++){
                //TODO: draw each cell
        }
    }
 }

}

    