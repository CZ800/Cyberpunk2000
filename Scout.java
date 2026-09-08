import java.awt.Point;
import java.util.Random;

     class Scout extends Ant { 
        boolean pathfinding;
        private Point position;
        private Random random;

        public Scout(int health , int stamina) {
            super(health, stamina);
            this.position = new Point(10,10);
            this.random = new Random();
            this.pathfinding = false;
        }

        public Point getPosition() {
            return position;
        }

        public void setPathfinding(boolean pathfinding_set) {
            this.pathfinding = pathfinding_set;
        }

        public boolean isPathfinding() {
            return pathfinding;
        }

    //movement
    public void wander(int gridWidth, int gridHeight) {
        // Pick a random direction (-1, 0, or 1) for x and y
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;

        // Calculate new potential position
        int newX = position.x + dx;
        int newY = position.y + dy;

        // new pos
        this.position.setLocation(newX, newY);

        // basic collision
        // If scout lands on FOOD, remove it
        if (Main.grid[newX][newY] == Main.CellType.FOOD) {
            Main.grid[newX][newY] = Main.CellType.EMPTY;
        }
    }

    /**
        drop pheremone at scount position
        retrun object with float strength
     */
    public Pheromone<Float> leavePheromone() {
        return new Pheromone<>(new Point(this.position), 1.0f);
    }

    public Point findFood(Point currentPosition) {
        // Placeholder for future pathfinding implementation
        return null;
    }
} 

    
    

