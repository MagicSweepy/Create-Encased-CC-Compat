package net.Rarin.create_connected_encased.compat.kubejs.wrappers;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import net.Rarin.create_connected_encased.CCEncased;
import net.createmod.catnip.render.SpriteShiftEntry;
import net.createmod.catnip.render.SpriteShifter;
import net.minecraft.resources.ResourceLocation;

public interface KJSSpriteShiftWrapper {

    static CTSpriteShiftEntry horizontal(ResourceLocation name) {
        return getCT(AllCTTypes.HORIZONTAL, name);
    }

    static CTSpriteShiftEntry vertical(ResourceLocation name) {
        return getCT(AllCTTypes.VERTICAL, name);
    }
    static CTSpriteShiftEntry omni(ResourceLocation name) {
        return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
    }

    static SpriteShiftEntry get(String originalLocation, String targetLocation) {
        return SpriteShifter.get(CCEncased.asResource(originalLocation), CCEncased.asResource(targetLocation));
    }

    static SpriteShiftEntry getFromCreate(String originalLocation, String targetLocation) {
        return SpriteShifter.get(Create.asResource(originalLocation), CCEncased.asResource(targetLocation));
    }
    static SpriteShiftEntry getFromCreate(String location) {
        return SpriteShifter.get(Create.asResource(location), CCEncased.asResource(location));
    }

    static CTSpriteShiftEntry getCT(CTType type, ResourceLocation blockTextureName, ResourceLocation connectedTextureName) {
        return CTSpriteShifter.getCT(type, blockTextureName.withPrefix("block/"), connectedTextureName.withPrefix("block/"));
    }

    static CTSpriteShiftEntry getCT(CTType type, ResourceLocation blockTextureName) {
        return getCT(type, blockTextureName, blockTextureName.withSuffix("_connected"));
    }

}
