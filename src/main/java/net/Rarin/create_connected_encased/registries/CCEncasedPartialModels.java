package net.Rarin.create_connected_encased.registries;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.Rarin.create_connected_encased.CCEncased;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class CCEncasedPartialModels {

    public static final List<PartialModel> ALL_ENCASED_MODELS = new ArrayList<>();
    public static final PartialModel

            MLDEG_SHEAR_PIN = block("shear_pin/mldeg");

    public static final Map<String, PartialModel> SHEAR_PIN_MODELS = new HashMap<>();

    static {
        String[] woods = new String[]{"oak", "birch", "acacia", "jungle", "warped", "dark_oak", "crimson", "mangrove", "cherry", "bamboo"};
        for (String w : woods) {
            SHEAR_PIN_MODELS.put(w, block("shear_pin/" + w));
        }

        SHEAR_PIN_MODELS.put("spruce", block("shear_pin/spruce"));
        SHEAR_PIN_MODELS.put("mldeg", MLDEG_SHEAR_PIN);

        for (String w : new String[]{"brass", "copper", "zinc", "andesite"}) {
            SHEAR_PIN_MODELS.put(w, block("shear_pin/" + w));
        }
    }

    public static PartialModel block(String path) {
        PartialModel model = PartialModel.of(CCEncased.asResource("block/" + path));
        if (ALL_ENCASED_MODELS != null) {
            ALL_ENCASED_MODELS.add(model);
        }
        return model;
    }

    public static PartialModel block(ResourceLocation path) {
        PartialModel model = PartialModel.of(path.withPrefix("block/"));
        if (ALL_ENCASED_MODELS != null) {
            ALL_ENCASED_MODELS.add(model);
        }
        return model;
    }

    public static void init() {
    }

}
