import java.awt.Point;


public class Pheromone<T> {
    Point position;
    T strength;

    public Pheromone(Point position, T strength) {
        this.position = position;
        this.strength = strength;

    }

    public T getStrength() { return strength; }
    public Point getPosition() { return position; }
    
public void decay {
    
    // pheromone fades over time 
    //1. Gets the current strength as a float
    //2. Multiplies by 0.99f
    //3. Updates the strength
    //Cast the result back

}
    
}
