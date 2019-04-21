package action;

import org.dreambot.api.script.*;
import main.*;
import org.dreambot.api.wrappers.interactive.*;

public class FailsafeAction extends Action
{
    public FailsafeAction(final AbstractScript script) {
        super(script);
    }
    
    @Override
    public boolean Condition() {
        return this.script.getLocalPlayer().getZ() == 1 || this.script.getLocalPlayer().getZ() == 2;
    }
    
    @Override
    public void Run() {
        final GameObject stairs = (GameObject)this.script.getGameObjects().closest(new String[] { "Staircase" });
        if (stairs != null) {
            if (stairs.isOnScreen()) {
                stairs.interact("Climb-down");
                Utility.SleepUntil(this.script.getLocalPlayer().getZ() == 0, 1500L);
            }
            else {
                this.script.getCamera().rotateToEntity((Entity)stairs);
            }
        }
    }
    
    @Override
    public int GetPriority() {
        return 3;
    }
}
