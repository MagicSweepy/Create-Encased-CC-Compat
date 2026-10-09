package net.Rarin.create_connected_encased.utils;

import com.hlysine.create_connected.registries.CCBlocks;
import com.simibubi.create.api.registry.SimpleRegistry;
import net.Rarin.create_connected_encased.casings.CCasingSet;
import net.Rarin.create_connected_encased.casings.CCasingSets;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CCEStressKeysModifier {
    
    private static final SimpleRegistry<Block, Block> STRESS_KEY_LOOKUP = SimpleRegistry.create();
    private static final List<CasingType> CASING_TYPES = List.of(
            new CasingType(CCBlocks.ENCASED_CHAIN_COGWHEEL, CCasingSet::doesGenerateChainCogwheel, CCasingSet::getChainCogwheel),
            new CasingType(CCBlocks.BRAKE, CCasingSet::doesGenerateBrake, CCasingSet::getBrake),
            new CasingType(CCBlocks.INVERTED_GEARSHIFT, CCasingSet::doesGenerateInvertedGearShift, CCasingSet::getInvertedGearShift),
            new CasingType(CCBlocks.INVERTED_CLUTCH, CCasingSet::doesGenerateInvertedClutch, CCasingSet::getInvertedClutch),
            new CasingType(CCBlocks.CENTRIFUGAL_CLUTCH, CCasingSet::doesGenerateCentrifugalClutch, CCasingSet::getCentrifugalClutch),
            new CasingType(CCBlocks.FREEWHEEL_CLUTCH, CCasingSet::doesGenerateFreewheelClutch, CCasingSet::getFreewheelClutch),
            new CasingType(CCBlocks.OVERSTRESS_CLUTCH, CCasingSet::doesGenerateOverstressClutch, CCasingSet::getOverstressClutch),
            new CasingType(CCBlocks.PARALLEL_GEARBOX, CCasingSet::doesGenerateParallelGearbox, CCasingSet::getParallelGearbox),
            new CasingType(CCBlocks.SIX_WAY_GEARBOX, CCasingSet::doesGenerateSixWayGearbox, CCasingSet::getSixWayGearbox)
    );

    public static Block getStressKey(Block block) {
        ensureLookupInitialized();
        Block key = STRESS_KEY_LOOKUP.get(block);
        return key == null ? block : key;
    }

    private static boolean initialized = false;

    private static void ensureLookupInitialized() {
        if (initialized)
            return;

        initialized = true;

        rebuildLookup();
    }

    private static void rebuildLookup() {
        STRESS_KEY_LOOKUP.invalidate();

        for (CCasingSet set : CCasingSets.getSets()) {
            for (CasingType type : CASING_TYPES) {
                if (!type.predicate().test(set))
                    continue;

                Block custom = type.getter().apply(set);
                if (custom != null)
                    STRESS_KEY_LOOKUP.register(custom, type.keySupplier().get());
            }
        }
    }

    private record CasingType(
            Supplier<? extends Block> keySupplier,
            Predicate<CCasingSet> predicate,
            Function<CCasingSet, Block> getter
    ) {}
}