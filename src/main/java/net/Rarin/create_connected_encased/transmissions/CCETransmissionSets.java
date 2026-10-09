package net.Rarin.create_connected_encased.transmissions;

import com.google.common.collect.ImmutableList;
import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.AllItems;
import net.Rarin.create_connected_encased.blocks.WoodenShearPinBlock;
import net.Rarin.create_connected_encased.registries.CCEncasedBlockEntities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.*;

public class CCETransmissionSets {

    private static final List<CCETransmissionSet> sets = new ArrayList<>();
    private static final Map<WoodType,CCETransmissionSet> woodSets = new HashMap<>();

    public static final CCETransmissionSet ANDESITE = register("andesite",new CCETransmissionSet.Options()
            .item(()-> AllItems.ANDESITE_ALLOY.get())
            .existingShearPin(CCBlocks.SHEAR_PIN)
    );

    public static final CCETransmissionSet BRASS = register("brass",new CCETransmissionSet.Options()
            .everything(()-> AllItems.BRASS_INGOT.get())
    );

    public static final CCETransmissionSet COPPER = register("copper",new CCETransmissionSet.Options()
            .everything(()-> Items.COPPER_INGOT)
    );

    public static final CCETransmissionSet ZINC = register("zinc",new CCETransmissionSet.Options()
            .everything(()-> AllItems.ZINC_INGOT.get())
    );

    public static final CCETransmissionSet MLDEG = register("mldeg",new CCETransmissionSet.Options()
            .item(()-> Items.BLACKSTONE)
            .shearPin()
    );

    static {
        for (WoodType woodType : new WoodType[]{WoodType.ACACIA,WoodType.BIRCH,WoodType.BAMBOO,WoodType.CHERRY,WoodType.CRIMSON,WoodType.DARK_OAK,WoodType.OAK,WoodType.JUNGLE,WoodType.MANGROVE,WoodType.WARPED}) {
            CCETransmissionSet set = register(woodType.name().toLowerCase(Locale.ROOT), new CCETransmissionSet.Options()
                    .shearPinConstructor(()-> WoodenShearPinBlock::new)
                    .shaftBlockEntityType(CCEncasedBlockEntities.WOODEN_SHEAR_PIN::get)
                    .everything(()-> BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(woodType.name().toLowerCase(Locale.ROOT) + "_planks")))
            );
            woodSets.put(woodType, set);
        }

        CCETransmissionSet spruceSet = register("spruce", new CCETransmissionSet.Options()
                .item(()-> Items.SPRUCE_PLANKS)
                .shearPin()
                .shearPinConstructor(()-> WoodenShearPinBlock::new)
                .shaftBlockEntityType(CCEncasedBlockEntities.WOODEN_SHEAR_PIN::get)
        );

        woodSets.put(WoodType.SPRUCE,spruceSet);
    }


    public static CCETransmissionSet register(String id, CCETransmissionSet.Options options){
        String name = id.toLowerCase(Locale.ROOT);
        if (sets.stream().anyMatch(set->set.getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("A transmission set with name `" + name + "` already exists !");
        CCETransmissionSet set = new CCETransmissionSet(name,options);
        sets.add(set);
        return set;
    }

    public static List<CCETransmissionSet> getSets() {
        return ImmutableList.copyOf(sets);
    }

    public static List<CCETransmissionSet> getWoodSets() {
        return ImmutableList.copyOf(woodSets.values());
    }

    public static CCETransmissionSet getWoodSet(WoodType woodType){
        return woodSets.get(woodType);
    }

    public static WoodType getWoodTypeForSet(CCETransmissionSet set) {
        if (!woodSets.containsValue(set)) return null;
        return woodSets.entrySet().stream().filter(e->e.getValue().equals(set)).map(Map.Entry::getKey).findFirst().orElse(null);
    }
}
