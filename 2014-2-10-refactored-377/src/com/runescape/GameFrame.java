package com.runescape;

import java.awt.Frame;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class GameFrame extends Frame {
	
	public GameShell gameShell;
	
	public GameFrame(int width, int height, GameShell gameShell) {
		this.gameShell = gameShell;
		setTitle("Jagex");
		setResizable(false);
		setVisible(true);
		toFront();
		setSize(width + 8, height + 28);
	}

	@Override
	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
	}

	@Override
	public void update(Graphics g) {
		gameShell.update(g);
	}

	@Override
	public void paint(Graphics g) {
		gameShell.paint(g);
	}
}
