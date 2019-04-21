package action;

import org.dreambot.api.script.*;
import main.*;

public class BankAction extends Action
{
    @Override
    public void Run() {
        if (this.script.getBank().isOpen()) {
            this.CloseBank();
        }
        else {
            this.OpenBank();
        }
    }
    
    public BankAction(final AbstractScript script) {
        super(script);
    }
    
    private void OpenBank() {
        if (this.script.getShop().isOpen()) {
            this.script.getShop().close();
            Utility.SleepUntil(!this.script.getShop().isOpen(), 2500L);
        }
        this.script.getBank().open();
        Utility.SleepUntil(this.script.getBank().isOpen(), 3000L);
    }
    
    private void CloseBank() {
        if (this.script.getInventory().onlyContains(new String[] { "Coins" })) {
            this.script.getBank().close();
            Utility.SleepUntil(!this.script.getBank().isOpen(), 2500L);
        }
        else {
            this.script.getBank().depositAllExcept(new String[] { "Coins" });
            Utility.SleepUntil(this.script.getBank().onlyContains(new String[] { "Coins" }), 2500L);
        }
    }
    
    @Override
    public boolean Condition() {
        return (this.script.getInventory().isFull() && this.script.getLocalPlayer().getZ() == 0) || this.script.getBank().isOpen();
    }
    
    @Override
    public int GetPriority() {
        return 1;
    }
}
