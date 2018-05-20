package Shapes;

import java.awt.*;
import java.awt.event.MouseEvent;

import static java.lang.Math.cos;
import static java.lang.Math.tan;

public class Hexagon {

    private int height = 30;
    private int radius = height/2;
    private int side = (int)(radius/cos(Math.toRadians(30)));
    private int t = (int)(radius*tan(Math.toRadians(30)));

    public Polygon createPolygon(int x, int y){

        int xP = x*(side + t);
        int yP = y*height + (x%2)*radius;
        int[] xCords, yCords;

        xCords = new int[] {xP + t,xP + side + t,xP + side + 2*t,xP + side + t, xP + t, xP};
        yCords = new int[] {yP, yP, yP + radius, yP + radius+ radius, yP + radius + radius, yP + radius};

        return new Polygon(xCords, yCords, 6);
    }

    public Point clickedHex(MouseEvent e){

        Point hexClicked = new Point(-1, -1);

        int x = e.getX() / (side+t);
        int y = (e.getY() - (x%2)*radius)/height;

        int dx = e.getX() - x*(side+t);
        int dy = e.getY() - y*height;

        if (e.getY() - (x%2)*radius < 0) return hexClicked;

        if (x%2==0) {
            if (dy > radius) {
                if (dx * radius /t < dy - radius) {
                    x--;
                }
            }
            if (dy < radius) {
                if ((t - dx)*radius/t > dy ) {
                    x--;
                    y--;
                }
            }
        } else {
            if (dy > height) {
                if (dx * radius/t < dy - height) {
                    x--;
                    y++;
                }
            }
            if (dy < height) {
                if ((t - dx)*radius/t > dy - radius) {
                    x--;
                }
            }
        }

        hexClicked.x = x;
        hexClicked.y = y;
        return hexClicked;
    }

}
