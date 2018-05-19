package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.awt.*;

public class Guarana extends Plant {

    public Guarana(int x, int y, World world){
        super(x, y, world);
        color = Color.LIGHT_GRAY;
    }

    public Guarana(int x, int y, World world, int strength){
        super(x, y, world, strength);
        color = Color.LIGHT_GRAY;
    }

    @Override
    public String getName(){
        return "Guarana";
    }

    @Override
    public Organism clone(int x, int y){
        return new Guarana(x, y, world);
    }

    @Override
    public int boost(){
        return 3;
    }
}
