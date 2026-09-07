
     class Scout extends Ant { 
        boolean pathfinding;

        public Scout(int health , int stamina) {
            super(health, stamina);
        }

        public void setPathfinding(boolean pathfinding) {
            this.isPathfinding = pathfinding;
        }

        public boolean isPathfinding() {
            return isPathfinding;
        }

        public void leavePheromone(Point position) {
            // add pheromon to the map at the position
        }
    
        public Point findFood(Point currentPosition) {
            // this is for the pathfinding logic
            return null;
        }
    } 

    
    

