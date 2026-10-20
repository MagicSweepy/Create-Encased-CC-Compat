package net.Rarin.create_connected_encased.sets.transmissions;

import com.hlysine.create_connected.content.shearpin.ShearPinBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.Rarin.create_connected_encased.sets.SetBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class CCETransmissionSet extends SetBase<CCETransmissionSet, CCETransmissionSet.Options> {
    private @Nullable BlockEntry<? extends ShearPinBlock> shearPinBlock;
    private @Nullable Supplier<Function<BlockBehaviour.Properties,? extends ShearPinBlock>> shearPinConstructor;

    private @Nullable Supplier<BlockEntityType<?>> shearPinBeType;

    private final boolean shearPin;

    protected CCETransmissionSet(String name, Options options) {
        super(name, options);
        shearPin = options.shearPin;

        this.shearPinConstructor = options.shearPinConstructor;
        this.shearPinBeType = options.shearPinBeType;

        if (options.existingShearPin != null) shearPinBlock = options.existingShearPin;
    }


    public boolean doesGenerateShearPin(){
        return shearPin;
    }

    @Nullable
    public BlockEntry<? extends ShearPinBlock> getShearPinSupplier() {
        return shearPinBlock;
    }

    @Nullable
    public ShearPinBlock getShearPin() {
        return shearPinBlock == null ? null : shearPinBlock.get();
    }

    @Nullable
    public Function<BlockBehaviour.Properties,? extends ShearPinBlock> getShearPinConstructor(){
        return shearPinConstructor == null ? null : shearPinConstructor.get();
    }

    @Nullable
    public BlockEntityType<?> getShearPinBlockEntityType(){
        return shearPinBeType == null ? null : shearPinBeType.get();
    }


    public void setShearPin(@Nonnull BlockEntry<? extends ShearPinBlock> shearPin){
        if (getShearPinSupplier() != null)
            throw new UnsupportedOperationException("You cannot modify a shearPin that has already been referenced");
        shearPinBlock = shearPin;
    }

    public boolean isInSet(Block block){
        return  block.equals(getShearPin());
    }

    public static class Options extends SetBase.Options<CCETransmissionSet, CCETransmissionSet.Options> {
        private boolean shearPin;

        private @Nullable BlockEntry<? extends ShearPinBlock> existingShearPin;

        private @Nullable Supplier<Function<BlockBehaviour.Properties,? extends ShearPinBlock>> shearPinConstructor;

        private @Nullable Supplier<BlockEntityType<?>> shearPinBeType;

        public Options() {
            shearPin = false;
        }

        public Options shearPin() {
            this.shearPin = true;
            return this;
        }

        public Options shearPinConstructor(Supplier<Function<BlockBehaviour.Properties,? extends ShearPinBlock>> constructor){
            this.shearPinConstructor = constructor;
            return this;
        }

        public Options shaftBlockEntityType(Supplier<BlockEntityType<?>> beType){
            this.shearPinBeType = beType;
            return this;
        }

        public Options everything(){
            return shearPin();
        }

        Options existingShearPin(BlockEntry<? extends ShearPinBlock> shearPin) {
            this.existingShearPin = shearPin;
            this.shearPin = false;
            return this;
        }

        @Override
        protected Options self() {
            return this;
        }
    }
}
