import java.awt.Point;

public class Ant implements AntBehaviour {

         int health;
         int stamina;
         boolean alive ;
         Point position;
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
        public void takeDamage(int amount) {
                this.health -= amount;
                if(this.health <= 0) die();
        }



}
