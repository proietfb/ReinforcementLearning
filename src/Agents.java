import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class Agents {

    public static final int maxSignal = 2;
    static int dimGridX, dimGridY;

    private int agentName;

    private int statesCount;

    ArrayList<Integer> previousStates;
    public int[] nodesStatesPositions;
    private double[][] Q;

    private int startPositionAgentX, startPositionAgentY, currentPositionX, currentPositionY;
    private int currentState, startState;
    private int signalPower;
    private int nNeighbourDiscovered;
    private int nGoalDiscovered;

    public boolean goalReached;

    Antenna antenna;

    public Agents(int agentName,int dimGridX, int dimGridY) {
        this.agentName = agentName;
        this.dimGridX = dimGridX;
        this.dimGridY = dimGridY;

        statesCount = getDimGridX() * getDimGridY();
        previousStates = new ArrayList<>();
        nNeighbourDiscovered = 0;
        nGoalDiscovered = 0;
        goalReached = false;
        nodesStatesPositions = new int[Model.nNodes];
        antenna = new Antenna();
        Q = new double[statesCount][statesCount];
        defineQ();
    }

    private void defineQ() {
        for (double[] i : Q) {
            Arrays.fill(i, 0);
        }
    }

    public void searchNeighbours(GridWorld gridWorld){
        antenna.discoverUP(gridWorld.getCopyGridW(),getCurrentPositionX(),getCurrentPositionY(),maxSignal);
        antenna.discoverDown(gridWorld.getCopyGridW(),getCurrentPositionX(),getCurrentPositionY(),maxSignal);
        antenna.discoverLeft(gridWorld.getCopyGridW(),getCurrentPositionX(),getCurrentPositionY(),maxSignal);
        antenna.discoverRight(gridWorld.getCopyGridW(),getCurrentPositionX(),getCurrentPositionY(),maxSignal);
    }

    public int getDimGridX() {
        return dimGridX;
    }

    public int getDimGridY() {
        return dimGridY;
    }

    public int getAgentName() {return agentName; }

    public int getStartPositionAgentX() {
        return startPositionAgentX;
    }

    public int getStartPositionAgentY() {
        return startPositionAgentY;
    }

    public int getSignalPower() {
        return signalPower;
    }

    public int getCurrentPositionX() {
        return currentPositionX;
    }

    public int getCurrentPositionY() {
        return currentPositionY;
    }

    public int getCurrentState() {
        return currentState;
    }

    public ArrayList<Integer> getPreviousStates() {
        return previousStates;
    }

    public int getnNeighbourDiscovered() {
        return nNeighbourDiscovered;
    }

    public int getnGoalDiscovered() {
        return nGoalDiscovered;
    }

    public void setStartPositionAgentX(int startPositionAgentX) {
        this.startPositionAgentX = startPositionAgentX;
    }

    public void setStartPositionAgentY(int startPositionAgentY) {
        this.startPositionAgentY = startPositionAgentY;
    }

    public void setCurrentPositionX(int currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    public void setCurrentPositionY(int currentPositionY) {
        this.currentPositionY = currentPositionY;
    }

    public void setSignalPower(int signalPower) {
        this.signalPower = signalPower;
    }

    public int getStartState() {
        return startState;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public void setCurrentState(int currentState) {this.currentState = currentState;}

    public void setnNeighbourDiscovered(int nNeighbourDiscovered) {
        this.nNeighbourDiscovered = nNeighbourDiscovered;
    }

    public void setnGoalDiscovered(int nGoalDiscovered) {
        this.nGoalDiscovered = nGoalDiscovered;
    }

    public int[] getNodesStatesPositions() {
        return nodesStatesPositions;
    }

    public void setNodesStatesPositions(int[] nodesStatesPositions, int position) {
        this.nodesStatesPositions[position] = nodesStatesPositions[position];
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
}
