package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

public abstract class Escaper extends Animal{

    public Escaper(int x, int y, World world){
        super(x, y, world);
    }

    public Escaper(int x, int y, World world, int strength){
        super(x, y, world, strength);
    }

    @Override
    public boolean escape(Organism organism, int dX, int dY){

        int maxX = world.getWorldSizeX();
        int maxY = world.getWorldSizeY();

        for (int i = -1; i < 2; i++) {

            for (int j = -1; j < 2; j++) {

                if (posY + i < 0 || posY + i >= maxY)break;
                if (posX + j >= 0 && posX + j < maxX && world.isEmpty(posX + j, posY + i) == null) {

                    posX += j;
                    posY += i;
                    return true;
                }
            }
        }
        return false;
    }
}
