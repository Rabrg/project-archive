package me.rabrg.herblore;

import me.rabrg.herblore.nodes.BankNode;
import me.rabrg.herblore.nodes.CreatePotionNode;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;

@ScriptManifest(category = Category.HERBLORE, name = "Rabrg Herblore", author = "Rabrg", version = 0.1)
public class Herblore extends TaskScript {

    private HerbloreGUI gui;

    private String unfinishedName = "Vial of water";
    private String secondaryName = "Toadflax";
    private String finishedName = "Toadflax potion (unf)";

    @Override
    public void onStart() {
//        createGUI();
        addNodes(new CreatePotionNode(this), new BankNode(this));
    }
    private void createGUI() {

        gui = new HerbloreGUI();
        gui.start();
    }

    public String getUnfinishedName() {
        return unfinishedName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public String getFinishedName() {
        return finishedName;
    }
}
