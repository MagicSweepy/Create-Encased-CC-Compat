package net.Rarin.create_connected_encased.blockEntities;

import com.hlysine.create_connected.content.shearpin.ShearPinBlockEntity;
import com.simibubi.create.content.kinetics.base.IRotate;
import fr.iglee42.createcasing.registries.EncasedSounds;
import net.Rarin.create_connected_encased.sets.transmissions.CCETransmissionSets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;

public class CustomShearPinBlockEntity extends ShearPinBlockEntity {
    static final int RANDOM_DELAY = 5;

    public CustomShearPinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void initialize() {
        onKineticUpdate();
        super.initialize();
    }

    private void onKineticUpdate() {
        if (IRotate.StressImpact.isEnabled()) {
            if (isOverStressed()) {
                if (level != null) {
                    level.scheduleTick(getBlockPos(), getBlockState().getBlock(), level.random.nextInt(RANDOM_DELAY), TickPriority.EXTREMELY_HIGH);
                }
            }
        }
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);
        onKineticUpdate();
    }


    @Override
    public void tick() {
        super.tick();

        if (CCETransmissionSets.MLDEG.getShearPin() != null && getBlockState().is(CCETransmissionSets.MLDEG.getShearPin())){
            if (getSpeed() == 256 || getSpeed() == -256){
                if (lazyTickCounter % 60 == 0) EncasedSounds.MLDEG.playAt(level,worldPosition,0.25f,0.5f,false);
            }
        }
    }

}
