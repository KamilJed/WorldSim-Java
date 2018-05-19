package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.util.Random;

public abstract class Animal extends Organism {

    protected Random instinct = new Random();

    public Animal(int x, int y, World world){
        super(x, y, world);
    }

    public Animal(int x, int y, World world, int strength){
        super(x, y, world, strength);
    }

    @Override
    public void action(){

        int dX = 0;
        int dY = 0;

        do {
            dX = instinct.nextInt(3) - 1;
        } while (posX + dX < 0 || posX + dX >= world.getWorldSizeX());

        do {
            dY = instinct.nextInt(3) - 1;
        } while (posY + dY < 0 || posY + dY >= world.getWorldSizeY());

        Organism field = world.isEmpty(posX + dX, posY + dY);

        posX += dX;
        posY += dY;

        if ((dX != 0 || dY != 0) && field != null) collision(field, dX, dY);
    }

    @Override
    public void collision(Organism organism, int dX, int dY){

        if(this.getClass() == organism.getClass()){

            posX -= dX;
            posY -= dY;
            int maxX = world.getWorldSizeX();
            int maxY = world.getWorldSizeY();

            for (int i = -1; i < 2; i++) {

                for (int j = -1; j < 2; j++) {

                    if (posY + i < 0 || posY + i >= maxY)break;
                    if (posX + j >= 0 && posX + j < maxX && world.isEmpty(posX + j, posY + i) == null) {

                        world.addOrganism(clone(posX + j, posY + i));
                        world.setMessage("New " + organism.getName() + " is born");
                        return;
                    }
                }
            }
        }
        else super.collision(organism, dX, dY);
    }

    public boolean escape(Organism organism, int dX, int dY){
        return false;
    }
}
