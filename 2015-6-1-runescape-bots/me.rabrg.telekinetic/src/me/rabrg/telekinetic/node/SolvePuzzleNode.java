package me.rabrg.telekinetic.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;

public final class SolvePuzzleNode extends Node {

	private static final PuzzelPart[] PUZZEL_PARTS = { new PuzzelPart(new Tile(9751, 6420), new Tile(9755, 6426)), new PuzzelPart(new Tile(9751, 6423), new Tile(9756, 6425)), new PuzzelPart(new Tile(9752, 6423), new Tile(9755, 6415)), new PuzzelPart(new Tile(9752, 6422), new Tile(9756, 6416)), new PuzzelPart(new Tile(9755, 6422), new Tile(9755, 6415)), new PuzzelPart(new Tile(9755, 6418), new Tile(9745, 6416)), new PuzzelPart(new Tile(9753, 6418), new Tile(9746, 6426)), new PuzzelPart(new Tile(9753, 6424), new Tile(9745, 6425)), new PuzzelPart(new Tile(9755, 6418), new Tile(9745, 6416)), new PuzzelPart(new Tile(9747, 6424), new Tile(9746, 6415)), new PuzzelPart(new Tile(9747, 6416), new Tile(9756, 6416)), null,
			new PuzzelPart(new Tile(10703, 6985), new Tile(10698, 6995)), new PuzzelPart(new Tile(10703, 6988), new Tile(10697, 6994)), new PuzzelPart(new Tile(10698, 6995), new Tile(10698, 6995)), new PuzzelPart(new Tile(10699, 6990), new Tile(10708, 6994)), new PuzzelPart(new Tile(10702, 6990), new Tile(10698, 6995)), new PuzzelPart(new Tile(10702, 6992), new Tile(10697, 6994)), new PuzzelPart(new Tile(10700, 6992), new Tile(10698, 6984)), new PuzzelPart(new Tile(10700, 6991), new Tile(10697, 6985)), new PuzzelPart(new Tile(10698, 6991), new Tile(10698, 6995)), new PuzzelPart(new Tile(10698, 6994), new Tile(10708, 6994)), null,
			new PuzzelPart(new Tile(8776, 7382), new Tile(8776, 7372)), new PuzzelPart(new Tile(8776, 7373), new Tile(8786, 7373)), new PuzzelPart(new Tile(8780, 7373), new Tile(8785, 7383)), new PuzzelPart(new Tile(8780, 7379), new Tile(8775, 7382)), new PuzzelPart(new Tile(8778, 7379), new Tile(8776, 7372)), new PuzzelPart(new Tile(8778, 7376), new Tile(8786, 7373)), new PuzzelPart(new Tile(8785, 7376), new Tile(8785, 7383)), null,
			new PuzzelPart(new Tile(8774, 7561), new Tile(8774, 7571)), new PuzzelPart(new Tile(8774, 7570), new Tile(8784, 7570)), new PuzzelPart(new Tile(8777, 7570), new Tile(8783, 7560)), new PuzzelPart(new Tile(8777, 7561), new Tile(8784, 7561)), new PuzzelPart(new Tile(8780, 7561), new Tile(8783, 7571)), new PuzzelPart(new Tile(8780, 7570), new Tile(8783, 7560)), null,
			new PuzzelPart(new Tile(10324, 4298), new Tile(10324, 4308)), new PuzzelPart(new Tile(10324, 4307), new Tile(10314, 4307)), new PuzzelPart(new Tile(10321, 4307), new Tile(10315, 4297)), new PuzzelPart(new Tile(10321, 4299), new Tile(10314, 4298)), new PuzzelPart(new Tile(10318, 4299), new Tile(10315, 4308)), new PuzzelPart(new Tile(10318, 4307), new Tile(10314, 4307)), new PuzzelPart(new Tile(10317, 4307), new Tile(10315, 4297)), new PuzzelPart(new Tile(10317, 4298), new Tile(10314, 4298)), new PuzzelPart(new Tile(10315, 4298), new Tile(10315, 4308)), null,
			new PuzzelPart(new Tile(6861, 4878), new Tile(6866, 4883)), new PuzzelPart(new Tile(6861, 4881), new Tile(6867, 4882)), new PuzzelPart(new Tile(6862, 4881), new Tile(6866, 4872)), new PuzzelPart(new Tile(6862, 4878), new Tile(6867, 4873)), new PuzzelPart(new Tile(6865, 4878), new Tile(6866, 4872)), new PuzzelPart(new Tile(6865, 4876), new Tile(6856, 4873)), new PuzzelPart(new Tile(6862, 4876), new Tile(6857, 4883)), new PuzzelPart(new Tile(6862, 4877), new Tile(6867, 4882)), new PuzzelPart(new Tile(6866, 4877), new Tile(6857, 4883)), new PuzzelPart(new Tile(6866, 4882), new Tile(6856, 4882)), null,
			new PuzzelPart(new Tile(9935, 5641), new Tile(9930, 5651)), new PuzzelPart(new Tile(9935, 5644), new Tile(9929, 5650)), new PuzzelPart(new Tile(9931, 5644), new Tile(9930, 5651)), new PuzzelPart(new Tile(9931, 5646), new Tile(9940, 5650)), new PuzzelPart(new Tile(9934, 5646), new Tile(9939, 5651)), new PuzzelPart(new Tile(9934, 5648), new Tile(9929, 5650)), new PuzzelPart(new Tile(9932, 5648), new Tile(9930, 5640)), new PuzzelPart(new Tile(9932, 5647), new Tile(9929, 5650)), new PuzzelPart(new Tile(9930, 5647), new Tile(9930, 5651)), new PuzzelPart(new Tile(9930, 5650), new Tile(9940, 5650)), null,
			new PuzzelPart(new Tile(9359, 6032), new Tile(9364, 6027)), new PuzzelPart(new Tile(9363, 6032), new Tile(9363, 6026)), new PuzzelPart(new Tile(9363, 6027), new Tile(9353, 6027)), new PuzzelPart(new Tile(9358, 6027), new Tile(9354, 6037)), new PuzzelPart(new Tile(9358, 6032), new Tile(9353, 6036)), new PuzzelPart(new Tile(9354, 6037), new Tile(9354, 6037)), new PuzzelPart(new Tile(9354, 6036), new Tile(9364, 6036)), null,
			new PuzzelPart(new Tile(7816, 6216), new Tile(7814, 6225)), new PuzzelPart(new Tile(7816, 6217), new Tile(7813, 6224)), new PuzzelPart(new Tile(7814, 6217), new Tile(7814, 6225)), new PuzzelPart(new Tile(7814, 6218), new Tile(7824, 6224)), new PuzzelPart(new Tile(7815, 6218), new Tile(7823, 6225)), new PuzzelPart(new Tile(7815, 6221), new Tile(7813, 6224)), new PuzzelPart(new Tile(7814, 6221), new Tile(7823, 6225)), new PuzzelPart(new Tile(7814, 6223), new Tile(7824, 6224)), new PuzzelPart(new Tile(7821, 6223), new Tile(7823, 6225)), new PuzzelPart(new Tile(7821, 6224), new Tile(7813, 6224)), null,
			new PuzzelPart(new Tile(8978, 6422), new Tile(8984, 6412)), new PuzzelPart(new Tile(8978, 6417), new Tile(8974, 6413)), new PuzzelPart(new Tile(8977, 6417), new Tile(8975, 6412)), new PuzzelPart(new Tile(8977, 6415), new Tile(8985, 6413)), new PuzzelPart(new Tile(8978, 6415), new Tile(8984, 6412)), new PuzzelPart(new Tile(8978, 6413), new Tile(8985, 6413)), new PuzzelPart(new Tile(8981, 6413), new Tile(8984, 6423)), new PuzzelPart(new Tile(8974, 6422), new Tile(8974, 6422)), new PuzzelPart(new Tile(8981, 6421), new Tile(8974, 6422)), new PuzzelPart(new Tile(8977, 6421), new Tile(8975, 6423)), null,
			new PuzzelPart(new Tile(10312, 6614), new Tile(10312, 6604)), new PuzzelPart(new Tile(10312, 6605), new Tile(10322, 6605)), new PuzzelPart(new Tile(10316, 6605), new Tile(10321, 6615)), new PuzzelPart(new Tile(10316, 6611), new Tile(10311, 6614)), new PuzzelPart(new Tile(10314, 6611), new Tile(10312, 6604)), new PuzzelPart(new Tile(10314, 6608), new Tile(10322, 6605)), new PuzzelPart(new Tile(10321, 6608), new Tile(10321, 6616)), null};

	private GroundItem statue;

	public SolvePuzzleNode(final MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean validate() {
		return (statue = ctx.getGroundItems().closest("Guardian statue")) != null;
	}

	@Override
	public int execute() {
		for (int i = 0; i < PUZZEL_PARTS.length; i++) {
			final int i_ = i;
			final PuzzelPart part = PUZZEL_PARTS[i];
			if (part == null) {
				continue;
			}
			if (statue.getTile().equals(part.statue)) {
				if (!ctx.getLocalPlayer().getTile().equals(part.pull)) {
					ctx.getWalking().walkExact(part.pull);
					return Calculations.random(1400, 1800);
				} else {
					if (ctx.getTabs().getOpen() != Tab.MAGIC) {
						ctx.getTabs().open(Tab.MAGIC);
					} else {
						if (ctx.getMagic().castSpellOn(Normal.TELEKINETIC_GRAB, statue)) {
							MethodProvider.sleepUntil(new Condition() {
								@Override
								public boolean verify() {
									return (statue = ctx.getGroundItems().closest("Guardian statue")) != null && statue.getTile().equals(PUZZEL_PARTS[i_ + 1].statue);
								}
							}, Calculations.random(6000, 9000));
						}
					}
				}
			}
		}
		return Calculations.random(800, 1600);
	}

	@Override
	public String getName() {
		return "Solving puzzel";
	}

	private static class PuzzelPart {
		final Tile statue;
		final Tile pull;
		
		PuzzelPart(final Tile statue, final Tile pull) {
			this.statue = statue;
			this.pull = pull;
		}
	}
}
