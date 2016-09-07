import java.util.ArrayList;
/**
 * Created by proietfb on 8/10/16.
 */
public class Antenna {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private int powerTmp = 0;
    public ArrayList<Integer>[] neighbourDiscovered;

    public Antenna() {
        neighbourDiscovered = new ArrayList[4];
        for (int i = 0; i< neighbourDiscovered.length; i++)
            neighbourDiscovered[i] = new ArrayList<>();
    }

    public void discoverUP(int[][] grid, int agentPositionX, int agentPositionY, int power){
        for (int i = 0; i < power; i++){
            powerTmp++;
            if (agentPositionX-powerTmp < 0)
                break;
            else
                neighbourDiscovered[UP].add(grid[agentPositionX - powerTmp][agentPositionY]);
        }
        powerTmp = 0;
        for (int i = 0; i < neighbourDiscovered[UP].size(); i++){
            if (neighbourDiscovered[UP].get(i) == GridWorld.AGENT_CELL){
                System.out.println("ho trovato un vicino sopra di me mentre mi trovo allo stato (" + agentPositionX + ", " + agentPositionY + ")");
            }
        }
    }

    public void discoverDown(int[][] grid, int agentPositionX, int agentPositionY, int power) {
        for (int i = 0; i < power; i++){
            powerTmp++;
            if (agentPositionX+powerTmp > Agents.dimGridX - 1)
                break;
            else
                neighbourDiscovered[DOWN].add(grid[agentPositionX + powerTmp][agentPositionY]);
        }
        powerTmp = 0;
    }

    public void discoverLeft(int[][] grid, int agentPositionX, int agentPositionY, int power){
        for (int i = 0; i < power; i++){
            powerTmp++;
            if (agentPositionY-powerTmp < 0)
                break;
            else
                neighbourDiscovered[LEFT].add(grid[agentPositionX][agentPositionY-powerTmp]);
        }
        powerTmp = 0;
    }

    public void discoverRight(int[][] grid, int agentPositionX, int agentPositionY, int power){
        for (int i = 0; i < power; i++){
            powerTmp++;
            if (agentPositionY+powerTmp > Agents.dimGridY - 1)
                break;
            else
                neighbourDiscovered[RIGHT].add(grid[agentPositionX][agentPositionY+powerTmp]);
        }
        powerTmp = 0;
    }

    public ArrayList<Integer>[] getNeighbourDiscovered() {
        return neighbourDiscovered;
    }
}
