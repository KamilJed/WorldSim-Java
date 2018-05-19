package WorldSim;

import WorldSim.World.SquareWorld;
import WorldSim.World.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WorldView extends JPanel{

    private boolean isHex;
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

 /*   public void initWorld(int sizeX, int sizeY){
        world.initWorld(sizeX, sizeY);
        requestFocusInWindow();
        repaint();
    }*/

    public void saveGame(File file){

       world.saveGame(file);
       setMessage("Game saved");
    }

    public void loadGame(File file){

        world.loadGame(file);
        requestFocusInWindow();
        repaint();
    }

    public void setHex(boolean hex) {
        isHex = hex;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}

