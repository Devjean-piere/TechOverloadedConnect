package de.jp.techoverloaded_connect.register;

import de.jp.techoverloaded_connect.TechOverloadedConnect;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import org.jetbrains.annotations.ApiStatus;

public class ModPartialModels {


    public static final PartialModel JS_GAUGE = block("js_gauge");
    private static PartialModel block(String path) {
        return PartialModel.of(TechOverloadedConnect.id("block/" + path));
    }

    @ApiStatus.Internal
    public static void init() {}
}
