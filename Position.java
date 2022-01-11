package org.cis120.tetris;

public class Position {
    private int x;
    private int y;

    public Position(int xCord, int yCord) {
        x = xCord;
        y = yCord;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x:" + getX() + ", y: " + this.getY();
    }

}
