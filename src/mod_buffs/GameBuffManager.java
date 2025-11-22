package mod_buffs;

import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.storage.CoreBlock;

public class GameBuffManager { //usar mais Vars e Rules no mindustry

    private Buffs buff;
    private boolean isCapacityDouble;

    public GameBuffManager(){
        buff = new Buffs();
        isCapacityDouble = false;
    }

    //updates game with game buffs that changes the whole game (freeze time, freeze enemies, double storage)
    public void updateGame(){
        //if the player isn't in the game, doesn't do anything
        if(Vars.player.unit() == null){ return;}

        updateTimeFreeze();

        updateFreezeEnemies();

        updateDoubleStorage();
    }

    private void auxDoubleStorage(float multiplier){
        Vars.content.blocks().each(block -> {
            if (block instanceof CoreBlock) {
                block.itemCapacity = (int) (block.itemCapacity * multiplier);
            }
        });
    }

    // Double Storage
    private void updateDoubleStorage() {
        if (Vars.player.unit().hasEffect(buff.getDoubleStorage()) && !isCapacityDouble) {
            auxDoubleStorage(2f);
            isCapacityDouble = true;
            Vars.ui.showInfoToast("Double Storage", 2f);
        } else if (!Vars.player.unit().hasEffect(buff.getDoubleStorage()) && isCapacityDouble){
            auxDoubleStorage(0.5f);
            isCapacityDouble = false;
            Vars.ui.showInfoToast("Normal Storage", 2f);
        }
    }

    // Time Freeze
    private void updateTimeFreeze(){
        Vars.state.rules.waveTimer = !Vars.player.unit().hasEffect(buff.getWaveTimeFreeze());
        Vars.ui.showInfoToast("Time Freeze", 2f);
    }

    // Enemy freeze
    private void updateFreezeEnemies(){
        if(Vars.player.unit().hasEffect(buff.getEnemyFreeze())) {
            Groups.unit.each(unit -> {
                if (unit.team != Vars.player.team()) {
                    unit.apply(buff.getEnemyFreezeEffect(), 30f);
                }
            });
            Vars.ui.showInfoToast("Freeze enemies", 2f);
        }
    }
}
