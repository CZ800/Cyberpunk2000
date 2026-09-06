import java.awt.Point;
// Implementing point since it allows to hold both x and y 


public interface AntBehaviour {


                void setStamina(int stamina);
                int getStamina();
                void setHealth(int health);
                int getHealth();
                void die();
                boolean isAlive();
                void move(Point destination);
                Point getPosition();


        
    
}
