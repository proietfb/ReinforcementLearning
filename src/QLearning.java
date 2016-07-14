/**
 * Created by proietfb on 7/13/16.
 */
public class QLearning {

    public int[][] R;

    public QLearning(Agent agent) {
        R = new int[(int) Math.pow(agent.getDimGridX(),2)][(int) Math.pow(agent.getDimGridY(),2)];
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

    public int[][] getR() {
        return R;
    }

    public void setR(int[][] r) {
        R = r;
    }
}
