package WorldSim.World;

import Shapes.Hexagon;
import WorldSim.Organisms.Organism;
import WorldSim.WorldView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class HexWorld extends World {

    private int hexHeight;
    private int radius;
    private int side;
    private int t;
    private Hexagon hexCreator = new Hexagon();

    public HexWorld(WorldView worldView){
        super(27, 22, worldView);
        isHex = true;
        hexHeight = 30;
        radius = hexHeight/2;
        side = (int)(radius/cos(Math.toRadians(30)));
        t = (int)(radius*tan(Math.toRadians(30)));
    }

    @Override
    public void drawWorld(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < worldSizeX; i++){
            for(int j = 0; j < worldSizeY; j++){

                g2d.drawPolygon(hexCreator.createPolygon(i, j));
            }
        }

        for (int i = 0; i < orgQueue.size(); i++){

            orgQueue.get(i).draw(g);
        }
    }

    @Override
    public void addOnClick(MouseEvent e, String name) {

        Point hexClicked = hexCreator.clickedHex(e);

        if(hexClicked.x < 0 || hexClicked.y < 0 || hexClicked.x >= worldSizeX || hexClicked.y >= worldSizeY) return;

        Organism field = isEmpty(hexClicked.x, hexClicked.y);
        if(field == null)addOrganism(createOrganism(name, hexClicked.x, hexClicked.y));
    }
}
