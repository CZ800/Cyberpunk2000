import java.util.ArrayList;
import java.util.List;


public class Queen extends Ant{
    static final int WORKER_COST = 10 ;
    static final int SCOUT_COST = 15;
    List <Worker> workers = new ArrayList<>();
    List<Scout> scouts = new ArrayList<>();
    int nextAntId = 1;
    int eat ;

    public Queen(int health , int stamina) {
        super(health,stamina);
    }
    

    void setEat(int eat) { this.eat = eat; }
    int getEat() { return eat ; }

    public Worker prodWorker() {
        if (stamina < WORKER_COST) {
            System.out.println("Queen: not enough energy to spawn a worker.");
            return null;
        }

        stamina -= WORKER_COST;
        Worker worker = new Worker(100, 100);
        workers.add(worker);
        System.out.println("Queen spawned " + worker + ". Energy left: " + stamina);
        return worker;
    }

    public Scout prodScout() {
        if (stamina < SCOUT_COST) {
            System.out.println("Queen: not enough energy to spawn a scout.");
            return null;
        }

        stamina -= SCOUT_COST;
        Scout scout = new Scout(100,100);
        scouts.add(scout);
        System.out.println("Queen spawned " + scout + ". Energy left: " + stamina);
        return scout;
    }

}


