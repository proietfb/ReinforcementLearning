import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    final DecimalFormat df;
    double alpha = 0.2;
    double gamma = 0.8;

    Model model;

    private int[][] R;

    public QLearning(Agents agent, Nodes[] nodes, Walls walls) {
        df = new DecimalFormat("#.##");
        R = new int[agent.getStatesCount()][agent.getStatesCount()];
        initR();
        for (int i = 0; i < nodes.length; i++)
            initRewardNodes(nodes[i]);
        initRewardWalls(walls);
    }

    public static void printWorld(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridW().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                System.out.print(gridWorld.getGridW()[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private void initRewardWalls(Walls walls) {
        for (int i = 0; i < walls.getnOfWalls(); i++) {
            if (walls.getWallsStatesPositions()[i] == 0) { //0,0
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; //basso alto
            } else if (walls.getWallsStatesPositions()[i] == ((1 * Agents.dimGridY) - 1)) { //0,n
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // basso alto
            } else if (walls.getWallsStatesPositions()[i] == ((Agents.dimGridX * Agents.dimGridY) - Agents.dimGridX)) { //n,0
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
            } else if (walls.getWallsStatesPositions()[i] == ((Agents.dimGridX * Agents.dimGridY) - 1)) { //n,n
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
            } else if ((walls.getWallPositionX()[i] > 0 && walls.getWallPositionX()[i] < Agents.dimGridX - 1) && walls.getWallPositionY()[i] == 0) { //prima colonna
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // basso alto
            } else if ((walls.getWallPositionX()[i] > 0 && walls.getWallPositionX()[i] < Agents.dimGridX - 1) && walls.getWallPositionY()[i] == Agents.dimGridY - 1) { //ultima colonna
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // basso alto
            } else if (walls.getWallPositionX()[i] == 0 && (walls.getWallPositionY()[i] > 0 && walls.getWallPositionY()[i] < Agents.dimGridY - 1)) { //prima riga
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // basso alto
            } else if (walls.getWallPositionX()[i] == Agents.dimGridX - 1 && (walls.getWallPositionY()[i] > 0 && walls.getWallPositionY()[i] < Agents.dimGridY - 1)) { //ultima riga
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
            } else {
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // basso alto
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -10; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -10; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -10; // alto basso
            }
        }
    }

    private void initR() {
        for (int[] i : R) //inizializzo la matrice dei reward tutti a zero
            Arrays.fill(i, 0);
    }

    private void initRewardNodes(Nodes node) { //definisce i reward massimi di base nelle posizioni accanto al goal
        if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == 0) { //se il punto finale è in 0,0, il reward viene dal basso verso l'alto e da dx verso sx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == Agents.dimGridY - 1) { //punto finale in 0,n il reward viene dal basso verso l'alto e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; //sx dx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10; // basso alto
        } else if (node.getPositionNodeX() == Agents.dimGridY - 1 && node.getPositionNodeY() == 0) { //punto finale in n,0 reward dall alto verso il basso e dadx verso sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; // alto basso
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; //dx sx
        } else if (node.getPositionNodeX() == Agents.dimGridY - 1 && node.getPositionNodeY() == Agents.dimGridY - 1) { // punto finale in n,n reward dall alto verso il basso e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; // alto basso
        } else if ((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agents.dimGridX - 1) && node.getPositionNodeY() == 0) { //prima colonna
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; //dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if ((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agents.dimGridX - 1) && node.getPositionNodeY() == Agents.dimGridY - 1) { //ultima colonna
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == 0 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agents.dimGridY - 1)) { //prima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == Agents.dimGridX - 1 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agents.dimGridY - 1)) { //ultima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
        } else { // in mezzo
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
        }

    }

    private double maxQ(States states, Agents agent, int state) {
        int[] actionsFromNextState = new int[states.getDefinePossibleStates().get(state).size()];
        listToArray(actionsFromNextState, states, state);
        double maxVal = 0;
        for (int i = 0; i < actionsFromNextState.length; i++) {
            int nextState = actionsFromNextState[i];
            double value = agent.getQ()[state][nextState];
            if (value >= maxVal)
                maxVal = value;
        }
        return maxVal;
    }

    private int checkValueMax(ArrayList<Double> arrayList) { // confrontando il valore Q corrispondente, ritorna la posizione della lista dove si trova il valore Q maggiore
        double valueCheck = arrayList.get(1);
        int position = 1;
        if (arrayList.size() > 2) {
            for (int i = 3; i < arrayList.size(); i++) {
                if (i % 2 != 0) {
                    if (arrayList.get(i) > valueCheck) {
                        valueCheck = arrayList.get(i);
                        position += 2;
                    }
                }
            }
            return position;
        } else
            return position;
    }

    private double[] policyMaxNextVal(Agents agent, States state, int[] array) {
        Random ran = new Random();
        double maxVal = 0;
        double[] stateQ = new double[2];
        int policyGoToState;
        for (int i = 0; i < state.getDefinePossibleStates().get(agent.getCurrentState()).size(); i++) {
            int nextState = array[i];
            double value = agent.getQ()[agent.getCurrentState()][nextState];
            if (value > maxVal) {
                maxVal = value;
                policyGoToState = nextState;
                stateQ[0] = policyGoToState;
                stateQ[1] = maxVal;
            }
        }
        if (maxVal == 0) {
            int index = ran.nextInt(array.length);
            int action = array[index];
            stateQ[0] = action;
            stateQ[1] = maxVal;
            return stateQ;
        } else
            return stateQ;
    }

    private double[] policyCoopAgentsMaxNextVal(Agents[] agenteses, Agents agent, States state, int[] array, int name) {
        Random ran = new Random();
        double maxVal = 0;
        int policyGoToState;
        double[] stateQ = new double[2];
        for (int i = 0; i < state.getDefinePossibleStates().get(agent.getCurrentState()).size(); i++) {
            int nextState = array[i];
            double value = agenteses[name].getQ()[agent.getCurrentState()][nextState];
            if (value > maxVal) {
                maxVal = value;
                policyGoToState = nextState;
                stateQ[0] = policyGoToState;
                stateQ[1] = maxVal;
            }
        }
        if (maxVal == 0) {
            int index = ran.nextInt(array.length);
            int action = array[index];
            stateQ[0] = action;
            stateQ[1] = maxVal;
            return stateQ;
        } else
            return stateQ;
    }

    private double[] policyEpsilonGreedy(Agents agent, States state, int[] array, double epsilonValue) {
        Random rand = new Random();
        double epsilon = epsilonValue;
        double greedy = rand.nextDouble();
        double maxVal = 0;
        double[] stateQ = new double[2];
        int policyGoToState;
        if (greedy <= epsilon) {
            for (int i = 0; i < state.getDefinePossibleStates().get(agent.getCurrentState()).size(); i++) {
                int nextState = array[i];
                double value = agent.getQ()[agent.getCurrentState()][nextState];
                if (value > maxVal) {
                    maxVal = value;
                    policyGoToState = nextState;
                    stateQ[0] = policyGoToState;
                    stateQ[1] = maxVal;
                }
            }
            if (maxVal == 0) {
                int index = rand.nextInt(array.length);
                int action = array[index];
                stateQ[0] = action;
                stateQ[1] = maxVal;
                return stateQ;
            } else
                return stateQ;
        } else {
            int index = rand.nextInt(array.length);
            int action = array[index];
            stateQ[0] = action;
            stateQ[1] = agent.getQ()[agent.getCurrentState()][action];
            return stateQ;
        }
    }

    private double[] policyEpsilonGreedyCoop(Agents[] agentses, Agents agent, States state, int[] array, int name, double epsilonValue) {
        Random rand = new Random();
        double epsilon = epsilonValue;
        double greedy = rand.nextDouble();
        double maxVal = 0;
        double[] stateQ = new double[2];
        int policyGoToState;
        if (greedy <= epsilon) {
            for (int i = 0; i < state.getDefinePossibleStates().get(agent.getCurrentState()).size(); i++) {
                int nextState = array[i];
                double value = agentses[name].getQ()[agent.getCurrentState()][nextState];
                if (value > maxVal) {
                    maxVal = value;
                    policyGoToState = nextState;
                    stateQ[0] = policyGoToState;
                    stateQ[1] = maxVal;
                }
            }
            if (maxVal == 0) {
                int index = rand.nextInt(array.length);
                int action = array[index];
                stateQ[0] = action;
                stateQ[1] = maxVal;
                return stateQ;
            } else
                return stateQ;
        } else { //scelgo una mossa random tra le possibili
            int index = rand.nextInt(array.length);
            int action = array[index];
            stateQ[0] = action;
            stateQ[1] = agent.getQ()[agent.getCurrentState()][action];
            return stateQ;
        }
    }

    private int policyTruthCoop(double[] tmpCurrentAgent, int pos, ArrayList<Double> arrayList, double epsilonValue) { // politica basata su un valore di verità, che definisce la mossa da scegliere
        Random rand = new Random();
        double epsilon = epsilonValue;
        double greedy = rand.nextDouble();

        if (greedy <= epsilon)
            return arrayList.get(pos - 1).intValue();
        else
            return (int) tmpCurrentAgent[0];
    }

    public void testQlearningCoop(Agents agent, Agents[] agentses, States states, GridWorld gridWorld, String typePolicy, double epsilonValue) {
        int state = agent.getCurrentState();
        int[] actionFromState = new int[states.getDefinePossibleStates().get(state).size()];
        listToArray(actionFromState, states, state);

        if (agent.goalReached == false) {
            agent.antenna.neighbourDiscovered.clear();
            agent.searchNeighbours(gridWorld, agent);
            if (agent.antenna.getNeighbourDiscovered().size() > 0) {
                ArrayList<Integer> convertHashSet = new ArrayList<>(agent.antenna.getNeighbourDiscovered());
                ArrayList<Double> stateTMP = new ArrayList<>();
                double[] tmpOtherAgents, tmpCurrentAgent;
                for (int i = 0; i < convertHashSet.size(); i++) {
                    if (typePolicy == "maxCoop") {
                        tmpOtherAgents = policyCoopAgentsMaxNextVal(agentses, agent, states, actionFromState, convertHashSet.get(i));
                        stateTMP.add(tmpOtherAgents[0]);
                        stateTMP.add(tmpOtherAgents[1]);
                    } else if (typePolicy == "epsilonCoop") {
                        tmpOtherAgents = policyEpsilonGreedyCoop(agentses, agent, states, actionFromState, convertHashSet.get(i), epsilonValue);
                        stateTMP.add(tmpOtherAgents[0]);
                        stateTMP.add(tmpOtherAgents[1]);
                    } else if (typePolicy == "truthPolicy") {
                        tmpOtherAgents = policyCoopAgentsMaxNextVal(agentses, agent, states, actionFromState, convertHashSet.get(i));
                        stateTMP.add(tmpOtherAgents[0]);
                        stateTMP.add(tmpOtherAgents[1]);
                    }

                }
                int pos = checkValueMax(stateTMP);

                if (typePolicy == "maxCoop") {
                    tmpCurrentAgent = policyMaxNextVal(agent, states, actionFromState);
                    if (tmpCurrentAgent[1] > stateTMP.get(pos))
                        state = (int) tmpCurrentAgent[0];
                    else
                        state = stateTMP.get(pos - 1).intValue();
                } else if (typePolicy == "epsilonCoop") {
                    tmpCurrentAgent = policyEpsilonGreedy(agent, states, actionFromState, epsilonValue);
                    if (tmpCurrentAgent[1] > stateTMP.get(pos))
                        state = (int) tmpCurrentAgent[0];
                    else
                        state = stateTMP.get(pos - 1).intValue();
                } else if (typePolicy == "truthPolicy") {
                    tmpCurrentAgent = policyMaxNextVal(agent, states, actionFromState);
                    state = policyTruthCoop(tmpCurrentAgent, pos, stateTMP, epsilonValue);
                }
            } else {
                if (typePolicy == "maxCoop")
                    state = (int) policyMaxNextVal(agent, states, actionFromState)[0];
                else if (typePolicy == "epsilonCoop")
                    state = (int) policyEpsilonGreedy(agent, states, actionFromState, epsilonValue)[0];
                else if (typePolicy == "truthPolicy") {
                    state = (int) policyMaxNextVal(agent, states, actionFromState)[0];
                }
            }
        }
        updateCoordinates(agent, gridWorld, state);
        agent.previousStates.add(agent.getCurrentState());
        for (int j = 0; j < agent.nodesStatesPositions.length; j++) {
            if (state == agent.getNodesStatesPositions()[j]) {
                agent.goalReached = true;
                model.count++;
                break;
            }
        }
    }

    public void testQlearning(Agents agent, States states, GridWorld gridWorld, String test, double epsilonValue) {
        int state = agent.getCurrentState();
        int[] actionFromState = new int[states.getDefinePossibleStates().get(state).size()];
        listToArray(actionFromState, states, state);
        if (test == "epsilon")
            state = (int) policyEpsilonGreedy(agent, states, actionFromState, epsilonValue)[0];
        else if (test == "max")
            state = (int) policyMaxNextVal(agent, states, actionFromState)[0];
        updateCoordinates(agent, gridWorld, state);
        agent.previousStates.add(agent.getCurrentState());
        for (int j = 0; j < agent.nodesStatesPositions.length; j++) {
            if (state == agent.getNodesStatesPositions()[j]) {
                agent.goalReached = true;
                model.count++;
                break;
            }
        }
    }

    public void runMultiAgent(Agents agent, States states, GridWorld gridWorld, int nTrains) {
        Random ran = new Random();
        for (int i = 0; i < nTrains; i++) {
            boolean loop = true;
            agent.previousStates.add(agent.getCurrentState());
            int state = agent.getCurrentState();
            while (loop) {
                for (int j = 0; j < agent.nodesStatesPositions.length; j++) {
                    if (state == agent.getNodesStatesPositions()[j]) {
                        agent.previousStates.clear();
                        gridWorld.fillGridWorldAgents(agent); //ad ogni ciclo ogni agente parte da uno stato iniziale random
                        loop = false;
                        break;
                    }
                }
                if (loop == false)
                    break;
                else {
                    int[] actionsFromState = new int[states.getDefinePossibleStates().get(state).size()];
                    listToArray(actionsFromState, states, state);
                    int index = ran.nextInt(actionsFromState.length);
                    int action = actionsFromState[index];
                    int nextState = action;
                    double q = agent.getQ()[state][action];
                    double maxQ = maxQ(states, agent, nextState);
                    int r = R[state][action];
                    double value = q + alpha * (r + (gamma * maxQ) - q);
                    agent.setQ(state, action, value);
                    state = nextState;
                    agent.setCurrentState(state);
                    agent.previousStates.add(agent.getCurrentState());
                }
            }
        }

    }

    private int[] listToArray(int[] ret, States state, int currentState) { //trasforma una lista in un array
        int i = 0;
        for (Integer e : state.getDefinePossibleStates().get(currentState)) {
            ret[i++] = e.intValue();
        }
        return ret;
    }

    private void updateCoordinates(Agents agent, GridWorld gridWorld, int state) { //aggiorno ad ogni passo le mie coordinate
        int oldState = agent.getCurrentState();
        int oldStatePositionX = agent.getCurrentPositionX();
        int oldStatePositionY = agent.getCurrentPositionY();
        agent.setCurrentState(state);
        int newState = agent.getCurrentState();

        if (oldState == newState - 1) { // spostamento dx
            agent.setCurrentPositionY(agent.getCurrentPositionY() + 1);
            gridWorld.updateRangeAntennaWorld(agent, oldStatePositionX, oldStatePositionY, agent.getCurrentPositionX(), agent.getCurrentPositionY());
        } else if (oldState == newState + 1) { //spostamento sx
            agent.setCurrentPositionY(agent.getCurrentPositionY() - 1);
            gridWorld.updateRangeAntennaWorld(agent, oldStatePositionX, oldStatePositionY, agent.getCurrentPositionX(), agent.getCurrentPositionY());
        } else if (oldState == newState + Agents.dimGridX) { // spostamento alto
            agent.setCurrentPositionX(agent.getCurrentPositionX() - 1);
            gridWorld.updateRangeAntennaWorld(agent, oldStatePositionX, oldStatePositionY, agent.getCurrentPositionX(), agent.getCurrentPositionY());
        } else { // spostamento basso
            agent.setCurrentPositionX(agent.getCurrentPositionX() + 1);
            gridWorld.updateRangeAntennaWorld(agent, oldStatePositionX, oldStatePositionY, agent.getCurrentPositionX(), agent.getCurrentPositionY());
        }
        updateAction(agent, gridWorld, oldStatePositionX, oldStatePositionY);
    }

    public void updateAction(Agents agent, GridWorld gridWorld, int oldPositionX, int oldPositionY) {
        gridWorld.getCopyGridW()[oldPositionX][oldPositionY] = GridWorld.FREE_CELL;
        gridWorld.getCopyGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = GridWorld.AGENT_CELL;
    }

    void printResult(Agents agent) {
        System.out.println("Print result");
        for (int i = 0; i < agent.getQ().length; i++) {
            System.out.print("i " + i + ":  ");
            for (int j = 0; j < agent.getQ()[i].length; j++) {
                if (agent.getQ()[i][j] != 0)
                    System.out.print("j " + j + ": " + df.format(agent.getQ()[i][j]) + " ");
                else
                    continue;
            }
            System.out.println("\n");
        }
    }

    public int[][] getR() {
        return R;
    }
}
/*

    private double sumQactions (int state, int[] array, Agents agent, double t){
        double sumQ = 0.;
        for (int i = 0; i<array.length;i++){
            sumQ += Math.exp((agent.getQ()[state][i])/t);
        }
        return sumQ;
    }

    private int  boltzmann (double t, States states, Agents agent, int state){
        int[] actionsFromNextState = new int[states.getDefinePossibleStates().get(state).size()];
        double[] actionsProbs = new double[actionsFromNextState.length];
        listToArray(actionsFromNextState, states, state);
        int actionChoosed = 0;
        for (int i = 0; i<actionsFromNextState.length;i++) {
            int nextState = actionsFromNextState[i];
            double q = agent.getQ()[state][nextState];
            actionsProbs[i] = (Math.exp(q/t))/(sumQactions(state,actionsFromNextState,agent,t));
        }
        double temp = 0., maxValue;
        int index = 0;
        for (int i = 0;i<actionsProbs.length;i++){
            maxValue = actionsProbs[i];
            if (maxValue > temp) {
                temp = maxValue;
                index = i;
            }
        }
        actionChoosed = actionsFromNextState[index];
        return actionChoosed;
    }*/