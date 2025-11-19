package mod_buffs;

import arc.*;
import arc.graphics.Color;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.type.StatusEffect;
import mindustry.ui.dialogs.*;

import java.util.ArrayList;
import java.util.List;

public class BuffsMod extends Mod{
    private static final int DELTA_POINTS = 50;

    private static final int POINTS_WHEN_DEAD = 0;

    private List<StatusEffect> buffs;

    private interface BuffConfig{
        void config(StatusEffect effect, float multiplier);
    }

    // initialise buffs with points,
    public void init(){
        buffs = new ArrayList<>();
    }

    private void buffEffects(StatusEffect buff, Color colorIcon, Effect effect){
        buff.color = colorIcon;     // muda a cor do icon
        buff.effect = effect;       // define o  efeito enquanto o buff está ativo
    }

    private StatusEffect createBuff(String id, String display, Color colorIcon, Effect effect, BuffConfig conf, float multiplier){
        StatusEffect buff = new StatusEffect(id);

        buff.color = colorIcon;     // muda a cor do icon
        buff.effect = effect;       // define o  efeito enquanto o buff está ativo
        buff.localizedName = display;

        conf.config(buff, multiplier);

        buffs.add(buff);    // adiciona nos meus buffs
        return buff;
    }




    // create new buffs
    public void loadBuffers(){
        createBuff("super-damage", "Super damage (3x)", Color.red, Fx.hitLaserColor,
                (buff, mult) -> buff.damageMultiplier = mult, 3f);

        createBuff("super-hp", "Super HP (3x)", Color.green, Fx.none,
                (buff, mult) -> buff.healthMultiplier = mult, 3f);

        createBuff("super-speed", "Super Speed (2x)", Color.gray, Fx.lightning,
                (buff, mult) -> buff.speedMultiplier = mult, 2f);

        createBuff("super-reload", "Fast Reload (2x)", Color.brown, Fx.fire,
                (buff, mult) -> buff.reloadMultiplier = mult, 2f);

        createBuff("build-speed", "Speed Buider (2x)", Color.orange, Fx.coreBuildBlock,
                (buff, mult) -> buff.buildSpeedMultiplier = mult, 2f);

    }
}
