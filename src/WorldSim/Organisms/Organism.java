package WorldSim.Organisms;

import WorldSim.Organisms.Animals.Animal;
import WorldSim.Organisms.Plants.Plant;
import WorldSim.World.World;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Organism {
    protected int strength = 0;
    protected int initiative = 0;
    protected int posX, posY;
    protected World world;
    protected double fieldWidth;
    protected double fieldHeight;
    protected  double xOffset;
    protected double yOffset;
    protected boolean grownUp = false;
    protected boolean alive = true;
    protected Color color;

    public Organism(int x, int y, World world){
        posX = x;
        posY = y;
        this.world = world;
        int width = world.getWidth();
        int height = world.getHeight();
        fieldWidth = width / world.getWorldSizeX();
        fieldHeight = height / world.getWorldSizeY();
        xOffset = (width - (world.getWorldSizeX() * fieldWidth)) / 2;
        yOffset = (height - (world.getWorldSizeY() * fieldHeight)) / 2;
    }

    public Organism(int x, int y, World world, int strength){
        this(x, y, world);
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void draw(Graphics g){

        Graphics2D graphics2D = (Graphics2D)g;

        Rectangle2D rect = new Rectangle2D.Double(xOffset + (posX * fieldWidth),yOffset + (posY * fieldHeight),
                fieldWidth, fieldHeight);

        graphics2D.setColor(color);
        graphics2D.fill(rect);
    }

    public void kill(){
        alive = false;
    }

    public boolean isAlive(){
        return alive;
    }

    public void collision(Organism organism, int dX, int dY){

        if(organism instanceof Plant){

            Plant plant = (Plant)organism;
            strength += plant.boost();

            if(plant.isPoisonous() && !getSpecial()){

                world.setMessage(getName() + " has been poisoned");
                kill();
                world.toDel(this);
                plant.kill();
                world.toDel(organism);
                return;
            }
        }
        else if(organism instanceof Animal){

            Animal animal = (Animal)organism;

            if(animal.escape(this, dX, dY)){
                world.setMessage(animal.getName() + " has escaped");
                return;
            }
            if(animal.deflectAttack(this)){
                world.setMessage(animal.getName() + " has deflected the attack");
                posX -= dX;
                posY -= dY;
                return;
            }
        }
        if(strength < organism.getStrength()){
            kill();
            world.toDel(this);
            world.setMessage(organism.getName() + " killed " + getName());
            return;
        }
        else{
            organism.kill();
            world.toDel(organism);
            world.setMessage(getName() + " killed " + organism.getName());
            return;
        }
    }

    public boolean deflectAttack(Organism organism){
        return false;
    }

    public boolean isGrownUp() {
        return grownUp;
    }

    public boolean getSpecial(){
        return false;
    }

    public abstract void action();

    public abstract Organism clone(int x, int y);

    public void growUp(){
        grownUp = true;
    }

    public abstract String getName();

    public String getFlatOrganism(){

        String flat = "";

        flat += getName();
        flat += ' ';
        flat += strength;
        flat += ' ';
        flat += posX;
        flat += ' ';
        flat += posY;

        return flat;
    }
}
