package net.Rarin.create_connected_encased.compat.kubejs.builders;

import com.google.common.base.Preconditions;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.Rarin.create_connected_encased.sets.casings.CCasingSet;

import java.util.function.Supplier;

public class CasingSetBuilder {

    private String name;
    public SourceLine sourceLine;
    private CCasingSet.Options options;


    /*public CasingSetBuilder(String name, TagKey<Item> tag){
        this(name, registries -> registries.registryOrThrow(Registries.ITEM).getTag(tag).map(HolderSet.class::cast).orElse(HolderSet.empty()));
    }

    public CasingSetBuilder(String name, Supplier<ItemLike> item){
        this(name,registries-> HolderSet.direct(item.get().asItem().builtInRegistryHolder()));
    }

    public CasingSetBuilder(String name, Function<RegistryAccess, HolderSet<Item>> item) {
        this.name = name;
        this.sourceLine = SourceLine.UNKNOWN;
        this.options = new CasingSet.Options(item).kjsGenerated();
    }*/

    @HideFromJS
    public String getName() {
        return name;
    }

    @HideFromJS
    public CCasingSet.Options getOptions() {
        return options;
    }

    public CasingSetBuilder ctSprite(Supplier<CTSpriteShiftEntry> ctSprite) {
        Preconditions.checkNotNull(ctSprite,"Connected Texture Sprite Supplier can't be null");
        this.options.ctSprite(ctSprite);
        return this;
    }

    public CasingSetBuilder chainCogwheel(){
        this.options.chainCogwheel();
        return this;
    }

    public CasingSetBuilder brake(){
        this.options.brake();
        return this;
    }

    public CasingSetBuilder parallelGearbox(){
        this.options.parallelGearbox();
        return this;
    }

    public CasingSetBuilder sixWayGearbox(){
        this.options.sixWayGearbox();
        return this;
    }

    public CasingSetBuilder invertedGearShift(){
        this.options.invertedGearShift();
        return this;
    }

    public CasingSetBuilder invertedClutch(){
        this.options.invertedClutch();
        return this;
    }

    public CasingSetBuilder centrifugalClutch(){
        this.options.centrifugalClutch();
        return this;
    }

    public CasingSetBuilder freewheelClutch(){
        this.options.freewheelClutch();
        return this;
    }

    public CasingSetBuilder overstressClutch(){
        this.options.overstressClutch();
        return this;
    }

    public CasingSetBuilder kineticBlocks(){
        return chainCogwheel().brake().invertedGearShift();
    }

    public CasingSetBuilder ClutchBlocks(){
        return invertedClutch().centrifugalClutch().freewheelClutch().overstressClutch();
    }

    public CasingSetBuilder GearboxBlocks(){
        return parallelGearbox().sixWayGearbox();
    }

    public CasingSetBuilder everything(){
        return chainCogwheel().brake().invertedGearShift().invertedClutch().centrifugalClutch().freewheelClutch().overstressClutch().parallelGearbox().sixWayGearbox();
    }
}
