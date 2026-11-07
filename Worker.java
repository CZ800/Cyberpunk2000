import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Worker extends Ant {
    boolean followingTrail;
    boolean carryingFood;
    int foodCarried;


    List<Pheromone<Float>> trail = new ArrayList<>();

    private Point position;
    private MovementStrategy movementStrategy = new FollowTrailStrategy();
    private final List<FoodDeliveryListener> foodDeliveryListeners = new ArrayList<>();

    public Worker(int health, int stamina) {
        super(health, stamina);
        this.position = new Point(Main.WIDTH / 2, Main.HEIGHT /2);
        this.followingTrail = false;
        this.carryingFood = false;
        this.foodCarried = 0;

    }

    public Point getPosition() { return position;}

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = Objects.requireNonNull(movementStrategy, "movementStrategy");
    }

    public void performMovement() {
        movementStrategy.move(this);
    }

    public void addFoodDeliveryListener(FoodDeliveryListener listener) {
        foodDeliveryListeners.add(Objects.requireNonNull(listener, "listener"));
    }

    public boolean removeFoodDeliveryListener(FoodDeliveryListener listener) {
        return foodDeliveryListeners.remove(listener);
    }

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
        new FollowTrailStrategy().move(this);
    }

    void wanderRandomly() {
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
            moveTo(nextPosition);
            if (Main.grid[position.x][position.y] == Main.CellType.FOOD) {
                Main.grid[position.x][position.y] = Main.CellType.EMPTY;
                Main.pheromoneGrid[position.x][position.y] = null;
                pickUpFood(1);
            }
        }
    }

    public void returnToColony() {
        returnToColony(Main.WIDTH, Main.HEIGHT);
    }

    public void returnToColony(int gridWidth, int gridHeight) {
        new ReturnToColonyStrategy().move(this, gridWidth, gridHeight);
    }

    void moveTo(Point nextPosition) {
        Point previousPosition = new Point(position);
        position = nextPosition;
        updateFacingDirection(previousPosition, position);
    }

    public void deliverFood() {
        boolean deliveredFood = carryingFood;
        carryingFood = false;
        foodCarried = 0;
        System.out.println("Worker delivered food to colony!");
        if (deliveredFood) {
            for (FoodDeliveryListener listener : new ArrayList<>(foodDeliveryListeners)) {
                listener.onFoodDelivered(this);
            }
        }
    }

 public void moveTowards(Point target) {
     Point nextPosition = findPathStep(position, target, Main.WIDTH, Main.HEIGHT);
     if (nextPosition == null) return;

     moveTo(nextPosition);
     System.out.println("Worker moving to: " + position.x + " ," + position.y);

     if (Main.grid[position.x][position.y] == Main.CellType.FOOD) {
         Main.grid[position.x][position.y] = Main.CellType.EMPTY;
         Main.pheromoneGrid[position.x][position.y] = null;
         pickUpFood(1);
         System.out.println("Worker picked up food! carryingFood = " + carryingFood);
     }
 }
    
}
