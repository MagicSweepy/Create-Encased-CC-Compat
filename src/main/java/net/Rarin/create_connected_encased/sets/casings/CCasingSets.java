package net.Rarin.create_connected_encased.sets.casings;

import com.google.common.collect.ImmutableList;
import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import fr.iglee42.createcasing.registries.EncasedSprites;
import fr.iglee42.createcasing.registries.EncasedTags;
import fr.iglee42.createcasing.sets.casings.CasingSets;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CCasingSets {

    private static final List<CCasingSet> sets = new ArrayList<CCasingSet>();

    public static final CCasingSet ANDESITE = register("andesite", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.ANDESITE_PLATES.tag)
            .ctSprite(() -> AllSpriteShifts.ANDESITE_CASING)
            .existingCasing(() -> AllBlocks.ANDESITE_CASING.get())
            .existingChainCogwheel(() -> CCBlocks.ENCASED_CHAIN_COGWHEEL.get())
            .existingBrake(() -> CCBlocks.BRAKE.get())
            .existingParallelGearbox(() -> CCBlocks.PARALLEL_GEARBOX.get())
            .existingSixWayGearbox(() -> CCBlocks.SIX_WAY_GEARBOX.get())
            .existingInvertedGearShift(() -> CCBlocks.INVERTED_GEARSHIFT.get())
            .existingInvertedClutch(() -> CCBlocks.INVERTED_CLUTCH.get())
            .existingCentrifugalClutch(() -> CCBlocks.CENTRIFUGAL_CLUTCH.get())
            .existingFreewheelClutch(() -> CCBlocks.FREEWHEEL_CLUTCH.get())
            .existingOverstressClutch(() -> CCBlocks.OVERSTRESS_CLUTCH.get())
    );

    public static final CCasingSet BRASS = register("brass", new CCasingSet.Options()
            .item(CommonMetal.BRASS.plates)
            .ctSprite(() -> AllSpriteShifts.BRASS_CASING)
            .existingCasing(() -> AllBlocks.BRASS_CASING.get())
            .kineticBlocks()
            .parallelGearbox()
            .sixWayGearbox()
            .ClutchBlocks()
    );

    public static final CCasingSet COPPER = register("copper", new CCasingSet.Options()
            .item(CommonMetal.COPPER.plates)
            .ctSprite(() -> AllSpriteShifts.COPPER_CASING)
            .existingCasing(() -> AllBlocks.COPPER_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet RAILWAY = register("railway", new CCasingSet.Options()
            .item(AllTags.AllItemTags.OBSIDIAN_PLATES.tag)
            .ctSprite(() -> AllSpriteShifts.RAILWAY_CASING)
            .existingCasing(() -> AllBlocks.RAILWAY_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet SHADOW_STEEL = register("shadow_steel", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.SHADOW_STEEL_PLATES.tag)
            .ctSprite(() -> AllSpriteShifts.SHADOW_STEEL_CASING)
            .existingCasing(() -> AllBlocks.SHADOW_STEEL_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet REFINED_RADIANCE = register("refined_radiance", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.REFINED_RADIANCE_PLATES.tag)
            .ctSprite(() -> AllSpriteShifts.REFINED_RADIANCE_CASING)
            .existingCasing(() -> AllBlocks.REFINED_RADIANCE_CASING.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet CREATIVE = register("creative", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.CHORIUM_PLATES.tag)
            .ctSprite(() -> AllSpriteShifts.CREATIVE_CASING)
            .existingCasing(() -> CasingSets.CREATIVE.getCasing())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet INDUSTRIAL_IRON = register("industrial_iron", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.INDUSTRIAL_IRON_PLATES.tag)
            .ctSprite(() -> null)
            .existingCasing(() -> AllBlocks.INDUSTRIAL_IRON_BLOCK.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet WEATHERED_IRON = register("weathered_iron", new CCasingSet.Options()
            .item(EncasedTags.EItemTags.WEATHERED_IRON_PLATES.tag)
            .ctSprite(() -> null)
            .existingCasing(() -> AllBlocks.WEATHERED_IRON_BLOCK.get())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static final CCasingSet ZINC = register("zinc", new CCasingSet.Options()
            .item(CommonMetal.ZINC.plates)
            .ctSprite(() -> EncasedSprites.ZINC_CASING)
            .existingCasing(() -> CasingSets.ZINC.getCasing())
            .kineticBlocks()
            .GearboxBlocks()
            .ClutchBlocks()
    );

    public static CCasingSet register(String id, CCasingSet.Options options) {
        String name = id.toLowerCase(Locale.ROOT);
        if (sets.stream().anyMatch(set -> set.getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("A casing set with name `" + name + "` already exists !");
        CCasingSet set = new CCasingSet(name, options);
        sets.add(set);
        return set;
    }

    public static List<CCasingSet> getSets() {
        return ImmutableList.copyOf(sets);
    }
}
