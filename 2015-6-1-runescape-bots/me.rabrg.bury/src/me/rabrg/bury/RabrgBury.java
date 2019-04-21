package me.rabrg.bury;

import java.awt.Graphics;
import me.rabrg.bury.node.BankNode;
import me.rabrg.bury.node.BuryNode;
import me.rabrg.bury.node.Node;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(author="Rabrg", category=Category.PRAYER, name="Rabrg Bone Burier", version=0.1D, description="A script which buries bones")
public final class RabrgBury
  extends AbstractScript
{
  public static int bonesId = -1;
  private final Node[] nodes = { new BankNode(this), new BuryNode(this) };
  
  @Override
public final void onStart()
  {
    this.getSkillTracker().start();
  }
  
  @Override
public final int onLoop()
  {
    final Node[] arrayOfNode = this.nodes;
    for (int i = 0; i < 2; i++)
    {
      Node node;
      if ((node = arrayOfNode[i]).validate()) {
        return node.execute();
      }
    }
    return Calculations.random(5, 75);
  }
  
  @Override
public final void onPaint(final Graphics g)
  {
    g.drawString("Rabrg Bone Burier", 5, 45);
    g.drawString("Prayer (" + this.getSkillTracker().getStartLevel(Skill.PRAYER) + "+" + this.getSkillTracker().getGainedLevels(Skill.PRAYER) + ") xp: " + this.getSkillTracker().getGainedExperience(Skill.PRAYER) + ", xp/h: " + this.getSkillTracker().getGainedExperiencePerHour(Skill.PRAYER), 5, 60);
  }
}
