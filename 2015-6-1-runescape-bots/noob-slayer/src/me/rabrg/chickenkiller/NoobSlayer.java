package me.rabrg.chickenkiller;

import me.rabrg.chickenkiller.node.AttackNode;
import me.rabrg.chickenkiller.node.ChangeWorldNode;
import me.rabrg.chickenkiller.node.EatNode;
import me.rabrg.chickenkiller.node.RunNode;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(category = Category.COMBAT, name = "Noob Slayer", author = "Rabrg", version = 0.2)
public class NoobSlayer extends TaskScript implements MessageListener {

    private boolean playerTalked;

    @Override
    public void onStart() {
        addNodes(new EatNode(this), new RunNode(), /*new ChangeWorldNode(this),*/ new AttackNode());
    }


    @Override
    public void onGameMessage(Message message) {
        if (message.getMessage().startsWith("Congratulations"))
            MethodProvider.log("congrats");
    }

    @Override
    public void onPlayerMessage(Message message) {
        setPlayerTalked(true);
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

    public void setPlayerTalked(final boolean playerTalked) {
        this.playerTalked = playerTalked;
    }
    public boolean hasPlayerTalked() {
        return playerTalked;
    }
}
