
/**
 * Created by proietfb on 7/13/16.
 */
public class Agent {

    public static final int maxSignal = 4;
    int dimGridX, dimGridY;

    int startPositionAgentX, startPositionAgentY;
    int currentPositionX, currentPositionY;
    int currentState;
    int signalPower;
    int nNeighbourDiscovered;
    int nGoalDiscovered;

    public Agent(int dimGridX, int dimGridY) {
        this.dimGridX = dimGridX;
        this.dimGridY = dimGridY;
        currentPositionX = startPositionAgentX;
        currentPositionY = startPositionAgentY;
        nNeighbourDiscovered = 0;
        nGoalDiscovered = 0;
    }

    public int currentState(GridWorld gridWorld){
        currentState = gridWorld.getGridValues()[currentPositionX][currentPositionY];
        return currentState;
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
