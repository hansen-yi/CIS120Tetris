package org.cis120.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class LBlock extends Piece {
    private Position[] coordinates;

    public LBlock(int courtWidth, int courtHeight) {
        super(30, 90, 30, 210, 570, courtWidth, courtHeight, 6);
        coordinates = new Position[4];
        coordinates[0] = new Position(90, 30);
        coordinates[1] = new Position(120, 30);
        coordinates[2] = new Position(150, 30);
        coordinates[3] = new Position(150, 0);
        super.setCoordinates(coordinates);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isMoving()) {
            this.coordinates = new Position[4];
            coordinates[0] = new Position(this.getPx(), this.getPy());
            coordinates[1] = new Position(this.getPx() + 30, this.getPy());
            coordinates[2] = new Position(this.getPx() + 60, this.getPy());
            coordinates[3] = new Position(this.getPx() + 60, this.getPy() - 30);
            super.setCoordinates(coordinates);
            for (int i = 0; i < coordinates.length; i++) {
                Square curr = new Square(
                        coordinates[i].getX(), coordinates[i].getY(), Color.ORANGE
                );
                curr.draw(g);
                g.setColor(Color.GRAY);
                g.drawRect(coordinates[i].getX(), coordinates[i].getY(), 30, 30);
            }
        }
    }
}
