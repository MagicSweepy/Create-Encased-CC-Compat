package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlock;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class CustomFluidVesselBlock extends FluidVesselBlock {
    private final Supplier<BlockEntityType<? extends FluidVesselBlockEntity>> blockEntityType;

    public CustomFluidVesselBlock(Properties properties, Supplier<BlockEntityType<? extends FluidVesselBlockEntity>> blockEntityType) {
        super(properties, false);
        this.blockEntityType = blockEntityType;
    }

    @Override
    public BlockEntityType<? extends FluidVesselBlockEntity> getBlockEntityType() {
        return blockEntityType.get();
    }
}
