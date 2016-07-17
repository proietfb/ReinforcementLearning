import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    int statesCount;
    double alpha = 0.1;
    double gamma = 0.9;

    public int[][] R;
    public double[][] Q;

    public QLearning(Agent agent) {
        statesCount = agent.getDimGridX()*agent.getDimGridY();
        R = new int[statesCount][statesCount];
        Q = new double[statesCount][statesCount];
    }

    public void initRewardValues(Agent agent,Node node, GridWorld gridWorld){ //definisce i reward massimi di base nelle posizioni accanto al goal
        if(node.getPositionNodeX() == 0)
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()+1][node.getPositionNodeY()]] = 100;
        else if (node.getPositionNodeX() == (agent.getDimGridX()-1))
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()-1][node.getPositionNodeY()]] = 100;
        else{
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()-1][node.getPositionNodeY()]] = 100;
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()+1][node.getPositionNodeY()]] = 100;
        }
        if(node.getPositionNodeY() == 0)
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()+1]] = 100;
        else if (node.getPositionNodeX() == (agent.getDimGridX()-1))
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()-1]] = 100;
        else{
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()+1]] = 100;
            R[gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]][gridWorld.getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()-1]] = 100;
        }

    }

    private void defineQ(){
        for (double[] i:Q) {
            Arrays.fill( i,0);
        }
    }

    private double maxQ(Agent agent, States state){
        double maxVal = Double.MIN_VALUE;
        for (int i = 0; i < state.definePossibleStates.get(agent.getCurrentState()).size(); i++){
            int nextState = state.definePossibleStates.get(agent.getCurrentState()).get(i);
            double value = Q[agent.getCurrentState()][nextState];
            if (value >= maxVal)
                maxVal = value;
        }
        return maxVal;
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


}
