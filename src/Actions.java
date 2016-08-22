/**
 * Created by proietfb on 7/13/16.
 */

//////                 //////

//     forse inutile   //

/////                 //////


public class Actions {

    public static final int N_ACTIONS = 6;


    public Actions() {}

    public void possibleActionsFromState(Agents agent, int step, GridWorld gridWorld) {
//        if (agent.getCurrentState() == agent.previousStates.get(step - 1) + 1) //mossa a dx
//            moveRight(agent, gridWorld);
//        else if (agent.getCurrentState() == agent.previousStates.get(step - 1) - 1) // mossa a sx
//            moveLeft(agent, gridWorld);
//        else if (agent.getCurrentState() == agent.previousStates.get(step - 1) + agent.getDimGridX()) //in basso
//            moveDown(agent, gridWorld);
//        else
//            moveUp(agent, gridWorld);
    }

    public void updateAction(Agents agent, GridWorld gridWorld, int oldPositionX, int oldPositionY) {
        gridWorld.getGridW()[oldPositionX][oldPositionY] = GridWorld.FREE_CELL;
        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveDown(Agents agent, GridWorld gridWorld) {

        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionX(agent.getCurrentPositionX() + 1);
        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveRight(Agents agent, GridWorld gridWorld) {

        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionY(agent.getCurrentPositionY() + 1);
        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    public void moveLeft(Agents agent, GridWorld gridWorld) {

        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.FREE_CELL;
        agent.setCurrentPositionY(agent.getCurrentPositionY() - 1);
        gridWorld.getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;

    }

    public void actionIncrementSignal(Agents agent) {

        if (agent.getSignalPower() != Agents.maxSignal)
            agent.setSignalPower(agent.getSignalPower() + 1);
    }

    public void actionDecrementSignal(Agents agent) {

        if (agent.getSignalPower() != 0)
            agent.setSignalPower(agent.getSignalPower() - 1);
    }
}
