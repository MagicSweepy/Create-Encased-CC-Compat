package net.Rarin.create_connected_encased.blockEntities.visuals;

import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import dev.engine_room.flywheel.api.model.Model;
import dev.engine_room.flywheel.api.visual.BlockEntityVisual;
import dev.engine_room.flywheel.api.visualization.VisualizationContext;
import dev.engine_room.flywheel.lib.model.Models;
import net.Rarin.create_connected_encased.blockEntities.WoodenShearPinBlockEntity;
import net.Rarin.create_connected_encased.registries.CCEncasedPartialModels;
import net.minecraft.core.registries.BuiltInRegistries;

public class WoodenShearPinVisual {

    public static BlockEntityVisual<WoodenShearPinBlockEntity> create(VisualizationContext context, WoodenShearPinBlockEntity blockEntity, float partialTick) {
        Model model = Models.partial(CCEncasedPartialModels.SHEAR_PIN_MODELS.get(BuiltInRegistries.BLOCK.getKey(blockEntity.getBlockState().getBlock()).getPath().replaceAll("_shear_pin","")));
        return new SingleAxisRotatingVisual<>(context, blockEntity, partialTick, model);
    }
}
