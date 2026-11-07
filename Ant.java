import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Ant implements AntBehaviour {

         int health;
         int stamina;
         boolean alive ;
         protected Point position;
         private int facingX = 1;
         private int facingY;
        // static final cost ;

        //constructor 
        public Ant(int health , int stamina) {
                this.health = health;
                this.stamina = stamina;
                this.alive = true;
        }

        public void setHealth(int health) {this.health = health;}
        public int getHealth() { return health;}
        public void setStamina(int stamina) {this.stamina = stamina;}
        public int getStamina() {return stamina;}
        public void die() {alive = false ; health = 0;}
        public boolean isAlive() { return alive;}
        public void move(Point destination) {
                if(destination == null) {
                        throw new InvalidPositionException("_Ant should not move to null position!_");
                }
                updateFacingDirection(position, destination);
                this.position = destination;
        }

        public Point getFacingDirection() { return new Point(facingX, facingY); }

        protected void updateFacingDirection(Point previousPosition, Point newPosition) {
                if (previousPosition != null && newPosition != null) {
                        int dx = Integer.compare(newPosition.x, previousPosition.x);
                        int dy = Integer.compare(newPosition.y, previousPosition.y);
                        if (dx != 0 || dy != 0) {
                                facingX = dx;
                                facingY = dy;
                        }
                }
        }
        public Point getPosition() { return position;}

        protected Point findPathStep(Point start, Point target, int gridWidth, int gridHeight) {
                if (start == null || target == null
                                || !isWalkable(start.x, start.y, gridWidth, gridHeight)
                                || !isWalkable(target.x, target.y, gridWidth, gridHeight)
                                || start.equals(target)) {
                        return null;
                }

                Point[][] previous = new Point[gridWidth][gridHeight];
                boolean[][] visited = new boolean[gridWidth][gridHeight];
                Queue<Point> queue = new ArrayDeque<>();
                queue.add(new Point(start));
                visited[start.x][start.y] = true;

                while (!queue.isEmpty() && !visited[target.x][target.y]) {
                        Point current = queue.remove();
                        for (int dx = -1; dx <= 1; dx++) {
                                for (int dy = -1; dy <= 1; dy++) {
                                        if (dx == 0 && dy == 0) continue;

                                        int nextX = current.x + dx;
                                        int nextY = current.y + dy;
                                        if (!isWalkable(nextX, nextY, gridWidth, gridHeight)
                                                        || visited[nextX][nextY]) {
                                                continue;
                                        }
                                        if (dx != 0 && dy != 0
                                                        && (!isWalkable(current.x + dx, current.y, gridWidth, gridHeight)
                                                        || !isWalkable(current.x, current.y + dy, gridWidth, gridHeight))) {
                                                continue;
                                        }

                                        Point next = new Point(nextX, nextY);
                                        previous[nextX][nextY] = current;
                                        visited[nextX][nextY] = true;
                                        queue.add(next);
                                }
                        }
                }

                if (!visited[target.x][target.y]) return null;

                Point step = new Point(target);
                while (!previous[step.x][step.y].equals(start)) {
                        step = previous[step.x][step.y];
                }
                return step;
        }

        protected Point findRandomOpenNeighbor(Point start, int gridWidth, int gridHeight, Random random) {
                List<Point> availableSteps = new ArrayList<>();
                for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                                if (dx == 0 && dy == 0) continue;

                                int nextX = start.x + dx;
                                int nextY = start.y + dy;
                                if (!isWalkable(nextX, nextY, gridWidth, gridHeight)) continue;
                                if (dx != 0 && dy != 0
                                                && (!isWalkable(start.x + dx, start.y, gridWidth, gridHeight)
                                                || !isWalkable(start.x, start.y + dy, gridWidth, gridHeight))) {
                                        continue;
                                }
                                availableSteps.add(new Point(nextX, nextY));
                        }
                }

                return availableSteps.isEmpty()
                                ? null
                                : availableSteps.get(random.nextInt(availableSteps.size()));
        }

        private boolean isWalkable(int x, int y, int gridWidth, int gridHeight) {
                return x >= 0 && x < gridWidth && x < Main.grid.length
                                && y >= 0 && y < gridHeight && y < Main.grid[x].length
                                && Main.grid[x][y] != Main.CellType.WALL;
        }

        public void takeDamage(int amount) {
                this.health -= amount;
                if(this.health <= 0) die();
        }



}
