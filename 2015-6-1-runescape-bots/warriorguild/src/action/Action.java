package action;

import org.dreambot.api.methods.*;
import org.dreambot.api.script.*;

public abstract class Action extends MethodContext
{
    protected AbstractScript script;
    
    public abstract boolean Condition();
    
    public abstract void Run();
    
    public abstract int GetPriority();
    
    public Action(final AbstractScript script) {
        this.script = script;
    }
}
