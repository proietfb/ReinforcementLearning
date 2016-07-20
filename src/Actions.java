import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by proietfb on 7/13/16.
 */
public class Actions {

    public static final int N_ACTIONS = 6;


    public Actions() {

    }

    public void possibleActionsFromState(Agent agent, int step, GridWorld gridWorld) {
        if (agent.getCurrentState() == agent.previousStates.get(step - 1) + 1) //mossa a dx
            moveRight(agent, gridWorld);
        else if (agent.getCurrentState() == agent.previousStates.get(step - 1) - 1) // mossa a sx
            moveLeft(agent, gridWorld);
        else if (agent.getCurrentState() == agent.previousStates.get(step - 1) + agent.getDimGridX()) //in basso
            moveDown(agent, gridWorld);
        else
            moveUp(agent, gridWorld);
    }

    public void moveUp(Agent agent, GridWorld gridWorld) {
        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionX(agent.getCurrentPositionX() - 1);
        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveDown(Agent agent, GridWorld gridWorld) {

        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionX(agent.getCurrentPositionX() + 1);
        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveRight(Agent agent, GridWorld gridWorld) {

        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionY(agent.getCurrentPositionY() + 1);
        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveLeft(Agent agent, GridWorld gridWorld) {

        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionY(agent.getCurrentPositionY() - 1);
        gridWorld.gridW[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;

    }

    public void actionIncrementSignal(Agent agent) {

        if (agent.getSignalPower() != Agent.maxSignal)
            agent.setSignalPower(agent.getSignalPower() + 1);
    }

    public void actionDecrementSignal(Agent agent) {

        if (agent.getSignalPower() != 0)
            agent.setSignalPower(agent.getSignalPower() - 1);
    }
}
