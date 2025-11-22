package mod_buffs;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.type.StatusEffect;

public class Buffs {

    // already in StatusEffect
    private final StatusEffect tripleDamage, doubleDamage, tripleHealth, doubleHealth, doubleSpeed, doubleBuildSpeed, doubleReloadSpeed;

    // new created
    private final StatusEffect waveTimeFreeze, enemyFreeze, enemyFreezeEffect, doubleStorage;


    // initialises all buffs
    public Buffs(){
        tripleDamage = new StatusEffect("tripleDamage");
        doubleDamage = new StatusEffect("doubleDamage");
        tripleHealth = new StatusEffect("tripleHealth");
        doubleHealth = new StatusEffect("doubleHealth");
        doubleSpeed = new StatusEffect("doubleSpeed");
        doubleBuildSpeed = new StatusEffect("doubleBuildSpeed");
        doubleReloadSpeed = new StatusEffect("doubleReloadSpeed");

        waveTimeFreeze = new StatusEffect("waveTimeFreeze");
        enemyFreeze = new StatusEffect("enemyFreeze");
        enemyFreezeEffect = new StatusEffect("enemyFreezeEffect");
        doubleStorage = new StatusEffect("doubleStorage");

        loadBuffers();
    }

    // get of the buffs
    public StatusEffect getTripleDamage(){
        return tripleDamage;
    }

    public StatusEffect getDoubleDamage(){
        return doubleDamage;
    }

    public StatusEffect getDoubleHealth(){
        return doubleHealth;
    }

    public StatusEffect getTripleHealth(){
        return tripleHealth;
    }

    public StatusEffect getDoubleSpeed(){
        return doubleSpeed;
    }

    public StatusEffect getDoubleBuildSpeed(){
        return doubleBuildSpeed;
    }

    public StatusEffect getDoubleReloadSpeed(){
        return doubleReloadSpeed;
    }

    public StatusEffect getWaveTimeFreeze(){return waveTimeFreeze;}

    public StatusEffect getEnemyFreeze(){return enemyFreeze;}

    public StatusEffect getEnemyFreezeEffect(){return enemyFreezeEffect;}

    public StatusEffect getDoubleStorage(){return doubleStorage;}


    private interface BuffConfig{
        void config(StatusEffect effect, float multiplier);
    }

    // creates a new buff for the ship using multipliers existing in the game
    private void createBuff(StatusEffect buff, String display, Color colorIcon, Effect effect, BuffConfig conf, float multiplier){

        buff.color = colorIcon;     // muda a cor do icon
        buff.effect = effect;       // define o  efeito enquanto o buff está ativo
        buff.localizedName = display;
        conf.config(buff, multiplier);
    }

    private void createBuff(StatusEffect buff, String display, Color colorIcon, Effect effect){
        buff.color = colorIcon;
        buff.effect = effect;
        buff.localizedName = display;
    }

    private void createBuff(StatusEffect buff, Color colorIcon, Effect effect){
        buff.color = colorIcon;
        buff.effect = effect;

        buff.speedMultiplier = 0f;
        buff.reloadMultiplier = 0f;
        buff.disarm = true;
    }

    // creates all the buffs
    private void loadBuffers(){
        createBuff(tripleDamage, "Super damage (3x)", Color.red, Fx.hitLaserColor,
                (buff, mult) -> buff.damageMultiplier = mult, 3f);

        createBuff(doubleDamage,"Super damage (2x)", Color.red, Fx.hitLaserColor,
                (buff, mult) -> buff.damageMultiplier = mult, 2f);

        createBuff(tripleHealth, "Super HP (3x)", Color.green, Fx.none,
                (buff, mult) -> buff.healthMultiplier = mult, 3f);

        createBuff(doubleDamage, "Super HP (2x)", Color.green, Fx.none,
                (buff, mult) -> buff.healthMultiplier = mult, 2f);

        createBuff(doubleSpeed, "Super Speed (2x)", Color.gray, Fx.lightning,
                (buff, mult) -> buff.speedMultiplier = mult, 2f);

        createBuff(doubleBuildSpeed, "Speed Buider (2x)", Color.yellow, Fx.coreBuildBlock,
                (buff, mult) -> buff.buildSpeedMultiplier = mult, 2f);

        createBuff(doubleReloadSpeed, "Fast Reload (2x)", Color.brown, Fx.fire,
                (buff, mult) -> buff.reloadMultiplier = mult, 2f);

        createBuff(waveTimeFreeze, "Wave time freezes", Color.orange, Fx.overdriven);

        // ------
        createBuff(enemyFreeze, "Enemy freeze", Color.blue, Fx.freezing);

        createBuff(enemyFreezeEffect, "Enemy freeze effects", Color.blue, Fx.freezing);
        // the reason for not joining this two is that the game doesn't know where to implement, so, it would paralize the enimies and the player.
        // also, every enemy that spawns later (after stating the buff) would spawn with normal stats, and not frozen

        createBuff(doubleStorage, "Double Storage", Color.yellow, Fx.overdriven);
    }
}
