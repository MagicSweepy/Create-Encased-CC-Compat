package net.Rarin.create_connected_encased.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface CCEncasedKJSEvents {
    EventGroup GROUP = EventGroup.of("CCEncasedEvents");

    EventHandler REGISTER_SETS = GROUP.startup("registerSets",()-> RegisterSetsEvent.class);
}
