import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class GridWorld {

    public static final int FREE_CELL = 0;
    public static final int WALL_CELL = 1;
    public static final int NODE_CELL = 2;
    public static final int AGENT_CELL = 3;

    int xGrid,yGrid;

    int[][] gridW;


    public GridWorld(int xGrid, int yGrid) {
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        gridW = new int[this.xGrid][this.yGrid];
    }

    public int[][] defineWorld(){

        for (int[] i:gridW) {
            Arrays.fill( i,FREE_CELL );
        }
        return gridW;
    }

    public int[][] fillGridWorldAgents(int x, int y, Agent agent){
        while(true) {
            if (gridW[x][y] == FREE_CELL) {
                gridW[x][y] = AGENT_CELL;
                break;
            }
            else {
                x = (int) (0+Math.random()*(xGrid-1));
                y = (int) (0+Math.random()*(yGrid-1));
                agent.setStartPositionAgentX(x);
                agent.setStartPositionAgentY(y);
            }

        }
        return gridW;
    }
    public int[][] fillGridWorldNodes(int x, int y, Node node){
        while(true) {
            if (gridW[x][y] == FREE_CELL) {
                gridW[x][y] = NODE_CELL;
                break;
            }
            else {
                x = (int) (0+Math.random()*(xGrid-1));
                y = (int) (0+Math.random()*(yGrid-1));
                node.setPositionNodeX(x);
                node.setPositionNodeY(y);
            }

        }
        return gridW;
    }


    public int[][] getGridW() {
        return gridW;
    }

    public int getxGrid() {
        return xGrid;
    }

    public int getyGrid() {
        return yGrid;
    }

}
