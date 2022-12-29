package org.cis120.tetris;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class PieceSequence{
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
        this.generate();
    }

    public Piece curr() {
        return pieces.removeFirst();
    }

    public void generate() {
        double value = r.nextDouble() * 100;
        Piece newPiece = probabilities.get((int) value);
        pieces.add(newPiece);
        int id = newPiece.getId();
        if (id == 1) {
        	probabilities.put((int) value, new SquareBlock(300, 600));
        } else if (id == 2) {
        	probabilities.put((int) value, new LineBlock(300, 600));
        } else if (id == 3) {
        	probabilities.put((int) value, new SBlock(300, 600));
        } else if (id == 4) {
        	probabilities.put((int) value, new ZBlock(300, 600));
        } else if (id == 5) {
        	probabilities.put((int) value, new TriBlock(300, 600));
        } else if (id == 6) {
        	probabilities.put((int) value, new LBlock(300, 600));
        } else if (id == 7) {
        	probabilities.put((int) value, new ReverseLBlock(300, 600));
        }
    }

    public int getSize() {
        return pieces.size();
    }

    public Piece getPiece(int ordernum) {
        return pieces.get(ordernum);
    }
    
    public LinkedList<Piece> getPieces() {
    	return pieces;
    }

    public void draw(Graphics g) {

    	for (int i = 0; i < pieces.size(); i++) {
        	Piece p = pieces.get(i);
        	int id = p.getId();
        	if (id == 1) {
        		p.setPx(45);
        		p.setPy(15);
        	} else if (id == 2) {
        		p.setPx(15);
        		p.setPy(30);
        	} else if (id == 3) {
        		p.setPx(30);
        		p.setPy(45);
        	} else if (id == 4) {
        		p.setPx(30);
        		p.setPy(15);
        	} else if (id == 5) {
        		p.setPx(60);
        		p.setPy(15);
        	} else if (id == 6) {
        		p.setPx(30);
        		p.setPy(45);
        	} else if (id == 7) {
        		p.setPx(30);
        		p.setPy(15);
        	}
        	if (i == 1) {
        		p.setPy(p.getPy() + 15 + 60);
        	}
        	if (i == 2) {
        		p.setPy(p.getPy() + 15 + 60 + 15 + 60);
        	}
        	p.draw(g);
        }
    }

}
