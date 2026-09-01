package de.jp.techoverloaded_connect.register;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import de.jp.techoverloaded_connect.interfaces.TechOverloadConnectedRegistrate;


public class ModBlocks {

    private static final TechOverloadConnectedRegistrate REGISTRATE = TechOverloadedConnect.registrate();
    static {
        REGISTRATE.setCreativeTab(ModCreativTabs.MOD_CREATIV_TAB);
    }

    public static void register() {

    }
}
