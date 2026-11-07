import java.awt.Point;
// This strategy is used by the Worker ant to return to the colony after picking up food.
public class ReturnToColonyStrategy implements MovementStrategy {

    private static final float REINFORCE_AMOUNT = 0.2f;

    @Override

    public void move(Worker worker) {
        Point previousPosition = new Point(worker.getPosition());
        worker.returnToColony(Main.WIDTH, Main.HEIGHT);
        Point newPosition = worker.getPosition();

        if (!newPosition.equals(previousPosition)
                && Main.pheromoneGrid[newPosition.x][newPosition.y] != null) {
            Main.pheromoneGrid[newPosition.x][newPosition.y].reinforce(REINFORCE_AMOUNT);
            System.out.println("Reinforced trail at " + newPosition.x + ", " + newPosition.y);
        }
    }
}
