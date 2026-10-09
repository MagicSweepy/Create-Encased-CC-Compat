package net.Rarin.create_connected_encased.transmissions;

import com.google.common.base.Preconditions;
import com.hlysine.create_connected.content.shearpin.ShearPinBlock;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class CCETransmissionSet {

    private final String name;
    private final Supplier<? extends Item> item;
    private @Nullable BlockEntry<? extends ShearPinBlock> shearPinBlock;
    private @Nullable Supplier<Function<BlockBehaviour.Properties,? extends ShearPinBlock>> shearPinConstructor;

    private @Nullable Supplier<BlockEntityType<?>> shearPinBeType;

    private final boolean shearPin;
    private final boolean notEncasable;
    private final boolean isKJSGenerated;


    protected CCETransmissionSet(String name, Options options) {
        this.name = name;
        this.item = options.item;
        shearPin = options.shearPin;

        this.notEncasable = options.notEncasable;
        isKJSGenerated = options.isKJSGenerated;

        this.shearPinConstructor = options.shearPinConstructor;
        this.shearPinBeType = options.shearPinBeType;

        if (options.existingShearPin != null) shearPinBlock = options.existingShearPin;
    }

    public String getName() {
        return name;
    }

    public boolean doesGenerateShearPin(){
        return shearPin;
    }

    public Item getItem() {
        return item.get();
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

    public boolean isNotEncasable() {
        return notEncasable;
    }

    public boolean isKJSGenerated() {
        return isKJSGenerated;
    }

    public static class Options {
        private Supplier<? extends Item> item;
        private boolean shearPin;
        private boolean notEncasable;
        private boolean isKJSGenerated;

        private @Nullable BlockEntry<? extends ShearPinBlock> existingShearPin;

        private @Nullable Supplier<Function<BlockBehaviour.Properties,? extends ShearPinBlock>> shearPinConstructor;

        private @Nullable Supplier<BlockEntityType<?>> shearPinBeType;


        public Options() {
            shearPin = false;
        }

        public Options item(Supplier<? extends Item> item) {
            Preconditions.checkNotNull(item,"Item Supplier can't be null");
            this.item = item;
            return this;
        }

        public Options shearPin() {
            this.shearPin = true;
            return this;
        }

        public Options notEncasable() {
            this.notEncasable = true;
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

        public Options everything(Supplier<? extends Item> item){
            return item(item).shearPin();
        }

        Options existingShearPin(BlockEntry<? extends ShearPinBlock> shearPin) {
            this.existingShearPin = shearPin;
            this.shearPin = false;
            return this;
        }

        public Options kjsGenerated(){
            this.isKJSGenerated = true;
            return this;
        }


    }
}
