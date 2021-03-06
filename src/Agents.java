import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Agents {

    public static final int maxSignal = 2;
    static int dimGridX, dimGridY;
    public int[] nodesStatesPositions, nodesPositionsX, nodesPositionsY;
    public boolean goalReached;
    ArrayList<Integer> previousStates;
    Antenna antenna;
    private int agentName;
    private int statesCount;
    private double[][] Q;
    private int startPositionAgentX, startPositionAgentY, currentPositionX, currentPositionY;
    private int currentState, startState;

    public Agents(int agentName, int dimGridX, int dimGridY) {
        this.agentName = agentName;
        this.dimGridX = dimGridX;
        this.dimGridY = dimGridY;
        statesCount = getDimGridX() * getDimGridY();
        previousStates = new ArrayList<>();
        goalReached = false;
        nodesStatesPositions = new int[Model.nNodes];
        nodesPositionsX = new int[Model.nNodes];
        nodesPositionsY = new int[Model.nNodes];
        antenna = new Antenna();
        Q = new double[statesCount][statesCount];
        defineQ();
    }

    private void defineQ() {
        for (double[] i : Q) {
            Arrays.fill(i, 0);
        }
    }

    public void searchNeighbours(GridWorld gridWorld, Agents agent) {
        for (int i = 0; i < Model.nAgents; i++) {
            antenna.discoverUP(gridWorld.getLinkToAntennaMatrix()[i].getGridRangeAntenna(), getCurrentPositionX(), getCurrentPositionY(), maxSignal, agent);
            antenna.discoverDown(gridWorld.getLinkToAntennaMatrix()[i].getGridRangeAntenna(), getCurrentPositionX(), getCurrentPositionY(), maxSignal, agent);
            antenna.discoverLeft(gridWorld.getLinkToAntennaMatrix()[i].getGridRangeAntenna(), getCurrentPositionX(), getCurrentPositionY(), maxSignal, agent);
            antenna.discoverRight(gridWorld.getLinkToAntennaMatrix()[i].getGridRangeAntenna(), getCurrentPositionX(), getCurrentPositionY(), maxSignal, agent);
        }
    }

    public int getDimGridX() {
        return dimGridX;
    }

    public int getDimGridY() {
        return dimGridY;
    }

    public int getAgentName() {
        return agentName;
    }

    public int getStartPositionAgentX() {
        return startPositionAgentX;
    }

    public void setStartPositionAgentX(int startPositionAgentX) {
        this.startPositionAgentX = startPositionAgentX;
    }

    public int getStartPositionAgentY() {
        return startPositionAgentY;
    }

    public void setStartPositionAgentY(int startPositionAgentY) {
        this.startPositionAgentY = startPositionAgentY;
    }

    public int getCurrentPositionX() {
        return currentPositionX;
    }

    public void setCurrentPositionX(int currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    public int getCurrentPositionY() {
        return currentPositionY;
    }

    public void setCurrentPositionY(int currentPositionY) {
        this.currentPositionY = currentPositionY;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public int getStartState() {
        return startState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public int[] getNodesStatesPositions() {
        return nodesStatesPositions;
    }

    public double[][] getQ() {
        return Q;
    }

    public void setQ(int state, int action, double value) {
        Q[state][action] = value;
    }

    public int getStatesCount() {
        return statesCount;
    }

    public ArrayList<Integer> getPreviousStates() {
        return previousStates;
    }
}
