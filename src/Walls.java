/**
 * Created by proietfb on 8/3/16.
 */
public class Walls {

    private int nOfWalls;
    private int[] wallsStatesPositions, wallPositionX, wallPositionY;

    public Walls(int nOfWalls) {
        this.nOfWalls = nOfWalls;
    }

    public int getnOfWalls() {
        return nOfWalls;
    }

    public int[] getWallsStatesPositions() {
        return wallsStatesPositions;
    }

    public void setWallsStatesPositions(int[] wallsStatesPositions) {
        this.wallsStatesPositions = wallsStatesPositions;
    }

    public int[] getWallPositionX() {
        return wallPositionX;
    }

    public void setWallPositionX(int[] wallPositionX) {
        this.wallPositionX = wallPositionX;
    }

    public int[] getWallPositionY() {
        return wallPositionY;
    }

    public void setWallPositionY(int[] wallPositionY) {
        this.wallPositionY = wallPositionY;
    }
}
