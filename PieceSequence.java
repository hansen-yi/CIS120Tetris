package org.cis120.tetris;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class PieceSequence {
    private LinkedList<Piece> pieces;
    private Map<Integer, Piece> probabilities;
    private Random r;

    public PieceSequence() {
        super();
        r = new Random();
        pieces = new LinkedList<Piece>();
        probabilities = new TreeMap<Integer, Piece>();
        for (int i = 0; i < 14; i++) {
            probabilities.put(i, new SquareBlock(300, 600));
        }
        for (int i = 14; i < 28; i++) {
            probabilities.put(i, new LineBlock(300, 600));
        }
        for (int i = 28; i < 43; i++) {
            probabilities.put(i, new SBlock(300, 600));
        }
        for (int i = 43; i < 58; i++) {
            probabilities.put(i, new ZBlock(300, 600));
        }
        for (int i = 58; i < 72; i++) {
            probabilities.put(i, new TriBlock(300, 600));
        }
        for (int i = 72; i < 86; i++) {
            probabilities.put(i, new LBlock(300, 600));
        }
        for (int i = 86; i < 100; i++) {
            probabilities.put(i, new ReverseLBlock(300, 600));
        }
        this.generate();
        this.generate();
        this.generate();
    }

    public Piece curr() {
        return pieces.removeFirst();
    }

    public void generate() {
        double value = r.nextDouble() * 100;
        pieces.add(probabilities.get((int) value));
    }

    public int getSize() {
        return pieces.size();
    }

    public Piece getPiece(int ordernum) {
        return pieces.get(ordernum);
    }

    public void draw(Graphics g) {

        if (pieces.get(0).getId() == 2) {
            pieces.get(0).setPx(5);
        }
        pieces.get(1).setPx(20);
        pieces.get(0).setPy(10);
        pieces.get(0).draw(g);

        if (pieces.get(1).getId() == 2) {
            pieces.get(1).setPx(5);
        }
        pieces.get(1).setPx(20);
        pieces.get(1).setPy(80);
        pieces.get(1).draw(g);

        if (pieces.get(2).getId() == 2) {
            pieces.get(2).setPx(5);
        }
        pieces.get(2).setPx(20);
        pieces.get(2).setPy(150);
        pieces.get(2).draw(g);
    }

}
