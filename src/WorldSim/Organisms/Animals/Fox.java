package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.World.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Fox extends Animal {

    public Fox(int x, int y, World world){
        super(x, y, world);
        initiative = 7;
        strength = 3;
        color = Color.PINK;
    }

    public Fox(int x, int y, World world, int strength){
        super(x, y, world, strength);
        initiative = 7;
        color = Color.PINK;
    }

    @Override
    public String getName(){
        return "Fox";
    }

    @Override
    public void draw(Graphics g){
        Graphics2D graphics2D = (Graphics2D)g;

        Rectangle2D rect = new Rectangle2D.Double(xOffset + (posX * fieldWidth),yOffset + (posY * fieldHeight),
                fieldWidth, fieldHeight);

        graphics2D.setColor(Color.PINK);
        graphics2D.fill(rect);
    }

    @Override
    public Organism clone(int x, int y){
        return new Fox(x, y, world);
    }

    @Override
    public void action(){

        int maxX = world.getWorldSizeX();
        int maxY = world.getWorldSizeY();
        int i = instinct.nextInt(3) - 1;

        for (int a = 0; a < 3; a++) {

            int j = instinct.nextInt(3) - 1;

            for (int b = 0; b < 2; b++) {

                if (posY + i < 0 || posY + i >= maxY || (i == 0 && j == 0)) {

                    if (++j == 2)j = -1;
                    break;
                }
                if (posX + j >= 0 && posX + j < maxX) {

                    Organism organism = world.isEmpty(posX + j, posY + i);

                    if (organism == null) {
                        posX += j;
                        posY += i;
                        return;
                    }
                    else if (organism.getStrength() <= strength || organism.getClass() == getClass()) {

                        posX += j;
                        posY += i;
                        super.collision(organism, j, i);
                        return;
                    }
                }
            }

            if (++i == 2)i = -1;
        }
    }
}
