package net.Rarin.create_connected_encased.compat.kubejs.builders;

import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.Rarin.create_connected_encased.sets.transmissions.CCETransmissionSet;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class TransmissionSetBuilder {

    private String name;
    public SourceLine sourceLine;
    private CCETransmissionSet.Options options;


    /*public TransmissionSetBuilder(String name) {
        this.name = name;
        this.sourceLine = SourceLine.UNKNOWN;
        this.options = new TransmissionSet.Options().kjsGenerated();
    }*/

    @HideFromJS
    public String getName() {
        return name;
    }

    @HideFromJS
    public CCETransmissionSet.Options getOptions() {
        return options;
    }

    public TransmissionSetBuilder shearPin() {
        this.options.shearPin();
        return this;
    }

    public TransmissionSetBuilder everything(Supplier<? extends Item> item){
        return shearPin();
    }
}
