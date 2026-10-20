package net.Rarin.create_connected_encased.mixin;

import com.simibubi.create.api.stress.BlockStressValues;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.config.CCEConfigs;
import net.Rarin.create_connected_encased.utils.CCEncasedStressKeysModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockStressValues.class, remap = false)
public class BlockStressValuesMixin {

    @Shadow
    public static double getImpact(Block block) {
        throw new UnsupportedOperationException();
    }

    @Shadow
    public static double getCapacity(Block block) {
        throw new UnsupportedOperationException();
    }

    @Inject(method = "getImpact", at = @At("HEAD"), cancellable = true)
    private static void encased$impact(Block block, CallbackInfoReturnable<Double> cir) {
        if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(CCEncased.ID))
            return;

        if (CCEConfigs.common().encasedBlocksUsesOwnKeys.get())
            return;

        Block key = CCEncasedStressKeysModifier.getStressKey(block);

        if (key != block)
            cir.setReturnValue(getImpact(key));
    }

    @Inject(method = "getCapacity", at = @At("HEAD"), cancellable = true)
    private static void encased$capacity(Block block, CallbackInfoReturnable<Double> cir) {
        if (!BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(CCEncased.ID))
            return;

        if (CCEConfigs.common().encasedBlocksUsesOwnKeys.get())
            return;

        Block key = CCEncasedStressKeysModifier.getStressKey(block);

        if (key != block)
            cir.setReturnValue(getCapacity(key));
    }
}
