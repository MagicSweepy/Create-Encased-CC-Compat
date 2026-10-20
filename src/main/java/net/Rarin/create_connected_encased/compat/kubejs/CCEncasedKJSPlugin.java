package net.Rarin.create_connected_encased.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.generator.KubeAssetGenerator;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.Rarin.create_connected_encased.CCEncased;
import net.Rarin.create_connected_encased.compat.kubejs.wrappers.KJSCTTypeWrapper;
import net.Rarin.create_connected_encased.compat.kubejs.wrappers.KJSSpriteShiftWrapper;


public class CCEncasedKJSPlugin implements KubeJSPlugin {

    public CCEncasedKJSPlugin() {
        CCEncased.KJS_HANDLER = new KJSExternalHandlerImpl();
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(CCEncasedKJSEvents.GROUP);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("SpriteShifts", KJSSpriteShiftWrapper.class);
        bindings.add("CTType", KJSCTTypeWrapper.class);
    }

    @Override
    public void init() {
    }

    @Override
    public void generateAssets(KubeAssetGenerator generator) {

    }
}
