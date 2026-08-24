package de.jp.techoverloaded_connect.register;

import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativTabs {

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_CREATIV_TAB = ModRegister.TABS.register("mod_creativ_tab", ()->
            CreativeModeTab.
                    builder()
                    .icon(ModItems.LAVA_BOWL::asStack)
                    .build());
    public static void register(IEventBus iEventBus) {
        ModRegister.TABS.register(iEventBus);
    }
}
