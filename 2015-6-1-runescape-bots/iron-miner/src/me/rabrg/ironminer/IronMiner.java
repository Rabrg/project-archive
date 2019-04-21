package me.rabrg.ironminer;

import me.rabrg.ironminer.nodes.ChangeWorldTaskNode;
import me.rabrg.ironminer.nodes.DropTaskNode;
import me.rabrg.ironminer.nodes.MineTaskNode;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;

@ScriptManifest(category = Category.MINING, name = "Iron Miners", author = "Rabrg", version = 1.0)
public class IronMiner extends TaskScript {

    @Override
    public void onStart() {
        addNodes(new MineTaskNode(), new DropTaskNode());
    }
}
