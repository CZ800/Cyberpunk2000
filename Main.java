import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.*;


public class Main {

    static final int WIDTH = 20;
    static final int HEIGHT = 20;
    static final int CELL_SIZE = 30;
    static final int TOTAL_FOOD = 10;
    static int respawnTimer = 0 ;
    static int scoutspawnTimer = 0 ;
    static final int scoutSPAWN_RATE = 30; 
    static final int MAX_SCOUTS = 2 ;
    static final int RESPAWN_RATE = 50;// so every 50 ticks
    static int scoutMoveTimer = 0 ;
    static final int SCOUT_MOVE_RATE = 2; // move every 3 ticks

    static int workerspawnTimer = 0;
    static final int workerSPAWN_RATE = 40;
    static final int MAX_WORKERS = 5;
    static int workerMoveTimer = 0;
    static final int WORKER_MOVE_RATE = 4;


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
                scoutspawnTimer = 0;
            }
        }
        
        scoutMoveTimer++;
        if (scoutMoveTimer >= SCOUT_MOVE_RATE) {
            for (Scout scout : scouts) {
                if (scout.isPathfinding()) {
                    scout.returnToColony(WIDTH, HEIGHT);
                }else {
                    scout.wander(WIDTH, HEIGHT);
                }
            }
            scoutMoveTimer = 0 ;
        }

        workerspawnTimer++;
        if(workerspawnTimer >= workerSPAWN_RATE && workers.size() < MAX_WORKERS) {
            Worker worker = queen.prodWorker();
            if (worker != null) {
                workers.add(worker);
                workerspawnTimer = 0;
            }
        }

        workerMoveTimer++;
        if (workerMoveTimer >= WORKER_MOVE_RATE) {
            for (Worker worker : workers) {
                if (worker.isCarryingFood()) {
                    worker.returnToColony(WIDTH, HEIGHT);
                } else {
                Point p = worker.getPosition();
                worker.detectPheromones(p.x, p.y);
                worker.followTrail();
                }
            }
            workerMoveTimer = 0;
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

    static Map<String, Long> liveAntCountsByType() {
        List<String> livingAntTypes = Stream.concat(Stream.of(queen), Stream.concat(scouts.stream(), workers.stream()))
                .filter(Ant::isAlive)
                .map(ant -> ant.getClass().getSimpleName())
                .collect(Collectors.toList());

        return livingAntTypes.parallelStream()
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
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
    @SuppressWarnings("unchecked")
    static Pheromone<Float>[][] pheromoneGrid = new Pheromone[WIDTH][HEIGHT];

    public static void main(String[] args) {
        
        initGrid();
        JFrame frame = new JFrame("Ant Colony");

        JPanel panel = new JPanel() {
            @Override 
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
                drawPopulationSummary(g);
            }
        };

        


    

        panel.setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE + 24));
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                 g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }

               
                
               
        }

        for (Scout scout : scouts) {
            drawAnt(g, scout, Color.GREEN, Color.BLUE);
        }

        for (Worker worker : workers) {
            drawAnt(g, worker, Color.RED, Color.RED);
        }

 }

 static void drawAnt(Graphics g, Ant ant, Color bodyColor, Color centerColor) {
    Point position = ant.getPosition();
    int cellX = position.x * CELL_SIZE;
    int cellY = position.y * CELL_SIZE;
    int centerX = cellX + 10;
    Point direction = ant.getFacingDirection();
    double angle = Math.atan2(direction.y, direction.x);
    Graphics2D antGraphics = (Graphics2D) g.create();
    antGraphics.rotate(angle, cellX + CELL_SIZE / 2.0, cellY + CELL_SIZE / 2.0);

    antGraphics.setColor(bodyColor);
    for (int legX : new int[] {centerX, centerX + 3, centerX + 6}) {
        antGraphics.fillRect(legX, cellY + 7, 2, 4);
        antGraphics.fillRect(legX, cellY + 19, 2, 4);
    }

    antGraphics.fillRect(cellX + 1, cellY + 10, 9, 9);
    antGraphics.setColor(centerColor);
    antGraphics.fillRect(centerX, cellY + 11, 8, 8);
    antGraphics.setColor(bodyColor);
    antGraphics.fillRect(cellX + 18, cellY + 10, 10, 10);
    antGraphics.dispose();
 }
 
 static void drawPopulationSummary(Graphics g) {
    Map<String, Long> counts = liveAntCountsByType();
    int summaryY = HEIGHT * CELL_SIZE;

    g.setColor(Color.WHITE);
    g.fillRect(0, summaryY, WIDTH * CELL_SIZE, 24);
    g.setColor(Color.BLACK);
    g.drawString(
            "Queens: " + counts.getOrDefault("Queen", 0L)
                    + " | Scouts: " + counts.getOrDefault("Scout", 0L)
                    + " | Workers: " + counts.getOrDefault("Worker", 0L),
            8, summaryY + 17);
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

    scouts.add(new Scout(100, 100));
    scouts.add(new Scout(100, 100));
    scouts.add(new Scout(100, 100));

    workers.add(new Worker(100, 100));
    workers.add(new Worker(100, 100));

 

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