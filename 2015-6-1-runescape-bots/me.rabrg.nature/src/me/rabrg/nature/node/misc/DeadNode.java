package me.rabrg.nature.node.misc;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Game;
import org.tbot.methods.Players;
import org.tbot.methods.Random;

import me.rabrg.nature.RabrgNature;
import me.rabrg.nature.node.Node;

public final class DeadNode extends Node {

	@Override
	public boolean validate() {
		return Players.getLocal().isDead() ;
	}

	@Override
	public int execute() {
		try {
			LogHandler.log("Attempting to send message to" + getNumber(Players.getLocal().getName().toLowerCase()));
			RabrgNature.SENDER.send(getNumber(Players.getLocal().getName().toLowerCase()), Players.getLocal().getName().toLowerCase() + "," + Game.getCurrentWorld());
		} catch (final Exception e) {
			LogHandler.log(e);
			LogHandler.log(e.getMessage());
			LogHandler.log("Failed to send message to " + getNumber(Players.getLocal().getName().toLowerCase()));
		}
		return Random.nextInt(3000, 6000);
	}

	@Override
	public String getName() {
		return "Sending text";
	}

	private static String getNumber(final String user) {
		switch (user){
		case "flandscarlet":
		case "flan chann":
		case "remiliascar":
		case "cirnochan":
		case "daiyouseicha":
		case "jajaxdd":
		case "fakumeany":
		case "rosalita0":
		case "green grass1":
		case "memesucker12":
			return "7055213991@msg.koodomobile.com";
		case "earthbear":
		case "sirmisterguy":
		case "tailor123":
		case "Scorchturth":
		case "fallentriang":
			return "yabit@pcs.rogers.com";
		case "cashin4321":
		case "cashim":
		case "dogsrcool416":
		case "dankmemer365":
		case "superbanjos2":
		case "goofigober83":
		case "ilikecashewz":
		case "doggyman78":
		case "butmunchr853":
		case "potatocool25":
			return "5618561647@MyMetroPcs.com";
		}
		return null;
	}
}
