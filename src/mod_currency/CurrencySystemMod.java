//Author: Tomás Alves 68681
//TODO: buyingBuff & activateBuff METHODS.
//NOTES: Throw a new task for the team to make a store and study more about the currency values. Still a lot to do...
package mod_currency;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.*;

public class CurrencySystemMod {
    private static final String KEY_CURRENCY = "CurrencyBalance";
    private static final String KEY_COST_PREFIX = "BuffCost_";

    private static final float TIER_BOSS_HP = 6000f;
    private static final float TIER_HIGH_HP = 1000f;
    private static final float TIER_MED_HP = 300f;

    private static final int REWARD_BOSS = 500;
    private static final int REWARD_HIGH = 100;
    private static final int REWARD_MED = 30;
    private static final int REWARD_LOW = 10;

    public enum BuffType {
        COMMON(10),
        RARE(50),
        LEGENDARY(200);

        public final int initialCost;

        BuffType(int cost) {
            this.initialCost = cost;
        }
    }

    public static void init() {
        // 1º Mobs Defeated
        Events.on(UnitDestroyEvent.class, new Cons<UnitDestroyEvent>() {
            public void get(UnitDestroyEvent event) {
                if (event.unit != null && Vars.player != null && event.unit.team != Vars.player.team()) {

                    float health = event.unit.maxHealth;
                    int reward = calculateReward(health);
                    addCurrency(reward);
                }
            }
        });

        // 2º Game Over
        Events.on(LoseEvent.class, new Cons<LoseEvent>() {
            public void get(LoseEvent event) {
                removeCurrency(getBalance());
                Log.info("GAME OVER! Wallet emptied.");
            }
        });
    }


    public static int calculateReward(float health) {
        if (health > TIER_BOSS_HP) return REWARD_BOSS;
        if (health > TIER_HIGH_HP) return REWARD_HIGH;
        if (health > TIER_MED_HP)  return REWARD_MED;
        return REWARD_LOW;
    }


    public static void addCurrency(int amount) {
        int current = getBalance();
        Core.settings.put(KEY_CURRENCY, current + amount);
    }


    public static void removeCurrency(int amount) {
        int current = getBalance();
        Core.settings.put(KEY_CURRENCY, Math.max(0, current - amount));
    }


    public static int getBalance() {
        return Core.settings.getInt(KEY_CURRENCY, 0);
    }

    public static int getBuffPrice(BuffType type) {
        String key = KEY_COST_PREFIX + type.name();
        return Core.settings.getInt(key, type.initialCost);
    }

    public static void setBuffPrice(BuffType type, int newValue) {
        String key = KEY_COST_PREFIX + type.name();
        Core.settings.put(key, newValue);
    }

    //TODO - MESSAGE FOR NO SUFF POINTS, CHECK BUFFPRICES VALUES BY TESTING THE GAME
    public static void buyingBuff(BuffType type) {
        int balance = getBalance();
        int price = getBuffPrice(type);

        if (balance >= price) {
            removeCurrency(price);
            activateBuff(type);
            setBuffPrice(type, price * 2);
        } else {
            // Insufficient Funds
        }
    }

    //TODO - Receive from BuffsMod
    public static void activateBuff(BuffType type) {
        // if (type == BuffType.COMMON)
        // if (type == BuffType.LEGENDARY)
        // else BuffType.RARE
    }
}