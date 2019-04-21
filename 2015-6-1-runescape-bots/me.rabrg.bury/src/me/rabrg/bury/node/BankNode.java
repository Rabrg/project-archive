package me.rabrg.bury.node;

import me.rabrg.bury.RabrgBury;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodContext;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class BankNode
  extends Node
{
  public BankNode(final MethodContext ctx)
  {
    super(ctx);
  }
  
  @Override
public final boolean validate()
  {
    return (this.ctx.getBank().isOpen()) || (RabrgBury.bonesId == -1) || (!this.ctx.getInventory().contains(RabrgBury.bonesId));
  }
  
  @Override
public final int execute()
  {
    if (!this.ctx.getBank().isOpen())
    {
      if (this.ctx.getBank().openClosest()) {
        MethodProvider.sleepUntil(new Condition()
        {
          @Override
		public final boolean verify()
          {
            return BankNode.this.ctx.getBank().isOpen();
          }
        }, Calculations.random(5000, 7500));
      }
    }
    else
    {
      if (RabrgBury.bonesId == -1)
      {
        RabrgBury.bonesId = 
        
          this.ctx.getBank().get(new Filter<Item>() {
			@Override
			public boolean match(final Item arg0) {
				return arg0.getName().endsWith("bones");
			}}).getID();
        return 0;
      }
      if (!this.ctx.getInventory().contains(RabrgBury.bonesId))
      {
        if (this.ctx.getBank().withdrawAll(RabrgBury.bonesId)) {
          MethodProvider.sleepUntil(new Condition()
          {
            @Override
			public final boolean verify()
            {
              return BankNode.this.ctx.getInventory().contains(RabrgBury.bonesId);
            }
          }, Calculations.random(1750, 2500));
        }
      }
      else if (this.ctx.getBank().close()) {
        MethodProvider.sleepUntil(new Condition()
        {
          @Override
		public final boolean verify()
          {
            return !BankNode.this.ctx.getBank().isOpen();
          }
        }, Calculations.random(1750, 2500));
      }
    }
    return Calculations.random(25, 200);
  }
  
  @Override
public final String getName()
  {
    return "Banking";
  }
}
