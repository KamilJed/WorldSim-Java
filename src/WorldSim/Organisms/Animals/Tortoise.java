package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World;

import java.awt.*;

public class Tortoise extends Animal {

    public Tortoise(int x, int y, World world){
        super(x, y, world);
        initiative = 1;
        strength = 2;
        color = Color.MAGENTA;
    }

    public Tortoise(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 1;
        color = Color.MAGENTA;
    }

    @Override
    public void action(){
        if(instinct.nextInt(100) < 25){
            super.action();
        }
    }

    @Override
    public Organism clone(int x, int y){
        return new Tortoise(x, y, world);
    }

    @Override
    public boolean deflectAttack(Organism organism){
        return organism.getStrength() < 5;
    }

    @Override
    public String getName(){
        return "Tortoise";
    }
}
