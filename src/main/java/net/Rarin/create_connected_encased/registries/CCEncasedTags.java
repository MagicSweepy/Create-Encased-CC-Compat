package net.Rarin.create_connected_encased.registries;


import com.hlysine.create_connected.CreateConnected;
import com.simibubi.create.Create;
import net.Rarin.create_connected_encased.CCEncased;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import static net.Rarin.create_connected_encased.registries.CCEncasedTags.NameSpace.*;

public class CCEncasedTags {

	public enum NameSpace {
		MOD(CCEncased.ID),
		CREATE(Create.ID),
		CREATE_CONNECTED(CreateConnected.MODID),
		COMMON("c");

		public final String id;

		NameSpace(String id) {
			this.id = id;
		}

		public ResourceLocation id(String path) {
			return ResourceLocation.fromNamespaceAndPath(this.id, path);
		}

		public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
			return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
		}
	}

	public enum EBlockTags {
		CHAIN_COGWHEELS(CREATE_CONNECTED),
		BRAKES(CREATE_CONNECTED),
		PARALLEL_GEARBOXS(CREATE_CONNECTED),
		SIX_WAY_GEARBOXS(CREATE_CONNECTED),
		INVERTED_GEARSHIFTS(CREATE_CONNECTED),
		INVERTED_CLUTCHS(CREATE_CONNECTED),
		CENTRIFUGAL_CLUTCHS(CREATE_CONNECTED),
		FREEWHEEL_CLUTCHS(CREATE_CONNECTED),
		OVERSTRESS_CLUTCHS(CREATE_CONNECTED),
		FLUID_VESSELS(CREATE_CONNECTED),
		SHEAR_PINS(CREATE_CONNECTED);

		public final TagKey<Block> tag;

		EBlockTags() {
			this(MOD);
		}

		EBlockTags(NameSpace namespace) {
			this(namespace, null);
		}

		EBlockTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.BLOCK, namespace.id(this, pathOverride));
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Block block) {
			return block.builtInRegistryHolder()
				.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
		}

		public boolean matches(BlockState state) {
			return state.is(tag);
		}

	}

	public enum EItemTags {
		CHAIN_COGWHEELS(CREATE_CONNECTED),
		BRAKES(CREATE_CONNECTED),
		PARALLEL_GEARBOXS(CREATE_CONNECTED),
		SIX_WAY_GEARBOXS(CREATE_CONNECTED),
		INVERTED_GEARSHIFTS(CREATE_CONNECTED),
		INVERTED_CLUTCHS(CREATE_CONNECTED),
		CENTRIFUGAL_CLUTCHS(CREATE_CONNECTED),
		FREEWHEEL_CLUTCHS(CREATE_CONNECTED),
		OVERSTRESS_CLUTCHS(CREATE_CONNECTED),
		FLUID_VESSELS(CREATE_CONNECTED),
		SHEAR_PINS(CREATE_CONNECTED);

		public final TagKey<Item> tag;

		EItemTags() {
			this(MOD);
		}

		EItemTags(NameSpace namespace) {
			this(namespace, null);
		}

		EItemTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.ITEM, namespace.id(this, pathOverride));
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Item item) {
			return item.builtInRegistryHolder()
				.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack.is(tag);
		}
	}

}
