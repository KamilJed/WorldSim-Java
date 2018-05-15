package WorldSim.Organisms.Plants;

import WorldSim.Organisms.Animals.Animal;
import WorldSim.Organisms.Organism;
import WorldSim.World;

import java.awt.*;

public class HeracleumSosnowskyi extends Plant{

    public HeracleumSosnowskyi(int x, int y, World world){
        super(x, y, world);
        strength = 10;
        color = Color.GRAY;
    }

    public HeracleumSosnowskyi(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 10;
        color = Color.GRAY;
    }

    @Override
    public void action(){

        int maxX = world.getWorldSizeX();
        int maxY = world.getWorldSizeY();

        for (int i = -1; i < 2; i++) {

            for (int j = -1; j < 2; j++) {

                if (posY + i < 0 || posY + i >= maxY || (i == 0 && j == 0))break;
                if (posX + j >= 0 && posX + j < maxX) {

                    Organism organism = world.isEmpty(posX + j, posY + i);

                    if (organism instanceof Animal)
                        if(!organism.getSpecial()){
                            world.toDel(organism);
                            organism.kill();
                        }
                }
            }
        }
    }

    @Override
    public boolean isPoisonous(){
        return true;
    }

    @Override
    public Organism clone(int x, int y){
        return new HeracleumSosnowskyi(x, y, world);
    }

    @Override
    public String getName(){
        return "HeracleumSosnowskyi";
    }
}
