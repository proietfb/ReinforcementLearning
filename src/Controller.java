/**
 * Created by proietfb on 8/22/16.
 */
public class Controller {

    private int xValueGrid = 7, yValueGrid = 7;

    private int nWalls = 5, nAgents = 3, nNodes = 5;

    public Controller() {}

    public int getxValueGrid() {
        return xValueGrid;
    }

    public int getyValueGrid() {
        return yValueGrid;
    }

    public int getnWalls() {
        return nWalls;
    }

    public int getnNodes() {
        return nNodes;
    }

    public int getnAgents() {
        return nAgents;
    }

    public void setxValueGrid(int xValueGrid) {
        this.xValueGrid = xValueGrid;
    }

    public void setyValueGrid(int yValueGrid) {
        this.yValueGrid = yValueGrid;
    }

    public void setnWalls(int nWalls) {
        this.nWalls = nWalls;
    }

    public void setnAgents(int nAgents) {
        this.nAgents = nAgents;
    }

    public void setnNodes(int nNodes) {
        this.nNodes = nNodes;
    }
}
