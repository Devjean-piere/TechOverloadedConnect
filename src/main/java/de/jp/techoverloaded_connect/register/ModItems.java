package de.jp.techoverloaded_connect.register;

import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.datamaps.BlazeBurnerFuel;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.tterrag.registrate.util.entry.ItemEntry;
import de.jp.techoverloaded_connect.TechOverloadedConnect;
import de.jp.techoverloaded_connect.interfaces.TechOverloadConnectedRegistrate;
import net.liukrast.deployer.lib.logistics.board.PanelBlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    private static final TechOverloadConnectedRegistrate REGISTRATE = TechOverloadedConnect.registrate();

    static {
        REGISTRATE.setCreativeTab(ModCreativTabs.MOD_CREATIV_TAB);
    }

    public static final ItemEntry<Item> LAVA_BOWL = REGISTRATE.item("lava_bowl", Item::new)
            .properties(p -> p.stacksTo(16))
            .tab(ModCreativTabs.MOD_CREATIV_TAB.getKey())
            .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
            .dataMap(CreateDataMaps.SUPERHEATED_BLAZE_BURNER_FUELS, new BlazeBurnerFuel(3200))
            .dataMap(CreateDataMaps.REGULAR_BLAZE_BURNER_FUELS, new BlazeBurnerFuel(3200))
            .burnTime(6400)
            .register();

    public static final ItemEntry<PanelBlockItem> JS_GAUGE = REGISTRATE.item("js_gauge", props -> new PanelBlockItem(ModPanels.JS::get, props))
            .tab(ModCreativTabs.MOD_CREATIV_TAB.getKey())
            .register();

    public static void register() {
    }
}
