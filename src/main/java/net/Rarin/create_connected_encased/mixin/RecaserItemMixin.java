package net.Rarin.create_connected_encased.mixin;

import fr.iglee42.createcasing.items.recaser.RecaserItem;
import fr.iglee42.createcasing.registries.EncasedDataComponents;
import net.Rarin.create_connected_encased.sets.SetBase;
import net.Rarin.create_connected_encased.utils.RecaserChangeManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(RecaserItem.class)
public abstract class RecaserItemMixin {

    @Inject(method = "useOn", at = @At("TAIL"), cancellable = true)
    private void createConnectedEncased$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        if (player == null || !player.mayBuild()) {
            return;
        }

        ItemStack heldStack = context.getItemInHand();
        RegistryAccess access = player.registryAccess();
        List<ItemStack> ammoStacks = RecaserItem.findAllAmmo(player, access);

        ItemStack ammoStack = ItemStack.EMPTY;

        if (heldStack.has(EncasedDataComponents.RECASER_PREFERRED_ITEM)) {
            Item preferredItem = heldStack.get(EncasedDataComponents.RECASER_PREFERRED_ITEM);
            Optional<ItemStack> preferredStack = ammoStacks.stream().filter(stack -> stack.getItem() == preferredItem).findFirst();
            if (preferredStack.isPresent()) {
                ammoStack = preferredStack.get();
            }
        }

        if (ammoStack.isEmpty()) {
            ammoStack = ammoStacks.stream().findFirst().orElse(ItemStack.EMPTY);
        }

        if (ammoStack.isEmpty()) {
            return;
        }

        List<SetBase<?, ?>> sets = RecaserChangeManager.getSetsForItem(access, ammoStack.getItem());

        if (sets.isEmpty()) {
            return;
        }

        RecaserChangeManager.CCEAmmo ammo = new RecaserChangeManager.CCEAmmo(ammoStack, sets);

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        cir.setReturnValue(RecaserChangeManager.tryChangeBlock(level, pos, state, player, ammo));
    }
}
