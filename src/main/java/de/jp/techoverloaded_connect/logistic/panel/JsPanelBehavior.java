package de.jp.techoverloaded_connect.logistic.panel;

import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlock;
import com.simibubi.create.content.logistics.factoryBoard.FactoryPanelBlockEntity;
import de.jp.techoverloaded_connect.register.ModItems;
import de.jp.techoverloaded_connect.register.ModPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.liukrast.deployer.lib.logistics.board.AbstractPanelBehaviour;
import net.liukrast.deployer.lib.logistics.board.PanelType;
import net.liukrast.deployer.lib.logistics.board.connection.PanelConnectionBuilder;
import net.liukrast.deployer.lib.logistics.board.connection.StockConnection;
import net.liukrast.deployer.lib.registry.DeployerPanelConnections;
import net.minecraft.world.item.Item;

public class JsPanelBehavior extends AbstractPanelBehaviour {

    public JsPanelBehavior(PanelType<?> type, FactoryPanelBlockEntity be, FactoryPanelBlock.PanelSlot slot) {
        super(type, be, slot);
        this.output = output;
    }

    private float output;
    private boolean redstoneOutput;
    private String stringOutput = "";
    private StockConnection<?> stockOutput;

    @Override
    public void addConnections(PanelConnectionBuilder builder) {
        builder.registerBoth(DeployerPanelConnections.NUMBERS, () -> output);
        builder.registerBoth(DeployerPanelConnections.REDSTONE, () -> redstoneOutput);
        builder.registerBoth(DeployerPanelConnections.STRING, () -> stringOutput);
        builder.registerBoth(DeployerPanelConnections.STOCK_CONNECTION, () -> stockOutput);
    }

    @Override
    public Item getItem() {
        return ModItems.JS_GAUGE.asItem();
    }

    @Override
    public PartialModel getModel(FactoryPanelBlock.PanelState panelState, FactoryPanelBlock.PanelType panelType) {
        return ModPartialModels.JS_GAUGE;
    }


    @Override
    public BulbState getBulbState() {
        return BulbState.RED;
    }
}