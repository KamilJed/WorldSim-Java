package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.Organisms.Plants.Plant;
import WorldSim.World;

import java.awt.*;

public class Antelope extends Escaper {

    public Antelope(int x, int y, World world){
        super(x, y, world);
        initiative = 4;
        strength = 4;
        color = Color.ORANGE;
    }

    public Antelope(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 4;
        color = Color.ORANGE;
    }

    @Override
    public Organism clone(int x, int y){
        return new Antelope(x, y, world);
    }

    @Override
    public void action(){
        super.action();
        if(alive)super.action();
    }

    @Override
    public boolean escape(Organism organism, int dX, int dY){

        if(organism instanceof Plant)return false;
        if(instinct.nextInt(100) <= 50)
            return super.escape(organism, dX, dY);
        return false;
    }

    @Override
    public String getName(){
        return "Antelope";
    }

    @Override
    public void collision(Organism organism, int dX, int dY){
        if(organism.getClass() != getClass() && escape(organism, dX, dY)) return;
        else super.collision(organism, dX, dY);
    }

}
