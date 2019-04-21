package me.rabrg.teleporter;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spell;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(category = Category.MAGIC, name = "Rabrg Teleporter", author = "Rabrg", version = 1)
public class Teleporter extends AbstractScript {

    @Override
    public int onLoop() {
        if (!getInventory().contains("Law rune"))
            stop();
        if (!getLocalPlayer().isAnimating() && getMagic().castSpell(getSpell())) {
            sleepUntil(() -> getLocalPlayer().isAnimating(), 1200);
            sleepUntil(() -> !getLocalPlayer().isAnimating(), 3000);
        }
        return Calculations.random(0, 300);
    }

    private Spell getSpell() {
        final int magic = getSkills().getBoostedLevels(Skill.MAGIC);
        if (magic >= 45)
            return Normal.CAMELOT_TELEPORT;
        else if (magic >= 37)
            return Normal.FALADOR_TELEPORT;
        else if (magic >= 31)
            return Normal.LUMBRIDGE_TELEPORT;
        else if (magic >= 25)
            return Normal.VARROCK_TELEPORT;
        return null;
    }
}
