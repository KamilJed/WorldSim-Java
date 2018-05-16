package WorldSim;

import WorldSim.Organisms.Animals.*;
import WorldSim.Organisms.Organism;
import WorldSim.Organisms.Plants.*;
import WorldSim.OwnCollections.InitiativeList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class World extends JPanel{

    private InitiativeList orgQueue;
    private LinkedList<Organism> delList;
    private WorldSimGUI graphicInterface;
    private int worldSizeX, worldSizeY;
    private boolean isHuman = false;
    private Human player;

    private void deleteOrganisms(){
        orgQueue.removeAll(delList);
    }

    private void addOrganism(Organism organism){
        orgQueue.addOrganism(organism);
    }

    Organism createOrganism(String name, int x, int y){

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

    private class KeyCatch extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(player != null)player.changeAction(e);
        }
    }

    private class MouseCatch extends MouseAdapter{

        private World parent;

         MouseCatch(World world){
            super();
            parent = world;
        }

        @Override
        public void mouseClicked(MouseEvent e){

            String name = graphicInterface.getChosenOrganism();

            int width = getWidth();
            int height = getHeight();

            int fieldWidth = width / worldSizeX;
            int fieldHeight = height / worldSizeY;

            int xOffset = (width - (worldSizeX * fieldWidth)) / 2;
            int yOffset = (height - (worldSizeY * fieldHeight)) / 2;

            if (e.getX() >= xOffset && e.getY() >= yOffset) {

                int column = (e.getX() - xOffset) /fieldWidth;
                int row = (e.getY() - yOffset) / fieldHeight;

                if (column >= 0 && row >= 0 && column < worldSizeX && row < worldSizeY) {

                   Organism field = parent.isEmpty(column, row);
                   if(field == null)addOrganism(parent.createOrganism(name, column, row));

                }
            }
            parent.repaint();
        }
    }

    private void deflatOrganism(String org, int init, int stght, int x, int y){

       if(org.equals("Fox"))orgQueue.addOrganism(new Fox(x, y, this, stght));
       else if(org.equals("Sheep")) orgQueue.addOrganism(new Sheep(x, y, this, stght));
       else if(org.equals("Wolf")) orgQueue.addOrganism(new Wolf(x, y, this, stght));
       else if(org.equals("Tortoise")) orgQueue.addOrganism(new Tortoise(x, y, this, stght));
       else if(org.equals("Dandelion")) orgQueue.addOrganism(new Dandelion(x, y, this, stght));
       else if(org.equals("DeadlyNightshade")) orgQueue.addOrganism(new DeadlyNightshade(x, y, this, stght));
       else if(org.equals("Grass")) orgQueue.addOrganism(new Grass(x, y, this, stght));
       else if(org.equals("Guarana")) orgQueue.addOrganism(new Guarana(x, y, this, stght));
       else if(org.equals("HeracleumSosnowskyi")) orgQueue.addOrganism(new HeracleumSosnowskyi(x, y, this, stght));
       else if(org.equals("Anetelope")) orgQueue.addOrganism(new Antelope(x, y, this, stght));
    }

    private void deflatOrganism(String org, int init, int stght, int x, int y, boolean special, int turns){
        orgQueue.addOrganism(new Human(x, y, this, stght, special, turns));
    }

    public World(WorldSimGUI graphicInterface){
        this(20, 20, graphicInterface);
    }

    public World(int sizeX, int sizeY, WorldSimGUI graphicInterface){
        super();

        this.graphicInterface = graphicInterface;
        worldSizeX = sizeX;
        worldSizeY = sizeY;
        orgQueue = new InitiativeList();
        delList = new LinkedList<>();
        addKeyListener(new KeyCatch());
        addMouseListener(new MouseCatch(this));
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
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        int fieldWidth = width / worldSizeX;
        int fieldHeight = height / worldSizeY;

        int xOffset = (width - (worldSizeX * fieldWidth)) / 2;
        int yOffset = (height - (worldSizeY * fieldHeight)) / 2;


        for(int i = 0; i < worldSizeY; i++){
            for(int j = 0; j < worldSizeX; j++){

               /* Rectangle2D rectangle2D = new Rectangle2D.Double(xOffset + (j * fieldWidth),yOffset + (i * fieldHeight),
                        fieldWidth, fieldHeight);
                g2d.draw(rectangle2D);*/

                int xpoints[] = {25, 145, 25, 145, 25};
                int ypoints[] = {25, 25, 145, 145, 25};
                int npoints = 5;

                g.drawPolygon(xpoints, ypoints, npoints);
            }
        }

       /* for (int i = 0; i < orgQueue.size(); i++){

            orgQueue.get(i).draw(g);
        }*/
    }

    public int getWorldSizeX() {
        return worldSizeX;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }

    public void startGame(){

        graphicInterface.setMessagesOutput("Press the new turn button to start.");
        repaint();
    }

    public void newTurn(){

        requestFocusInWindow();

        for(int i = 0; i < orgQueue.size(); i++){
            Organism org = orgQueue.get(i);
            if(org.isAlive() && org.isGrownUp())org.action();
            else org.growUp();
        }

        deleteOrganisms();
        if (isHuman)player.abilityControl();

        repaint();
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

    public void addBaby(Organism organism){
        orgQueue.addOrganism(organism);
    }

    public void setMessage(String message){
        graphicInterface.setMessagesOutput(message+"<br>");
    }

    public void initWorld(int sizeX, int sizeY){
        orgQueue.clear();
        delList.clear();
        isHuman = false;
        player = null;
        worldSizeY = sizeY;
        worldSizeX = sizeX;
        initWorld();
        requestFocusInWindow();
        repaint();
    }

    public void setHuman(Human human){
        isHuman = true;
        player = human;
    }

    public void delHuman(){
        isHuman = false;
        player = null;
    }

    public void saveGame(File file){

        try(PrintWriter out = new PrintWriter(file.getAbsolutePath())){

            out.println(worldSizeX);
            out.println(worldSizeY);
            for(Organism org : orgQueue){
                out.println(org.getFlatOrganism());
            }

            out.println("e");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void loadGame(File file){

        orgQueue.clear();
        delList.clear();
        isHuman = false;
        player = null;

        try {

            Scanner sc = new Scanner(file);
            worldSizeX = sc.nextInt();
            worldSizeY = sc.nextInt();

            while(sc.hasNextLine()){
                String s = sc.next();
                if(s.equals("e"))break;
                int init = sc.nextInt();
                int stght = sc.nextInt();
                int x = sc.nextInt();
                int y = sc.nextInt();
                if(s.equals("Human")){
                    boolean special = sc.nextBoolean();
                    int turns = sc.nextInt();
                    deflatOrganism(s, init, stght, x, y, special, turns);
                }
                else deflatOrganism(s, init, stght, x, y);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        requestFocusInWindow();
        repaint();
    }
}

