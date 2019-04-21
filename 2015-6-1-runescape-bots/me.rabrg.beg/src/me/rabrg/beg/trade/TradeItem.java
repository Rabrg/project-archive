package me.rabrg.beg.trade;
import java.awt.Point;
import java.awt.Rectangle;

import org.tbot.methods.Random;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.WidgetChild;

public class TradeItem extends Item {
	private WidgetChild wc;

	public TradeItem(int arg0, int arg1, int arg2, WidgetChild wc) {
		super(arg0, arg1, arg2, wc);
		this.wc = wc;
	}

	@Override
	public Rectangle getBounds() {
		return wc.getBounds();
	}

	@Override
	public Point getRandomPoint() {
		return new Point(getBounds().x + Random.nextInt(0, 36), getBounds().y
				+ Random.nextInt(0, 32));
	}

	@Override
	public String toString() {
		return "TradeItem = [Name: " + this.getName() + " , ID: "
				+ this.getID() + " , Size: " + this.getStackSize() + "]";
	}

}