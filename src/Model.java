import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by proietfb on 7/13/16.
 */
public class Model{

    static int xGrid,yGrid,nOfWalls,nAgents, nNodes;
    Agents[] agents;
    Nodes[] nodes;
    GridWorld newWorld;
    Walls walls;
    States state;
    QLearning q;
    static int count;

    public Model() {}

    public void runModel() throws BrokenBarrierException, InterruptedException {

        xGrid = 7;
        yGrid = 7;
        nOfWalls = 10;
        nAgents = 8;
        nNodes = 2;

        newWorld = new GridWorld(xGrid, yGrid);
        walls = new Walls(nOfWalls);
        agents = new Agents[nAgents];
        nodes = new Nodes[nNodes];
        state = new States();

        newWorld.defineWorld();

        newWorld.defineGridValues();

        state.defineStates(newWorld);

        newWorld.fillGridWorldWalls(xGrid,yGrid,walls);



        for (int i = 0; i < nodes.length; i++){
            nodes[i] = new Nodes();
            newWorld.fillGridWorldNodes(nodes[i]);
        }

        for (int i = 0; i < agents.length;i++){ // creo gli agenti, ogni agente conosce tutti gli obiettivi disponibili
            agents[i] = new Agents(i,xGrid,yGrid);
            newWorld.defineFirstAgentPositions(agents[i], newWorld,walls);
            System.out.println("agente " + i + " creato nello stato " + agents[i].getCurrentState());
            printWorld(newWorld);
            for (int j=0; j< nodes.length;j++){
                agents[i].nodesStatesPositions[j] = nodes[j].getNodeCurrentState();
            }
        }

        System.out.println(Arrays.deepToString(newWorld.getGridValues()) + "\n");

        System.out.println("obiettivi creati: " + Arrays.toString(agents[0].nodesStatesPositions));

        long BEGIN = System.currentTimeMillis();


        System.out.println("Fase di Learning da parte degli agenti iniziata");

        for (int i = 0; i < nAgents; i++){
            q = new QLearning(agents[i], nodes, walls);
            q.runMultiAgent(agents[i], state, newWorld);
            System.out.println("agent " + i+ ": " +agents[i].getCurrentState());
        }

        System.out.println("Fase di Learning da parte degli agenti conclusa");

        System.out.println("Stato dell'ambiente alla fine della fase di Learning");

        for (int i = 0; i< agents.length;i++)
            System.out.println("stato dell'agente " + i + " " + agents[i].getCurrentState() );
        printWorld(newWorld);

        System.out.println("Inizio Fase di testing \n");

        count = 0;
        while(true){
            System.out.println(count);
            if (count == nAgents){
                break;
            }
            newWorld.copyWorld();
            System.out.println("Copia dell'ambiene effettuata");
            for (int i = 0; i < nAgents;i++){
                if (agents[i].goalReached == false){
                    System.out.println(agents[i] + ": ho fatto la mossa");
                    q.testQlearning(agents[i], state, newWorld);
                    printCopyWorld(newWorld);
                }
            }
            System.out.println("Update dell'ambiente effettuata");
            newWorld.updateWorld();
        }





        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
//        printCopyWorld(newWorld);
//        System.out.println("\n");
//        printWorld(newWorld);
        printRangeAntenna(newWorld);
    }

    public static void main(String args[]) throws BrokenBarrierException, InterruptedException {
        new Model().runModel();
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static void printCopyWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getCopyGridW().length; i++) {
            for (int j = 0; j < gridWorld.getCopyGridW()[0].length; j++) {
                System.out.print(gridWorld.getCopyGridW()[i][j] + " ");
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
    private static void printRangeAntenna(GridWorld gridWorld) {
        System.out.println("ciao");
        for (int i = 0; i < gridWorld.linkToAntennaMatrix[0].getGridRangeAntenna().length; i++) {
            for (int j = 0; j < gridWorld.linkToAntennaMatrix[0].getGridRangeAntenna()[0].length; j++) {
                System.out.print(gridWorld.linkToAntennaMatrix[0].getGridRangeAntenna()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
