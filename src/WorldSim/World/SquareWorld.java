package WorldSim.World;

import WorldSim.Organisms.Organism;
import WorldSim.WorldView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class SquareWorld extends World {

    public SquareWorld(WorldView worldView){
        super(worldView);
        isHex = false;
        worldView.setHex(isHex);
    }

    public SquareWorld(int sizeX, int sizeY, WorldView worldView){
        super(sizeX, sizeY, worldView);
        isHex = false;
        worldView.setHex(isHex);
    }

    @Override
    public void drawWorld(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        double fieldWidth = width / worldSizeX;
        double fieldHeight = height / worldSizeY;

        double xOffset = (width - (worldSizeX * fieldWidth)) / 2;
        double yOffset = (height - (worldSizeY * fieldHeight)) / 2;


        for(int i = 0; i < worldSizeY; i++){
            for(int j = 0; j < worldSizeX; j++){

                Rectangle2D rectangle2D = new Rectangle2D.Double(xOffset + (j * fieldWidth),yOffset + (i * fieldHeight),
                        fieldWidth, fieldHeight);
                g2d.draw(rectangle2D);
            }
        }

        for (int i = 0; i < orgQueue.size(); i++){

            orgQueue.get(i).draw(g);
        }
    }

    @Override
    public void addOnClick(MouseEvent e, String name){

        int width = getWidth();
        int height = getHeight();

        double fieldWidth = width / worldSizeX;
        double fieldHeight = height / worldSizeY;

        double xOffset = (width - (worldSizeX * fieldWidth)) / 2;
        double yOffset = (height - (worldSizeY * fieldHeight)) / 2;

        if (e.getX() >= xOffset && e.getY() >= yOffset) {

            int column = (int)((e.getX() - xOffset) /fieldWidth);
            int row = (int)((e.getY() - yOffset) / fieldHeight);

            if (column >= 0 && row >= 0 && column < worldSizeX && row < worldSizeY) {

                Organism field = isEmpty(column, row);
                if(field == null)addOrganism(createOrganism(name, column, row));
            }
        }
    }
}
