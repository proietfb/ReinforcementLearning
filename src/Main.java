import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Main {

    public Main() {
    }

    public static void main(String[] args) {
        int x = 10, y=10;
        GridWorld newWorld = new GridWorld(x,y);
        Agent agent0 = new Agent();
        Agent agent1 = new Agent();

        newWorld.defineWorld();
        agent0.setStartPositionAgentX((int) (0+Math.random()*(x-1)));
        agent0.setStartPositionAgentY((int) (0+Math.random()*(y-1)));

        newWorld.fillGridWorldAgents(agent0.getStartPositionAgentX(),agent0.getStartPositionAgentY(), agent0);


        printWorld(newWorld);
    }

    public static void printWorld(GridWorld gridWorld){
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
