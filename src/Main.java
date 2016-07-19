import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Main {


    public Main() {
    }

    public static void main(String[] args) {
        long BEGIN = System.currentTimeMillis();
        int x = 5, y=5;
        GridWorld newWorld = new GridWorld(x,y);
        Agent agent0 = new Agent(x,y);
        Node node0 = new Node(newWorld);
        States state = new States();
        Actions action = new Actions();

        newWorld.defineWorld();
        newWorld.defineGridValues();
        agent0.setStartPositionAgentX((int) (0+Math.random()*(x-1)));
        agent0.setStartPositionAgentY((int) (0+Math.random()*(y-1)));
        agent0.setStartState(newWorld.getGridValues()[agent0.getStartPositionAgentX()][agent0.getStartPositionAgentY()]);
        node0.setPositionNodeX((int) (0+Math.random()*(x)-1));
        node0.setPositionNodeY((int) (0+Math.random()*(y)-1));
        node0.setNodeCurrentState(newWorld.defineGridValues()[node0.getPositionNodeX()][node0.getPositionNodeY()]);
        agent0.setCurrentPositionX(agent0.getStartPositionAgentX());
        agent0.setCurrentPositionY(agent0.getStartPositionAgentY());
        agent0.currentState(newWorld);

        state.defineStates(newWorld);

        newWorld.fillGridWorldNodes(node0.getPositionNodeX(),node0.getPositionNodeY(),node0);
        newWorld.fillGridWorldAgents(agent0.getStartPositionAgentX(),agent0.getStartPositionAgentY(), agent0);
        agent0.currentState(newWorld);
        //agent0.previousStates.add(agent0.getCurrentState());

        QLearning q = new QLearning(agent0,node0,newWorld);


        q.run(agent0,state,node0, newWorld);




        System.out.println(Arrays.deepToString(newWorld.getGridValues()));
        printWorld(newWorld);
        System.out.println("\n");
        //printMatrix(q);
        System.out.println(agent0.getPreviousStates());
        System.out.println(agent0.getCurrentState());
        System.out.println(state.getDefinePossibleStates().get(agent0.getCurrentState()));

        //printQ(q);
        //q.printResult(agent0);

        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");


    }

    public static void printWorld(GridWorld gridWorld){
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void printQ(QLearning qLearning){
        for (int i = 0; i < qLearning.getQ().length; i++) {
            for (int j = 0; j < qLearning.getQ()[0].length; j++) {
                System.out.print(qLearning.getQ()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public static void printR(QLearning q){
        for (int i = 0; i < q.R.length; i++) {
            for (int j = 0; j < q.R[0].length; j++) {
                System.out.print(q.R[i][j] + " ");
            }
            System.out.print("\n");
        }
    }


}
