package me.rabrg.nature.node.mage;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Game;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Player;
import org.tbot.wrappers.Tile;

import me.rabrg.nature.RabrgNature;
import me.rabrg.nature.node.Node;

public final class WalkMageNode extends Node {

	private static final Tile MAGE_OF_ZAMORAK_TILE = new Tile(3104, 3557);

	private Path path;
	private NPC mageOfZamorak;

	@Override
	public boolean validate() {
		return Inventory.isFull() && EDGEVILLE_WILDERNESS_AREA.contains(Players.getLocal()) && ((mageOfZamorak = Npcs.getNearest("Mage of Zamorak")) == null || mageOfZamorak.distance() > 8) && (path = Walking.findLocalPath(mageOfZamorak == null ? MAGE_OF_ZAMORAK_TILE : mageOfZamorak)) != null;
	}

	@Override
	public int execute() {
                int localCMBLevel = Players.getLocal().getCombatLevel();
                final int min = localCMBLevel - 5;
                final int max = localCMBLevel + 5;
                int dangerousPlayers = Players.getLoaded(new Filter<Player>() {
                    @Override
                    public boolean accept(Player player) {
                        int combatLevel = player.getCombatLevel();
                        switch (player.getName()) {
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
                		case "earthbear":
                		case "sirmisterguy":
                		case "tailor123":
                		case "Scorchturth":
                		case "fallentriang":
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
                			return false;
                        }
                        return player.getLocation().getY() >= 3523 && !player.getName().equals(Players.getLocal().getName()) && combatLevel >= min && combatLevel <= max;
                    }
                }).length;
                if(dangerousPlayers > 0) {
                    LogHandler.log("Attackable player detected!");
                    Game.instaHopRandomP2P();
                    RabrgNature.hoppedWorlds++;
                }
		path.traverse();
		return Random.nextInt(650, 1250);
	}

	@Override
	public String getName() {
		return "Walking to mage";
	}

}
