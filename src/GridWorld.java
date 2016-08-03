import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class GridWorld {

    public static final int FREE_CELL = 0;
    public static final int WALL_CELL = 1;
    public static final int NODE_CELL = 2;
    public static final int AGENT_CELL = 3;

    int xGrid, yGrid;

    int[][] gridW, gridValues;

    boolean setAgents = false;
    int previouStartX, previouStartY;

    public GridWorld(int xGrid, int yGrid) {
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        gridW = new int[this.xGrid][this.yGrid];
        gridValues = new int[this.xGrid][this.yGrid];
    }

    public int[][] defineWorld() {

        for (int[] i : gridW) {
            Arrays.fill(i, FREE_CELL);
        }
        return gridW;
    }

    public int[][] defineGridValues() { //assegno un valore ad ogni cella della griglia

        for (int i = 0; i < gridValues.length; i++) {
            for (int j = 0; j < gridValues[0].length; j++) {
                if (i > 0)
                    gridValues[i][j] = xGrid + (gridValues[i - 1][0]) + j;
                else
                    gridValues[i][j] = i + j;
            }
        }
        return gridValues;
    }

    public int[][] fillGridWorldAgents(Agents agent) {
        while (true) {

            int x = (int) (0 + Math.random() * (xGrid - 1));
            int y = (int) (0 + Math.random() * (yGrid - 1));
            if (gridW[x][y] == FREE_CELL && setAgents == false) { // definisco per la prima volta la posizione dell'agente
                gridW[x][y] = AGENT_CELL;
                agent.setStartPositionAgentX(x);
                agent.setStartPositionAgentY(y);
                agent.setStartState(getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
                agent.setCurrentState(agent.getStartState());
                setAgents = true;
                previouStartX = agent.getStartPositionAgentX();
                previouStartY = agent.getStartPositionAgentY();
                break;
            } else if(setAgents == true){ // altrimenti la precedente posizione diventa una cella vuota e ne imposto un'altra --> il nuovo ciclo di training parte da qui
                gridW[getPreviouStartX()][getPreviouStartY()] = FREE_CELL;
                if (gridW[x][y] == FREE_CELL){
                    gridW[x][y] = AGENT_CELL;
                    agent.setStartPositionAgentX(x);
                    agent.setStartPositionAgentY(y);
                    agent.setStartState(getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
                    agent.setCurrentState(agent.getStartState());
                    previouStartX = agent.getStartPositionAgentX();
                    previouStartY = agent.getStartPositionAgentY();
                    break;
                }
            } else continue;

        }
        return gridW;
    }

    public int[] fillGridWorldWalls(int xGridSize, int yGridSize, Walls wall){
        wall.wallsStatesPositions = new int[wall.nOfWalls];
        wall.wallPositionX = new int[wall.nOfWalls];
        wall.wallPositionY = new int[wall.nOfWalls];
        int posX, posY;
        for (int i = 0; i < wall.nOfWalls; i++){
            while (true) {
                posX = (int) (0 + Math.random() * (xGridSize - 1));
                posY = (int) (0 + Math.random() * (yGridSize - 1));
                if (gridW[posX][posY] == FREE_CELL) {
                    gridW[posX][posY] = WALL_CELL;
                    wall.wallsStatesPositions[i] = getGridValues()[posX][posY];
                    wall.wallPositionX[i] = posX;
                    wall.wallPositionY[i] = posY;
                    break;
                } else continue;
            }
        }
        return wall.wallsStatesPositions;
    }

    public int[][] fillGridWorldNodes(Nodes node) {
        while (true) {
            int x = (int) (0 + Math.random() * (xGrid - 1));
            int y = (int) (0 + Math.random() * (yGrid - 1));
            if (gridW[x][y] == FREE_CELL) {
                gridW[x][y] = NODE_CELL;
                node.setPositionNodeX(x);
                node.setPositionNodeY(y);
                node.setNodeCurrentState(getGridValues()[node.getPositionNodeX()][node.getPositionNodeY()]);
                break;
            } else continue;
        }
        return gridW;
    }


    public int[][] getGridW() {
        return gridW;
    }

    public int[][] getGridValues() {
        return gridValues;
    }

    public int getxGrid() {
        return xGrid;
    }

    public int getyGrid() {
        return yGrid;
    }

    public void setGridW(int[][] gridW) {
        this.gridW = gridW;
    }

    public int getPreviouStartX() {
        return previouStartX;
    }

    public int getPreviouStartY() {
        return previouStartY;
    }
}
