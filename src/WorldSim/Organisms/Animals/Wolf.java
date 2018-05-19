package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.awt.*;

public class Wolf extends Animal {

    public Wolf(int x, int y, World world){
        super(x, y, world);
        initiative = 5;
        strength = 9;
        color = Color.BLACK;
    }

    public Wolf(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 5;
        strength = 9;
        color = Color.BLACK;
    }

    @Override
    public Organism clone(int x, int y){
        return new Wolf(x, y, world);
    }

    @Override
    public String getName(){
        return "Wolf";
    }
}
