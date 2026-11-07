import java.awt.Point;

public class FollowTrailStrategy implements MovementStrategy {

    @Override
    public void move(Worker worker) {
        Point position = worker.getPosition();
        worker.detectPheromones(position.x, position.y);

        int colonyX = Main.WIDTH / 2;
        int colonyY = Main.HEIGHT / 2;
        double currentDist = position.distance(colonyX, colonyY);

        Pheromone<Float> strongest = null;
        for (Pheromone<Float> pheromone : worker.trail) {
            if (pheromone.getPosition().equals(position)
                    || pheromone.getPosition().distance(colonyX, colonyY) <= currentDist) {
                continue;
            }
            if (strongest == null || pheromone.getStrength() > strongest.getStrength()) {
                strongest = pheromone;
            }
        }

        if (strongest == null) {
            worker.wanderRandomly();
        } else {
            worker.moveTowards(strongest.getPosition());
        }
    }
}
