package me.rabrg.aiocombat.nodes;

import me.rabrg.aiocombat.AIOCombat;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public final class AttackNode extends TaskNode {

    private final AIOCombat script;

    private NPC target;

    public AttackNode(final AIOCombat script) {
        this.script = script;
    }

    @Override
    public boolean accept() {
        log("" + (!getLocalPlayer().isInCombat() || getLocalPlayer().getInteractingCharacter() != null
                && getLocalPlayer().getInteractingCharacter().getHealth() == 0) + " "
                + getInventory().contains(script.getFoodName()));
        return ((!getLocalPlayer().isInCombat() || getLocalPlayer().getInteractingCharacter() != null
                && getLocalPlayer().getInteractingCharacter().getHealth() == 0)
                && getInventory().contains(script.getFoodName()) || getDialogues().canContinue())
                && ((target = getNpcs().closest(new Filter<NPC>() {
            @Override
            public boolean match(final NPC npc) {
                return npc != null && (!npc.isInCombat() || npc.getInteractingCharacter() != null
                        && getLocalPlayer().getName().equals(npc.getInteractingCharacter().getName()))
                        && npc.getName() != null && script.getTargets().contains(npc.getName())
                        && getMap().canReach(npc);
            }
        })) != null || script.getAttackTile() != null && script.getAttackTile().distance() > 25);
    }

    @Override
    public int execute() {
        log("attack node ran");
        if (target != null) {
            log("target not null");
            if (!target.isOnScreen() && getCamera().rotateToEntity(target)) {
                log("target camera");
                sleep(0, 300);
            } else if (target.interact("Attack")) {
                log("target attack");
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return getLocalPlayer().isInCombat();
                    }
                }, Calculations.random(1200, 3000));
                if (getLocalPlayer().isInCombat() && Calculations.random(10) > 4)
                    getMouse().moveMouseOutsideScreen();
            }
        } else if (script.getAttackTile() != null && script.getAttackTile().distance() > 25 && getWalking().walk(script.getAttackTile())) {
            sleep(300, 1800);
            log("target walk");
        }
        return Calculations.random(0, 300);
    }
}
