package org.cis120.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class LineBlock extends Piece {
    private Position[] coordinates;

    public LineBlock(int courtWidth, int courtHeight) {
        super(30, 90, 0, 180, 570, courtWidth, courtHeight, 2);
//        coordinates = new Position[4];
//        coordinates[0] = new Position(90, 0);
//        coordinates[1] = new Position(120, 0);
//        coordinates[2] = new Position(150, 0);
//        coordinates[3] = new Position(180, 0);
        coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx() + 30, this.getPy());
        coordinates[2] = new Position(this.getPx() + 60, this.getPy());
        coordinates[3] = new Position(this.getPx() + 90, this.getPy());
        super.setCoordinates(coordinates);
    }

    @Override
    public void draw(Graphics g) {
    	int[] xAxis = this.getxAxis();
//    		int[] yAxis = this.getyAxis();
		int[] d1 = new int[]{30 * xAxis[0], 30 * xAxis[1]};
		int[] d2 = new int[]{60 * xAxis[0], 60 * xAxis[1]};
		int[] d3 = new int[]{90 * xAxis[0], 90 * xAxis[1]};
		this.coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx() + d1[0], this.getPy() + d1[1]);
        coordinates[2] = new Position(this.getPx() + d2[0], this.getPy() + d2[1]);
        coordinates[3] = new Position(this.getPx() + d3[0], this.getPy() + d3[1]);
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
                    coordinates[i].getX(), coordinates[i].getY(), Color.CYAN
            );
            curr.draw(g);
            g.setColor(Color.GRAY);
            g.drawRect(coordinates[i].getX(), coordinates[i].getY(), 30, 30);
        }
    }
    
	@Override
	public void updateCoords() {
		int[] xAxis = this.getxAxis();
//		int[] yAxis = this.getyAxis();
		int dx1, dx2, dx3, dy1, dy2, dy3;
		int[] d1 = new int[]{30 * xAxis[0], 30 * xAxis[1]};
		int[] d2 = new int[]{60 * xAxis[0], 60 * xAxis[1]};
		int[] d3 = new int[]{90 * xAxis[0], 90 * xAxis[1]};
		dx1 = d1[0];
		dx2 = d2[0];
		dx3 = d3[0];
		dy1 = d1[1];
		dy2 = d2[1];
		dy3 = d3[1];
		this.coordinates = new Position[4];
        coordinates[0] = new Position(this.getPx(), this.getPy());
        coordinates[1] = new Position(this.getPx() + dx1, this.getPy() + dy1);
        coordinates[2] = new Position(this.getPx() + dx2, this.getPy() + dy2);
        coordinates[3] = new Position(this.getPx() + dx3, this.getPy() + dy3);
        super.setCoordinates(coordinates);
	}
}
