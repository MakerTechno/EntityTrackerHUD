package nowebsite.makertechno.terra_ethud.client.event;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import nowebsite.makertechno.terra_ethud.TerraETHUD;
import nowebsite.makertechno.terra_ethud.client.gui.TGui;
import nowebsite.makertechno.terra_ethud.client.gui.TGuiLayers;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = TerraETHUD.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class TModClient {
    @SubscribeEvent
    public static void regRenderer(@NotNull RegisterGuiLayersEvent event){
        event.registerBelow(VanillaGuiLayers.PLAYER_HEALTH, TGuiLayers.POINTER, new TGui(Minecraft.getInstance()));
    }
}
