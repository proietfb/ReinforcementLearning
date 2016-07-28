import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    final DecimalFormat df;
    int statesCount;
    double alpha = 0.5;
    double gamma = 0.9;

    public int[][] R;
    public double[][] Q;

    public QLearning(Agent agent, Node node, GridWorld gridWorld) {
        df = new DecimalFormat("#.##");
        statesCount = agent.getDimGridX() * agent.getDimGridY();
        R = new int[statesCount][statesCount];
        Q = new double[statesCount][statesCount];
        initRewardValues(node);
        defineQ();
    }

    private void initRewardValues(Node node) { //definisce i reward massimi di base nelle posizioni accanto al goal

        for (int[] i : R) //inizializzo la matrice dei reward tutti a zero
            Arrays.fill(i, 0);

        if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == 0){ //se il punto finale è in 0,0, il reward viene dal basso verso l'alto e da dx verso sx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 100; // dx sx
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100;  //basso alto
        } else if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == Agent.dimGridY-1) { //punto finale in 0,n il reward viene dal basso verso l'alto e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; //sx dx
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100; // basso alto
        } else if (node.getPositionNodeX() == Agent.dimGridY - 1 && node.getPositionNodeY() == 0){ //punto finale in n,0 reward dall alto verso il basso e dadx verso sx
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; // alto basso
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 100; //dx sx
        } else if (node.getPositionNodeX() == Agent.dimGridY - 1 && node.getPositionNodeY() == Agent.dimGridY-1) { // punto finale in n,n reward dall alto verso il basso e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; // sx dx
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; // alto basso
        } else if((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agent.dimGridX - 1) && node.getPositionNodeY() == 0){ //prima colonna
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; //alto basso
            R[node.getNodeCurrentState() + 1 ][node.getNodeCurrentState()] = 100; //dx sx
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100;  //basso alto
        } else if ((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agent.dimGridX - 1) && node.getPositionNodeY() == Agent.dimGridY - 1){ //ultima colonna
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; //alto basso
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; // sx dx
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100;  //basso alto
        } else if (node.getPositionNodeX() == 0 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agent.dimGridY - 1)) { //prima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 100; // dx sx
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100;  //basso alto
        } else if (node.getPositionNodeX() == Agent.dimGridX - 1 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agent.dimGridY - 1)){ //ultima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 100; // dx sx
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; //alto basso
        } else { // in mezzo
            R[node.getNodeCurrentState() + Agent.dimGridX][node.getNodeCurrentState()] = 100;  //basso alto
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 100; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 100; // dx sx
            R[node.getNodeCurrentState() - Agent.dimGridX][node.getNodeCurrentState()] = 100; //alto basso
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
        double maxVal = 0;
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

        for (int i = 0; i < 10000; i++) {
            agent.previousStates.clear();
            agent.setCurrentState(agent.getStartState());
            agent.previousStates.add(agent.getCurrentState());
            int state = agent.getCurrentState();

            if (i < 9999) {

                while (state != node.getNodeCurrentState()) {
                    int[] actionsFromState = new int[states.definePossibleStates.get(state).size()];
                    listToArray(actionsFromState, states, state);
                    int index = ran.nextInt(actionsFromState.length);
                    int action = actionsFromState[index];
                    int nextState = action;
                    double q = Q[state][action];
                    double maxQ = maxQ(agent, states);
                    int r = R[state][action];
                    double value = q + alpha * (r + (gamma * maxQ) - q);
                    setQ(state, action, value);
                    state = nextState;
                    agent.setCurrentState(state);
                    agent.previousStates.add(agent.getCurrentState());
                }

//                agent.setStartPositionAgentX((int) (0 + Math.random() * (gridWorld.getxGrid() - 1)));
//                agent.setStartPositionAgentY((int) (0 + Math.random() * (gridWorld.getyGrid() - 1)));
//                agent.setStartState(gridWorld.getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
//                agent.setCurrentState(agent.getStartState());
                //printResult(agent);

            }
            else {
                System.out.println(Arrays.deepToString(getQ()));
                //agent.previousStates.clear();
                while (state != node.getNodeCurrentState()){
                    int[] actionFromState = new int[states.definePossibleStates.get(state).size()];
                    listToArray(actionFromState,states,state);
                    double maxVal = 0;
                    int action = actionFromState[ran.nextInt(actionFromState.length)];
                    for (int j = 0; j < actionFromState.length; j++){
                        int nextState = actionFromState[j];
                        double val = Q[state][nextState];
                        if (val > maxVal){
                            maxVal = val;
                            action = nextState;
                        }
                    }
                    state = action;
                    agent.setCurrentState(state);
                    agent.previousStates.add(agent.getCurrentState());
                    //System.out.println(agent.previousStates);
                }
            }

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
