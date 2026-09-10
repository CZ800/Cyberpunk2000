import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Worker extends Ant{
    /*1. Worker needs to detect pheromones nearby
    2. Follow the strongest pheromone trail
    3. Pick up food when it reaches the source
    4. Carry food back to colony  */
    boolean followingTrail;
    boolean carryingFood;
    int foodCarried;
        
    boolean pathfinding;
    private Point position;
    private Random random;

    /*The following refers to list of pheromones where strength is measured as a Float .
    So each pheromone in the trail has a position and a float strength value like 0.8f
    */
    
    List<Pheromone<Float>> trail = new ArrayList<>();
    

    public Worker (int health, int stamina) {
       super(health,stamina);
       this.followingTrail = false;
       this.carryingFood = false;
       this.foodCarried = 0 ;

        this.position = new Point(10,10);
        this.random = new Random();
        this.pathfinding = false;
    }

    public Point getPosition(){
        return position;
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

    public boolean isCarryingFood () { 
        return carryingFood;
    } 

    public int getFoodCarried() { 
        return foodCarried; 
    }

    public void pickUpFood(int amount) {
        this.foodCarried = amount;
        this.carryingFood = true;
    }

     public boolean isPathfinding() {
    return pathfinding;
    }


    //Connecting to the generics Pheromone<T>
    //Worker checks which pheromone in the list is the strongest
    //and moves towards it .

    public void followTrail(){

    Pheromone<Float> strongest = null;
    for (Pheromone<Float> p : trail) {
        if (strongest == null || p.getStrength() > strongest.getStrength()) {
            strongest = p;
        }
    }

    if(strongest != null) {
        move(strongest.getPosition());
    }

}
    
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
        // new pos
        this.position.setLocation(newX, newY);

        // Scout need to mark food location with pheromone
        if (Main.grid[newX][newY] == Main.CellType.FOOD) {
            pathfinding = true; // heading back to colony
        }
       
    }

    public void returnToColony(int gridWidth , int gridHeight) {
        int colonyX = Main.WIDTH / 2;
        int colonyY = Main.HEIGHT / 2;

        int dx = Integer.compare(colonyX , position.x); 
        int dy = Integer.compare(colonyY, position.y); 

        int newX = position.x + dx;
        int newY = position.y + dy;

        position.setLocation(newX, newY);


        if (newX == colonyX && newY == colonyY) {
            pathfinding = false;
        }

        
    }


}


 