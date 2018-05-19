package WorldSim.Organisms.Animals;

import WorldSim.Organisms.Organism;
import WorldSim.Organisms.Plants.Plant;
import WorldSim.World.World;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Human extends Escaper {

    private int dX, dY;
    private boolean special;
    private int turnsRemaining;

    public Human(int x, int y, World world){
        super(x, y, world);
        initiative = 4;
        strength = 5;
        color = Color.RED;
        world.setHuman(this);
    }

    public Human(int x, int y, World world, int strength, boolean special, int turns){
        super(x, y, world, strength);
        initiative = 4;
        color = Color.RED;
        world.setHuman(this);
        this.special = special;
        turnsRemaining = turns;
    }

    @Override
    public String getName(){
        return "Human";
    }

    @Override
    public Organism clone(int x, int y){
        return new Human(x, y, world);
    }

    @Override
    public void kill(){
        super.kill();
        world.delHuman();
    }

    @Override
    public void action(){

        if(posY + dY > 0 && posY + dY < world.getWorldSizeY() && posX + dX > 0 && posX + dX < world.getWorldSizeX()){

            Organism field = world.isEmpty(posX + dX, posY + dY);
            posX += dX;
            posY += dY;
            if ((dX != 0 || dY != 0) && field != null) collision(field, dX, dY);
        }

        dX = 0;
        dY = 0;
    }

    public void changeAction(KeyEvent e){

        dX = 0;
        dY = 0;

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_DOWN){
            dY = 1;
        }
        if(key == KeyEvent.VK_UP){
            dY = -1;
        }
        if(key == KeyEvent.VK_LEFT){
            dX = -1;
        }
        if(key == KeyEvent.VK_RIGHT){
            dX = 1;
        }
        if(key == KeyEvent.VK_E){
            if (turnsRemaining == 0) {
                special = true;
                turnsRemaining = 5;
                color = new Color(122, 16, 0);
            }
        }
    }

    @Override
    public boolean getSpecial(){
        return special;
    }

    public void abilityControl() {
        if (turnsRemaining < 0) turnsRemaining++;
        else if (turnsRemaining != 0) turnsRemaining--;

        if (turnsRemaining == 0 && special) {
            turnsRemaining = -5;
            special = false;
            color = Color.RED;
        }
    }

    @Override
    public boolean escape(Organism organism, int dX, int dY){

        if(organism instanceof Plant)return false;

        if(special && organism.getStrength() >= strength)
            return super.escape(organism, dX, dY);
        return false;
    }

    @Override
    public String getFlatOrganism(){
        String flat = super.getFlatOrganism();
        flat += ' ';
        flat += special;
        flat += ' ';
        flat += turnsRemaining;

        return flat;
    }
}
