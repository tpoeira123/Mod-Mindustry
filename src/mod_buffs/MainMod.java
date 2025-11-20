package mod_buffs;

import mindustry.mod.Mod;

public class MainMod extends Mod {
    private static GameBuffManager gameBuffManager;
    private static Buffs buffs;
    private static Shop shop;

    public MainMod() {
        gameBuffManager = new GameBuffManager();
        buffs = new Buffs();
        shop = new Shop();
    }

}
