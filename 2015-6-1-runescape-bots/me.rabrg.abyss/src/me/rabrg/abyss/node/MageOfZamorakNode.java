package me.rabrg.abyss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;

import me.rabrg.abyss.RabrgAbyssPro;

public final class MageOfZamorakNode extends Node {

	private static final Area MAGE_OF_ZAMORAK_AREA = new Area(3098, 3553, 3109, 3565);
	private static final Area AIR_OBELISK_AREA = new Area(3083, 3558, 3092, 3577);

	private final Filter<Player> DANGEROUS_PLAYER_FILTER = new Filter<Player>() {
		@Override
		public boolean match(final Player player) {
			return !player.getName().equals(script.getLocalPlayer().getName()) && WILDERNESS_AREA.contains(player) && !AIR_OBELISK_AREA.contains(script.getLocalPlayer()) && player.getLevel() >= script.getLocalPlayer().getLevel() - 5 && player.getLevel() <= script.getLocalPlayer().getLevel() + 5;
		}
	};

	private NPC mageOfZamorak;

	public MageOfZamorakNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return WILDERNESS_AREA.contains(script.getLocalPlayer());
	}

	@Override
	public void execute() {
		if (script.getPlayers().all(DANGEROUS_PLAYER_FILTER).size() > 0  && (script.getTabs().isOpen(Tab.EQUIPMENT) || script.getTabs().open(Tab.EQUIPMENT)) && script.getEquipment().get(EquipmentSlot.AMULET.getSlot()).interact("Edgeville")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					return WILDERNESS_AREA.contains(script.getLocalPlayer());
				}
			}, Calculations.random(4200, 4800));
			script.getWorldHopper().hopWorld(script.getWorlds().getRandomWorld(new Filter<World>() {
				@Override
				public boolean match(final World world) {
					return world.isMembers() && !world.isPVP() && world.getID() != 353 && world.getID() != 361 && world.getID() != 366 && world.getID() != 373;
				}
			}));
		} else if ((mageOfZamorak = script.getNpcs().closest("Mage of Zamorak")) == null && script.getWalking().walk(MAGE_OF_ZAMORAK_AREA.getRandomTile())) {
			sleep(600, 1800);
		} else if (mageOfZamorak != null && mageOfZamorak.distance() > 8 && script.getWalking().walk(mageOfZamorak)) {
			sleep(600, 1800);
		} else if (mageOfZamorak != null && mageOfZamorak.interact("Teleport")) {
			sleepUntil(new Condition() {
				@Override
				public boolean verify() {
					sleep(5, 25);
					return !WILDERNESS_AREA.contains(script.getLocalPlayer()) || script.getPlayers().all(DANGEROUS_PLAYER_FILTER).size() > 0;
				}
			}, Calculations.random(2400, 4800));
		}
	}

	@Override
	public String toString() {
		return "Mage of Zamorak";
	}

	
}
