// This strategy is used by the Worker ant to follow the strongest pheromone trail back to the colony.
public class FollowTrailStrategy implements MovementStrategy {

    @Override 
    public void move(Worker worker) {
        java.awt.Point position = worker.getPosition();
        worker.detectPheromones(position.x, position.y);

        int colonyX = Main.WIDTH/2;
        int colonyY = Main.HEIGHT/2;
        double currentDist = position.distance(colonyX, colonyY);

        Pheromone<Float> strongest = null;
        for (Pheromone<Float> p : worker.trail) {
            if(p.getPosition().equals(position)) continue;
            if(p.getPosition().distance(colonyX, colonyY) <= currentDist) continue;
            if (strongest == null || p.getStrength() > strongest.getStrength()) { 
                strongest = p;
            }
        }

        if (strongest != null) {
            worker.moveTowards(strongest.getPosition());
        } else {
            worker.moveRandomly();
        }
    }
}
