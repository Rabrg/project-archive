package me.matt.combatar;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "Rabrg", category = Category.COMBAT, name = "RCombatar", version = 0.7)
public final class Combatar extends AbstractScript {

	private static final Rectangle DEFENSIVE_RECTANGLE = new Rectangle(660, 312, 40, 25);
	private static final Rectangle STRENGTH_RECTANGLE = new Rectangle(658, 256, 40, 25);
	private static final Rectangle ATTACK_RECTANGLE = new Rectangle(577, 259, 40, 25);

	private static final Area SEAGULL_AREA = new Area(3017, 3191, 3055, 3254);
	private static final Tile SEAGULL_TILE = new Tile(3028, 3236);

	private static final Area FROGS_AREA = new Area(3177, 3159, 3237, 3194);

	private static final Area HILL_GIANTS_AREA = new Area(2430, 3200, 3452, 3425);
	private static final Tile HILL_GIANT_TILE = new Tile(2440, 3210);
	private static final Area CASTLE_WARS_AREA = new Area(2438, 3083, 2444, 3098);
	private static final Tile CASTLE_WARS_TILE = new Tile(2441, 3086);

	private String state;

	private int nextRun = Calculations.random(40, 80);
	private int nextHeal = -1;

	@Override
	public void onStart() {
		nextHeal = Calculations.random(10, getSkills().getRealLevel(Skill.HITPOINTS) - 12);
		if (getSkills().getRealLevel(Skill.DEFENCE) >= 30) {
			getTabs().open(Tab.INVENTORY);
			if (getInventory().contains("Adamant platebody")) {
				getInventory().get("Adamant platebody").interact("Wear");
			}
			if (getInventory().contains("Adamant platelegs")) {
				getInventory().get("Adamant platelegs").interact("Wear");
			}
			if (getInventory().contains("Adamant boots")) {
				getInventory().get("Adamant boots").interact("Wear");
			}
			if (getInventory().contains("Adamant kiteshield")) {
				getInventory().get("Adamant kiteshield").interact("Wear");
			}
		}
		if (getSkills().getRealLevel(Skill.ATTACK) >= 40) {
			getTabs().open(Tab.INVENTORY);
			if (getInventory().contains("Rune scimitar")) {
				getInventory().get("Rune scimitar").interact("Wear");
			}
		}
		if (getSkills().getRealLevel(Skill.DEFENCE) < 30 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(DEFENSIVE_RECTANGLE);
			getMouse().click(DEFENSIVE_RECTANGLE);
			getMouse().click(DEFENSIVE_RECTANGLE);
		} else if (getSkills().getRealLevel(Skill.STRENGTH) < 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(STRENGTH_RECTANGLE);
			getMouse().click(STRENGTH_RECTANGLE);
			getMouse().click(STRENGTH_RECTANGLE);
		} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 40 && getSkills().getRealLevel(Skill.ATTACK) < 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(ATTACK_RECTANGLE);
			getMouse().click(ATTACK_RECTANGLE);
			getMouse().click(ATTACK_RECTANGLE);
		} else if (getSkills().getRealLevel(Skill.STRENGTH) < 60 && getSkills().getRealLevel(Skill.ATTACK) >= 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(STRENGTH_RECTANGLE);
			getMouse().click(STRENGTH_RECTANGLE);
			getMouse().click(STRENGTH_RECTANGLE);
		} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 60 && getSkills().getRealLevel(Skill.ATTACK) < 70 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(ATTACK_RECTANGLE);
			getMouse().click(ATTACK_RECTANGLE);
			getMouse().click(ATTACK_RECTANGLE);
		} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 60 && getSkills().getRealLevel(Skill.ATTACK) >= 70 && getSkills().getRealLevel(Skill.DEFENCE) < 60 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
			getMouse().click(DEFENSIVE_RECTANGLE);
			getMouse().click(DEFENSIVE_RECTANGLE);
			getMouse().click(DEFENSIVE_RECTANGLE);
		}
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		if (getSkills().getRealLevel(Skill.DEFENCE) < 30) {
			state = "Seagull";
		} else if (getSkills().getRealLevel(Skill.STRENGTH) < 50 && getSkills().getRealLevel(Skill.RANGED) < 70) {
			state = "Frogs";
		} else {
			state = "Hill Giant";
		}
		if (((state.equals("Frogs") && FROGS_AREA.contains(getLocalPlayer())) || (state.equals("Hill Giant") && HILL_GIANTS_AREA.contains(getLocalPlayer())))) {
			for (final Player player : getPlayers().all()) {
				if (!player.getName().equals(getLocalPlayer().getName()) && player.distance() < 12) {
					getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
						@Override
						public boolean match(final World world) {
							return world.isMembers() && !world.isPVP();
						}
					}).getID());
					return Calculations.random(600, 1200);
				}
			}
		}
		if (getSkills().getBoostedLevels(Skill.HITPOINTS) <= nextHeal && getInventory().contains("Lobster")) {
			log("1");
			getTabs().open(Tab.INVENTORY);
			if (getInventory().get("Lobster").interact("Eat")) {
				log("11");
				nextHeal = Calculations.random(10, getSkills().getRealLevel(Skill.HITPOINTS) - 12);
			}
		} else if (!getWalking().isRunEnabled() && getWalking().getRunEnergy() >= nextRun) {
			log("4");
			getWalking().toggleRun();
			nextRun = Calculations.random(40, 80);
		} else if (state.equals("Seagull")) {
			log("5");
			if (!SEAGULL_AREA.contains(getLocalPlayer())) {
				getWalking().walk(SEAGULL_TILE);
			} else if (!getLocalPlayer().isInCombat()) {
				final NPC seagull = getAttackable(state);
				if (seagull.distance() > 10) {
					getWalking().walk(seagull);
				} else if (seagull != null && seagull.interact("Attack")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getLocalPlayer().isInCombat();
						}
					}, Calculations.random(2400, 4800));
				}
			}
		} else if (state.equals("Frogs")) {
			log("6");
			if (SEAGULL_AREA.contains(getLocalPlayer()) && !getLocalPlayer().isInCombat()) {
				if (getMagic().castSpell(Normal.HOME_TELEPORT)) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !SEAGULL_AREA.contains(getLocalPlayer());
						}
					}, Calculations.random(12000, 24000));
				}
			} else if (FROGS_AREA.contains(getLocalPlayer())) {
				log("7");
				if (!getLocalPlayer().isInCombat()) {
					final NPC giantFrog = getAttackable("Giant frog");
					if (giantFrog != null) {
						if (giantFrog.distance() > 10) {
							getWalking().walk(giantFrog);
						} else if (giantFrog != null && giantFrog.interact("Attack")) {
							sleepUntil(new Condition() {
								@Override
								public boolean verify() {
									return getLocalPlayer().isInCombat();
								}
							}, Calculations.random(2400, 4800));
						}
					} else {
						final NPC bigFrog = getAttackable("Big frog");
						if (bigFrog != null) {
							if (bigFrog.distance() > 10) {
								getWalking().walk(bigFrog);
							} else if (bigFrog != null && bigFrog.interact("Attack")) {
								sleepUntil(new Condition() {
									@Override
									public boolean verify() {
										return getLocalPlayer().isInCombat();
									}
								}, Calculations.random(2400, 4800));
							}
						}
					}
				}
			} else {
				log("8");
				getWalking().walk(FROGS_AREA.getRandomTile());
			}
		} else if (state.equals("Hill Giant")) {
			log("9");
			if (getInventory().contains("Lobster")) {
				log("10");
				if (!HILL_GIANTS_AREA.contains(getLocalPlayer())) {
					log("11");
					getWalking().walk(HILL_GIANT_TILE);
				} else if (!getLocalPlayer().isInCombat()){
					log("12");
					final NPC giant = getAttackable("Hill Giant");
					if (giant != null) {
						log("120");
						if (giant.distance() > 10) {
							log("121");
							getWalking().walk(giant);
						} else {
							log("122");
							if (giant.interact("Attack")) {
								log("123");
								MethodProvider.sleepUntil(new Condition() {
									@Override
									public boolean verify() {
										return getLocalPlayer().isInCombat();
									}
									
								}, Calculations.random(750, 2500));
								if (Calculations.random(1, 5) >= 4) {
									MethodProvider.sleep(750, 1250);
									getMouse().moveMouseOutsideScreen();
								}
							}
						}
					}
				} else {
					log("" + getLocalPlayer().isInCombat());
					return Calculations.random(600, 1200);
				}
			} else {
				log("13");
				if (CASTLE_WARS_AREA.contains(getLocalPlayer())) {
					log("15");
					if (!getBank().isOpen() && getBank().openClosest()) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return getBank().isOpen();
							}
							
						}, Calculations.random(2400, 4800));
					} else if (getBank().isOpen() && getBank().withdrawAll("Lobster")) {
						sleepUntil(new Condition() {
							@Override
							public boolean verify() {
								return getInventory().contains("Lobster");
							}
							
						}, Calculations.random(2400, 4800));
					}
				} else {
					log("14");
					if (FROGS_AREA.contains(getLocalPlayer())) {
						Item ring = getEquipment().getItemInSlot(EquipmentSlot.RING.getSlot());
						if (ring == null) {
							getTabs().open(Tab.INVENTORY);
							ring = getInventory().get(new Filter<Item>() {

								@Override
								public boolean match(Item arg0) {
									return arg0 != null && arg0.getName() != null && arg0.getName().contains("dueling");
								}
								
							});
						} else {
							getTabs().open(Tab.EQUIPMENT);
						}
						if (ring.interact("Castle wars")) {
							sleepUntil(new Condition() {
								@Override
								public boolean verify() {
									return !FROGS_AREA.contains(getLocalPlayer());
								}
								
							}, Calculations.random(4800, 6000));
						}
					} else {
						getWalking().walk(CASTLE_WARS_TILE);
					}
				}
			}
		}
		return Calculations.random(0, 2400);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("Congratulations")) {
			if (getSkills().getRealLevel(Skill.DEFENCE) < 30) {
				state = "Seagull";
			} else if (getSkills().getRealLevel(Skill.STRENGTH) < 50) {
				state = "Frogs";
			} else {
				state = "Hill Giant";
			}
			if (getSkills().getRealLevel(Skill.DEFENCE) >= 30) {
				getTabs().open(Tab.INVENTORY);
				if (getInventory().contains("Adamant platebody")) {
					getInventory().get("Adamant platebody").interact("Wear");
				}
				if (getInventory().contains("Adamant platelegs")) {
					getInventory().get("Adamant platelegs").interact("Wear");
				}
				if (getInventory().contains("Adamant boots")) {
					getInventory().get("Adamant boots").interact("Wear");
				}
				if (getInventory().contains("Adamant kiteshield")) {
					getInventory().get("Adamant kiteshield").interact("Wear");
				}
			}
			if (getSkills().getRealLevel(Skill.ATTACK) >= 40) {
				if (getInventory().contains("Rune scimitar")) {
					getTabs().open(Tab.INVENTORY);
					getTabs().open(Tab.INVENTORY);
					getTabs().open(Tab.INVENTORY);
					getTabs().open(Tab.INVENTORY);
					getInventory().get("Rune scimitar").interact("Wear");
				}
			}
			if (getSkills().getRealLevel(Skill.DEFENCE) < 30 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(DEFENSIVE_RECTANGLE);
				getMouse().click(DEFENSIVE_RECTANGLE);
				getMouse().click(DEFENSIVE_RECTANGLE);
			} else if (getSkills().getRealLevel(Skill.STRENGTH) < 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(STRENGTH_RECTANGLE);
				getMouse().click(STRENGTH_RECTANGLE);
				getMouse().click(STRENGTH_RECTANGLE);
			} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 40 && getSkills().getRealLevel(Skill.ATTACK) < 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(ATTACK_RECTANGLE);
				getMouse().click(ATTACK_RECTANGLE);
				getMouse().click(ATTACK_RECTANGLE);
			} else if (getSkills().getRealLevel(Skill.STRENGTH) < 60 && getSkills().getRealLevel(Skill.ATTACK) >= 40 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(STRENGTH_RECTANGLE);
				getMouse().click(STRENGTH_RECTANGLE);
				getMouse().click(STRENGTH_RECTANGLE);
			} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 60 && getSkills().getRealLevel(Skill.ATTACK) < 70 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(ATTACK_RECTANGLE);
				getMouse().click(ATTACK_RECTANGLE);
				getMouse().click(ATTACK_RECTANGLE);
			} else if (getSkills().getRealLevel(Skill.STRENGTH) >= 60 && getSkills().getRealLevel(Skill.ATTACK) >= 70 && getSkills().getRealLevel(Skill.DEFENCE) < 60 && (getCombat().openTab() || getCombat().openTab() || getCombat().openTab())) {
				getMouse().click(DEFENSIVE_RECTANGLE);
				getMouse().click(DEFENSIVE_RECTANGLE);
				getMouse().click(DEFENSIVE_RECTANGLE);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Attack (" + getSkillTracker().getStartLevel(Skill.ATTACK) + "+" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ") xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 60);
		g.drawString("Strength (" + getSkillTracker().getStartLevel(Skill.STRENGTH) + "+" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ") xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence (" + getSkillTracker().getStartLevel(Skill.DEFENCE) + "+" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ") xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 90);
		g.drawString("Hitpoints (" + getSkillTracker().getStartLevel(Skill.HITPOINTS) + "+" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ") xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 105);
	}

	private NPC getAttackable(final String name) {
		return getNpcs().closest(new Filter<NPC>(){
			@Override
			public boolean match(final NPC n) {
				if(n == null || n.getActions() == null || n.getActions().length <= 0)
					return false;
				if(n.getName() == null || !n.getName().startsWith(name))
					return false;
				if(n.isInCombat()){
					final Character c = n.getInteractingCharacter();
					if(c == null)
						return false;
					if(c.getName() == null)
						return false;
					if(c.getName().equals(getLocalPlayer().getName()))
						return true;
					return false;
				}
				return true;
			}
		});
	}
}
