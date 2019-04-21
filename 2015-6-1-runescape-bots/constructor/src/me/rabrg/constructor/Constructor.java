package me.rabrg.constructor;

import me.rabrg.constructor.enums.ConstructionType;
import me.rabrg.constructor.nodes.BuildTaskNode;
import me.rabrg.constructor.nodes.EnterPortalTaskNode;
import me.rabrg.constructor.nodes.PhialsTaskNode;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.PaintListener;

import java.awt.*;

@ScriptManifest(category = Category.CONSTRUCTION, name = "Constructor", author = "Rabrg", version = 0.1)
public class Constructor extends TaskScript implements PaintListener {

    private ConstructionType constructionType;

    @Override
    public void onStart() {
        constructionType= ConstructionType.CRUDE_WOODEN_CHAIR;
        addNodes(new BuildTaskNode(constructionType), new EnterPortalTaskNode(), new PhialsTaskNode());
    }

    @Override
    public void onPaint(final Graphics g) {

    }

    public ConstructionType getConstructionType() {
        return constructionType;
    }
}
