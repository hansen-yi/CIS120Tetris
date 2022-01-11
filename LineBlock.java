package org.cis120.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class LineBlock extends Piece {
    private Position[] coordinates;
    private boolean rotate;

    public LineBlock(int courtWidth, int courtHeight) {
        super(30, 90, 0, 180, 570, courtWidth, courtHeight, 2);
        coordinates = new Position[4];
        coordinates[0] = new Position(90, 0);
        coordinates[1] = new Position(120, 0);
        coordinates[2] = new Position(150, 0);
        coordinates[3] = new Position(180, 0);
        super.setCoordinates(coordinates);
        rotate = this.isRotated();
    }

    @Override
    public void rotate() {
        int y = this.getPy();
        int x = this.getPx();
        this.setPx(y);
        this.setPy(x);

        int maxy = this.getMaxY();
        int maxx = this.getMaxX();
        this.setMaxX(maxy);
        this.setMaxY(maxx);
        this.setVx(30);
        this.setVy(0);
        coordinates = new Position[4];
        coordinates[0] = new Position(this.getPy() + 60, this.getPx() - 60);
        coordinates[1] = new Position(this.getPy() + 60, this.getPx() - 30);
        coordinates[2] = new Position(this.getPy() + 60, this.getPx());
        coordinates[3] = new Position(this.getPy() + 60, this.getPx() + 30);
        super.setCoordinates(coordinates);

        rotate = !rotate;
        this.setRotated(rotate);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isMoving() && !rotate) {
            this.coordinates = new Position[4];
            coordinates[0] = new Position(this.getPx(), this.getPy());
            coordinates[1] = new Position(this.getPx() + 30, this.getPy());
            coordinates[2] = new Position(this.getPx() + 60, this.getPy());
            coordinates[3] = new Position(this.getPx() + 90, this.getPy());
            super.setCoordinates(coordinates);
            for (int i = 0; i < coordinates.length; i++) {
                Square curr = new Square(
                        coordinates[i].getX(), coordinates[i].getY(), Color.CYAN
                );
                curr.draw(g);
                g.setColor(Color.GRAY);
                g.drawRect(coordinates[i].getX(), coordinates[i].getY(), 30, 30);
            }
        } else if (this.isMoving() && rotate) {
            this.coordinates = new Position[4];
            coordinates[0] = new Position(this.getPy() + 60, this.getPx() - 60);
            coordinates[1] = new Position(this.getPy() + 60, this.getPx() - 30);
            coordinates[2] = new Position(this.getPy() + 60, this.getPx());
            coordinates[3] = new Position(this.getPy() + 60, this.getPx() + 30);
            super.setCoordinates(coordinates);
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
