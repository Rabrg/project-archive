package action;

import org.dreambot.api.methods.filter.*;
import org.dreambot.api.methods.world.*;

class BuyAction$1 implements Filter<World> {
    public boolean match(final World w) {
        return !w.isDeadmanMode() && !w.isF2P() && !w.isHighRisk() && !w.isPVP() && w.getMinimumLevel() <= 1000;
    }
}