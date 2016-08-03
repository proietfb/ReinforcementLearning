import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Main {


    public Main() {
    }

    public static void main(String[] args) {
        long BEGIN = System.currentTimeMillis();
        int x = 7, y = 7, nOfWalls = 6;

        GridWorld newWorld = new GridWorld(x, y);
        Walls walls = new Walls(nOfWalls);
        Agents agent0 = new Agents(x, y);
        Nodes node0 = new Nodes();
        States state = new States();
        Actions action = new Actions();

        newWorld.defineWorld();
        newWorld.defineGridValues();
        state.defineStates(newWorld);
        newWorld.fillGridWorldWalls(x,y,walls);
        newWorld.fillGridWorldAgents(agent0);
        newWorld.fillGridWorldNodes(node0);
        //agent0.setCurrentPositionX(agent0.getStartPositionAgentX());
        //agent0.setCurrentPositionY(agent0.getStartPositionAgentY());

        //agent0.currentState(newWorld);

        QLearning q = new QLearning(agent0, node0, walls);


        q.run(agent0, state, node0, newWorld);

        System.out.println("walls: " + Arrays.toString(walls.getWallsStatesPositions()));
        System.out.println(Arrays.deepToString(newWorld.getGridValues()));
        printWorld(newWorld);
        System.out.println("\n");
        //printMatrix(q);
        System.out.println(agent0.getPreviousStates());

        //printQ(q);
        //q.printResult(agent0);

        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");


    }

    public static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printQ(QLearning qLearning) {
        for (int i = 0; i < qLearning.getQ().length; i++) {
            for (int j = 0; j < qLearning.getQ()[0].length; j++) {
                System.out.print(qLearning.getQ()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printR(QLearning q) {
        for (int i = 0; i < q.R.length; i++) {
            for (int j = 0; j < q.R[0].length; j++) {
                System.out.print(q.R[i][j] + " ");
            }
            System.out.print("\n");
        }
    }


}
