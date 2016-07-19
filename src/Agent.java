import java.util.ArrayList;

/**
 * Created by proietfb on 7/13/16.
 */
public class Agent {

    public static final int maxSignal = 4;
    static int dimGridX, dimGridY;

    ArrayList<Integer> previousStates;

    int startPositionAgentX, startPositionAgentY;
    int currentPositionX, currentPositionY;
    int currentState, startState;
    int signalPower;
    int nNeighbourDiscovered;
    int nGoalDiscovered;

    public Agent(int dimGridX, int dimGridY) {
        this.dimGridX = dimGridX;
        this.dimGridY = dimGridY;
        previousStates = new ArrayList<>();
        nNeighbourDiscovered = 0;
        nGoalDiscovered = 0;
    }

    public int currentState(GridWorld gridWorld){
        startState =gridWorld.getGridValues()[getStartPositionAgentX()][getStartPositionAgentY()];
        currentState = gridWorld.getGridValues()[currentPositionX][currentPositionY];
        return currentState;
    }

    public ArrayList<Integer> listOfMoves (GridWorld gridWorld){

        previousStates.add(currentState);
        return previousStates;
    }

    public int getDimGridX() {
        return dimGridX;
    }

    public int getDimGridY() {
        return dimGridY;
    }

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

    public void setPreviousStates(ArrayList<Integer> previousStates) {
        this.previousStates = previousStates;
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

    public void setCurrentState(int currentState) {
        this.currentState = currentState;

    }



    public void setnNeighbourDiscovered(int nNeighbourDiscovered) {
        this.nNeighbourDiscovered = nNeighbourDiscovered;
    }

    public void setnGoalDiscovered(int nGoalDiscovered) {
        this.nGoalDiscovered = nGoalDiscovered;
    }
}
