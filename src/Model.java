import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by proietfb on 7/13/16.
 */
public class Model {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static int xGrid, yGrid, nOfWalls, nAgents, nNodes;
    static int count;
    double nRun;
    Agents[] agents;
    Nodes[] nodes;
    GridWorld newWorld;
    Walls walls;
    States state;
    QLearning q;
    Scanner scanner;
    private int[] nMoves;
    private int[][] totalMoves;

    private long[] times;
    private long[][] totalTimes;

    private long[] meanTimes;
    private long[] meanCoopTimes;

    private double[] meanMoves;
    private double[] meanCoopMoves;

    public Model() {
    }

    public static void main(String args[]) throws BrokenBarrierException, InterruptedException, IOException {
        Scanner scanner1 = new Scanner(System.in);
        int policy = 0, typePolicy = 2, nTrains = 500;
        double epsilonValue = 0.0;
        String policyStr = "single", typePolicyStr = "max";

        System.out.println("Insert n° of exploration trains: Deafult: 500\n");
        nTrains = scanner1.nextInt();

        System.out.println("Insert Policy:\n 0: Single\t 1: Cooperative\tDeafult: Single\n");
        policy = scanner1.nextInt();
        if (policy == 0)
            policyStr = "single";
        else if (policy == 1)
            policyStr = "coop";

        System.out.println("Insert Type of Policy: \n");
        if (policy == 1) {
            System.out.println("0: truthPolicy\t1: epsilonCoop\t2: maxCoop\n");
            typePolicy = scanner1.nextInt();
            if (typePolicy == 0) {
                typePolicyStr = "truthPolicy";
                System.out.println("Insert epsilon value: ");
                epsilonValue = scanner1.nextDouble();
            }
            else if (typePolicy == 1) {
                typePolicyStr = "epsilonCoop";
                System.out.println("Insert epsilon value: ");
                epsilonValue = scanner1.nextDouble();
            }
            else if (typePolicy == 2)
                typePolicyStr = "maxCoop";
        }
        else {
            System.out.println("0: max\t1:epsilon\tDefault: max");
            typePolicy = scanner1.nextInt();
            if (typePolicy == 0)
                typePolicyStr = "max";
            else if (typePolicy == 1) {
                typePolicyStr = "epsilon";
                System.out.println("Insert epsilon value: ");
                epsilonValue = scanner1.nextDouble();
            }
        }
        new Model().runModelMoves(nTrains, policyStr, typePolicyStr, epsilonValue);
    }

    private static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                switch (gridWorld.getGridW()[i][j]) {
                    case 1:
                        System.out.print(ANSI_GREEN + gridWorld.getGridW()[i][j] + " " + ANSI_RESET);
                        break;
                    case 2:
                        System.out.print(ANSI_RED + gridWorld.getGridW()[i][j] + " " + ANSI_RESET);
                        break;
                    case 3:
                        System.out.print(ANSI_BLUE + gridWorld.getGridW()[i][j] + " " + ANSI_RESET);
                        break;
                    default:
                        System.out.print(gridWorld.getGridW()[i][j] + " ");
                        break;
                }

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private static void printRangeAntennaCopy(GridWorld gridWorld) {
        System.out.println("ciaoCopy");
        for (int i = 0; i < gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy().length; i++) {
            for (int j = 0; j < gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy()[0].length; j++) {
                System.out.print(gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public double[] runModelMoves(int nTrains, String typeTest, String typePolicy, double epsilonValue) throws BrokenBarrierException, InterruptedException, IOException {

        scanner = new Scanner(System.in);

        System.out.println("Enter x and y Grid dim: ");
        xGrid = scanner.nextInt();
        yGrid = xGrid;
        System.out.println("Enter n° of walls: ");
        nOfWalls = scanner.nextInt();
        System.out.println("Enter n° of Agents: ");
        nAgents = scanner.nextInt();
        System.out.println("Enter n° of Nodes: ");
        nNodes = scanner.nextInt();
        nRun = 20.;

        nMoves = new int[nAgents];
        totalMoves = new int[nAgents][1]; //alla fine avrò la somma di tutte le mosse fatte durante tutti i run per raggiungere l'obiettivo
        meanMoves = new double[nAgents];
        meanCoopMoves = new double[1];

        meanCoopTimes = new long[1];

        newWorld = new GridWorld(xGrid, yGrid);
        walls = new Walls(nOfWalls);
        state = new States();

        newWorld.defineWorld();

        newWorld.defineGridValues();

        state.defineStates(newWorld);

        newWorld.fillGridWorldWalls(xGrid, yGrid, walls);

        // il mondo è identico per tutti i run, la definizione dei nodi e degli agenti è casuale

        agents = new Agents[nAgents];
        nodes = new Nodes[nNodes];


        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Nodes();
            newWorld.fillGridWorldNodes(nodes[i]);
        }

        for (int i = 0; i < agents.length; i++) { // creo gli agenti, ogni agente conosce tutti gli obiettivi disponibili
            agents[i] = new Agents(i, xGrid, yGrid);
            newWorld.defineFirstAgentPositions(agents[i], newWorld, walls);
            for (int j = 0; j < nodes.length; j++) {
                agents[i].nodesStatesPositions[j] = nodes[j].getNodeCurrentState();
                agents[i].nodesPositionsX[j] = nodes[j].getPositionNodeX();
                agents[i].nodesPositionsY[j] = nodes[j].getPositionNodeY();
            }
        }
        System.out.println("World initialized: ");
        printWorld(newWorld);
        System.out.print("\n");
        System.out.println("0: FREE_CELL\t1: WALL_CELL\n2: GOAL_CELL\t3: AGENT_CELL");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Exploration map started");
        System.out.println("Random policy");
        for (int i = 0; i < nAgents; i++) {
            q = new QLearning(agents[i], nodes, walls);
            q.runMultiAgent(agents[i], state, newWorld, nTrains);
        }
        System.out.println("Exploration map done");
        count = 0;
        while (true) {
            if (count == nAgents) {
                break;
            }
            newWorld.copyWorld();
            for (int i = 0; i < nAgents; i++) {
                if (agents[i].goalReached == false) {
                    if (typeTest == "single")
                        q.testQlearning(agents[i], state, newWorld, typePolicy, epsilonValue);
                    else if (typeTest == "coop")
                        q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                    else if (typeTest == "truthPolicy")
                        q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                    nMoves[i]++;
                    System.out.println("-------------------------------------------------------------------------------------");
                }
            }
            System.out.println("-------------------------------------------------------------------------------------");
            printWorld(newWorld);
            newWorld.updateWorld();

            for (int j = 0; j < nAgents; j++)
                newWorld.copyRangeAntennaWorld(agents[j], newWorld);
        }

        for (int i = 0; i < nAgents; i++) {
            totalMoves[i][0] += nMoves[i];
            nMoves[i] = 0;
        }
        for (int i = 0; i < meanMoves.length; i++)
            meanMoves[i] = totalMoves[i][0] / nRun;

        double sumMeansMoves = 0;

        for (int i = 0; i < meanMoves.length; i++)
            sumMeansMoves += meanMoves[i];

        meanCoopMoves[0] = sumMeansMoves / nAgents;
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        printWorld(newWorld);

        System.out.println("Previous states");
        for (int i = 0; i<nAgents;i++){
            System.out.println("Agent " + i + ": " + agents[i].getPreviousStates());
        }

        return meanCoopMoves;
    }

    public long[] runModelTimes(int nTrains, String typeTest, String typePolicy, double epsilonValue) throws BrokenBarrierException, InterruptedException, IOException {

        xGrid = 30;
        yGrid = 30;
        nOfWalls = 30;
        nAgents = 8;
        nNodes = 1;
        nRun = 20.;

        times = new long[nAgents];
        totalTimes = new long[nAgents][1];

        meanTimes = new long[nAgents];
        meanCoopTimes = new long[1];


        newWorld = new GridWorld(xGrid, yGrid);
        walls = new Walls(nOfWalls);
        state = new States();

        newWorld.defineWorld();

        newWorld.defineGridValues();

        state.defineStates(newWorld);

        newWorld.fillGridWorldWalls(xGrid, yGrid, walls);

        // il mondo è identico per tutti i run, la definizione dei nodi e degli agenti è casuale
        for (int k = 0; k < (int) nRun; k++) {

            agents = new Agents[nAgents];
            nodes = new Nodes[nNodes];


            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Nodes();
                newWorld.fillGridWorldNodes(nodes[i]);
            }

            for (int i = 0; i < agents.length; i++) { // creo gli agenti, ogni agente conosce tutti gli obiettivi disponibili
                agents[i] = new Agents(i, xGrid, yGrid);
                newWorld.defineFirstAgentPositions(agents[i], newWorld, walls);
                for (int j = 0; j < nodes.length; j++) {
                    agents[i].nodesStatesPositions[j] = nodes[j].getNodeCurrentState();
                    agents[i].nodesPositionsX[j] = nodes[j].getPositionNodeX();
                    agents[i].nodesPositionsY[j] = nodes[j].getPositionNodeY();
                }
            }
            long BEGIN, END;

            for (int i = 0; i < nAgents; i++) {
                q = new QLearning(agents[i], nodes, walls);
                q.runMultiAgent(agents[i], state, newWorld, nTrains);
            }
            count = 0;
            while (true) {
                if (count == nAgents) {
                    break;
                }
                newWorld.copyWorld();
                for (int i = 0; i < nAgents; i++) {
                    if (agents[i].goalReached == false) {
                        BEGIN = System.nanoTime();
                        if (typeTest == "single")
                            q.testQlearning(agents[i], state, newWorld, typePolicy, epsilonValue);
                        else if (typeTest == "coop")
                            q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                        else if (typeTest == "truthPolicy")
                            q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                        END = System.nanoTime();
                        times[i] = END - BEGIN;
                        //System.out.println("-------------------------------------------------------------------------------------");
                    }
                }
                //System.out.println("-------------------------------------------------------------------------------------");
                newWorld.updateWorld();

                for (int j = 0; j < nAgents; j++)
                    newWorld.copyRangeAntennaWorld(agents[j], newWorld);
            }
            for (int i = 0; i < nAgents; i++) {
                totalTimes[i][0] += times[i];
                times[i] = 0;
            }
        }
        meanCoopTimes[0] = 0;

        for (int i = 0; i < totalTimes.length; i++)
            meanCoopTimes[0] += totalTimes[i][0];

        meanCoopTimes[0] = (long) ((meanCoopTimes[0] / nAgents) / nRun);

        return meanCoopTimes;
    }
}
