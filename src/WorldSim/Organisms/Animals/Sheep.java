package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.awt.*;

public class Sheep extends Animal{

    public Sheep(int x, int y, World world){
        super(x, y, world);
        initiative = 4;
        strength = 4;
        color = Color.BLUE;
    }

    public Sheep(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 4;
        color = Color.BLUE;
    }

    @Override
    public Organism clone(int x, int y) {
        return new Sheep(x, y, world);
    }


    @Override
    public String getName(){
        return "Sheep";
    }
}
