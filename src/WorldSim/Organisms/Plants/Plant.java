package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Organism;
import WorldSim.World;

import java.util.Random;

public abstract class Plant extends Organism {

    Random forestMind = new Random();

    public Plant(int x, int y, World world){
        super(x, y, world);
    }

    public Plant(int x, int y, World world, int strength){
        super(x, y, world, strength);
    }

    @Override
    public void action() {

        if (forestMind.nextInt(100) < 20) {

            int maxX = world.getWorldSizeX();
            int maxY = world.getWorldSizeY();

            for (int i = -1; i < 2; i++) {

                for (int j = -1; j < 2; j++) {

                    if (posY + i < 0 || posY + i >= maxY || (i == 0 && j == 0))break;
                    if (posX + j >= 0 && posX + j < maxX && world.isEmpty(posX + j, posY + i) == null) {

                        world.addBaby(clone(posX + j, posY + i));
                        world.setMessage("New " + getName() +" has grown");
                        return;
                    }
                }
            }
        }
    }

    public int boost(){
        return 0;
    }

    public boolean isPoisonous(){
        return false;
    }
}
