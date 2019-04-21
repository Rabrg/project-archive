package me.rabrg.roguecooker;

import me.rabrg.roguecooker.task.CookTask;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;

@ScriptManifest(category = Category.COOKING, name = "Rogue Cooker", author = "Rabrg", version = 0.1)
public class RogueCooker extends TaskScript {

    @Override
    public void onStart() {
        addNodes(new CookTask());
    }
}
