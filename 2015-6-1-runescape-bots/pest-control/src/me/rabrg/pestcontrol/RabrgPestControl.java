package me.rabrg.pestcontrol;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;

@ScriptManifest(category = Category.MINIGAME, name = "Rabrg Pest Control", author = "Rabrg", version = 1.0)
public class RabrgPestControl extends TaskScript {

    @Override
    public void onStart() {
        addNodes();
    }
}
