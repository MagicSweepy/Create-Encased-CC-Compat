package net.Rarin.create_connected_encased.registries.data;

import com.simibubi.create.foundation.data.TagGen.CreateTagsProvider;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.registries.CCEncasedTags;
import net.Rarin.create_connected_encased.sets.casings.CCasingSets;
import net.Rarin.create_connected_encased.sets.transmissions.CCETransmissionSets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import static net.Rarin.create_connected_encased.CCEncased.REGISTRATE;

public class CCEncasedRegistrateTags {

	public static void addGenerators() {
		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CCEncasedRegistrateTags::genBlockTags);
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CCEncasedRegistrateTags::genItemTags);
	}

	private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
		CreateTagsProvider<Item> prov = new CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);
		forEachRecasableBlock((blockTag, itemTag, block) -> {
			if (block != null && !isEncasedGenerated(block) && block.asItem() != Items.AIR)
				prov.tag(itemTag.tag).add(block.asItem());
		});

	}

	private static void genBlockTags(RegistrateTagsProvider<Block> provIn) {
		CreateTagsProvider<Block> prov = new CreateTagsProvider<>(provIn, Block::builtInRegistryHolder);
		forEachRecasableBlock((blockTag, itemTag, block) -> {
			if (block != null && !isEncasedGenerated(block))
				prov.tag(blockTag.tag).add(block);
		});
	}

	private static boolean isEncasedGenerated(Block block) {
		return CCEncased.ID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace());
	}

	private static void forEachRecasableBlock(TaggedBlockConsumer consumer) {
		CCasingSets.getSets().forEach(set -> {
			consumer.accept(CCEncasedTags.EBlockTags.CHAIN_COGWHEELS, CCEncasedTags.EItemTags.CHAIN_COGWHEELS, set.getChainCogwheel());
			consumer.accept(CCEncasedTags.EBlockTags.BRAKES, CCEncasedTags.EItemTags.BRAKES, set.getBrake());
			consumer.accept(CCEncasedTags.EBlockTags.PARALLEL_GEARBOXS, CCEncasedTags.EItemTags.PARALLEL_GEARBOXS, set.getParallelGearbox());
			consumer.accept(CCEncasedTags.EBlockTags.SIX_WAY_GEARBOXS, CCEncasedTags.EItemTags.SIX_WAY_GEARBOXS, set.getSixWayGearbox());
			consumer.accept(CCEncasedTags.EBlockTags.INVERTED_GEARSHIFTS, CCEncasedTags.EItemTags.INVERTED_GEARSHIFTS, set.getInvertedGearShift());
			consumer.accept(CCEncasedTags.EBlockTags.INVERTED_CLUTCHS, CCEncasedTags.EItemTags.INVERTED_CLUTCHS, set.getInvertedClutch());
			consumer.accept(CCEncasedTags.EBlockTags.CENTRIFUGAL_CLUTCHS, CCEncasedTags.EItemTags.CENTRIFUGAL_CLUTCHS, set.getCentrifugalClutch());
			consumer.accept(CCEncasedTags.EBlockTags.FREEWHEEL_CLUTCHS, CCEncasedTags.EItemTags.FREEWHEEL_CLUTCHS, set.getFreewheelClutch());
			consumer.accept(CCEncasedTags.EBlockTags.OVERSTRESS_CLUTCHS, CCEncasedTags.EItemTags.OVERSTRESS_CLUTCHS, set.getOverstressClutch());
			consumer.accept(CCEncasedTags.EBlockTags.FLUID_VESSELS, CCEncasedTags.EItemTags.FLUID_VESSELS, set.getFluidVessel());
		});

		CCETransmissionSets.getSets().forEach(set -> {
			consumer.accept(CCEncasedTags.EBlockTags.SHEAR_PINS, CCEncasedTags.EItemTags.SHEAR_PINS, set.getShearPin());
		});
	}

	@FunctionalInterface
	private interface TaggedBlockConsumer {
		void accept(CCEncasedTags.EBlockTags blockTag, CCEncasedTags.EItemTags itemTag, Block block);
	}

}
