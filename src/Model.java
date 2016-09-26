import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

/**
 * Created by proietfb on 7/13/16.
 */
public class Model {

    static int xGrid, yGrid, nOfWalls, nAgents, nNodes;
    static int count;
    double nRun;
    Agents[] agents;
    Nodes[] nodes;
    GridWorld newWorld;
    Walls walls;
    States state;
    QLearning q;
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
        new Model().runModelMoves(500, "coop", "maxCoop", 0.0);
    }

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

        xGrid = 30;
        yGrid = 30;
        nOfWalls = 30;
        nAgents = 8;
        nNodes = 1;
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
                        //System.out.println(agents[i].getAgentName() + ": ho fatto la mossa");
                        if (typeTest == "single")
                            q.testQlearning(agents[i], state, newWorld, typePolicy, epsilonValue);
                        else if (typeTest == "coop")
                            q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                        else if (typeTest == "truthPolicy")
                            q.testQlearningCoop(agents[i], agents, state, newWorld, typePolicy, epsilonValue);
                        nMoves[i]++;
                        //System.out.println("-------------------------------------------------------------------------------------");
                    }
                }
                //System.out.println("-------------------------------------------------------------------------------------");
                newWorld.updateWorld();

                for (int j = 0; j < nAgents; j++)
                    newWorld.copyRangeAntennaWorld(agents[j], newWorld);
            }

            for (int i = 0; i < nAgents; i++) {
                totalMoves[i][0] += nMoves[i];
                nMoves[i] = 0;
            }
        }
        for (int i = 0; i < meanMoves.length; i++)
            meanMoves[i] = totalMoves[i][0] / nRun;

        double sumMeansMoves = 0;

        for (int i = 0; i < meanMoves.length; i++)
            sumMeansMoves += meanMoves[i];

        meanCoopMoves[0] = sumMeansMoves / nAgents;

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
