package net.Rarin.create_connected_encased.registries.data;

import com.simibubi.create.api.data.recipe.DeployingRecipeGen;

import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.registries.CCEncasedTags;
import net.Rarin.create_connected_encased.sets.SetBase;
import net.Rarin.create_connected_encased.sets.casings.CCasingSet;
import net.Rarin.create_connected_encased.sets.casings.CCasingSets;
import net.Rarin.create_connected_encased.sets.transmissions.CCETransmissionSet;
import net.Rarin.create_connected_encased.sets.transmissions.CCETransmissionSets;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CCEncasedRecipeGens extends DeployingRecipeGen {

    private final CompletableFuture<HolderLookup.Provider> registries;

    public CCEncasedRecipeGens(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CCEncased.ID);
        this.registries = registries;

        for (CCasingSet set : CCasingSets.getSets()) {
            addRecipes(set, CCEncasedTags.EItemTags.CHAIN_COGWHEELS, CCasingSet::getChainCogwheel);
            addRecipes(set, CCEncasedTags.EItemTags.BRAKES, CCasingSet::getBrake);
            addRecipes(set, CCEncasedTags.EItemTags.PARALLEL_GEARBOXS, CCasingSet::getParallelGearbox);
            addRecipes(set, CCEncasedTags.EItemTags.SIX_WAY_GEARBOXS, CCasingSet::getSixWayGearbox);
            addRecipes(set, CCEncasedTags.EItemTags.INVERTED_GEARSHIFTS, CCasingSet::getInvertedGearShift);
            addRecipes(set, CCEncasedTags.EItemTags.INVERTED_CLUTCHS, CCasingSet::getInvertedClutch);
            addRecipes(set, CCEncasedTags.EItemTags.CENTRIFUGAL_CLUTCHS, CCasingSet::getCentrifugalClutch);
            addRecipes(set, CCEncasedTags.EItemTags.FREEWHEEL_CLUTCHS, CCasingSet::getFreewheelClutch);
            addRecipes(set, CCEncasedTags.EItemTags.OVERSTRESS_CLUTCHS, CCasingSet::getOverstressClutch);
            addRecipes(set, CCEncasedTags.EItemTags.FLUID_VESSELS, CCasingSet::getFluidVessel);
        }

        for (CCETransmissionSet set : CCETransmissionSets.getSets()) {
            addRecipes(set, CCEncasedTags.EItemTags.SHEAR_PINS, CCETransmissionSet::getShearPin);

        }
    }

    private <T extends SetBase<?, ?>> void addRecipes(T set, CCEncasedTags.EItemTags category,
                                                      Function<T, ? extends ItemLike> resultGetter) {
        ItemLike result = resultGetter.apply(set);
        if (result == null || result.asItem() == Items.AIR)
            return;

        ResourceLocation recipeId = CCEncased.asResource(category.name().toLowerCase() + "/" + set.getName());
        Ingredient categoryIngredient = Ingredient.of(category.tag);
        TagKey<Item> categoryTag = category.tag;

        Optional<Ingredient> itemIngredient = getItemIngredient(set, recipeId.getPath());
        if (itemIngredient.isEmpty())
            return;

        register(recipeOutput -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                    .requires(categoryIngredient)
                    .requires(itemIngredient.get())
                    .unlockedBy("has_matching_block", inventoryTrigger(
                            ItemPredicate.Builder.item()
                                    .of(categoryTag)
                                    .build()))
                    .save(recipeOutput, recipeId.withPrefix("crafting/").withSuffix("_manual_only"));
        });

        register(recipeOutput -> {
            getBuilder(recipeId)
                    .require(categoryIngredient)
                    .require(itemIngredient.get())
                    .output(result)
                    .build(recipeOutput);
        });
    }

    private Optional<Ingredient> getItemIngredient(SetBase<?, ?> set, String recipePath) {
        HolderLookup.Provider provider = registries.join();
        Optional<Ingredient> ingredient = set.getItemIngredient(provider);
        if (ingredient.isEmpty())
            CCEncased.LOGGER.warn("Skipping recipes for set {} because its item source cannot be represented as a recipe ingredient", recipePath);
        return ingredient;
    }
}