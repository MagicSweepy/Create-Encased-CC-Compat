package net.Rarin.create_connected_encased.blocks;

import com.hlysine.create_connected.content.shearpin.ShearPinBlock;
import com.hlysine.create_connected.content.shearpin.ShearPinBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CustomShearPinBlock extends ShearPinBlock {

    public CustomShearPinBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends ShearPinBlockEntity> getBlockEntityType() {
        return CCEncasedBlockEntities.SHEAR_PIN.get();
    }
}
