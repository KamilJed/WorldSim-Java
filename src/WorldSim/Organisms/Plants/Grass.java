package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Organism;
import WorldSim.World;

import java.awt.*;

public class Grass extends Plant {

    public Grass(int x, int y, World world){
        super(x, y, world);
        color = Color.GREEN;
    }

    public Grass(int x, int y, World world, int strength){
        super(x, y, world, strength);
        color = Color.GREEN;
    }

    @Override
    public Organism clone(int x, int y) {
        return new Grass(x, y, world);
    }

    @Override
    public String getName(){
        return "Grass";
    }
}
