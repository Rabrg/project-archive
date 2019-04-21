package action;

import org.dreambot.api.script.*;
import org.dreambot.api.methods.*;
import org.dreambot.api.methods.input.mouse.*;
import org.dreambot.api.methods.input.*;

public class AntibanAction extends Action
{
    private final int MAX_YAW = 2050;
    private final int MAX_PITCH = 383;
    
    public AntibanAction(final AbstractScript script) {
        super(script);
    }
    
    @Override
    public boolean Condition() {
        return Calculations.random(100) == 70;
    }
    
    @Override
    public void Run() {
        MouseSettings.setSpeed(this.R(1, 4));
        this.RotateCamera(this.R(-2050, 2050), this.R(-383, 383));
    }
    
    private void RotateCamera(final int dyaw, final int dpitch) {
        final Camera c = this.script.getCamera();
        int yaw = c.getYaw() + dyaw;
        if (yaw > 2050) {
            yaw -= 2050;
        }
        int pitch = c.getPitch() + dpitch;
        if (pitch > 383) {
            pitch -= 383;
        }
        this.script.getCamera().rotateTo(yaw, pitch);
    }
    
    @Override
    public int GetPriority() {
        return 2;
    }
    
    private int R(final int a, final int b) {
        return Calculations.random(a, b);
    }
}
