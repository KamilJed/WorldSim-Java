package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Organism;
import WorldSim.World;

import java.awt.*;

public class DeadlyNightshade extends Plant {

    public DeadlyNightshade(int x, int y, World world){
        super(x, y, world);
        strength = 99;
        color = Color.CYAN;
    }

    public DeadlyNightshade(int x, int y, World world, int strength){
        super(x, y, world, strength);
        strength = 99;
        color = Color.CYAN;
    }

    @Override
    public boolean isPoisonous(){
        return true;
    }

    @Override
    public Organism clone(int x, int y){
        return new DeadlyNightshade(x, y, world);
    }

    @Override
    public String getName(){
        return "DeadlyNightshade";
    }
}
