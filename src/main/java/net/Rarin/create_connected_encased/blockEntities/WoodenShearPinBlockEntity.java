package net.Rarin.create_connected_encased.blockEntities;

import net.Rarin.create_connected_encased.config.CCEConfigs;
import net.Rarin.create_connected_encased.registries.CCEncasedBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenShearPinBlockEntity extends CustomShearPinBlockEntity {
    public WoodenShearPinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (CCEConfigs.common().shouldWoodenShearPinBreak.get()) {
            if ((getSpeed() > CCEConfigs.common().maxSpeedWoodenShearPin.get() || getSpeed() < -CCEConfigs.common().maxSpeedWoodenShearPin.get())) {
                if (source != null) {
                    if (!(CCEncasedBlocks.isWoodenShaftHasState(getLevel().getBlockState(source))))
                        getLevel().destroyBlock(worldPosition, false);
                } else {
                    getLevel().destroyBlock(worldPosition, false);
                }
            }
        }

    }


}
