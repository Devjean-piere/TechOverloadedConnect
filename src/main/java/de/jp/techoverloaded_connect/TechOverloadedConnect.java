package de.jp.techoverloaded_connect;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import de.jp.techoverloaded_connect.datagen.ModBlockModelProvider;
import de.jp.techoverloaded_connect.datagen.ModItemModelProvider;
import de.jp.techoverloaded_connect.interfaces.TechOverloadConnectedRegistrate;
import de.jp.techoverloaded_connect.register.ModPartialModels;
import de.jp.techoverloaded_connect.register.ModRegister;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;


@Mod(TechOverloadedConnect.MODID)
public class TechOverloadedConnect {
    public static final String MODID = "techoverloaded_connect";


    public static ResourceLocation id(String path, Object... args) {
        return ResourceLocation.fromNamespaceAndPath(TechOverloadedConnect.MODID, String.format(path, args));
    }
    public static final Logger LOGGER = LogUtils.getLogger();


    private static final TechOverloadConnectedRegistrate REGISTRATE = TechOverloadConnectedRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public TechOverloadedConnect(IEventBus modEventBus, ModContainer modContainer) {
        // Mod-Bus (Setup, Datagen etc.)
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::clientSetup);

        // NeoForge-Bus (Gameplay-Events)
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

        REGISTRATE.registerEventListeners(modEventBus);
        ModRegister.register(modEventBus);
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        var provider = event.getLookupProvider();
        generator.addProvider(event.includeClient(), new ModBlockModelProvider(packOutput, helper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, helper));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModPartialModels.init();
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {


        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }


    private void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();


    }
    public static TechOverloadConnectedRegistrate registrate() {
        return REGISTRATE;
    }
}
