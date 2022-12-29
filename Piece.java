package org.cis120.tetris;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.TreeMap;

public abstract class Piece {
    private int px;
    private int py;

    /* Velocity: number of pixels to move every time move() is called. */
    private int vy;
    private int vx;
    private int pace;

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */
    private int maxX;
    private int maxY;

    private int blockId;
    private boolean moving;
    private int set;

    private boolean rotated;

    private Position[] coordinates;
    
    private int[] xAxis;
    private int[] yAxis;
    protected int courtWidth;
    protected int courtHeight;

    /**
     * Constructor
     */
    public Piece(
            int vy, int px, int py, int maxX, int maxY, int courtWidth, int courtHeight, int blockId
    ) {
        this.pace = 0;
        this.vy = vy;
        this.vx = 0;
        this.px = px;
        this.py = py;

        this.maxX = maxX;
        this.maxY = maxY;

        this.moving = true;

        this.blockId = blockId;

        this.rotated = false;
        
        this.xAxis = new int[]{1, 0};
        this.yAxis = new int[]{0, 1};
        this.courtWidth = courtWidth;
        this.courtHeight = courtHeight;
    }
    
//    public Piece(Piece other) {
//    	this.pace = other.pace;
//        this.vy = other.vy;
//        this.vx = other.vx;
//        this.px = other.px;
//        this.py = other.py;
//
//        this.maxX = other.maxX;
//        this.maxY = other.maxY;
//
//        this.moving = other.moving;
//
//        this.blockId = other.blockId;
//
//        this.rotated = other.rotated;
//        
//        this.xAxis = other.xAxis;
//        this.yAxis = other.yAxis;
//        this.courtWidth = other.courtWidth;
//        this.courtHeight = other.courtHeight;
//    }

    /***
     * GETTERS
     **********************************************************************************/
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }

    public int getPace() {
        return this.pace;
    }

    public int getVy() {
        return this.vy;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public boolean isRotated() {
        return this.rotated;
    }

    public int getId() {
        return this.blockId;
    }

    public Position[] getCoordinates() {
        return this.coordinates;
    }

    public int getWidth() {
        int xMax = px;
        int xMin = px;
        for (int i = 1; i < coordinates.length; i++) {
            int x = coordinates[i].getX();
            if (x > xMax) {
                xMax = x;
            }
            if (x < xMin) {
                xMin = x;
            }
        }
        return xMax - xMin;
    }

    public int getHeight() {
        int yMax = py;
        int yMin = py;
        for (int i = 1; i < coordinates.length; i++) {
            int y = coordinates[i].getY();
            if (y > yMax) {
                yMax = y;
            }
            if (y < yMin) {
                yMin = y;
            }
        }
        return yMax - yMin;
    }

    public Position[] getBottomPositions(Position[] coords) {
        TreeMap<Integer, Integer> chosen = new TreeMap<Integer, Integer>();
        int x = coords[0].getX();
        chosen.put(x, 0);

        for (int i = 1; i < coords.length; i++) {
            int cordX = coords[i].getX();
            int cordY = coords[i].getY();
            if (chosen.containsKey(cordX) && cordY > coords[chosen.get(cordX)].getY()) {
                chosen.put(cordX, i);
            }
            if (!chosen.containsKey(cordX)) {
                chosen.put(cordX, i);
            }
        }
        Position[] ret = new Position[chosen.size()];
        int i = 0;
        for (int cord : chosen.values()) {
            ret[i] = coords[cord];
            i++;
        }
        return ret;
    }

    public int getYDiffFromIntial(Position other) {
        int y = other.getY();
        return y - this.py;
    }
    
    public int getXDiffFromIntial(Position other) {
        int x = other.getX();
        return x - this.px;
    }

    /**************************************************************************
     * SETTERS
     **************************************************************************/
    public void setPx(int px) {
        this.px = px;
        clip();
    }

    public void setPy(int py) {
        this.py = py;
        clip();
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setMoving(boolean status) {
        this.moving = status;
    }

    public void setCoordinates(Position[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setRotated(boolean status) {
        this.rotated = status;
    }

    /**************************************************************************
     * UPDATES AND OTHER METHODS
     **************************************************************************/

    /**
     * Prevents the object from going outside of the bounds of the area
     * designated for the object (i.e. Object cannot go outside of the active
     * area the user defines for it).
     */
    private void clip() {
        this.px = Math.min(Math.max(this.px, 0), this.maxX);
        this.py = Math.min(Math.max(this.py, 0), this.maxY);
    }

    /**
     * Moves the object by its velocity. Ensures that the object does not go
     * outside its bounds by clipping.
     */
    public void move() {
        if (pace >= 25 && !rotated) {
            this.py += this.vy;
            clip();
            pace = 0;
        }
        if (pace >= 25 && rotated) {
            this.px += this.vx;
            clip();
            pace = 0;
        }
        pace++;
    }

    public void setting() {
        if (set >= 120) {
            moving = false;
        }
        set++;
    }

    public boolean notLeftRightBlocked(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if ((board[coordinates[i].getY() / 30][coordinates[i].getX() / 30 + 1] != 0) ||
                        (board[coordinates[i].getY() / 30][coordinates[i].getX() / 30 - 1] != 0)) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return ret;
    }

    public boolean notLeftBlocked(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if ((board[coordinates[i].getY() / 30][coordinates[i].getX() / 30 - 1] != 0)) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return ret;
    }

    public boolean notRightBlocked(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if (board[coordinates[i].getY() / 30][coordinates[i].getX() / 30 + 1] != 0) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return ret;
    }

    public boolean notBottomBlocked(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if (board[coordinates[i].getY() / 30 + 1][coordinates[i].getX() / 30] != 0) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ret = false;
        }
        return ret;
    }

    public boolean notTopBlocked(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if (board[coordinates[i].getY() / 30 - 1][coordinates[i].getX() / 30] != 0) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            ret = true;
        }
        return ret;
    }

    public boolean notOverlapping(int[][] board) {
        boolean ret = true;
        try {
            for (int i = 0; i < coordinates.length; i++) {
                if (board[coordinates[i].getY() / 30][coordinates[i].getX() / 30] != 0) {
                    ret = false;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return ret;
    }

    public void drop(int[][] board) {
        int row = board.length;
        int coordNum = 0;
        Position[] bottoms = this.getBottomPositions(coordinates);
        for (int i = 0; i < bottoms.length; i++) {
            for (int j = bottoms[i].getY() / 30; j < board.length; j++) {
                if (board[j][bottoms[i].getX() / 30] != 0 && j < row) {
                    coordNum = i;
                    row = j;
                }
                if (board[j][bottoms[i].getX() / 30] != 0 && j == row
                        && this.getYDiffFromIntial(bottoms[i]) > this
                                .getYDiffFromIntial(bottoms[coordNum])) {
                    coordNum = i;
                }
            }
        }

        this.setPy(((row - 1) * 30) - this.getYDiffFromIntial(bottoms[coordNum]));
        this.set += 500;
    }

    public void rotate(int[][] board) {
        int[] x = this.getxAxis();
        int[] y = this.getyAxis();
        if (Arrays.equals(x, new int[]{1, 0}) && Arrays.equals(y, new int[]{0, 1})) {
        	this.setxAxis(new int[]{0, 1});
        	this.setyAxis(new int[]{-1, 0});
        } else if (Arrays.equals(x, new int[]{0, 1}) && Arrays.equals(y, new int[]{-1, 0})) {
        	this.setxAxis(new int[]{-1, 0});
        	this.setyAxis(new int[]{0, -1});
        } else if (Arrays.equals(x, new int[]{-1, 0}) && Arrays.equals(y, new int[]{0, -1})) {
        	this.setxAxis(new int[]{0, -1});
        	this.setyAxis(new int[]{1, 0});
        } else if (Arrays.equals(x, new int[]{0, -1}) && Arrays.equals(y, new int[]{1, 0})) {
        	this.setxAxis(new int[]{1, 0});
        	this.setyAxis(new int[]{0, 1});
        }
    }

    public abstract void draw(Graphics g);
    
    public abstract void updateCoords();

	public int[] getxAxis() {
		return xAxis;
	}

	public void setxAxis(int[] xAxis) {
		this.xAxis = xAxis;
	}

	public int[] getyAxis() {
		return yAxis;
	}

	public void setyAxis(int[] yAxis) {
		this.yAxis = yAxis;
	}
	
	public void tryAdjust(int[][] board) {
		int overlap = 0;
		for (int i = 0; i < coordinates.length; i++) {
			int x = coordinates[i].getX();
			int y = coordinates[i].getY();
			if (board[y / 30][x / 30] != 0 ) {
				overlap++;
			}
		}
		if (overlap == 0) {
			return;
		}
		int leftX = 0;
		int rightX = (board[0].length - 1) * 30;
		int topY = 0;
		int bottomY = (board.length - 1) * 30;
		for (int i = 0; i < coordinates.length; i++) {
			int x = coordinates[i].getX();
			int y = coordinates[i].getY();
			leftX = Math.min(leftX, x);
			rightX = Math.max(rightX, x);
			topY = Math.min(topY, y);
			bottomY = Math.max(bottomY, y);
		}
		if (leftX < 0 || rightX > (board[0].length - 1) * 30 || topY < 0 || bottomY > (board.length - 1) * 30) {
			if (leftX < 0) {
				for (int i = 0; i < this.coordinates.length; i++) {
		        	coordinates[i].setX(coordinates[i].getX() - leftX);
		        }
			}
			if (rightX > (board[0].length - 1) * 30) {
				for (int i = 0; i < this.coordinates.length; i++) {
					int dX = rightX - (board[0].length - 1) * 30;
		        	coordinates[i].setX(coordinates[i].getX() - dX);
		        }
			}
			if (topY < 0) {
				for (int i = 0; i < this.coordinates.length; i++) {
		        	coordinates[i].setY(coordinates[i].getY() - topY);
		        }
			}
			if (bottomY < (board.length - 1) * 30) {
				for (int i = 0; i < this.coordinates.length; i++) {
					int dY = bottomY - (board.length - 1) * 30;
		        	coordinates[i].setY(coordinates[i].getY() - dY);
		        }
			}
		} else {
			int dRow = 0;
	        Position[] bottoms = this.getBottomPositions(coordinates);
	        for (int i = 0; i < bottoms.length; i++) {
	        	int currY = bottoms[i].getY();
	            for (int j = currY / 30; j > -1; j--) {
	                if (board[j][bottoms[i].getX() / 30] == 0 && currY / 30 - j >= dRow) {
	                    dRow = currY / 30 - j;
	                    break;
	                }
	            }
	        }
	        for (int i = 0; i < this.coordinates.length; i++) {
	        	coordinates[i].setY(coordinates[i].getY() - dRow * 30);
	        }
		}
		
	}
	
	public void adjust() {
		int leftX = 0;
		int rightX = 270;
		int topY = 0;
		int bottomY = 570;
		for (int i = 0; i < coordinates.length; i++) {
			int x = coordinates[i].getX();
			int y = coordinates[i].getY();
			leftX = Math.min(leftX, x);
			rightX = Math.max(rightX, x);
			topY = Math.min(topY, y);
			bottomY = Math.max(bottomY, y);
		}
		if (leftX < 0) {
			for (int i = 0; i < this.coordinates.length; i++) {
	        	coordinates[i].setX(coordinates[i].getX() - leftX);
	        }
		}
		if (rightX > 270) {
			for (int i = 0; i < this.coordinates.length; i++) {
				int dX = rightX - 270;
	        	coordinates[i].setX(coordinates[i].getX() - dX);
	        }
		}
		if (topY < 0) {
			for (int i = 0; i < this.coordinates.length; i++) {
	        	coordinates[i].setY(coordinates[i].getY() - topY);
	        }
		}
		if (bottomY > 570) {
			for (int i = 0; i < this.coordinates.length; i++) {
				int dY = bottomY - 570;
	        	coordinates[i].setY(coordinates[i].getY() - dY);
	        }
		}
	}

}
