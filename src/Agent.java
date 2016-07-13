
/**
 * Created by proietfb on 7/13/16.
 */
public class Agent {

    public static final int maxSignal = 4;
    int dimGridX, dimGridY;

    int startPositionAgentX, startPositionAgentY;
    int currentPositionX, currentPositionY;
    int signalPower = maxSignal;

    public Agent(int dimGridX, int dimGridY) {
        this.dimGridX = dimGridX;
        this.dimGridY = dimGridY;
        currentPositionX = startPositionAgentX;
        currentPositionY = startPositionAgentY;
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


}
