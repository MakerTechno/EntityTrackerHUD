package nowebsite.makertechno.terra_ethud.client.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import nowebsite.makertechno.terra_ethud.TerraETHUD;
import nowebsite.makertechno.terra_ethud.client.track.EntityTracker;

@EventBusSubscriber(modid = TerraETHUD.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class TModClientGame {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        EntityTracker.trigger();
    }
}
