package de.jp.techoverloaded_connect.register;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import de.jp.techoverloaded_connect.logistic.panel.JsPanelBehavior;
import net.liukrast.deployer.lib.registry.DeployerRegistries;
import net.liukrast.deployer.lib.logistics.board.PanelType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static de.jp.techoverloaded_connect.register.ModRegister.PANELS;

public class ModPanels {

    public ModPanels() {
    }

    public static final DeferredHolder<PanelType<?>, PanelType<JsPanelBehavior>> JS = PANELS.register("js", () -> new PanelType<>(JsPanelBehavior::new, JsPanelBehavior.class));

    public static void register(IEventBus bus) {
        PANELS.register(bus);
    }
}
