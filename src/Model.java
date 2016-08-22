import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Model {

    static int x,y,nOfWalls,nAgents, nNodes;

    public Model() {}

    public void runModel(){
        long BEGIN = System.currentTimeMillis();

        Controller controller = new Controller();

        x = controller.getxValueGrid();
        y = controller.getyValueGrid();
        nOfWalls = controller.getnWalls();
        nAgents = controller.getnAgents();
        nNodes = controller.getnNodes();

        GridWorld newWorld = new GridWorld(x, y);
        Walls walls = new Walls(nOfWalls);
        Agents[] agents = new Agents[nAgents];
        Nodes[] nodes = new Nodes[nNodes];
        States state = new States();

        newWorld.defineWorld();
        newWorld.defineGridValues();
        state.defineStates(newWorld);
        newWorld.fillGridWorldWalls(x,y,walls);

        for (int i = 0; i < nodes.length; i++){
            nodes[i] = new Nodes();
            newWorld.fillGridWorldNodes(nodes[i]);
            System.out.println("state of node " + i + ": "+ nodes[i].getNodeCurrentState());

        }


        for (int i = 0; i < agents.length; i++){
            agents[i] = new Agents(x,y);
            newWorld.fillGridWorldAgents(agents[i]);
            System.out.println("state of agent " + i + ": "+ agents[i].getCurrentState());
        }

        for (int i = 0; i < agents.length;i++){
            for (int j=0; j< nodes.length;j++){
                agents[i].nodesStatesPositions[j] = nodes[j].getNodeCurrentState();
            }
        }

        System.out.println(Arrays.deepToString(newWorld.getGridValues()) + "\n");
        //printWorld(newWorld);
        System.out.println("\n");
        System.out.println(Arrays.toString(agents[0].getNodesStatesPositions()));


        for (int i = 0; i < agents.length;i++){
            QLearning q = new QLearning(agents[i], nodes, walls);
            q.runMultiAgent(agents[i], state, newWorld);
            System.out.println("agent " + i+ ": " +agents[i].getPreviousStates());
        }



        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
    }

    private static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void printQ(Agents agent) {
        for (int i = 0; i < agent.getQ().length; i++) {
            for (int j = 0; j < agent.getQ()[0].length; j++) {
                System.out.print(agent.getQ()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void printR(QLearning q) {
        for (int i = 0; i < q.getR().length; i++) {
            for (int j = 0; j < q.getR()[0].length; j++) {
                System.out.print(q.getR()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

}
