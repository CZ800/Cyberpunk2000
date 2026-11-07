import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker extends Ant {
    boolean followingTrail;
    boolean carryingFood;
    int foodCarried;


    List<Pheromone<Float>> trail = new ArrayList<>();

    private Point position;

    public Worker(int health, int stamina) {
        super(health, stamina);
        this.position = new Point(Main.WIDTH / 2, Main.HEIGHT /2);
        this.followingTrail = false;
        this.carryingFood = false;
        this.foodCarried = 0;

    }

    public Point getPosition() { return position;}

    public void detectPheromones(int x, int y) { 
    trail.clear() ;

    for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
            int nx = x+dx;
            int ny = y+dy;

            if(nx>=0    &&  nx<Main.WIDTH   &&  ny>=0   &&ny<Main.HEIGHT){
                if(Main.pheromoneGrid[nx][ny] != null){
                    trail.add(Main.pheromoneGrid[nx][ny]);
                }
            }
        }
    }
    }

    public void setFollowingTrail(boolean followingTrail) {
        this.followingTrail = followingTrail;
    }

    public boolean isFollowingTrail() {
        return followingTrail;
    }

    public void setCarryingFood(boolean carryingFood) {
        this.carryingFood = carryingFood;
    }

    public boolean isCarryingFood() {
        return carryingFood;
    }

    public int getFoodCarried() {
        return foodCarried;
    }

    public void pickUpFood(int amount) {
        this.foodCarried = amount;
        this.carryingFood = true;
    }

    public void followTrail() {
    Pheromone<Float> strongest = null;
    int colonyX = Main.WIDTH /2 ;
    int colonyY = Main.HEIGHT /2 ;

    for (Pheromone<Float> p : trail) {
        if (p.getPosition().equals(position)) continue;


    double currentDist = position.distance(colonyX, colonyY);
    double pDist = p.getPosition().distance(colonyX, colonyY);
    if(pDist <= currentDist) continue;


    //Once worker reaches a pheromone position it should ignore that pheromone and look for the next one. We need 
    // to find the strongest pheromone that is not at the worker's current position
     //   if(p.getPosition().equals(position)) continue;

        if (strongest == null || p.getStrength() > strongest.getStrength()) {
            strongest = p;
        }
    }
    


    if(strongest != null) {
       moveTowards(strongest.getPosition()); //use the method
       //when no pheromone trail is left nearby , workers should find another path randomly
    } else {
        Random random = new Random();
        int dx = random.nextInt(3) -1;
        int dy = random.nextInt(3) -1;
        int newX = position.x+dx;
        int newY = position.y+dy;
                Point nextPosition = null;
                if (newX >= 0 && newX < Main.WIDTH && newY >= 0 && newY < Main.HEIGHT) {
                    nextPosition = new Point(newX, newY);
                    if (Main.grid[newX][newY] == Main.CellType.WALL) {
                        nextPosition = findRandomOpenNeighbor(position, Main.WIDTH, Main.HEIGHT, random);
                    }
                }
                if (nextPosition != null) {
                    Point previousPosition = new Point(position);
                    position = nextPosition;
                    updateFacingDirection(previousPosition, position);
                    if (Main.grid[position.x][position.y] == Main.CellType.FOOD) {
                        Main.grid[position.x][position.y] = Main.CellType.EMPTY;
                        Main.pheromoneGrid[position.x][position.y] = null;
                        pickUpFood(1);
                    }
                }
    }

}

    public void returnToColony() {
        returnToColony(Main.WIDTH, Main.HEIGHT);
    }

    public void returnToColony(int gridWidth, int gridHeight) {
        int colonyX = Main.WIDTH / 2;
        int colonyY = Main.HEIGHT / 2;

        Point nextPosition = findPathStep(position, new Point(colonyX, colonyY), gridWidth, gridHeight);
        if (nextPosition == null) return;
        Point previousPosition = new Point(position);
        position = nextPosition;
        int newX = nextPosition.x;
        int newY = nextPosition.y;
        updateFacingDirection(previousPosition, position);

    if ( newX == colonyX && newY == colonyY) {
        carryingFood = false;
        foodCarried = 0;
        System.out.println("Worker delivered food to colony!");
    }
    }

 public void moveTowards(Point target) {
     Point nextPosition = findPathStep(position, target, Main.WIDTH, Main.HEIGHT);
     if (nextPosition == null) return;

     Point previousPosition = new Point(position);
     position = nextPosition;
     updateFacingDirection(previousPosition, position);
     System.out.println("Worker moving to: " + position.x + " ," + position.y);

     if (Main.grid[position.x][position.y] == Main.CellType.FOOD) {
         Main.grid[position.x][position.y] = Main.CellType.EMPTY;
         Main.pheromoneGrid[position.x][position.y] = null;
         pickUpFood(1);
         System.out.println("Worker picked up food! carryingFood = " + carryingFood);
     }
 }
    
}
