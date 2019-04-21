package action;

import org.dreambot.api.methods.map.*;
import org.dreambot.api.script.*;
import org.dreambot.api.methods.tabs.*;
import main.*;
import org.dreambot.api.methods.filter.*;
import org.dreambot.api.methods.world.*;
import org.dreambot.api.wrappers.interactive.*;

public class BuyAction extends Action
{
    private int potion_1_amount;
    private int food_1_amount;
    private int food_2_amount;
    private Area potion_shop_area;
    private Area food_shop_area;
    private String currentShop;
    
    public BuyAction(final AbstractScript script) {
        super(script);
        this.potion_1_amount = 1;
        this.food_1_amount = 1;
        this.food_2_amount = 1;
        this.potion_shop_area = new Area(2844, 3555, 2848, 3548);
        this.food_shop_area = new Area(2838, 3555, 2843, 3548);
        this.currentShop = "";
    }
    
    @Override
    public void Run() {
        if (this.script.getShop().isOpen()) {
            this.CloseShop();
        }
        else {
            if (!this.script.getTabs().isOpen(Tab.INVENTORY)) {
                this.script.getTabs().open(Tab.INVENTORY);
                Utility.SleepUntil(this.script.getTabs().isOpen(Tab.INVENTORY), 1000L);
            }
            this.OpenShop();
        }
    }
    
    private void CloseShop() {
        if (this.currentShop == "Warrior Guild Food Shop") {
            this.food_1_amount = this.script.getShop().count("Plain pizza");
            this.food_2_amount = this.script.getShop().count("Potato with cheese");
            if (this.food_1_amount > 0) {
                this.PurchaseTen("Plain pizza");
            }
            else if (this.food_2_amount > 0) {
                this.PurchaseTen("Potato with cheese");
            }
            else {
                this.script.getShop().close();
                Utility.SleepUntil(!this.script.getShop().isOpen(), 1500L);
            }
        }
        else {
            this.potion_1_amount = this.script.getShop().count("Strength potion(3)");
            if (this.potion_1_amount > 0) {
                this.PurchaseTen("Strength potion(3)");
            }
            else {
                this.script.getShop().close();
                Utility.SleepUntil(!this.script.getShop().isOpen(), 1500L);
            }
        }
    }
    
    public static void DefineUserSettings() {
        Values.USER_SETTINGS = String.valueOf(Values.BANKER_NAME) + ":" + Values.HAS_BANKPIN;
    }
    
    private void OpenShop() {
        if (this.currentShop == "Warrior Guild Potion Shop") {
            if (this.potion_1_amount > 0) {
                if (this.potion_shop_area.contains((Entity)this.script.getLocalPlayer())) {
                    this.InteractWithShopOwner("Lilly");
                }
                else {
                    if (this.script.getLocalPlayer().isMoving()) {
                        return;
                    }
                    this.script.getWalking().walk(this.potion_shop_area.getCenter());
                    Utility.SleepUntil(this.potion_shop_area.contains((Entity)this.script.getLocalPlayer()), 2500L);
                }
            }
            else if (this.food_1_amount > 0 || this.food_2_amount > 0) {
                if (this.food_shop_area.contains((Entity)this.script.getLocalPlayer())) {
                    this.InteractWithShopOwner("Lidio");
                }
                else {
                    if (this.script.getLocalPlayer().isMoving()) {
                        return;
                    }
                    this.script.getWalking().walk(this.food_shop_area.getCenter());
                    Utility.SleepUntil(this.food_shop_area.contains((Entity)this.script.getLocalPlayer()), 2500L);
                }
            }
            else {
                this.HopWorld();
            }
        }
        else if (this.food_1_amount > 0 || this.food_2_amount > 0) {
            if (this.food_shop_area.contains((Entity)this.script.getLocalPlayer())) {
                this.InteractWithShopOwner("Lidio");
            }
            else {
                if (this.script.getLocalPlayer().isMoving()) {
                    return;
                }
                this.script.getWalking().walk(this.food_shop_area.getRandomTile());
                Utility.SleepUntil(this.food_shop_area.contains((Entity)this.script.getLocalPlayer()), 2500L);
            }
        }
        else if (this.potion_1_amount > 0) {
            if (this.potion_shop_area.contains((Entity)this.script.getLocalPlayer())) {
                this.InteractWithShopOwner("Lilly");
            }
            else {
                if (this.script.getLocalPlayer().isMoving()) {
                    return;
                }
                this.script.getWalking().walk(this.potion_shop_area.getRandomTile());
                Utility.SleepUntil(this.potion_shop_area.contains((Entity)this.script.getLocalPlayer()), 2500L);
            }
        }
        else {
            this.HopWorld();
        }
    }
    
    private void HopWorld() {
        this.script.getWorldHopper().hopWorld(this.script.getWorlds().getRandomWorld((Filter)new Filter<World>() {
            public boolean match(final World w) {
                return !w.isDeadmanMode() && !w.isF2P() && !w.isHighRisk() && !w.isPVP() && w.getMinimumLevel() <= 1000;
            }
        }));
        final boolean food_1_amount = true;
        this.potion_1_amount = (food_1_amount ? 1 : 0);
        this.food_2_amount = (food_1_amount ? 1 : 0);
        this.food_1_amount = (food_1_amount ? 1 : 0);
        sleep(6500);
    }
    
    private void PurchaseTen(final String item) {
        this.script.getShop().purchaseTen(item);
        sleep(600, 1000);
    }
    
    private void InteractWithShopOwner(final String owner) {
        if (this.script.getLocalPlayer().isMoving()) {
            return;
        }
        final NPC shopOwner = (NPC)this.script.getNpcs().closest(new String[] { owner });
        if (shopOwner == null) {
            return;
        }
        shopOwner.interact("Trade");
        this.currentShop = Values.GetShopForOwner(owner);
        Utility.SleepUntil(this.script.getShop().isOpen(), 1000L);
    }
    
    @Override
    public boolean Condition() {
        return !this.script.getInventory().isFull();
    }
    
    @Override
    public int GetPriority() {
        return 1;
    }
}
