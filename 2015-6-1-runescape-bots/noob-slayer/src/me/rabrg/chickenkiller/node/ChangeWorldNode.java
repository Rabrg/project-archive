package me.rabrg.chickenkiller.node;

import me.rabrg.chickenkiller.NoobSlayer;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.Player;

public class ChangeWorldNode extends TaskNode {

    private final NoobSlayer noobSlayer;

    public ChangeWorldNode(final NoobSlayer noobSlayer) {
        this.noobSlayer = noobSlayer;
    }

    @Override
    public boolean accept() {
        return !getLocalPlayer().isInCombat() && (noobSlayer.hasPlayerTalked()
                || getPlayers().all(new Filter<Player>() {
            @Override
            public boolean match(final Player player) {
                return player != null && player.getName() != null
                        && !player.getName().equals(getLocalPlayer().getName()) && player.distance() < 3;
            }
        }).size() > 0);
    }

    @Override
    public int execute() {
        MethodProvider.sleep(3600, 5400);
        if (getWorldHopper().hopWorld(getWorlds().getRandomWorld(new Filter<World>() {
            @Override
            public boolean match(World world) {
                return world != null && !world.isHighRisk() && !world.isF2P() && !world.isDeadmanMode() && world.isMembers()
                        && world.getMinimumLevel() < 1 && world.getID() != 373 && world.getID() != 366
                        && world.getID() != 361 && world.getID() != 353 && world.getID() != 349
                        && !world.isPVP();
            }
        })));
            noobSlayer.setPlayerTalked(false);
        return Calculations.random(2400) + 1800;
    }
}
