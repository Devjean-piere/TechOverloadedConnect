package de.jp.techoverloaded_connect.interfaces;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.liukrast.deployer.lib.logistics.board.PanelBlockItem;
import org.jetbrains.annotations.Nullable;

import com.simibubi.create.api.registrate.CreateRegistrateRegistrationCallback;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

public class TechOverloadConnectedRegistrate extends AbstractRegistrate<TechOverloadConnectedRegistrate> {
    private static final Map<RegistryEntry<?, ?>, DeferredHolder<CreativeModeTab, CreativeModeTab>> TAB_LOOKUP = Collections.synchronizedMap(new IdentityHashMap<>());

    @Nullable
    protected Function<Item, TooltipModifier> currentTooltipModifierFactory;
    protected DeferredHolder<CreativeModeTab, CreativeModeTab> currentTab;

    protected TechOverloadConnectedRegistrate(String modid) {
        super(modid);
    }

    public static TechOverloadConnectedRegistrate create(String modid) {
        return new TechOverloadConnectedRegistrate(modid);
    }

    public static boolean isInCreativeTab(RegistryEntry<?, ?> entry, DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        return TAB_LOOKUP.get(entry) == tab;
    }

    public TechOverloadConnectedRegistrate setTooltipModifierFactory(@Nullable Function<Item, TooltipModifier> factory) {
        currentTooltipModifierFactory = factory;
        return self();
    }

    @Nullable
    public Function<Item, TooltipModifier> getTooltipModifierFactory() {
        return currentTooltipModifierFactory;
    }

    @Nullable
    public TechOverloadConnectedRegistrate setCreativeTab(DeferredHolder<CreativeModeTab, CreativeModeTab> tab) {
        currentTab = tab;
        return self();
    }

    public DeferredHolder<CreativeModeTab, CreativeModeTab> getCreativeTab() {
        return currentTab;
    }

    @Override
    public TechOverloadConnectedRegistrate registerEventListeners(IEventBus bus) {
        return super.registerEventListeners(bus);
    }

    @Override
    protected <R, T extends R> RegistryEntry<R, T> accept(String name, ResourceKey<? extends Registry<R>> type, Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator, NonNullFunction<DeferredHolder<R, T>, ? extends RegistryEntry<R, T>> entryFactory) {
        RegistryEntry<R, T> entry = super.accept(name, type, builder, creator, entryFactory);
        if (type.equals(Registries.ITEM) && currentTooltipModifierFactory != null) {
            Function<Item, TooltipModifier> factory = currentTooltipModifierFactory;
            this.addRegisterCallback(name, Registries.ITEM, item -> {
                TooltipModifier modifier = factory.apply(item);
                TooltipModifier.REGISTRY.register(item, modifier);
            });
        }
        if (currentTab != null)
            TAB_LOOKUP.put(entry, currentTab);

        return entry;
    }


}