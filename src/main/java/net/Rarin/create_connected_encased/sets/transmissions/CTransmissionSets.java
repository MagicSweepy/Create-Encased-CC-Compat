package net.Rarin.create_connected_encased.sets.transmissions;

import com.google.common.collect.ImmutableList;
import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.foundation.data.recipe.CommonMetal;
import fr.iglee42.createcasing.registries.EncasedTags;
import net.Rarin.create_connected_encased.blocks.WoodenShearPinBlock;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.*;

public class CTransmissionSets {

    private static final List<CTransmissionSet> sets = new ArrayList<>();
    private static final Map<WoodType, CTransmissionSet> woodSets = new HashMap<>();

    public static final CTransmissionSet ANDESITE = register("andesite", new CTransmissionSet.Options()
            .item(EncasedTags.EItemTags.ANDESITE_PLATES.tag)
            .existingShearPin(CCBlocks.SHEAR_PIN)
    );

    public static final CTransmissionSet BRASS = register("brass", new CTransmissionSet.Options()
            .item(CommonMetal.BRASS.plates)
            .everything()
    );

    public static final CTransmissionSet COPPER = register("copper", new CTransmissionSet.Options()
            .item(CommonMetal.COPPER.plates)
            .everything()
    );

    public static final CTransmissionSet ZINC = register("zinc", new CTransmissionSet.Options()
            .item(CommonMetal.ZINC.plates)
            .everything()
    );

    public static final CTransmissionSet MLDEG = register("mldeg", new CTransmissionSet.Options()
            .item(() -> Items.BLACKSTONE)
            .shearPin()
    );

    static {
        for (WoodType woodType : new WoodType[]{WoodType.ACACIA, WoodType.BIRCH, WoodType.BAMBOO, WoodType.CHERRY, WoodType.CRIMSON, WoodType.DARK_OAK, WoodType.OAK, WoodType.JUNGLE, WoodType.MANGROVE, WoodType.WARPED}) {
            CTransmissionSet set = register(woodType.name().toLowerCase(Locale.ROOT), new CTransmissionSet.Options()
                    .item(() -> BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(woodType.name().toLowerCase(Locale.ROOT) + "_planks")))
                    .shearPinConstructor(() -> WoodenShearPinBlock::new)
                    .shaftBlockEntityType(CCEncasedBlockEntities.WOODEN_SHEAR_PIN::get)
                    .everything()
            );
            woodSets.put(woodType, set);
        }

        CTransmissionSet spruceSet = register("spruce", new CTransmissionSet.Options()
                .item(() -> Items.SPRUCE_PLANKS)
                .shearPin()
                .shearPinConstructor(() -> WoodenShearPinBlock::new)
                .shaftBlockEntityType(CCEncasedBlockEntities.WOODEN_SHEAR_PIN::get)
        );

        woodSets.put(WoodType.SPRUCE, spruceSet);
    }


    public static CTransmissionSet register(String id, CTransmissionSet.Options options) {
        String name = id.toLowerCase(Locale.ROOT);
        if (sets.stream().anyMatch(set -> set.getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("A transmission set with name `" + name + "` already exists !");
        CTransmissionSet set = new CTransmissionSet(name, options);
        sets.add(set);
        return set;
    }

    public static List<CTransmissionSet> getSets() {
        return ImmutableList.copyOf(sets);
    }

    public static List<CTransmissionSet> getWoodSets() {
        return ImmutableList.copyOf(woodSets.values());
    }

    public static CTransmissionSet getWoodSet(WoodType woodType) {
        return woodSets.get(woodType);
    }

    public static WoodType getWoodTypeForSet(CTransmissionSet set) {
        if (!woodSets.containsValue(set)) return null;
        return woodSets.entrySet().stream().filter(e -> e.getValue().equals(set)).map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
