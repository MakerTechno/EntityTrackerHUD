package nowebsite.makertechno.entity_tracker.core.event;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import nowebsite.makertechno.entity_tracker.client.gui.TGui;
import nowebsite.makertechno.entity_tracker.client.gui.TGuiStatics;
import nowebsite.makertechno.entity_tracker.core.config.TConfig;
import nowebsite.makertechno.entity_tracker.core.config.ConfigProcessor;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = EntityTracker.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TModClient {
    public static boolean isLoaded = false;
    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        isLoaded = true;
        TConfig.cursorWithEntities = ConfigProcessor.collectEntityBindCursor(TConfig.CENTER_RELATIVE_BIND.get());
    }
    @SubscribeEvent
    public static void regRenderer(@NotNull RegisterGuiLayersEvent event){
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, TGuiStatics.POINTER, new TGui(Minecraft.getInstance()));
    }
}
