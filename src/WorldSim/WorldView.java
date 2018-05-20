package WorldSim;

import WorldSim.Organisms.Organism;
import WorldSim.World.HexWorld;
import WorldSim.World.SquareWorld;
import WorldSim.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class WorldView extends JPanel{

    private WorldSimGUI graphicInterface;
    private World world;

    private class KeyCatch extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            world.changeAction(e);
        }
    }

    private class MouseCatch extends MouseAdapter{

        private WorldView parent;

         MouseCatch(WorldView world){
            super();
            parent = world;
        }

        @Override
        public void mouseClicked(MouseEvent e){

            String name = graphicInterface.getChosenOrganism();
            world.addOnClick(e, name);
            parent.repaint();
        }
    }


    public WorldView(WorldSimGUI graphicInterface){
        this(20, 20, graphicInterface);
    }

    public WorldView(int sizeX, int sizeY, WorldSimGUI graphicInterface){
        super();

        this.graphicInterface = graphicInterface;
        addKeyListener(new KeyCatch());
        addMouseListener(new MouseCatch(this));
        world = new SquareWorld(sizeX, sizeY, this);
    }

    public void initWorldView(){

        world.initWorld();
        setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.drawWorld(g);
    }

    public void startGame(){

        graphicInterface.setMessagesOutput("Press the new turn button to start.");
        repaint();
    }

    public void newTurn(){

        requestFocusInWindow();

        world.newTurn();

        repaint();
    }

    public void setMessage(String message){
        graphicInterface.setMessagesOutput(message+"<br>");
    }

    public void saveGame(File file){

        try(PrintWriter out = new PrintWriter(file.getAbsolutePath())){

            out.println(world.isHexWorld());
            out.println(world.getWorldSizeX());
            out.println(world.getWorldSizeY());
            for(Organism org : world.getOrgQueue()){
                out.println(org.getFlatOrganism());
            }

            out.println("e");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void loadGame(File file){

        int worldSizeX, worldSizeY;
        boolean isHex;

        try {

            Scanner sc = new Scanner(file);
            isHex = sc.nextBoolean();
            worldSizeX = sc.nextInt();
            worldSizeY = sc.nextInt();

            if(isHex)world = new HexWorld(this);
            else world = new SquareWorld(worldSizeX, worldSizeY, this);

            world.clear();

            while(sc.hasNextLine()){
                String s = sc.next();
                if(s.equals("e"))break;
                int stght = sc.nextInt();
                int x = sc.nextInt();
                int y = sc.nextInt();
                if(s.equals("Human")){
                    boolean special = sc.nextBoolean();
                    int turns = sc.nextInt();
                    world.deflatOrganism(stght, x, y, special, turns);
                }
                else world.deflatOrganism(s, stght, x, y);
            }
        }
        catch(NoSuchElementException e){
            world = new SquareWorld(this);
            setMessage("Corrupted save file");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        repaint();
    }

    public void setWorld(World world) {
        this.world = world;
    }
}

