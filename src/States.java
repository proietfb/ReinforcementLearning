import java.util.ArrayList;

/**
 * Created by proietfb on 7/13/16.
 */
public class States {

    public static int STATE_POSITION = 0;
    public static int STATE_SIGNALPOWER = 1;
    public static int STATE_N_NEIGHBOURS = 2;
    public static int STATE_N_GOALS = 3;

    private ArrayList<ArrayList<Integer>> definePossibleStates;
    ArrayList<Integer> nextStatesList;

    public States() {
        definePossibleStates = new ArrayList<>();

    }

    public ArrayList<ArrayList<Integer>> defineStates(GridWorld gridWorld) {
        for (int i = 0; i < gridWorld.getGridValues().length; i++) {
            for (int j = 0; j < gridWorld.defineGridValues()[0].length; j++) {
                definePossibleStates.add(gridWorld.getGridValues()[i][j], nextState(i, j, gridWorld));
            }
        }
        return definePossibleStates;
    }

    private ArrayList<Integer> nextState(int i, int j, GridWorld gridWorld) {
        nextStatesList = new ArrayList<>();

        int statesFromCell = gridWorld.getGridValues()[i][j];

        if (statesFromCell == 0) { //in alto a sx
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]); //in basso
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]); //a dx
        } else if (statesFromCell == gridWorld.getGridValues()[0][gridWorld.getyGrid() - 1]) { //in alto a dx
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]);
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]);
        } else if (statesFromCell == gridWorld.getGridValues()[gridWorld.getxGrid() - 1][0]) { // in basso a sx
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]);
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]);
        } else if (statesFromCell == gridWorld.getGridValues()[gridWorld.getxGrid() - 1][gridWorld.getyGrid() - 1]) { //in basso a dx
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]);
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]);
        } else if (i == 0 && statesFromCell > 0 && statesFromCell < gridWorld.getGridValues()[0][gridWorld.getyGrid() - 1]) { //prima riga in alto
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]); //in basso
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]); //a dx
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]); //a sx
        } else if (i == gridWorld.getxGrid() - 1 && statesFromCell > gridWorld.getGridValues()[gridWorld.getxGrid() - 1][0] && statesFromCell < gridWorld.getGridValues()[gridWorld.getxGrid() - 1][gridWorld.getyGrid() - 1]) {
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]); //a dx
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]); //a sx
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]); //in alto
        } else if (j == 0 && statesFromCell > gridWorld.getGridValues()[0][0] && statesFromCell < gridWorld.getGridValues()[gridWorld.getxGrid() - 1][0]) { // prima colonna
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]); //in alto
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]); //in basso
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]); //a dx
        } else if (j == gridWorld.getyGrid() - 1 && statesFromCell > gridWorld.getGridValues()[0][gridWorld.getxGrid() - 1] && statesFromCell < gridWorld.getGridValues()[gridWorld.getxGrid() - 1][gridWorld.getyGrid() - 1]) { //ultima colonna
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]); //in alto
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]); //in basso
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]); //a sx
        } else {
            nextStatesList.add(gridWorld.getGridValues()[i - 1][j]); //in alto
            nextStatesList.add(gridWorld.getGridValues()[i + 1][j]); //in basso
            nextStatesList.add(gridWorld.getGridValues()[i][j - 1]); //a sx
            nextStatesList.add(gridWorld.getGridValues()[i][j + 1]); //a dx
        }
        return nextStatesList;
    }

    public ArrayList<ArrayList<Integer>> getDefinePossibleStates() {
        return definePossibleStates;
    }
}
