package nowebsite.makertechno.the_trackers.core.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import nowebsite.makertechno.the_trackers.TheTrackers;
import nowebsite.makertechno.the_trackers.client.gui.TGui;
import nowebsite.makertechno.the_trackers.core.config.TConfig;
import nowebsite.makertechno.the_trackers.core.track.TrackersMonitor;
import nowebsite.makertechno.the_trackers.core.track.WorldSingletonTracker;

@EventBusSubscriber(modid = TheTrackers.MOD_ID, value = Dist.CLIENT)
public class TModClientGame {
    private static WorldSingletonTracker tracker;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (TConfig.available) {
            if (tracker == null) {
                tracker = WorldSingletonTracker.getInstance();
                TGui.setExtendTracker(tracker);
            }
            TrackersMonitor.trigger(tracker);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        TGui.setExtendTracker(null);
        if (tracker != null) tracker.clean();
        tracker = null;
    }
}
