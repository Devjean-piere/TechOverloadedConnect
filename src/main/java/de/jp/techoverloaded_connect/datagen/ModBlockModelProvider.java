package de.jp.techoverloaded_connect.datagen;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import de.jp.techoverloaded_connect.register.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


import static net.liukrast.deployer.lib.helper.MinecraftHelpers.ModelProvider.Blocks.createPanel;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechOverloadedConnect.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        createPanel(this, ModItems.JS_GAUGE.asItem());
    }
}
