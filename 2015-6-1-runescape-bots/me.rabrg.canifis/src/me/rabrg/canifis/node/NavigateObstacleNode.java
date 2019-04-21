package me.rabrg.canifis.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.wrappers.interactive.GameObject;

public final class NavigateObstacleNode extends Node {

	private static final Tile TREE_TILE = new Tile(3506, 3486);

	public static final Area PART_ZERO_AREA = new Area(3470, 3467, 3528, 3513, 0);
	public static final Area PART_ONE_AREA = new Area(3503, 3489, 3511, 3500, 2);
	public static final Area PART_TWO_AREA = new Area(3494, 3502, 3505, 3508, 2);
	public static final Area PART_THREE_AREA = new Area(3483, 3497, 3494, 3506, 2);
	public static final Area PART_FOUR_AREA = new Area(3473, 3489, 3481, 3501, 3);
	public static final Area PART_FIVE_AREA = new Area(3475, 3479, 3486, 3489, 2);
	public static final Area PART_SIX_AREA = new Area(3487, 3467, 3506, 3480, 3);
	public static final Area PART_SEVEN_AREA = new Area(3507, 3473, 3513, 3485, 2);

	public NavigateObstacleNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public int execute() {
		if (PART_ZERO_AREA.contains(ctx.getLocalPlayer())) {
			final GameObject tree = ctx.getGameObjects().closest("Tall tree");
			if (tree == null || tree.distance() > 10) {
				ctx.getWalking().walk(tree == null ? TREE_TILE : tree.getTile().getRandomizedTile(2));
				return Calculations.random(1250, 1900);
			} else if (!tree.isOnScreen()) {
				ctx.getCamera().rotateToEntity(tree);
			} else if (navigateInsideArea("Tall tree", "Climb", PART_ZERO_AREA)){
				sleepUntilInsideArea(PART_ONE_AREA);
			}
		} else if (PART_ONE_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_ONE_AREA)) {
			sleepUntilInsideArea(PART_TWO_AREA);
		} else if (PART_TWO_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_TWO_AREA)) {
			sleepUntilInsideArea(PART_THREE_AREA);
		} else if (PART_THREE_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_THREE_AREA)) {
			sleepUntilInsideArea(PART_FOUR_AREA);
		} else if (PART_FOUR_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_FOUR_AREA)) {
			sleepUntilInsideArea(PART_FIVE_AREA);
		} else if (PART_FIVE_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Pole-vault", "Vault", PART_FIVE_AREA)) {
			sleepUntilInsideArea(PART_SIX_AREA);
		} else if (PART_SIX_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_SIX_AREA)) {
			sleepUntilInsideArea(PART_SEVEN_AREA);
		} else if (PART_SEVEN_AREA.contains(ctx.getLocalPlayer()) && navigateInsideArea("Gap", "Jump", PART_SEVEN_AREA)) {
			sleepUntilInsideArea(PART_ZERO_AREA);
		}
		return Calculations.random(450, 900);
	}

}
