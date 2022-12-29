package org.cis120.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class ReverseLBlock extends Piece {
    private Position[] coordinates;

    public ReverseLBlock(int courtWidth, int courtHeight) {
        super(30, 90, 0, 210, 540, courtWidth, courtHeight, 7);
        coordinates = new Position[4];
        coordinates[0] = new Position(90, 0);
        coordinates[1] = new Position(90, 30);
        coordinates[2] = new Position(120, 30);
        coordinates[3] = new Position(150, 30);
        super.setCoordinates(coordinates);
    }

    @Override
    public void draw(Graphics g) {
        if (this.isMoving()) {
        	int[] xAxis = this.getxAxis();
    		int[] yAxis = this.getyAxis();
            this.coordinates = new Position[4];
            coordinates[0] = new Position(this.getPx(), this.getPy());
            coordinates[1] = new Position(this.getPx() + 30 * yAxis[0], this.getPy() + 30 * yAxis[1]);
            coordinates[2] = new Position(this.getPx() + 30 * xAxis[0] + 30 * yAxis[0], this.getPy() + 30 * xAxis[1] + 30 * yAxis[1]);
            coordinates[3] = new Position(this.getPx() + 60 * xAxis[0] + 30 * yAxis[0], this.getPy() + 60 * xAxis[1] + 30 * yAxis[1]);
            super.setCoordinates(coordinates);
            int diffRight = 0;
            int diffBottom = 0;
            for (int i = 0; i < coordinates.length; i++) {
            	if (this.getXDiffFromIntial(coordinates[i]) > diffRight) {
            		diffRight = this.getXDiffFromIntial(coordinates[i]);
            	}
            	if (this.getYDiffFromIntial(coordinates[i]) > diffBottom) {
            		diffBottom = this.getYDiffFromIntial(coordinates[i]);
            	}
            }
            this.setMaxX(this.courtWidth - diffRight - 30);
            this.setMaxY(this.courtHeight - diffBottom - 30);
            this.adjust();
            for (int i = 0; i < coordinates.length; i++) {
                Square curr = new Square(
                        coordinates[i].getX(), coordinates[i].getY(), Color.BLUE
                );
                curr.draw(g);
                g.setColor(Color.GRAY);
                g.drawRect(coordinates[i].getX(), coordinates[i].getY(), 30, 30);
            }
        }
    }

	@Override
	public void updateCoords() {
		this.coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx(), this.getPy() + 30);
        coordinates[2] = new Position(this.getPx() + 30, this.getPy() + 30);
        coordinates[3] = new Position(this.getPx() + 60, this.getPy() + 30);
        this.setCoordinates(coordinates);
	}
}
