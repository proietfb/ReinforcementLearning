
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by proietfb on 8/10/16.
 */
public class Antenna {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private int powerTmp = 0;
    public HashSet<Integer> neighbourDiscovered;


    public Antenna() {
        neighbourDiscovered = new HashSet<>();
    }

    public void discoverUP(int[][] grid, int agentPositionX, int agentPositionY, int power, Agents agent) {
        for (int i = 0; i < power; i++) {
            powerTmp++;
            if (agentPositionX - powerTmp < 0)
                break;
            else if (grid[agentPositionX - powerTmp][agentPositionY] != GridWorld.ANTENNA && grid[agentPositionX - powerTmp][agentPositionY] != agent.getAgentName())
                neighbourDiscovered.add(grid[agentPositionX - powerTmp][agentPositionY]);
        }
        powerTmp = 0;
    }

    public void discoverDown(int[][] grid, int agentPositionX, int agentPositionY, int power, Agents agent) {
        for (int i = 0; i < power; i++) {
            powerTmp++;
            if (agentPositionX + powerTmp > Agents.dimGridX - 1)
                break;
            else if (grid[agentPositionX + powerTmp][agentPositionY] != GridWorld.ANTENNA && grid[agentPositionX + powerTmp][agentPositionY] != agent.getAgentName())
                neighbourDiscovered.add(grid[agentPositionX + powerTmp][agentPositionY]);
        }
        powerTmp = 0;
    }

    public void discoverLeft(int[][] grid, int agentPositionX, int agentPositionY, int power, Agents agent) {
        for (int i = 0; i < power; i++) {
            powerTmp++;
            if (agentPositionY - powerTmp < 0)
                break;
            else if (grid[agentPositionX][agentPositionY - powerTmp] != GridWorld.ANTENNA && grid[agentPositionX][agentPositionY - powerTmp] != agent.getAgentName())
                neighbourDiscovered.add(grid[agentPositionX][agentPositionY - powerTmp]);
        }
        powerTmp = 0;
    }

    public void discoverRight(int[][] grid, int agentPositionX, int agentPositionY, int power, Agents agent) {
        for (int i = 0; i < power; i++) {
            powerTmp++;
            if (agentPositionY + powerTmp > Agents.dimGridY - 1)
                break;
            else if (grid[agentPositionX][agentPositionY + powerTmp] != GridWorld.ANTENNA && grid[agentPositionX][agentPositionY + powerTmp] != agent.getAgentName())
                neighbourDiscovered.add(grid[agentPositionX][agentPositionY + powerTmp]);

        }
        powerTmp = 0;
    }

    public HashSet<Integer> getNeighbourDiscovered() {
        return neighbourDiscovered;
    }
}
