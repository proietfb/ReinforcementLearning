/**
 * Created by proietfb on 7/13/16.
 */
public class Node {

    int positionNodeX, positionNodeY;

    int nodeCurrentState;

    public Node(GridWorld gridWorld) {
        nodeCurrentState = gridWorld.defineGridValues()[positionNodeX][positionNodeY];
    }

    public int getPositionNodeX() {
        return positionNodeX;
    }

    public void setPositionNodeX(int positionNodeX) {
        this.positionNodeX = positionNodeX;
    }

    public int getPositionNodeY() {
        return positionNodeY;
    }

    public void setPositionNodeY(int positionNodeY) {
        this.positionNodeY = positionNodeY;
    }

    public int getNodeCurrentState() {
        return nodeCurrentState;
    }

    public void setNodeCurrentState(int nodeCurrentState) {
        this.nodeCurrentState = nodeCurrentState;
    }
}
