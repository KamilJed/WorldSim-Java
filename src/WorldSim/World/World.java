package WorldSim.World;

import WorldSim.Organisms.Animals.*;
import WorldSim.Organisms.Organism;
import WorldSim.Organisms.Plants.*;
import WorldSim.OwnCollections.InitiativeList;
import WorldSim.WorldView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class World {

    protected int width;
    protected int height;
    protected InitiativeList orgQueue;
    protected LinkedList<Organism> delList;
    protected int worldSizeX, worldSizeY;
    protected boolean isHuman = false;
    protected Human player;
    protected boolean isHex;
    protected WorldView worldView;;


    protected void deleteOrganisms(){
        orgQueue.removeAll(delList);
    }

    protected Organism createOrganism(String name, int x, int y){

        if(name.equals("Antelope"))return new Antelope(x, y, this);
        else if(name.equals("Fox"))return new Fox(x, y, this);
        else if(name.equals("Sheep"))return new Sheep(x, y, this);
        else if(name.equals("Tortoise"))return new Tortoise(x, y, this);
        else if(name.equals("Wolf"))return new Wolf(x, y, this);
        else if(name.equals("Dandelion"))return new Dandelion(x, y, this);
        else if(name.equals("Deadly Nightshade"))return new DeadlyNightshade(x, y, this);
        else if(name.equals("Grass"))return new Grass(x, y, this);
        else if(name.equals("Guarana"))return new Guarana(x, y, this);
        else if(name.equals("Heracleum Sosnowskyi"))return new HeracleumSosnowskyi(x, y, this);

        return null;
    }

    public World(WorldView worldView){
        this(20, 20, worldView);
    }

    public World(int sizeX, int sizeY, WorldView worldView){

        worldSizeX = sizeX;
        worldSizeY = sizeY;
        this.worldView = worldView;
        orgQueue = new InitiativeList();
        delList = new LinkedList<>();
        initWorld();
    }

    public void initWorld(){

        Random random = new Random();

        for(int i = 0; i < worldSizeY; i++){

            for(int j = 0; j < worldSizeX; j++) {

                int character = random.nextInt(40);

                if (character == 0) orgQueue.addOrganism(new Sheep(j, i, this));
                else if (character == 1) orgQueue.addOrganism(new Wolf(j, i, this));
                else if (character == 2) orgQueue.addOrganism(new Grass(j, i, this));
                else if (character == 3 && !isHuman) orgQueue.addOrganism(new Human(j, i, this));
                else if (character == 4) orgQueue.addOrganism(new Tortoise(j, i, this));
                else if (character == 5) orgQueue.addOrganism(new Dandelion(j, i, this));
                else if (character == 6) orgQueue.addOrganism(new Antelope(j, i, this));
                else if (character == 7) orgQueue.addOrganism(new Guarana(j, i, this));
                else if (character == 8) orgQueue.addOrganism(new Fox(j, i, this));
                else if (character == 9) orgQueue.addOrganism(new DeadlyNightshade(j, i, this));
                else if (character == 10) orgQueue.addOrganism(new HeracleumSosnowskyi(j, i, this));
            }
        }
    }

    public void deflatOrganism(String org, int stght, int x, int y){

        if(org.equals("Fox"))orgQueue.addOrganism(new Fox(x, y, this, stght));
        else if(org.equals("Sheep")) orgQueue.addOrganism(new Sheep(x, y, this, stght));
        else if(org.equals("Wolf")) orgQueue.addOrganism(new Wolf(x, y, this, stght));
        else if(org.equals("Tortoise")) orgQueue.addOrganism(new Tortoise(x, y, this, stght));
        else if(org.equals("Dandelion")) orgQueue.addOrganism(new Dandelion(x, y, this, stght));
        else if(org.equals("DeadlyNightshade")) orgQueue.addOrganism(new DeadlyNightshade(x, y, this, stght));
        else if(org.equals("Grass")) orgQueue.addOrganism(new Grass(x, y, this, stght));
        else if(org.equals("Guarana")) orgQueue.addOrganism(new Guarana(x, y, this, stght));
        else if(org.equals("HeracleumSosnowskyi")) orgQueue.addOrganism(new HeracleumSosnowskyi(x, y, this, stght));
        else if(org.equals("Antelope")) orgQueue.addOrganism(new Antelope(x, y, this, stght));
    }

    public void deflatOrganism(int stght, int x, int y, boolean special, int turns){
        orgQueue.addOrganism(new Human(x, y, this, stght, special, turns));
    }

    public abstract void drawWorld(Graphics g);

    public abstract void addOnClick(MouseEvent e, String name);

    public int getWorldSizeX() {
        return worldSizeX;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }

    public void newTurn(){

        for(int i = 0; i < orgQueue.size(); i++){
            Organism org = orgQueue.get(i);
            if(org.isAlive() && org.isGrownUp())org.action();
            else org.growUp();
        }

        deleteOrganisms();
        if (isHuman)player.abilityControl();
    }

    public void toDel(Organism organism){
        delList.add(organism);
    }

    public Organism isEmpty(int x, int y){
        List<Organism> result = orgQueue.stream()
                .filter(item -> item.getPosX() == x && item.getPosY() == y)
                .collect(Collectors.toList());

        if(result.size() == 0)return null;
        return result.get(0);
    }

    public void addOrganism(Organism organism){
        orgQueue.addOrganism(organism);
    }

    public void setMessage(String message){
        worldView.setMessage(message);
    }

    public void setHuman(Human human){
        isHuman = true;
        player = human;
    }

    public void delHuman(){
        isHuman = false;
        player = null;
    }

    public int getWidth(){
        return worldView.getWidth();
    }

    public int getHeight(){
        return worldView.getWidth();
    }

    public void changeAction(KeyEvent e){
        if(player != null)player.changeAction(e);
    }

    public boolean isHexWorld(){
        return isHex;
    }

    public InitiativeList getOrgQueue() {
        return orgQueue;
    }

    public void clear(){
        orgQueue.clear();
        isHuman = false;
        player = null;
    }
}
