import java.util.Arrays;

/**
 * Created by proietfb on 7/13/16.
 */
public class GridWorld {

    public static final int ANTENNA = 9;
    public static final int FREE_CELL = 0;
    public static final int WALL_CELL = 1;
    public static final int NODE_CELL = 2;
    public static final int AGENT_CELL = 3;
    public LinkToAntennaMatrix[] linkToAntennaMatrix;
    private int xGrid, yGrid;
    private int[][] gridW, gridValues, copyGridW;

    public GridWorld(int xGrid, int yGrid) {
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        gridW = new int[this.xGrid][this.yGrid];
        copyGridW = new int[this.xGrid][this.yGrid];
        gridValues = new int[this.xGrid][this.yGrid];
        linkToAntennaMatrix = new LinkToAntennaMatrix[Model.nAgents];
        for (int i = 0; i < Model.nAgents; i++)
            linkToAntennaMatrix[i] = new LinkToAntennaMatrix();
    }

    public int[][] defineWorld() {
        for (int[] i : gridW) {
            Arrays.fill(i, FREE_CELL);
        }
        return gridW;
    }

    public void copyWorld() {
        for (int i = 0; i < getGridW().length; i++) {
            for (int j = 0; j < getGridW()[0].length; j++) {
                copyGridW[i][j] = getGridW()[i][j];
            }
        }
    }

    public void updateWorld() {
        for (int i = 0; i < getGridW().length; i++) {
            for (int j = 0; j < getGridW()[0].length; j++) {
                gridW[i][j] = getCopyGridW()[i][j];
            }
        }
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


    public int[][] defineFirstAgentPositions(Agents agent, GridWorld gridWorld, Walls walls) {
        while (true) {
            int x = (int) (0 + Math.random() * (xGrid - 1));
            int y = (int) (0 + Math.random() * (yGrid - 1));
            if (gridW[x][y] == FREE_CELL) {
                gridW[x][y] = AGENT_CELL;
                agent.setStartPositionAgentX(x);
                agent.setStartPositionAgentY(y);
                agent.setCurrentPositionX(agent.getStartPositionAgentX());
                agent.setCurrentPositionY(agent.getStartPositionAgentY());
                agent.setStartState(getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
                agent.setCurrentState(agent.getStartState());
                linkToAntennaMatrix[agent.getAgentName()].defineRangeAntennaWorld(gridWorld);
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[x][y] = agent.getAgentName();
                for (int i = 1; i <= Agents.maxSignal; i++) {
                    if (x - i >= 0)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[x - i][y] = agent.getAgentName(); //up
                    if (x + i <= Agents.dimGridX - 1)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[x + i][y] = agent.getAgentName(); //down
                    if (y - i >= 0)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[x][y - i] = agent.getAgentName(); //left
                    if (y + i <= Agents.dimGridY - 1)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[x][y + i] = agent.getAgentName(); //right
                }
                break;
            }
        }
        return gridW;
    }

    public int[][] fillGridWorldAgents(Agents agent) {
        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = ANTENNA;
        getGridW()[agent.getCurrentPositionX()][agent.getCurrentPositionY()] = FREE_CELL;
        for (int i = 1; i <= Agents.maxSignal; i++) {
            if (agent.getCurrentPositionX() - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[agent.getCurrentPositionX() - i][agent.getCurrentPositionY()] = ANTENNA; //up
            if (agent.getCurrentPositionX() + i <= Agents.dimGridX - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[agent.getCurrentPositionX() + i][agent.getCurrentPositionY()] = ANTENNA; //down
            if (agent.getCurrentPositionY() - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[agent.getCurrentPositionX()][agent.getCurrentPositionY() - i] = ANTENNA; //left
            if (agent.getCurrentPositionY() + i <= Agents.dimGridY - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[agent.getCurrentPositionX()][agent.getCurrentPositionY() + i] = ANTENNA; //right
        }
        while (true) {
            int x = (int) (0 + Math.random() * (xGrid - 1));
            int y = (int) (0 + Math.random() * (yGrid - 1));
            if (gridW[x][y] == FREE_CELL) {
                gridW[x][y] = AGENT_CELL;
                agent.setStartPositionAgentX(x);
                agent.setStartPositionAgentY(y);
                agent.setStartState(getGridValues()[agent.getStartPositionAgentX()][agent.getStartPositionAgentY()]);
                agent.setCurrentPositionX(agent.getStartPositionAgentX());
                agent.setCurrentPositionY(agent.getStartPositionAgentY());
                agent.setCurrentState(agent.getStartState());
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[x][y] = agent.getAgentName();
                for (int i = 1; i <= Agents.maxSignal; i++) {
                    if (x - i >= 0)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[x - i][y] = agent.getAgentName(); //up
                    if (x + i <= Agents.dimGridX - 1)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[x + i][y] = agent.getAgentName(); //down
                    if (y - i >= 0)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[x][y - i] = agent.getAgentName(); //left
                    if (y + i <= Agents.dimGridY - 1)
                        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[x][y + i] = agent.getAgentName(); //right
                }
                break;
            }
        }
        return gridW;
    }

    public int[] fillGridWorldWalls(int xGridSize, int yGridSize, Walls wall) {
        wall.setWallsStatesPositions(new int[wall.getnOfWalls()]);
        wall.setWallPositionX(new int[wall.getnOfWalls()]);
        wall.setWallPositionY(new int[wall.getnOfWalls()]);
        int posX, posY;
        for (int i = 0; i < wall.getnOfWalls(); i++) {
            while (true) {
                posX = (int) (0 + Math.random() * (xGridSize - 1));
                posY = (int) (0 + Math.random() * (yGridSize - 1));
                if (gridW[posX][posY] == FREE_CELL) {
                    gridW[posX][posY] = WALL_CELL;
                    wall.getWallsStatesPositions()[i] = getGridValues()[posX][posY];
                    wall.getWallPositionX()[i] = posX;
                    wall.getWallPositionY()[i] = posY;
                    break;
                } else continue;
            }
        }
        return wall.getWallsStatesPositions();
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

    public void updateRangeAntennaWorld(Agents agent, int oldPositionX, int oldPositionY, int newPositionX, int newPositionY) { // adogni passo aggiorna anche il proprio range di copertura wireless
        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[oldPositionX][oldPositionY] = ANTENNA;
        for (int i = 1; i <= Agents.maxSignal; i++) {
            if (oldPositionX - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[oldPositionX - i][oldPositionY] = ANTENNA; //up
            if (oldPositionX + i <= Agents.dimGridX - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[oldPositionX + i][oldPositionY] = ANTENNA; //down
            if (oldPositionY - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[oldPositionX][oldPositionY - i] = ANTENNA; //left
            if (oldPositionY + i <= Agents.dimGridY - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[oldPositionX][oldPositionY + i] = ANTENNA; //right
        }
        linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[newPositionX][newPositionY] = agent.getAgentName();
        for (int i = 1; i <= Agents.maxSignal; i++) {
            if (newPositionX - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[newPositionX - i][newPositionY] = agent.getAgentName(); //up
            if (newPositionX + i <= Agents.dimGridX - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[newPositionX + i][newPositionY] = agent.getAgentName(); //down
            if (newPositionY - i >= 0)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[newPositionX][newPositionY - i] = agent.getAgentName(); //left
            if (newPositionY + i <= Agents.dimGridY - 1)
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntennaCopy[newPositionX][newPositionY + i] = agent.getAgentName(); //right
        }
    }

    public void copyRangeAntennaWorld(Agents agent, GridWorld gridWorld) {
        for (int i = 0; i < linkToAntennaMatrix[agent.getAgentName()].getGridRangeAntenna().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                linkToAntennaMatrix[agent.getAgentName()].gridRangeAntenna[i][j] = linkToAntennaMatrix[agent.getAgentName()].getGridRangeAntennaCopy()[i][j];
            }
        }
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

    public int[][] getCopyGridW() {
        return copyGridW;
    }

    public LinkToAntennaMatrix[] getLinkToAntennaMatrix() {
        return linkToAntennaMatrix;
    }
}

class LinkToAntennaMatrix {

    public int[][] gridRangeAntenna, gridRangeAntennaCopy;

    public LinkToAntennaMatrix() {
        gridRangeAntenna = new int[Model.xGrid][Model.yGrid];
        gridRangeAntennaCopy = new int[Model.xGrid][Model.yGrid];
    }


    public void defineRangeAntennaWorld(GridWorld gridWorld) {
        for (int i = 0; i < getGridRangeAntenna().length; i++) {
            for (int j = 0; j < gridWorld.getGridW()[0].length; j++) {
                gridRangeAntenna[i][j] = gridWorld.ANTENNA;
                gridRangeAntennaCopy[i][j] = gridWorld.ANTENNA;
            }
        }
    }

    public int[][] getGridRangeAntenna() {
        return gridRangeAntenna;
    }

    public int[][] getGridRangeAntennaCopy() {
        return gridRangeAntennaCopy;
    }

}
