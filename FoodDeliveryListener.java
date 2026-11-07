@FunctionalInterface 
// This interface defines a listener for food delivery events in the simulation.
public interface FoodDeliveryListener {
    void onFoodDelivered(Worker worker);

}
