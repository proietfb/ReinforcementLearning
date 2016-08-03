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
    double gamma = 0.1;

    public int[][] R;
    public double[][] Q;

    public QLearning(Agents agent,Nodes node, Walls walls) {
        df = new DecimalFormat("#.##");
        statesCount = agent.getDimGridX() * agent.getDimGridY();
        R = new int[statesCount][statesCount];
        Q = new double[statesCount][statesCount];
        initRewardValues(node,walls);
        defineQ();
    }

    private void initRewardValues(Nodes node, Walls walls) { //definisce i reward massimi di base nelle posizioni accanto al goal

        for (int[] i : R) //inizializzo la matrice dei reward tutti a zero
            Arrays.fill(i, 0);

        if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == 0){ //se il punto finale è in 0,0, il reward viene dal basso verso l'alto e da dx verso sx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == 0 && node.getPositionNodeY() == Agents.dimGridY-1) { //punto finale in 0,n il reward viene dal basso verso l'alto e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; //sx dx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10; // basso alto
        } else if (node.getPositionNodeX() == Agents.dimGridY - 1 && node.getPositionNodeY() == 0){ //punto finale in n,0 reward dall alto verso il basso e dadx verso sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; // alto basso
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; //dx sx
        } else if (node.getPositionNodeX() == Agents.dimGridY - 1 && node.getPositionNodeY() == Agents.dimGridY-1) { // punto finale in n,n reward dall alto verso il basso e da sx verso dx
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; // alto basso
        } else if((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agents.dimGridX - 1) && node.getPositionNodeY() == 0){ //prima colonna
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
            R[node.getNodeCurrentState() + 1 ][node.getNodeCurrentState()] = 10; //dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if ((node.getPositionNodeX() > 0 && node.getPositionNodeX() < Agents.dimGridX - 1) && node.getPositionNodeY() == Agents.dimGridY - 1){ //ultima colonna
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == 0 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agents.dimGridY - 1)) { //prima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
        } else if (node.getPositionNodeX() == Agents.dimGridX - 1 && (node.getPositionNodeY() > 0 && node.getPositionNodeY() < Agents.dimGridY - 1)){ //ultima riga
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
        } else { // in mezzo
            R[node.getNodeCurrentState() + Agents.dimGridX][node.getNodeCurrentState()] = 10;  //basso alto
            R[node.getNodeCurrentState() - 1][node.getNodeCurrentState()] = 10; // sx dx
            R[node.getNodeCurrentState() + 1][node.getNodeCurrentState()] = 10; // dx sx
            R[node.getNodeCurrentState() - Agents.dimGridX][node.getNodeCurrentState()] = 10; //alto basso
        }

        for(int i = 0; i< walls.nOfWalls;i++){
            if (walls.getWallsStatesPositions()[i] == 0){ //0,0
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; //basso alto
            } else if (walls.getWallsStatesPositions()[i] == ((1*Agents.dimGridY)-1)) { //0,n
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
            } else if (walls.getWallsStatesPositions()[i] == ((Agents.dimGridX*Agents.dimGridY)-Agents.dimGridX)) { //n,0
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
            } else if (walls.getWallsStatesPositions()[i] == ((Agents.dimGridX*Agents.dimGridY) - 1)) { //n,n
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
            } else if ((walls.wallPositionX[i] > 0 && walls.getWallPositionX()[i] < Agents.dimGridX - 1) && walls.getWallPositionY()[i] == 0) { //prima colonna
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
            } else if ((walls.wallPositionX[i] > 0 && walls.wallPositionX[i] < Agents.dimGridX - 1) && walls.wallPositionY[i] == Agents.dimGridY - 1){ //ultima colonna
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
            } else if (walls.getWallPositionX()[i] == 0 && (walls.getWallPositionY()[i] > 0 && walls.getWallPositionY()[i]< Agents.dimGridY - 1)){ //prima riga
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
            } else if (walls.getWallPositionX()[i] == Agents.dimGridX - 1 && (walls.getWallPositionY()[i] > 0 && walls.getWallPositionY()[i] < Agents.dimGridY - 1)) { //ultima riga
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
            } else {
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
                R[walls.getWallsStatesPositions()[i] - 1][walls.getWallsStatesPositions()[i]] = -1; //sx dx
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
            }
        }


    }

    private void defineQ() {
        for (double[] i : Q) {
            Arrays.fill(i, 0);
        }
    }

    private double maxQ(States states, int state) {
        int[] actionsFromNextState = new int[states.definePossibleStates.get(state).size()];
        listToArray(actionsFromNextState,states,state);
        double maxVal = 0;
        for (int i = 0; i < actionsFromNextState.length; i++) {
            int nextState = actionsFromNextState[i];
            double value = Q[state][nextState];
            if (value >= maxVal)
                maxVal = value;
        }
        return maxVal;
    }

    private int policy(Agents agent, States state, int[] array) {
        Random ran = new Random();
        double maxVal = 0;
        int policyGoToState = agent.getCurrentState();
        for (int i = 0; i < state.definePossibleStates.get(agent.getCurrentState()).size(); i++) {
            int nextState = array[i];
            double value = Q[agent.getCurrentState()][nextState];
            if (value > maxVal) {
                maxVal = value;
                policyGoToState = nextState;
            }
        }
        if (maxVal == 0){
            int index = ran.nextInt(array.length);
            int action = array[index];
            return action;

        }
        else
            return policyGoToState;
    }

    public void run(Agents agent, States states, Nodes node, GridWorld gridWorld) {
        Random ran = new Random();
        for (int i = 0; i < 100; i++) {
            agent.previousStates.add(agent.getCurrentState());
            int state = agent.getCurrentState();

            if (i < 99) {

                while (state != node.getNodeCurrentState()) {
                    int[] actionsFromState = new int[states.definePossibleStates.get(state).size()];
                    listToArray(actionsFromState, states, state);
                    int index = ran.nextInt(actionsFromState.length);
                    int action = actionsFromState[index];
                    int nextState = action;
                    double q = Q[state][action];
                    double maxQ = maxQ(states,nextState);
                    int r = R[state][action];
                    double value = q + alpha * (r + (gamma * maxQ) - q);
                    setQ(state, action, value);
                    state = nextState;
                    agent.setCurrentState(state);
                    agent.previousStates.add(agent.getCurrentState());
                }
                //printResult();
                agent.previousStates.clear();
                gridWorld.fillGridWorldAgents(agent); //ad ogni ciclo ogni agente parte da uno stato iniziale random


            }
            else {
                //printResult();
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

    void printResult() {
        System.out.println("Print result");
        for (int i = 0; i < Q.length; i++) {
            System.out.print("i " + i + ":  ");
            for (int j = 0; j < Q[i].length; j++) {
                if (Q[i][j] != 0)
                    System.out.print("j " + j +": " + df.format(Q[i][j]) + " ");
                else
                    continue;
            }
            System.out.println("\n");
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
