package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.awt.*;

public class Dandelion extends Plant{

    public Dandelion(int x, int y, World world){
        super(x, y, world);
        color = Color.YELLOW;
    }

    public Dandelion(int x, int y, World world, int strenght){
        super(x, y, world, strenght);
        color = Color.YELLOW;
    }

    @Override
    public Organism clone(int x, int y) {
        return new Dandelion(x, y, world);
    }

    @Override
    public String getName(){
        return "Dandelion";
    }

    @Override
    public void action(){
        for(int i = 0; i < 3; i++){
            super.action();
        }
    }
}
