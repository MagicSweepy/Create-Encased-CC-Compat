package net.Rarin.create_connected_encased.registries;

import com.hlysine.create_connected.content.fluidvessel.FluidVesselBlockEntity;
import com.hlysine.create_connected.content.fluidvessel.FluidVesselItem;
import com.hlysine.create_connected.content.parallelgearbox.ParallelGearboxBlock;
import com.hlysine.create_connected.content.shearpin.ShearPinBlock;
import com.hlysine.create_connected.registries.CCDisplaySources;
import com.hlysine.create_connected.registries.CCMountedStorageTypes;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.api.contraption.BlockMovementChecks;
import com.simibubi.create.content.decoration.encasing.CasingConnectivity;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.fluids.tank.FluidTankMovementBehavior;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.blocks.*;
import net.Rarin.create_connected_encased.sets.casings.CCasingSets;
import net.Rarin.create_connected_encased.client.ClientModelWrappers;
import net.Rarin.create_connected_encased.config.CCEStress;
import net.Rarin.create_connected_encased.sets.fluids.CFluidSets;
import net.Rarin.create_connected_encased.sets.transmissions.CTransmissionSets;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType.mountedFluidStorage;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static net.Rarin.create_connected_encased.CCEncased.REGISTRATE;
import static net.Rarin.create_connected_encased.registries.CCEncasedBlockStateGens.*;

@SuppressWarnings("removal")
public class CCEncasedBlocks {

    static {
        REGISTRATE.setCreativeTab(CCEncasedCreativeModeTabs.MAIN_TAB);
    }

    public static BlockEntry<CustomChainCogwheelBlock> createChainWheel(String name) {
        return REGISTRATE.block(name + "_encased_chain_cogwheel", p -> new CustomChainCogwheelBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate(CCEncasedBlockStateGens.encasedChainWheel(name))
                .tag(CCEncasedTags.EBlockTags.CHAIN_COGWHEELS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.CHAIN_COGWHEELS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(Objects.requireNonNull(createChainWheelModel(p, name, true, "item"))))
                .build()
                .register();
    }

    public static BlockEntry<CustomBrakeBlock> createBrake(String name) {
        return REGISTRATE.block(name + "_brake", p -> new CustomBrakeBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> CCEncasedBlockStateGens.axisBlock(c, p, brakeModel(p, name), false))
                .tag(CCEncasedTags.EBlockTags.BRAKES.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.BRAKES.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(brakeItemModel(p, name)))
                .build()
                .register();
    }

    public static BlockEntry<CustomParallelGearboxBlock> createParallelGearbox(String name, CTSpriteShiftEntry sprite, Supplier<BlockItem> item) {
        BlockBuilder<CustomParallelGearboxBlock, CreateRegistrate> entry = REGISTRATE.block(name + "_parallel_gearbox", p -> new CustomParallelGearboxBlock(p, item))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate(parallelgearbox(name))
                .tag(CCEncasedTags.EBlockTags.PARALLEL_GEARBOXS.tag);
        entry = connectedTexture(entry, sprite, (block, cc) -> cc.make(block, sprite,
                (s, f) -> f.getAxis() == s.getValue(ParallelGearboxBlock.AXIS)));
        return entry.item()
                .tag(CCEncasedTags.EItemTags.PARALLEL_GEARBOXS.tag)
                .model((ctx, prov) -> prov.getBuilder(ctx.getName()).parent(Objects.requireNonNull(parallelGearboxModel(prov, name, "item"))))
                .build()
                .register();
    }

    public static BlockEntry<CustomSixWayGearboxBlock> createSixWayGearbox(String name, Supplier<BlockItem> item) {
        BlockBuilder<CustomSixWayGearboxBlock, CreateRegistrate> entry = REGISTRATE.block(name + "_six_way_gearbox", p -> new CustomSixWayGearboxBlock(p, item))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                //.lang(name + " 6-way Gearbox")
                .blockstate(sixWaygearbox(name))
                .tag(CCEncasedTags.EBlockTags.SIX_WAY_GEARBOXS.tag);
        return entry.item()
                .tag(CCEncasedTags.EItemTags.SIX_WAY_GEARBOXS.tag)
                .model((ctx, prov) -> prov.getBuilder(ctx.getName()).parent(Objects.requireNonNull(sixWayGearboxModel(prov, name, "item"))))
                .build()
                .register();
    }

    public static BlockEntry<CustomInvertedGearShiftBlock> createInvertedGearShift(String name) {
        return REGISTRATE.block(name + "_inverted_gearshift", p -> new CustomInvertedGearShiftBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> CCEncasedBlockStateGens.axisBlock(c, p, invertedGearshiftModel(p, name), false))
                .tag(CCEncasedTags.EBlockTags.INVERTED_GEARSHIFTS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.INVERTED_GEARSHIFTS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(invertedGearshiftItemModel(p, name)))
                .build()
                .register();
    }

    public static BlockEntry<CustomInvertedClutchBlock> createInvertedClutch(String name) {
        return REGISTRATE.block(name + "_inverted_clutch", p -> new CustomInvertedClutchBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> CCEncasedBlockStateGens.axisBlock(c, p, invertedClutchModel(p, name), false))
                .tag(CCEncasedTags.EBlockTags.INVERTED_CLUTCHS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.INVERTED_CLUTCHS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(invertedClutchItemModel(p, name)))
                .build()
                .register();
    }

    public static BlockEntry<CustomCentrifugalClutchBlock> createCentrifugalClutch(String name) {
        return REGISTRATE.block(name + "_centrifugal_clutch", p -> new CustomCentrifugalClutchBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> p.directionalBlock(c.get(), centrifugalClutchModel(p, name)))
                .tag(CCEncasedTags.EBlockTags.CENTRIFUGAL_CLUTCHS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.CENTRIFUGAL_CLUTCHS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(centrifugalClutchItemModel(p, name)))
                .build()
                .register();
    }


    public static BlockEntry<CustomFreewheelClutchBlock> createFreewheelClutch(String name) {
        return REGISTRATE.block(name + "_freewheel_clutch", p -> new CustomFreewheelClutchBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> p.directionalBlock(c.get(), freewheelClutchModel(p, name)))
                .tag(CCEncasedTags.EBlockTags.FREEWHEEL_CLUTCHS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.FREEWHEEL_CLUTCHS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(freewheelClutchItemModel(p, name)))
                .build()
                .register();
    }

    public static BlockEntry<CustomOverstressClutchBlock> createOverstressClutch(String name) {
        return REGISTRATE.block(name + "_overstress_clutch", p -> new CustomOverstressClutchBlock(p, name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> CCEncasedBlockStateGens.axisBlock(c, p, overstressClutchModel(p, name), false))
                .tag(CCEncasedTags.EBlockTags.OVERSTRESS_CLUTCHS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.OVERSTRESS_CLUTCHS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(overstressClutchItemModel(p, name)))
                .build()
                .register();
    }

    public static BlockEntry<CustomFluidVesselBlock> createFluidVessel(String name, CTSpriteShiftEntry sideSprite, CTSpriteShiftEntry topSprite, CTSpriteShiftEntry innerSprite, Supplier<BlockEntityType<? extends FluidVesselBlockEntity>> beType) {
        return REGISTRATE.block(name + "_fluid_vessel", p -> new CustomFluidVesselBlock(p, beType))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.noOcclusion()
                        .isRedstoneConductor((p1, p2, p3) -> true))
                .transform(pickaxeOnly())
                .blockstate(new CustomFluidVesselGenerator(name)::generate)
                .onRegister(CreateRegistrate.blockModel(() -> ClientModelWrappers.encasedFluidVessel(sideSprite, topSprite, innerSprite)))
                .onRegister(b -> BlockMovementChecks.registerAttachedCheck((state, world, pos, direction) -> {
                    if (state.getBlock() instanceof CustomFluidVesselBlock)
                        return BlockMovementChecks.CheckResult.of(ConnectivityHandler.isConnected(world, pos, pos.relative(direction)));
                    return BlockMovementChecks.CheckResult.PASS;
                }))
                .transform(displaySource(CCDisplaySources.BOILER_STATUS))
                .transform(mountedFluidStorage(CCMountedStorageTypes.FLUID_VESSEL))
                .onRegister(movementBehaviour(new FluidTankMovementBehavior()))
                .addLayer(() -> RenderType::cutoutMipped)
                .tag(CCEncasedTags.EBlockTags.FLUID_VESSELS.tag)
                .item(FluidVesselItem::new)
                .tag(CCEncasedTags.EItemTags.FLUID_VESSELS.tag)
                .model(AssetLookup.customBlockItemModel("fluid_vessel", name, "block_x_single_window"))
                .build()
                .register();
    }

    public static BlockEntry<ShearPinBlock> createShearPin(String name, Function<BlockBehaviour.Properties, ? extends ShearPinBlock> factory) {
        return REGISTRATE.block(name + "_shear_pin", (p) -> factory != null ? factory.apply(p) : new CustomShearPinBlock(p))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.METAL).forceSolidOn())
                .transform(CCEStress.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate(CCEncasedBlockStateGens.shearPin(name))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .tag(CCEncasedTags.EBlockTags.SHEAR_PINS.tag)
                .item()
                .tag(CCEncasedTags.EItemTags.SHEAR_PINS.tag)
                .model((c, p) -> p.getBuilder(c.getName()).parent(Objects.requireNonNull(shearPinModel(p, name))))
                .build()
                .register();
    }

    private static <T extends Block> BlockBuilder<T, CreateRegistrate> connectedTexture(BlockBuilder<T, CreateRegistrate> entry, CTSpriteShiftEntry sprite, BiConsumer<T, CasingConnectivity> consumer) {
        if (sprite != null) {
            return entry.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(sprite)))
                    .onRegister(CreateRegistrate.casingConnectivity(consumer));
        }
        return entry;
    }


    public static void register() {

        CCEncased.KJS_HANDLER.dispatchRegisterEvent();

        CCasingSets.getSets().forEach(set -> {

            if (set.doesGenerateChainCogwheel())
                set.setChainCogwheel(createChainWheel(set.getName()));

            if (set.doesGenerateBrake())
                set.setBrake(createBrake(set.getName()));

            if (set.doesGenerateParallelGearbox())
                set.setParallelGearbox(createParallelGearbox(set.getName(), set.getConnectedTextureSprite(), set::getVerticalParallelGearboxItem));

            if (set.doesGenerateSixWayGearbox())
                set.setSixWayGearbox(createSixWayGearbox(set.getName(), set::getVerticalSixWayGearboxItem));

            if (set.doesGenerateInvertedGearShift())
                set.setInvertedGearShift(createInvertedGearShift(set.getName()));

            if (set.doesGenerateInvertedClutch())
                set.setInvertedClutch(createInvertedClutch(set.getName()));

            if (set.doesGenerateCentrifugalClutch())
                set.setCentrifugalClutch(createCentrifugalClutch(set.getName()));

            if (set.doesGenerateFreewheelClutch())
                set.setFreewheelClutch(createFreewheelClutch(set.getName()));

            if (set.doesGenerateOverstressClutch())
                set.setOverstressClutch(createOverstressClutch(set.getName()));

        });

        CTransmissionSets.getSets().forEach(set -> {
            if (set.doesGenerateShearPin())
                set.setShearPin(createShearPin(set.getName(), set.getShearPinConstructor()));
        });

        CFluidSets.getSets().forEach(set -> {
            if (set.doesGenerateFluidVessel()) {
                BlockEntityEntry<?>[] holder = new BlockEntityEntry<?>[1];
                BlockEntry<CustomFluidVesselBlock> tank = createFluidVessel(set.getName(), set.getTankSideSprite(), set.getTankTopSprite(), set.getTankInnerSprite(),
                        () -> (BlockEntityType<? extends FluidVesselBlockEntity>) holder[0].get());
                BlockEntityEntry<FluidVesselBlockEntity> be = CCEncasedBlockEntities.FLUID_VESSEL(set.getName(), tank);
                holder[0] = be;
                set.setFluidVessel(tank);
                set.setFluidVesselBlockEntity(be);
            }
        });
    }

    public static boolean isWoodenShaftHasState(BlockState state) {

        return state.getBlock() instanceof WoodenShearPinBlock;

    }
}
