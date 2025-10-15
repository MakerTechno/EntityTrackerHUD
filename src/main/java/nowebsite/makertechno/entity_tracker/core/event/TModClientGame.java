package nowebsite.makertechno.entity_tracker.core.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import nowebsite.makertechno.entity_tracker.EntityTracker;
import nowebsite.makertechno.entity_tracker.core.config.TConfig;
import nowebsite.makertechno.entity_tracker.core.track.TrackerLogic;

@EventBusSubscriber(modid = EntityTracker.MOD_ID, value = Dist.CLIENT)
public class TModClientGame {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (TConfig.available) TrackerLogic.trigger();
    }
}
