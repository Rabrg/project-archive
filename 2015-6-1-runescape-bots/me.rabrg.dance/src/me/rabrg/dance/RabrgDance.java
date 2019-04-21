package me.rabrg.dance;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

@ScriptManifest(author = "Rabrg", category = Category.MONEYMAKING, name = "Rabrg Dance", version = 0.2)
public final class RabrgDance extends AbstractScript {

	private static final String[] DANCING_QUOTES = { "Dancing for money!", "Dancing for money!1!11", "Plz give me money!", "Plz money me PLS!", "Give me money!11", "Plss", "Im poor give money" };
	private static final String[] THANKING_QUOTES = { "Wow really???!", "Thanks a butt load!", "Thanks man!", "Wowzas thanks!", "Holy mother of cow, thanks!", "Splendid!", "You are amazing!" };

	private int profit = 0;
	private int dances = 0;

	@Override
	public int onLoop() {
		if (!getTrade().isOpen() && !getKeyboard().isTyping()) {
			getKeyboard().type(DANCING_QUOTES[Calculations.random(DANCING_QUOTES.length)], true, true);
			if (!getEmotes().isTabOpen())
				getEmotes().openTab();
			getEmotes().doRandomEmote();
			dances++;
		} else if (getTrade().isOpen()) {
			boolean valid = false;
			for (final Item item : getTrade().getTheirItems())
				if (item != null && item.getName() != null) {
					if (getTrade().isOpen(1)) {
						if (item.getID() != 995)
							profit += RSBuddyGrandExchange.getPrice(item.getID());
						else
							profit += item.getAmount();
					}
					valid = true;
					break;
				}
			if (valid)
				getTrade().acceptTrade();
		}
		return Calculations.random(1200, 7200);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.TRADE && !getTrade().isOpen())
			getTrade().tradeWithPlayer(message.getMessage().split(" wishes")[0]);
		else if (message.getMessage().contains("Accepted trade"))
			getKeyboard().type(THANKING_QUOTES[Calculations.random(THANKING_QUOTES.length)], true, true);
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Dances: " + dances , 5, 45);
		g.drawString("Profit: " + profit , 5, 60);
		g.drawString("Profit per dance: " + (profit / dances) , 5, 75);
	}
}

