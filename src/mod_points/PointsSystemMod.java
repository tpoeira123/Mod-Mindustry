package mod_points;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType.*;

public class PointsSystemMod {

    private static final String KEY_POINTS = "PlayerPoints";

    private static final float TIER_BOSS_HP = 6000f;
    private static final float TIER_HIGH_HP = 1000f;
    private static final float TIER_MED_HP = 300f;

    private static final int REWARD_BOSS = 500;
    private static final int REWARD_HIGH = 100;
    private static final int REWARD_MED = 30;
    private static final int REWARD_LOW = 10;
    private static final int REWARD_SECTOR = 1000;

    public static void init() {
        // 1ºMobs Defeated
        Events.on(UnitDestroyEvent.class, new Cons<UnitDestroyEvent>() {
            public void get(UnitDestroyEvent event) {
                if (event.unit != null && Vars.player != null && event.unit.team != Vars.player.team()) {

                    float health = event.unit.maxHealth;
                    int pointsEarned = pointsCalculator(health);

                    addPoints(pointsEarned);
                    Log.info("Enemy defeated (" + (int)health + " HP) -> +" + pointsEarned + " pts");
                }
            }
        });

        // 2ºGame Over
        Events.on(LoseEvent.class, new Cons<LoseEvent>() {
            public void get(LoseEvent event) {
                resetPoints();
                Log.info("GAME OVER! Reset Points.");
            }
        });

        // 3ºChange Sector
        Events.on(SectorLaunchEvent.class, new Cons<SectorLaunchEvent>() {
            public void get(SectorLaunchEvent event) {
                resetPoints();
                Log.info("New sector. Reset Points.");
            }
        });
    }

    public static int pointsCalculator(float health) {
        if (health > TIER_BOSS_HP) return REWARD_BOSS;
        if (health > TIER_HIGH_HP) return REWARD_HIGH;
        if (health > TIER_MED_HP)  return REWARD_MED;
        return REWARD_LOW;
    }

    public static void addPoints(int amount) {
        int current = Core.settings.getInt(KEY_POINTS, 0);
        Core.settings.put(KEY_POINTS, current + amount);
        Log.info("Total Points: " + (current + amount));
    }

    public static int getPoints() {
        return Core.settings.getInt(KEY_POINTS, 0);
    }

    public static void resetPoints() {
        Core.settings.put(KEY_POINTS, 0);
    }
}