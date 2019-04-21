package me.rabrg.blackjack;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.methods.Npcs;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.util.Condition;
import org.tbot.wrappers.NPC;

@Manifest(authors = "Rabrg", name = "Rabrg Blackjack", category = ScriptCategory.THIEVING)
public final class RabrgCannon extends AbstractScript {

	private NPC target;

	@Override
	public int loop() {
		if ((target = Npcs.getNearest("Bandit")) != null) {
			if (target.getAnimation() != 828 && target.getOverheadText() == null && target.interact("Knock-Out")) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return target.getAnimation() == 828 || target.getOverheadText() != null;
					}
				});
				if (target.getAnimation() == 828 || target.getOverheadText().equals("Zzzzzz")) {
					if (target.interact("Pickpocket")) {
						sleep(400, 700);
						target.interact("Pickpocket");
					}
				} else {
					if (target.interact("Pickpocket")) {
						sleep(2500, 5000);
					}
				}
			}
		}
		return Random.nextInt(25, 175);
	}

}
