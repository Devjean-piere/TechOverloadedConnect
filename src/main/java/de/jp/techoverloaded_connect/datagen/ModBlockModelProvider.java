package de.jp.techoverloaded_connect.datagen;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TechOverloadedConnect.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
