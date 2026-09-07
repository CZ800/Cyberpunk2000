public class Main {

    class resourcePoint {};
    

}

    interface Explore {

        findResource() ;
        leavePheromon() ;

    }

    abstract boolean resourceExist { 
        if resource <= 0  ( return 0 );
    }

    interface Pathfinding {

        void findpheromon () ;
    }

    interface Carrier {
        
        void carry () ;
    }

    interface ProduceAnt {

        prodScout () ;
        prodWorker () ;
    }

    Jrame frame = new Jframe("Ant Colony");
    Jpanel panel = new JPanel() {
        @Override 
        protected void paintComponent(Graphics g) {
            //drawing grid , ants , food , pheromones here

        }
    };

    // timer to redraw every 100ms (10 times per second)
    Timer timer = new Timer (100, e -> panel.repaint());
    timer.start();


    // layer 1 - static map
    CellType[][] grid = new CellType[WIDTH][HEIGHT];

    // layer 2 - pheromone strengths(fade over time)
    Pheromone<Float>[][] pheromoneGrid = new Pheromone[WIDTH][HEIGHT];

    enum CellType {
        EMPTY , FOOD , WALL, COLONY
    }

}

    
    

   



