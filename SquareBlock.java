package org.cis120.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class SquareBlock extends Piece {

    private Position[] coordinates;

    public SquareBlock(int courtWidth, int courtHeight) {
        super(30, 120, 0, 240, 540, courtWidth, courtHeight, 1);
        this.coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx(), this.getPy() + 30);
        coordinates[2] = new Position(this.getPx() + 30, this.getPy() + 30);
        coordinates[3] = new Position(this.getPx() + 30, this.getPy());
        this.setCoordinates(coordinates);
    }
    
    @Override
    public void rotate() {
    }

    public void updateCoordinates() {
        this.coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx(), this.getPy() + 30);
        coordinates[2] = new Position(this.getPx() + 30, this.getPy() + 30);
        coordinates[3] = new Position(this.getPx() + 30, this.getPy());
        this.setCoordinates(coordinates);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isMoving()) {
            this.updateCoordinates();
            for (int i = 0; i < coordinates.length; i++) {
                Square curr = new Square(
                        coordinates[i].getX(), coordinates[i].getY(), Color.YELLOW
                );
                curr.draw(g);
                g.setColor(Color.GRAY);
                g.drawRect(coordinates[i].getX(), coordinates[i].getY(), 30, 30);
            }
        }
    }

}
