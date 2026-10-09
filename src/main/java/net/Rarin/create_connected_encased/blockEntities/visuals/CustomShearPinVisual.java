package net.Rarin.create_connected_encased.blockEntities.visuals;

import com.hlysine.create_connected.content.shearpin.ShearPinBlockEntity;
import com.hlysine.create_connected.registries.CCPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.Rarin.create_connected_encased.registries.CCEncasedPartialModels;
import net.minecraft.core.registries.BuiltInRegistries;

public class CustomShearPinVisual extends SingleAxisRotatingVisual<ShearPinBlockEntity> {

    public CustomShearPinVisual(VisualizationContext context, ShearPinBlockEntity blockEntity, float partialTick) {
        super(context, blockEntity, partialTick,
                Models.partial(
                        CCEncasedPartialModels.SHEAR_PIN_MODELS.getOrDefault(BuiltInRegistries.BLOCK.getKey(blockEntity.getBlockState().getBlock()).getPath().replace("_shear_pin", ""), CCPartialModels.SHEAR_PIN)
                ));
    }
}
