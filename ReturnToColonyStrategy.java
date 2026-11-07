import java.awt.Point;

public class ReturnToColonyStrategy implements MovementStrategy {

    @Override
    public void move(Worker worker) {
        move(worker, Main.WIDTH, Main.HEIGHT);
    }

    void move(Worker worker, int gridWidth, int gridHeight) {
        Point position = worker.getPosition();
        int colonyX = Main.WIDTH / 2;
        int colonyY = Main.HEIGHT / 2;
        Point nextPosition = worker.findPathStep(
                position, new Point(colonyX, colonyY), gridWidth, gridHeight);
        if (nextPosition == null) return;

        worker.moveTo(nextPosition);
        if (nextPosition.x == colonyX && nextPosition.y == colonyY) {
            worker.deliverFood();
        }
    }
}
