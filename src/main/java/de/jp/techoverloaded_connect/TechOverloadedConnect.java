package de.jp.techoverloaded_connect;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import com.simibubi.create.AllTags;
import com.simibubi.create.api.data.datamaps.BlazeBurnerFuel;
import com.simibubi.create.api.registry.CreateDataMaps;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.Registrate;
import de.jp.techoverloaded_connect.interfaces.TechOverloadConnectedRegistrate;
import de.jp.techoverloaded_connect.register.ModRegister;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.slf4j.Logger;

@Mod(TechOverloadedConnect.MODID)
public class TechOverloadedConnect {
    public static final String MODID = "techoverloaded_connect";
    public static final Logger LOGGER = LogUtils.getLogger();


    private static final TechOverloadConnectedRegistrate REGISTRATE = TechOverloadConnectedRegistrate.create(MODID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public TechOverloadedConnect(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // RICHTIG (NeoForge Game-Bus)
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);

        REGISTRATE.registerEventListeners(modEventBus);
        ModRegister.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

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

        dispatcher.register(
                Commands.literal("techoverload")
                        .then(Commands.literal("test")
                                .then(Commands.literal("item")
                                        .then(Commands.literal("mainhand")
                                                // /techoverload test item mainhand tag
                                                .then(Commands.literal("tag")
                                                        .executes(context -> {
                                                            CommandSourceStack source = context.getSource();
                                                            ServerPlayer player = source.getPlayerOrException();
                                                            ItemStack stack = player.getMainHandItem();

                                                            if (stack.isEmpty()) {
                                                                source.sendFailure(Component.literal("You Hand Is Empty!"));
                                                                return 0;
                                                            }

                                                            source.sendSuccess(() -> Component.literal("--- Tags für Item ---"), false);
                                                            stack.getTags().forEach(tagKey ->
                                                                    source.sendSuccess(() -> Component.literal("- " + tagKey.location()), false)
                                                            );

                                                            return 1;
                                                        })
                                                )
                                                // /techoverload test item mainhand data_map
                                                .then(Commands.literal("data_map")
                                                        .executes(context -> {
                                                            CommandSourceStack source = context.getSource();
                                                            ServerPlayer player = source.getPlayerOrException();
                                                            ItemStack stack = player.getMainHandItem();

                                                            if (stack.isEmpty()) {
                                                                source.sendFailure(Component.literal("You Hand is Empty!"));
                                                                return 0;
                                                            }

                                                            source.sendSuccess(() -> Component.literal("--- DataMaps für Item ---"), false);

                                                            var holder = stack.getItem().builtInRegistryHolder();
                                                            boolean found = false;

                                                            // 1. Regular Blaze Burner Fuel prüfen
                                                            var regularFuel = holder.getData(CreateDataMaps.REGULAR_BLAZE_BURNER_FUELS);
                                                            if (regularFuel != null) {
                                                                source.sendSuccess(() -> Component.literal("- regular_blaze_burner_fuels -> burnTime: " + regularFuel.burnTime()), false);
                                                                found = true;
                                                            }

                                                            // 2. Superheated Blaze Burner Fuel prüfen (Das, was du für deine lava_bowl brauchst!)
                                                            var superheatedFuel = holder.getData(CreateDataMaps.SUPERHEATED_BLAZE_BURNER_FUELS);
                                                            if (superheatedFuel != null) {
                                                                source.sendSuccess(() -> Component.literal("- superheated_blaze_burner_fuels -> burnTime: " + superheatedFuel.burnTime()), false);
                                                                found = true;
                                                            }

                                                            if (!found) {
                                                                source.sendSuccess(() -> Component.literal("Keine Create-Blaze-Burner-DataMaps für dieses Item gefunden, jp!"), false);
                                                            }

                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                        )
        );
    }
    public static TechOverloadConnectedRegistrate registrate() {
        return REGISTRATE;
    }
}
