import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


public class Main {

    static final int WIDTH = 20;
    static final int HEIGHT = 20;
    static final int CELL_SIZE = 30;
    static final int TOTAL_FOOD = 10;
    static int respawnTimer = 0 ;
    static int scoutspawnTimer = 0 ;
    static final int scoutSPAWN_RATE = 30; 
    static final int MAX_SCOUTS = 5 ;
    static final int RESPAWN_RATE = 50;// so every 50 ticks


    static Queen queen;
    static List<Scout> scouts = new ArrayList<>();
    static List<Worker> workers = new ArrayList<>();



    enum CellType {
        EMPTY , FOOD , WALL , COLONY , QUEEN
    }


   ///  Timer for food respawn

   static void update() {
        respawnTimer++;
        if (respawnTimer >= RESPAWN_RATE){
            respawnFood();
          respawnTimer = 0 ;
        }

        scoutspawnTimer++;
        if(scoutspawnTimer >= scoutSPAWN_RATE && scouts.size() < MAX_SCOUTS) {
            Scout scout = queen.prodScout();
            if (scout != null) {
                scouts.add(scout);
            }
        }
    }



    static int countFood() {
        int count = 0;
        for (int x = 0; x < WIDTH ; x++) {
            for (int y = 0 ; y < HEIGHT; y++) {
                if (grid[x][y] == CellType.FOOD) count++ ;
            }
        }
        return count;
    }
    

    static Random random = new Random() ;

    static void respawnFood() { 
        if (countFood() >= TOTAL_FOOD) return; // stop food from spawning if it exceeds counter

    int foodPlaced = 0;
    while (foodPlaced < 3){
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        if (grid[x][y] == CellType.EMPTY) {
            grid[x][y] = CellType.FOOD;
            foodPlaced++;
        
            }

        }
    }



    static CellType[][] grid = new CellType[WIDTH][HEIGHT];
    static Pheromone<Float>[][] pheromoneGrid = new Pheromone[WIDTH][HEIGHT];

    public static void main(String[] args) {
        
        initGrid();
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

        Timer timer = new Timer(100, e -> {
            update();
            panel.repaint();
    });
            timer.start();
}

    

    static void drawGrid(Graphics g) {
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0 ; y < HEIGHT; y++){
                switch (grid[x][y]){
                    case EMPTY:     g.setColor(Color.BLACK); break;
                    case FOOD:      g.setColor(Color.YELLOW); break;
                    case WALL:      g.setColor(Color.RED);  break;
                    //case COLONY:    g.setColor(Color.MAGENTA);  break;
                    case QUEEN:     g.setColor(Color.PINK); break;
                    default:        g.setColor(Color.BLACK);    break;
                    
                }
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                //drawing the grid lines
                g.setColor(Color.DARK_GRAY);
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
 }

 static void initGrid () {
    // set every cell to Empty 
    // place COLONY in the middle 
    // scatter food around the map
    // place Queen

    for (int x = 0; x < WIDTH; x++) {
        for (int y = 0; y < HEIGHT; y++) {
            grid[x][y] = CellType.EMPTY;
        }
    }
// Spawn Queen
      queen = new Queen(100,100);
    grid[WIDTH / 2][HEIGHT / 2] = CellType.QUEEN;

    //place colony in the middle
   // grid[WIDTH / 2][HEIGHT / 2] = CellType.COLONY;


 

    // scatter food 
    Random random = new Random();
    int foodPlaced = 0;
    while (foodPlaced < 10){
        int x = random.nextInt(WIDTH);
        int y = random.nextInt(HEIGHT);
        if (grid[x][y] == CellType.EMPTY) {
            grid[x][y] = CellType.FOOD;
            foodPlaced++;
        }
    }
 }

}

    