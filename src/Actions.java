/**
 * Created by proietfb on 7/13/16.
 */
public class Actions {

    public Actions() {

    }

    public void moveUp(Agent agent){

        if(agent.getCurrentPositionX() != 0)
            agent.setCurrentPositionX(agent.getCurrentPositionX()-1);

    }

    public void moveDown(Agent agent){

        if(agent.getCurrentPositionX() != (agent.getDimGridX()-1))
            agent.setCurrentPositionX(agent.getCurrentPositionX()+1);

    }

    public void moveRight(Agent agent){

        if(agent.getCurrentPositionY() != (agent.getDimGridY()-1))
            agent.setCurrentPositionY(agent.getCurrentPositionY()+1);
    }

    public void moveLeft(Agent agent){

        if(agent.getCurrentPositionY() != 0)
            agent.setCurrentPositionY(agent.getCurrentPositionY()-1);
    }

    public void actionIncrementSignal(Agent agent){

        if (agent.getSignalPower() != Agent.maxSignal)
            agent.setSignalPower(agent.getSignalPower()+1);
    }

    public void actionDecrementSignal(Agent agent){

        if (agent.getSignalPower() != 0)
            agent.setSignalPower(agent.getSignalPower()-1);
    }
}
