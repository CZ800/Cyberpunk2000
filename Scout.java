import java.awt.Point;
import java.util.Random;


/* 1. Scounts should wander randomly , 2. When they find food - mark it with pheromones and return to colony
3. Not pick up food - that's the Worker's job */

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


         //Boundary check for wander 
        // have to check that newX/newY are within the grid otherwise might cause arrayoutofbounds exception
        if (newX < 0 || newX >= gridWidth || newY < 0 || newY >= gridHeight) return;
        if (Main.grid[newX][newY] == Main.CellType.WALL) {
            Point alternativeStep = findRandomOpenNeighbor(position, gridWidth, gridHeight, random);
            if (alternativeStep == null) return;
            newX = alternativeStep.x;
            newY = alternativeStep.y;
        }
        // new pos
        updateFacingDirection(position, new Point(newX, newY));
        this.position.setLocation(newX, newY);

        // Scout need to mark food location with pheromone
        if (Main.grid[newX][newY] == Main.CellType.FOOD) {
            Main.pheromoneGrid[newX][newY] = leavePheromone();
            pathfinding = true; // heading back to colony
        }
       
    }

    public void returnToColony(int gridWidth , int gridHeight) {
        int colonyX = Main.WIDTH / 2;
        int colonyY = Main.HEIGHT / 2;

        Point nextPosition = findPathStep(position, new Point(colonyX, colonyY), gridWidth, gridHeight);
        if (nextPosition == null) return;
        int newX = nextPosition.x;
        int newY = nextPosition.y;

        updateFacingDirection(position, new Point(newX, newY));
        position.setLocation(newX, newY);

        //leave pheromon t on the way back
        Main.pheromoneGrid[newX][newY] = leavePheromone();

        if (newX == colonyX && newY == colonyY) {
            pathfinding = false;
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

    
    
