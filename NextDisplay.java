package org.cis120.tetris;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NextDisplay extends JPanel{
	private LinkedList<Piece> pieces;
	
	public NextDisplay() {
		pieces = new LinkedList<Piece>();
	}
	
	public NextDisplay(PieceSequence ps) {
		pieces = new LinkedList<Piece>();
		for (Piece p : ps.getPieces()) {
			int id = p.getId();
			if (id == 1) {
	        	pieces.add(new SquareBlock(150, 240));
	        } else if (id == 2) {
	        	pieces.add(new LineBlock(150, 240));
	        } else if (id == 3) {
	        	pieces.add(new SBlock(150, 240));
	        } else if (id == 4) {
	        	pieces.add(new ZBlock(150, 240));
	        } else if (id == 5) {
	        	pieces.add(new TriBlock(150, 240));
	        } else if (id == 6) {
	        	pieces.add(new LBlock(150, 240));
	        } else if (id == 7) {
	        	pieces.add(new ReverseLBlock(150, 240));
	        }
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 240);
    }
    
    public void update(PieceSequence updated) {
    	if (this.pieces.size() == 3 || this.pieces.size() == 0) {
    		pieces = new NextDisplay(updated).pieces;
    	}
    	else {
    		pieces.pop();
    		int id = updated.getPiece(2).getId();
    		if (id == 1) {
	        	pieces.add(new SquareBlock(150, 240));
	        } else if (id == 2) {
	        	pieces.add(new LineBlock(150, 240));
	        } else if (id == 3) {
	        	pieces.add(new SBlock(150, 240));
	        } else if (id == 4) {
	        	pieces.add(new ZBlock(150, 240));
	        } else if (id == 5) {
	        	pieces.add(new TriBlock(150, 240));
	        } else if (id == 6) {
	        	pieces.add(new LBlock(150, 240));
	        } else if (id == 7) {
	        	pieces.add(new ReverseLBlock(150, 240));
	        }
    	}
    }

}
