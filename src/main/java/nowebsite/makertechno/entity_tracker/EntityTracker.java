package nowebsite.makertechno.entity_tracker;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import nowebsite.makertechno.entity_tracker.config.TConfig;
import nowebsite.makertechno.entity_tracker.datagen.ModDataGenerators;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(value = EntityTracker.MOD_ID, dist = Dist.CLIENT)
public class EntityTracker {
    public static final String MOD_ID = "entity_tracker";
    public static final Logger LOGGER = LoggerFactory.getLogger("TrackerLogic");

    public EntityTracker(@NotNull IEventBus modEventBus, @NotNull ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, TConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(ModDataGenerators::gatherData);
    }
}
