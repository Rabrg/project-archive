package action;

import org.dreambot.api.script.*;
import main.*;
import java.util.*;

public class ActionHandler
{
    public static List<Action> actions;
    public static Action currentAction;
    private static AbstractScript script;
    
    static {
        ActionHandler.actions = new ArrayList<Action>();
    }
    
    public static void InitializeActions(final AbstractScript absScript) {
        ActionHandler.script = absScript;
        ActionHandler.actions.add(new BankAction(absScript));
        ActionHandler.actions.add(new BuyAction(absScript));
        ActionHandler.actions.add(new FailsafeAction(absScript));
        Collections.sort(ActionHandler.actions, new ActionSorter());
    }
    
    public static void UpdateActions() {
        if (ActionHandler.currentAction != null) {
            if (!ActionHandler.currentAction.Condition()) {
                ActionHandler.currentAction = null;
                return;
            }
            ActionHandler.currentAction.Run();
            ActionHandler.currentAction = null;
        }
        else {
            ActionHandler.currentAction = getNextAction();
        }
    }
    
    private static Action getNextAction() {
        final Action nextAction = null;
        for (final Action action : ActionHandler.actions) {
            if (action.Condition()) {
                return action;
            }
        }
        Main.log("No available action to perform. Trying again.");
        return nextAction;
    }
    
    public static class ActionSorter implements Comparator<Action>
    {
        @Override
        public int compare(final Action o1, final Action o2) {
            if (o1.GetPriority() < o2.GetPriority()) {
                return 1;
            }
            return -1;
        }
    }
}
