import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    final DecimalFormat df;
    int statesCount;
    double alpha = 0.1;
    double gamma = 0.9;

    public int[][] R;
    public double[][] Q;

    public QLearning(Agent agent, Node node, GridWorld gridWorld) {
        df = new DecimalFormat("#.##");
        statesCount = agent.getDimGridX() * agent.getDimGridY();
        R = new int[statesCount][statesCount];
        Q = new double[statesCount][statesCount];
        initRewardValues(agent, node, gridWorld);
        defineQ();
    }

    private void initRewardValues(Agent agent, Node node, GridWorld gridWorld) { //definisce i reward massimi di base nelle posizioni accanto al goal

        for (int[] i : R) //inizializzo la matrice dei reward tutti a zero
            Arrays.fill(i, 1);

        if (node.getPositionNodeX() == 0)
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX() + 1][node.getPositionNodeY()]] = 100;
        else if (node.getPositionNodeX() == (agent.getDimGridX() - 1))
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX() - 1][node.getPositionNodeY()]] = 100;
        else {
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX() - 1][node.getPositionNodeY()]] = 100;
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX() + 1][node.getPositionNodeY()]] = 100;
        }
        if (node.getPositionNodeY() == 0)
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY() + 1]] = 100;
        else if (node.getPositionNodeX() == (agent.getDimGridX() - 1))
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY() - 1]] = 100;
        else {
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY() + 1]] = 100;
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY() - 1]] = 100;
        }

    }

    private void defineQ() {
        for (double[] i : Q) {
            Arrays.fill(i, 0);
        }
    }

    private double maxQ(Agent agent, States state) {
        double maxVal = 0;
        for (int i = 0; i < state.definePossibleStates.get(agent.getCurrentState()).size(); i++) {
            int nextState = state.definePossibleStates.get(agent.getCurrentState()).get(i);
            double value = Q[agent.getCurrentState()][nextState];
            if (value >= maxVal)
                maxVal = value;
        }
        return maxVal;
    }

    private int policy(Agent agent, States state) {
        double maxVal = Double.MIN_VALUE;
        int policyGoToState = agent.getCurrentState();
        for (int i = 0; i < state.definePossibleStates.get(agent.getCurrentState()).size(); i++) {
            int nextState = state.definePossibleStates.get(agent.getCurrentState()).get(i);
            double value = Q[agent.getCurrentState()][nextState];
            if (value >= maxVal) {
                maxVal = value;
                policyGoToState = nextState;
            }
        }
        return policyGoToState;
    }

    public void run(Agent agent, States states, Node node, GridWorld gridWorld, Actions actions) {
        Random ran = new Random();

        for (int i = 0; i < 10; i++) {
            agent.previousStates.clear();
            agent.setCurrentState(agent.getStartState());
            System.out.println("init state: " + agent.getCurrentState());
            agent.previousStates.add(agent.getCurrentState());
            //System.out.println("run" + i);
            int state = agent.getCurrentState();
            while (state != node.getNodeCurrentState()) {
                int[] actionsFromState = new int[states.definePossibleStates.get(state).size()];
                listToArray(actionsFromState, states, state);
                int index = ran.nextInt(actionsFromState.length);
                int action = actionsFromState[index];
                int nextState = action;
                double q = Q[state][action];
                double maxQ = maxQ(agent, states);
                int r = R[state][action];
                double value = q + alpha * (r + gamma * maxQ - q);
                setQ(state, action, value);
                state = nextState;
                agent.setCurrentState(state);
                agent.previousStates.add(agent.getCurrentState());
                //if (i>0)
                   // actions.possibleActionsFromState(agent, i, gridWorld);
                //System.out.println( "state: " + state);
                printWorld(gridWorld);
                System.out.println("---------------------------");
            }

            agent.setStartPositionAgentX((int) (0 + Math.random() * (gridWorld.getxGrid() - 1)));
            agent.setStartPositionAgentY((int) (0 + Math.random() * (gridWorld.getyGrid() - 1)));
            agent.setStartState(gridWorld.getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
            agent.setCurrentState(agent.getStartState());
            //printResult(agent);

        }

    }

    public int[] listToArray(int[] ret, States state, int currentState) {
        int i = 0;
        for (Integer e : state.definePossibleStates.get(currentState)) {
            ret[i++] = e.intValue();
        }
        return ret;
    }

    void printResult(Agent agent) {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("out from " + agent.getCurrentState() + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                System.out.print(df.format(Q[i][j]) + " ");
            }
            System.out.println();
        }
    }

    public static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public int getStatesCount() {
        return statesCount;
    }

    public int[][] getR() {
        return R;
    }

    public void setR(int[][] r) {
        R = r;
    }

    public double[][] getQ() {
        return Q;
    }

    public void setQ(int state, int action, double value) {
        Q[state][action] = value;
    }
}
