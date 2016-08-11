import java.util.ArrayList;
/**
 * Created by proietfb on 8/10/16.
 */
public class Antenna {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public ArrayList<Integer>[] neighbourDiscovered;

    public Antenna() {
        neighbourDiscovered = new ArrayList[4];
        for (int i = 0; i< neighbourDiscovered.length; i++)
            neighbourDiscovered[i] = new ArrayList<>();
    }

    public void discoverUP(int[][] grid, int agentPositionX, int agentPositionY, int power){
        int powerTmp = 0;

        while(powerTmp < power+1){
            powerTmp++;
            if (agentPositionX-powerTmp >= 0)
                neighbourDiscovered[UP].add(grid[agentPositionX-powerTmp][agentPositionY]);
            else
                break;

        }

    }

    public void discoverDown(int[][] grid, int agentPositionX, int agentPositionY, int power) {
        int powerTmp = 0;

        while(powerTmp < power+1){
            powerTmp++;
            if (agentPositionX+powerTmp <= Agents.dimGridX - 1)
                neighbourDiscovered[DOWN].add(grid[agentPositionX+powerTmp][agentPositionY]);
            else
                break;

        }
    }

    public void discoverLeft(int[][] grid, int agentPositionX, int agentPositionY, int power){
        int powerTmp = 0;

        while(powerTmp < power+1){
            powerTmp++;
            if (agentPositionY-powerTmp >= 0)
                neighbourDiscovered[LEFT].add(grid[agentPositionX][agentPositionY-powerTmp]);
            else
                break;

        }
    }

    public void discoverRight(int[][] grid, int agentPositionX, int agentPositionY, int power){
        int powerTmp = 0;

        while(powerTmp < power+1){
            powerTmp++;
            if (agentPositionY+powerTmp <= Agents.dimGridY - 1)
                neighbourDiscovered[RIGHT].add(grid[agentPositionX][agentPositionY+powerTmp]);
            else
                break;

        }
    }

    public ArrayList<Integer>[] getNeighbourDiscovered() {
        return neighbourDiscovered;
    }
}
