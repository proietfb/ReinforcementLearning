import org.jfree.ui.RefineryUtilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;


/**
 * Created by proietfb on 7/13/16.
 */
public class Model {

    static int xGrid, yGrid, nOfWalls, nAgents, nNodes;
    double nRun;
    Agents[] agents;
    Nodes[] nodes;
    GridWorld newWorld;
    Walls walls;
    States state;
    QLearning q;
    static int count;
    int[] nMoves;
    int[][] totalMoves;

    long[] times;
    long[][] totalTimes;

    double[] meanMoves;

    public Model() {
    }

    public double[] runModel(int nTrains) throws BrokenBarrierException, InterruptedException, IOException {

        xGrid = 30;
        yGrid = 30;
        nOfWalls = 70;
        nAgents = 1;
        nNodes = 1;
        nRun = 50.;

        times = new long[nAgents];
        totalTimes = new long[nAgents][1];
        nMoves = new int[nAgents];
        totalMoves = new int[nAgents][1]; //alla fine avrò la somma di tutte le mosse fatte durante tutti i run per raggiungere l'obiettivo
        meanMoves = new double[nAgents];

        for (int k = 0; k < (int)nRun; k++) {

            newWorld = new GridWorld(xGrid, yGrid);
            walls = new Walls(nOfWalls);
            agents = new Agents[nAgents];
            nodes = new Nodes[nNodes];
            state = new States();

            newWorld.defineWorld();

            newWorld.defineGridValues();

            state.defineStates(newWorld);

            newWorld.fillGridWorldWalls(xGrid, yGrid, walls);


            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Nodes();
                newWorld.fillGridWorldNodes(nodes[i]);
            }
            System.out.println("DEBUG " + k);
            for (int i = 0; i < agents.length; i++) { // creo gli agenti, ogni agente conosce tutti gli obiettivi disponibili
                agents[i] = new Agents(i, xGrid, yGrid);
                newWorld.defineFirstAgentPositions(agents[i], newWorld, walls);
                System.out.println("agente " + i + " creato nello stato " + agents[i].getCurrentState());
                for (int j = 0; j < nodes.length; j++) {
                    agents[i].nodesStatesPositions[j] = nodes[j].getNodeCurrentState();
                    agents[i].nodesPositionsX[j] = nodes[j].getPositionNodeX();
                    agents[i].nodesPositionsY[j] = nodes[j].getPositionNodeY();
                }
            }

            System.out.println(Arrays.deepToString(newWorld.getGridValues()) + "\n");

            System.out.println("obiettivi creati: " + Arrays.toString(agents[0].nodesStatesPositions));

            long BEGIN, END;

            System.out.println("Fase di Learning da parte degli agenti iniziata");

            for (int i = 0; i < nAgents; i++) {
                q = new QLearning(agents[i], nodes, walls);
                q.runMultiAgent(agents[i], state, newWorld, nTrains);
                System.out.println("agent " + i + ": " + agents[i].getCurrentState());
            }

            System.out.println("Fase di Learning da parte degli agenti conclusa");

            System.out.println("Stato dell'ambiente alla fine della fase di Learning");

            for (int i = 0; i < agents.length; i++)
                System.out.println("stato dell'agente " + i + " " + agents[i].getCurrentState());
            //printWorld(newWorld);

            System.out.println("Inizio Fase di testing \n");

            count = 0;
            while (true) {
                if (count == nAgents) {
                    break;
                }
                newWorld.copyWorld();
                System.out.println("Copia dell'ambiene effettuata");
                for (int i = 0; i < nAgents; i++) {
                    if (agents[i].goalReached == false) {
                        BEGIN = System.nanoTime();
                        System.out.println(agents[i].getAgentName() + ": ho fatto la mossa");
                        q.testQlearning(agents[i], agents, state, newWorld);
                        nMoves[i]++;
                        END = System.nanoTime();
                        times[i] = END-BEGIN;
                        System.out.println("-------------------------------------------------------------------------------------");
                    }
                }
                System.out.println("-------------------------------------------------------------------------------------");
                newWorld.updateWorld();
                System.out.println("Update dell'ambiente effettuata");

                for (int j = 0; j < nAgents; j++)
                    newWorld.copyRangeAntennaWorld(agents[j], newWorld);
            }

            for (int i = 0;i<nAgents;i++) {
                totalTimes[i][0] += times[i];
                totalMoves[i][0] += nMoves[i];
                nMoves[i] = 0;
                times[i] = 0;
            }
            for (int i = 0;i<meanMoves.length;i++){
                meanMoves[i] = totalMoves[i][0]/nRun;
                System.out.println(meanMoves[i]);
            }


        }
        int sum = 0;
        for (int i = 0;i<totalTimes.length;i++)
            sum += totalTimes[i][0];

        System.out.println("TotalTime: " + sum / 1000000000.0 + " sec.");

        for (int i = 0;i<nAgents;i++) {
            System.out.println("TotalMoves dell'agente: " + i + ": " + totalMoves[i][0]);
            System.out.println("TotalTimes dell'agente: " + i + ": " + totalTimes[i][0]);
        }

        return meanMoves;

    }




    public static void main(String args[]) throws BrokenBarrierException, InterruptedException, IOException {
        ArrayList<double[]> outputRunsNMoves = new ArrayList<>();
        int[] nTrains = new int[10];
        int step = 500;
        for (int i =0;i<nTrains.length;i++) {
            nTrains[i] = step;
            step +=50;
        }
        for (int i = 0; i<nTrains.length;i++) {
            outputRunsNMoves.add(new Model().runModel(nTrains[i]));
        }
        Graphics chart = new Graphics("Individual Graphic", "maxNextInt policy",outputRunsNMoves,nTrains);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);


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

    private static void printRangeAntennaCopy(GridWorld gridWorld) {
        System.out.println("ciaoCopy");
        for (int i = 0; i < gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy().length; i++) {
            for (int j = 0; j < gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy()[0].length; j++) {
                System.out.print(gridWorld.linkToAntennaMatrix[0].getGridRangeAntennaCopy()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}
