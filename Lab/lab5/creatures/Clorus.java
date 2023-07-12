package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature{
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to e
     * and default colors.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }
    @Override
    public void move() {
        energy-=0.03;
        if(energy<0)
        {
            energy=0;

        }
    }

    @Override
    public void attack(Creature c) {
        energy+=c.energy();

    }

    @Override
    public Creature replicate() {
        energy=0.5*energy;
        Clorus babyClorus=new Clorus(energy);
        return babyClorus;
    }

    @Override
    public void stay() {
        energy-=0.01;
        if(energy<0)
        {
            energy=0;
        }
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        if(energy<=0)
        {
            return new Action(Action.ActionType.DIE);
        }
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip=false;
        for(Direction key:neighbors.keySet())
        {
            if(neighbors.get(key).name().equals("empty"))
            {
                emptyNeighbors.add(key);
            }
            else if(neighbors.get(key).name().equals("plip"))
            {
                plipNeighbors.add(key);
                anyPlip=true;
            }
        }
        // Rule 1
        if (emptyNeighbors.size()==0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        else if(anyPlip)
        {
            return new Action(Action.ActionType.ATTACK,randomEntry(plipNeighbors));
        }

        // Rule 3
        else if(energy>=1)
        {
            return new Action(Action.ActionType.REPLICATE,randomEntry(emptyNeighbors));
        }
        // Rule 4
        else
        {
            return new Action(Action.ActionType.MOVE,randomEntry(emptyNeighbors));
        }
    }

    @Override
    public Color color() {
        return color(r,g,b);
    }
}
