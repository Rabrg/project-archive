package me.rabrg.construction;

import me.rabrg.construction.node.BuildNode;
import me.rabrg.construction.node.NoteNode;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(category = Category.CONSTRUCTION, name = "Construction", author = "Rabrg", version = 0.2)
public class Construction extends TaskScript implements MessageListener {

    private boolean doneBuilding;
    private int nailCount;

    @Override
    public void onStart() {
        addNodes(new NoteNode(this), new BuildNode(this));
    }

    @Override
    public void onGameMessage(Message message) {
        if (message.getMessage().equals("You use a nail.")) {
            nailCount++;
            if (nailCount == 2) {
                doneBuilding = true;
                nailCount = 0;
            }
        }
    }

    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {

    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }

    public boolean isDoneBuilding() {
        return doneBuilding;
    }

    public void setDoneBuilding(boolean doneBuilding) {
        this.doneBuilding = doneBuilding;
    }

    public boolean isOak() {
        return getSkills().getRealLevel(Skill.CONSTRUCTION) >= 33 && getInventory().contains("Oak plank");
    }
}
