import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    final DecimalFormat df;
    double alpha = 0.1;
    double gamma = 0.9;

    private int[][] R;

    public QLearning(Agents agent,Nodes[] nodes, Walls walls) {
        df = new DecimalFormat("#.##");
        R = new int[agent.getStatesCount()][agent.getStatesCount()];
        initR();
        for(int i = 0 ;i<nodes.length;i++)
            initRewardNodes(nodes[i]);
        initRewardWalls(walls);

    }

    private  void initRewardWalls(Walls walls){

        for(int i = 0; i< walls.getnOfWalls();i++){
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
            } else if ((walls.getWallPositionX()[i] > 0 && walls.getWallPositionX()[i] < Agents.dimGridX - 1) && walls.getWallPositionY()[i] == 0) { //prima colonna
                R[walls.getWallsStatesPositions()[i] - Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // alto basso
                R[walls.getWallsStatesPositions()[i] + 1][walls.getWallsStatesPositions()[i]] = -1; // dx sx
                R[walls.getWallsStatesPositions()[i] + Agents.dimGridX][walls.getWallsStatesPositions()[i]] = -1; // basso alto
            } else if ((walls.getWallPositionX()[i] > 0 && walls.getWallPositionX()[i] < Agents.dimGridX - 1) && walls.getWallPositionY()[i] == Agents.dimGridY - 1){ //ultima colonna
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

    private void initR(){
        for (int[] i : R) //inizializzo la matrice dei reward tutti a zero
            Arrays.fill(i, 0);
    }

    private void initRewardNodes(Nodes node) { //definisce i reward massimi di base nelle posizioni accanto al goal

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

    }



    private double maxQ(States states,Agents agent, int state) {
        int[] actionsFromNextState = new int[states.getDefinePossibleStates().get(state).size()];
        listToArray(actionsFromNextState,states,state);
        double maxVal = 0;
        for (int i = 0; i < actionsFromNextState.length; i++) {
            int nextState = actionsFromNextState[i];
            double value = agent.getQ()[state][nextState];
            if (value >= maxVal)
                maxVal = value;
        }
        return maxVal;
    }

    private int policyMaxNextVal(Agents agent, States state, int[] array) {
        Random ran = new Random();
        double maxVal = 0;
        int policyGoToState = array[ran.nextInt(array.length)];
        for (int i = 0; i < state.getDefinePossibleStates().get(agent.getCurrentState()).size(); i++) {
            int nextState = array[i];
            double value = agent.getQ()[agent.getCurrentState()][nextState];
            if (value > maxVal) {
                maxVal = value;
                policyGoToState = nextState;
            }
        }
        if (maxVal == 0){
            int index = ran.nextInt(array.length);
            int action = array[index];
            return action;
        } else
            return policyGoToState;
    }


    public void runMultiAgent(Agents agent, States states, GridWorld gridWorld){
        Random ran = new Random();
        int[] nTimes4Obj = new int[Main.nNodes];
        for (int i = 0; i < 1000; i++) {
            boolean loop = true;
            agent.previousStates.add(agent.getCurrentState());
            int state = agent.getCurrentState();
            if (i < 999) {

                while (loop) {
                    for (int j=0;j< agent.nodesStatesPositions.length;j++){
                        if (state == agent.getNodesStatesPositions()[j]) {
                            nTimes4Obj[j]++;
                            agent.previousStates.clear();
                            gridWorld.fillGridWorldAgents(agent); //ad ogni ciclo ogni agente parte da uno stato iniziale random
                            loop = false;
                            break;
                        }
                    }
                    if (loop == false) {
                        break;
                    }
                    else {
                        int[] actionsFromState = new int[states.getDefinePossibleStates().get(state).size()];
                        listToArray(actionsFromState, states, state);
                        int index = ran.nextInt(actionsFromState.length);
                        int action = actionsFromState[index];
                        int nextState = action;
                        double q = agent.getQ()[state][action];
                        double maxQ = maxQ(states,agent,nextState);
                        int r = R[state][action];
                        double value = q + alpha * (r + (gamma * maxQ) - q);
                        agent.setQ(state, action, value);
                        state = nextState;
                        agent.setCurrentState(state);
                        agent.previousStates.add(agent.getCurrentState());
                    }
                }
                //printResult(agent);
            }
            else {

                System.out.println(agent);
                System.out.println(Arrays.toString(nTimes4Obj));
                //printResult();
                while (loop){
                    for (int j=0;j< agent.nodesStatesPositions.length;j++){
                        if (state == agent.getNodesStatesPositions()[j]) {
                            loop = false;
                            break;
                        }
                    }
                    if (loop == false){
                        break;
                    }
                    else {
                        int[] actionFromState = new int[states.getDefinePossibleStates().get(state).size()];
                        listToArray(actionFromState,states,state);
                        state = policyMaxNextVal(agent,states,actionFromState);
                        //agent.setCurrentState(state);
                        updateCoordinates(agent, state);
                        agent.previousStates.add(agent.getCurrentState());
                        for (int j = 0; j < agent.antenna.getNeighbourDiscovered().length; j++)
                            agent.antenna.neighbourDiscovered[j].clear();
                        agent.searchNeighbours(gridWorld);
                        printWorld(gridWorld);
                        //System.out.println("previous states" + agent.previousStates);
                        System.out.println(Arrays.deepToString(agent.antenna.getNeighbourDiscovered()));
                    }
                }
            }

        }

    }

    public void run(Agents agent, States states, Nodes node, GridWorld gridWorld) { //algoritmo Qlearning funzionante
        Random ran = new Random();
        for (int i = 0; i < 200; i++) {
            agent.previousStates.add(agent.getCurrentState());
            int state = agent.getCurrentState();

            if (i < 199) {

                while (state != node.getNodeCurrentState()) {
                    int[] actionsFromState = new int[states.getDefinePossibleStates().get(state).size()];
                    listToArray(actionsFromState, states, state);
                    int index = ran.nextInt(actionsFromState.length);
                    int action = actionsFromState[index];
                    int nextState = action;
                    double q = agent.getQ()[state][action];
                    double maxQ = maxQ(states,agent,nextState);
                    int r = R[state][action];
                    double value = q + alpha * (r + (gamma * maxQ) - q);
                    agent.setQ(state, action, value);
                    state = nextState;
                    agent.setCurrentState(state);
                    agent.previousStates.add(agent.getCurrentState());
                }
                //printResult(agent);
                agent.previousStates.clear();
                gridWorld.fillGridWorldAgents(agent); //ad ogni ciclo ogni agente parte da uno stato iniziale random

            }
            else {
                //printResult();
                while (state != node.getNodeCurrentState()){
                    int[] actionFromState = new int[states.getDefinePossibleStates().get(state).size()];
                    listToArray(actionFromState,states,state);
                    double maxVal = 0;
                    int action = actionFromState[ran.nextInt(actionFromState.length)];
                    for (int j = 0; j < actionFromState.length; j++){
                        int nextState = actionFromState[j];
                        double val = agent.getQ()[state][nextState];
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

    private int[] listToArray(int[] ret, States state, int currentState) {
        int i = 0;
        for (Integer e : state.getDefinePossibleStates().get(currentState)) {
            ret[i++] = e.intValue();
        }
        return ret;
    }

    private void updateCoordinates(Agents agent, int state){
        int oldState = agent.getCurrentState();
        agent.setCurrentState(state);
        int newState = agent.getCurrentState();

        if (oldState == newState - 1){ // spostamento dx
            agent.setCurrentPositionY(agent.getCurrentPositionY() + 1);
        } else if (oldState == newState + 1) { //spostamento sx
            agent.setCurrentPositionY(agent.getCurrentPositionY() - 1);
        } else if (oldState == newState - Agents.dimGridX) { // spostamento alto
            agent.setCurrentPositionX(agent.getCurrentPositionX() - 1);
        } else { // spostamento basso
            agent.setCurrentPositionX(agent.getCurrentPositionX() + 1);
        }
    }

    void printResult(Agents agent) {
        System.out.println("Print result");
        for (int i = 0; i < agent.getQ().length; i++) {
            System.out.print("i " + i + ":  ");
            for (int j = 0; j < agent.getQ()[i].length; j++) {
                if (agent.getQ()[i][j] != 0)
                    System.out.print("j " + j +": " + df.format(agent.getQ()[i][j]) + " ");
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

    public int[][] getR() {
        return R;
    }

}
