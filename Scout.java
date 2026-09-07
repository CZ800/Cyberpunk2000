import java.awt.Point;

     class Scout extends Ant { 
        boolean pathfinding;

        public Scout(int health , int stamina) {
            super(health, stamina);
        }

        public void setPathfinding(boolean pathfinding_set) {
            this.pathfinding = pathfinding_set;
        }

        public boolean isPathfinding() {
            return pathfinding;
        }

        public void leavePheromone(Point position) {
            // add pheromon to the map at the position
        }
    
        public Point findFood(Point currentPosition) {
            // this is for the pathfinding logic
            return null;
        }
    } 

    
    

