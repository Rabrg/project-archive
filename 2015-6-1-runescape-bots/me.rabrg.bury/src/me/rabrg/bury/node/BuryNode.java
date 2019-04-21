package me.rabrg.bury.node;

import me.rabrg.bury.RabrgBury;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.wrappers.items.Item;

public final class BuryNode
  extends Node
{
  private Item bones;
  
  public BuryNode(final MethodContext ctx)
  {
    super(ctx);
  }
  
  @Override
public final boolean validate()
  {
    return (this.bones = this.ctx.getInventory().get(RabrgBury.bonesId)) != null;
  }
  
  @Override
public final int execute()
  {
    if (this.bones.interact("Bury")) {
      return Calculations.random(700, 800);
    }
    return Calculations.random(75, 175);
  }
  
  @Override
public final String getName()
  {
    return "Burying bones";
  }
}
