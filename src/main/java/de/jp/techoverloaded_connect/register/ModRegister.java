package de.jp.techoverloaded_connect.register;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import net.liukrast.deployer.lib.logistics.board.PanelType;
import net.liukrast.deployer.lib.registry.DeployerRegistries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRegister {

    public static DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, TechOverloadedConnect.MODID);
    public static final DeferredRegister<PanelType<?>> PANELS = DeferredRegister.create(DeployerRegistries.PANEL, TechOverloadedConnect.MODID);
    public static void register(IEventBus iEventBus) {
        ModCreativTabs.register(iEventBus);
        ModPanels.register(iEventBus);
        ModBlocks.register();
        ModItems.register();
    }
}
